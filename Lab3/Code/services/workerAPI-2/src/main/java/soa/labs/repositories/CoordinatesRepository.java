package soa.labs.repositories;

import soa.labs.entities.Coordinates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, Integer> {

    public Optional<Coordinates> findByXAndY(Double xCoord, Double yCoord);
}
