package hr.axion.serverside.clustering.service;

import hr.axion.serverside.clustering.model.City;
import hr.axion.serverside.clustering.model.Country;
import hr.axion.serverside.clustering.model.Poi;
import org.apache.logging.log4j.util.TriConsumer;

public interface PoiDataProvider {

    void getPoi(TriConsumer<City, Country, Poi> consumer);
}
