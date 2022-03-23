package hr.axion.serverside.clustering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class PoiParkingPriceStatistic {

    @JsonProperty("min")
    private final double minimum;

    @JsonProperty("max")
    private final double maximum;

    @JsonProperty("avg")
    private final double mean;

    @JsonProperty("sd")
    private final double standardDeviation;
}
