package soa.labs.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import soa.labs.entities.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
