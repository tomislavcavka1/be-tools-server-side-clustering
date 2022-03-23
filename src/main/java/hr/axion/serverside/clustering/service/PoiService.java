package hr.axion.serverside.clustering.service;

import hr.axion.serverside.clustering.model.City;
import hr.axion.serverside.clustering.model.Country;
import hr.axion.serverside.clustering.model.Poi;
import hr.axion.serverside.clustering.model.PoiCluster;
import hr.axion.serverside.clustering.model.PoiParkingPriceStatistic;
import hr.axion.serverside.clustering.repository.CityRepository;
import hr.axion.serverside.clustering.repository.CountryRepository;
import hr.axion.serverside.clustering.repository.PoiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class PoiService {

    private static final String POI_PRICE_STDDEV_CACHE_NAME = "poi-price-stddev";

    private final PoiDataProvider poiDataProvider;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final PoiRepository poiRepository;
    private final ZoomToGeohashPrecisionConverter zoomToGeohashPrecisionConverter;

    @CacheEvict(POI_PRICE_STDDEV_CACHE_NAME)
    @Transactional
    public void reimportPoi() {
        log.info("Starting POI reimport...");
        long start = System.currentTimeMillis();

        poiRepository.deleteAll();
        log.info("Deleted all POI objects");

        var cities = cityRepository.findAll()
                .stream()
                .collect(toMap(City::getName, Function.identity()));

        var countries = countryRepository.findAll()
                .stream()
                .collect(toMap(Country::getName, Function.identity()));

        var poiCount = new LongAdder();
        poiDataProvider.getPoi((City newCity, Country newCountry, Poi newPoi) -> {
            City city = cities.computeIfAbsent(newCity.getName(), name -> cityRepository.save(newCity));
            newPoi.setCityId(city.getId());
            Country county = countries.computeIfAbsent(newCountry.getName(), name -> countryRepository.save(newCountry));
            newPoi.setCountry(county);
            poiRepository.save(newPoi);
            poiCount.increment();
        });

        long executionTime = System.currentTimeMillis() - start;
        log.info("Successfully imported {} POI objects in {} ms", poiCount.sum(), executionTime);
    }

    public List<PoiCluster> findPoiClustersWithinBounds(
            double southWestLat, double southWestLon, double northEastLat, double northEastLon,
            double zoom) {
        int precision = zoomToGeohashPrecisionConverter.toGeohashPrecision(zoom);
        return poiRepository.findPoiClustersWithinBounds(
                southWestLat, southWestLon, northEastLat, northEastLon, precision);
    }

    @Cacheable(POI_PRICE_STDDEV_CACHE_NAME)
    public PoiParkingPriceStatistic totalPoiParkingPriceStatistic() {
        return poiRepository.calculateTotalPoiParkingPriceStatistic();
    }
}