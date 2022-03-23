package hr.axion.serverside.clustering.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class PoiCluster {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lng")
    private double longitude;

    @JsonProperty("geohash")
    private String geohashPrefix;

    @JsonProperty("qnt")
    private long quantity;

    @JsonProperty("avgParkingPrice")
    private double averageParkingPrice;
}
