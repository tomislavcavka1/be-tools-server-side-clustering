package hr.axion.serverside.clustering.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
public class Poi {

    public static final int GEOHASH_PRECISION = 10;

    @Id
    @Column("poi_id")
    private Long id;

    private double latitude;

    private double longitude;

    private String geohash;

    @Column("parking_price")
    private double parkingPrice;

    private Integer cityId;

    @Column("country_id")
    private Country country;

    public Poi(Coordinates coordinates, double parkingPrice) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
        this.geohash = coordinates.toGeohash(GEOHASH_PRECISION);
        this.parkingPrice = parkingPrice;
    }
}
