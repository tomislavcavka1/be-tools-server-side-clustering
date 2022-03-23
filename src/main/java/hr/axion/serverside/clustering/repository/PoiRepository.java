package hr.axion.serverside.clustering.repository;

import hr.axion.serverside.clustering.model.Poi;
import hr.axion.serverside.clustering.model.PoiCluster;
import hr.axion.serverside.clustering.model.PoiParkingPriceStatistic;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PoiRepository extends CrudRepository<Poi, Long> {

    @Query("SELECT"
            + " AVG(LATITUDE) latitude,"
            + " AVG(LONGITUDE) longitude,"
            + " SUBSTRING(GEOHASH FROM 1 FOR :precision) geohash_prefix,"
            + " COUNT(*) quantity,"
            + " AVG(PARKING_PRICE) average_parking_price"
            + " FROM POI P"
            + " WHERE LATITUDE BETWEEN :southWestLat AND :northEastLat"
            + " AND LONGITUDE BETWEEN :southWestLon AND :northEastLon"
            + " GROUP BY geohash_prefix")
    List<PoiCluster> findPoiClustersWithinBounds(
            @Param("southWestLat") double southWestLat,
            @Param("southWestLon") double southWestLon,
            @Param("northEastLat") double northEastLat,
            @Param("northEastLon") double northEastLon,
            @Param("precision") int precision);

    @Query("SELECT"
            + " MIN(PARKING_PRICE) minimum,"
            + " MAX(PARKING_PRICE) maximum,"
            + " AVG(PARKING_PRICE) mean,"
            + " STDDEV_POP(PARKING_PRICE) standard_deviation"
            + " FROM POI")
    PoiParkingPriceStatistic calculateTotalPoiParkingPriceStatistic();
}
