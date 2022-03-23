package hr.axion.serverside.clustering.controller;

import hr.axion.serverside.clustering.service.PoiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/poi")
public class PoiController {

    private final PoiService poiService;

    @GetMapping
    public PoiClusterResponse findPoiClustersWithinBounds(
            @RequestParam("sw_lat") double southWestLat,
            @RequestParam("sw_lng") double southWestLon,
            @RequestParam("ne_lat") double northEastLat,
            @RequestParam("ne_lng") double northEastLon,
            @RequestParam("zoom") double zoom) {
        log.info("Starting with loading of the POI clusters within bounds [southWestLat={}, southWestLon={}, " +
                "northEastLat={}, northEastLon={}, zoom={}]", southWestLat, southWestLon, northEastLat, northEastLon, zoom);
        long start = System.currentTimeMillis();
        var poi = poiService.findPoiClustersWithinBounds(
                southWestLat, southWestLon, northEastLat, northEastLon, zoom);
        var totalPoiParkingPriceStatistic = poiService.totalPoiParkingPriceStatistic();
        long executionTime = System.currentTimeMillis() - start;
        log.info("Successfully loaded {} POI clusters in {} ms", poi.size(), executionTime);
        return new PoiClusterResponse(totalPoiParkingPriceStatistic, poi);
    }
}
