package hr.axion.serverside.clustering.repository;

import hr.axion.serverside.clustering.model.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAll();

    Optional<Country> findById(Integer countyId);
}
