package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.Employee;
import ar.com.data.access.n_plus_one_demo.model.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select new ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto( e.id,  o.id,  e.name,  o.address) " +
            "from Employee e join Office o on e.office.id = o.id")
    <T> List<T> findJoined(Class<T> type);

    Page<Employee> findAllByOffice(Office o, Pageable pageable);

    @Query(value = "SELECT * FROM EMPLOYEE WHERE OFFICE_ID = :officeId",
            countQuery =  "SELECT COUNT(*) FROM EMPLOYEE WHERE OFFICE_ID = :officeId",
            nativeQuery = true)
    Page<Employee> findAllByNativeQuery(@Param("officeId") Long officeId, Pageable pageable);

}