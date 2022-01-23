package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.Office;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

   // @EntityGraph(attributePaths = {"employeeList"})
    List<Office> findAll();

    @Query("from Office o join fetch o.employeeList e")
//    @Query("from Office o join fetch Employee e on e.office.id =o.id ") this does not work
    Set<Office> findAllJoined();
}