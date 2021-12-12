package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}