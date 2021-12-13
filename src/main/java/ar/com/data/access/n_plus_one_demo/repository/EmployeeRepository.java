package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select new ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto( e.id,  o.id,  e.name,  o.address) " +
            "from Employee e join Office o on e.office.id = o.id")
    <T> List<T> findJoined(Class<T> type);


}