package hr.axion.serverside.clustering.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.axion.serverside.clustering.model.PoiCluster;
import hr.axion.serverside.clustering.model.PoiParkingPriceStatistic;
import lombok.Value;

import java.util.List;

@Value
public class PoiClusterResponse {

    @JsonProperty("stats")
    private final PoiParkingPriceStatistic parkingPriceStatistic;

    private final List<PoiCluster> poi;
}
