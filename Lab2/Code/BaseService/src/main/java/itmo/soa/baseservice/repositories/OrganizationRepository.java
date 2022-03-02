package itmo.soa.baseservice.repositories;

import itmo.soa.baseservice.entities.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
