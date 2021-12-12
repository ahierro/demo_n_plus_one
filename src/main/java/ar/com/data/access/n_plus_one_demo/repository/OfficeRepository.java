package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends CrudRepository<Office, Long> {
}