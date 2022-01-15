package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto;
import ar.com.data.access.n_plus_one_demo.model.Employee;
import ar.com.data.access.n_plus_one_demo.model.EmployeeDTO;
import ar.com.data.access.n_plus_one_demo.model.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select new ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto( e.id,  o.id,  e.name,  o.address) " +
            "from Employee e join Office o on e.office.id = o.id")
    List<EmployeeOfficeDto> findJoined();

    @Query(name = "findAllDataMapping",nativeQuery = true)
    List<EmployeeDTO> findAllDataMapping();

    Employee findFirstByName(String name);
    List<Employee> findFirst2ByName(String name);

    Page<Employee> findAllByOffice(Office o, Pageable pageable);

    @Query(value = "SELECT * FROM EMPLOYEE WHERE OFFICE_ID = :officeId",
            countQuery =  "SELECT COUNT(*) FROM EMPLOYEE WHERE OFFICE_ID = :officeId",
            nativeQuery = true)
    Page<Employee> findAllByNativeQuery(@Param("officeId") Long officeId, Pageable pageable);

    @Query(value = "from Employee where office.id = :officeId",
            countQuery =  "select count(e) from Employee e where e.office.id  = :officeId")
    Page<Employee> findAllByJpqlQuery(@Param("officeId") Long officeId, Pageable pageable);


    @Query(value = "from Employee where office.id IN (:officeIds)")
    List<Employee> findAllByOffices(@Param("officeIds") List<Long> officeIds);

    @Query(value = "from Employee where office.id = :officeId")
    List<Employee> findAllByOffice(@Param("officeId")Long officeId);

    List<Employee> findAll(Sort sort);

    int deleteAllByName(String name);

    @Modifying
    @Query("delete Employee e where e.name = :name")
    int deleteByQuery(String name);

    @Modifying
    @Query("update Employee e set e.name = 'NO' where e.name = :name")
    void updateByQuery(String name);


}