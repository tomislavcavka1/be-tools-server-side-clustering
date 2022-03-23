package hr.axion.serverside.clustering.service;

import hr.axion.serverside.clustering.model.City;
import hr.axion.serverside.clustering.model.Coordinates;
import hr.axion.serverside.clustering.model.Country;
import hr.axion.serverside.clustering.model.Poi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class ZipFilePoiDataProvider implements PoiDataProvider {

    private final ZipFilePoiDataProviderProperties properties;

    public void getPoi(TriConsumer<City, Country, Poi> consumer) {
        log.info("Reading file {} from {} archive", properties.getEntryName(), properties.getLocation());

        try (var zipFile = new ZipFile(properties.getLocation())) {
            var zipEntry = zipFile.getEntry(properties.getEntryName());
            var inputStream = zipFile.getInputStream(zipEntry);
            try (var scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                scanner.useDelimiter("\\n");
                var count = 0;
                Random random = new Random();
                while (scanner.hasNext()) {
                    log.debug("Iteration >>>> " + count++);
                    var line = scanner.next();
                    var cells = line.split("\\|");
                    log.debug("Inserting values: {} {} {}", cells[0], cells[1], cells[2]);

                    var cityName = cells[0];
                    var city = new City(cityName);

                    var countryName = cells[3];
                    var country = new Country(countryName);

                    var latitude = Double.parseDouble(cells[1]);
                    var longitude = Double.parseDouble(cells[2]);
                    var price = random.nextInt(20001 - 60) + 60;
                    var poi = new Poi(new Coordinates(latitude, longitude), price);

                    consumer.accept(city, country, poi);
                }
            }
        } catch (IOException e) {
            log.error("Error occurred during storing new POI values", e);
        }
    }
}