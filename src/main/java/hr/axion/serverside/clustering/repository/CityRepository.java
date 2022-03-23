package hr.axion.serverside.clustering.repository;

import hr.axion.serverside.clustering.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findAll();
}
