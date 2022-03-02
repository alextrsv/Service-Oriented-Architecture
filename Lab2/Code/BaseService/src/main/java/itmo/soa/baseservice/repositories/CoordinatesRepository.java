package itmo.soa.baseservice.repositories;

import itmo.soa.baseservice.entities.Coordinates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, Integer> {

    public Optional<Coordinates> findByXAndY(Double xCoord, Double yCoord);
}
