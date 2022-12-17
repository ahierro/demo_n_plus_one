package ar.com.data.access.n_plus_one_demo;

import ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto;
import ar.com.data.access.n_plus_one_demo.model.Employee;
import ar.com.data.access.n_plus_one_demo.model.EmployeeDTO;
import ar.com.data.access.n_plus_one_demo.model.EmployeeOfficeView;
import ar.com.data.access.n_plus_one_demo.model.Office;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeOfficeViewRepository;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeRepository;
import ar.com.data.access.n_plus_one_demo.repository.OfficeRepository;
import com.google.common.collect.Lists;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class NPlusOneDemoApplicationTests {

    @Autowired
    EntityManager em;

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeOfficeViewRepository employeeOfficeViewRepository;

    @Test
    void loadOffices() {
        List<Office> offices = officeRepository.findAll();
        assertNotNull(offices);
        assertEquals(2, offices.size());
        offices.forEach(office -> {
            assertNotNull(office.getEmployeeList());
            assertEquals(2, office.getEmployeeList().size());
        });
    }

    @Test
    void loadOfficesJoin() {
        Set<Office> offices = officeRepository.findAllJoined();
        assertNotNull(offices);
        assertEquals(2, offices.size());
        offices.forEach(office -> {
            assertNotNull(office.getEmployeeList());
            assertEquals(2, office.getEmployeeList().size());
        });
    }

    @Test
    void loadView() {
        List<EmployeeOfficeView> view = employeeOfficeViewRepository.findAll();
        assertNotNull(view);
        assertEquals(4, view.size());
    }

    @Test
    void employeeFindJoined() {
        List<EmployeeOfficeDto> view = employeeRepository.findJoined();
        assertNotNull(view);
        assertEquals(4, view.size());

        Map<UUID, List<EmployeeOfficeDto>> groupedByOfficeId = view.stream()
                .collect(groupingBy(EmployeeOfficeDto::getIdOffice));
        assertEquals(2, groupedByOfficeId.size());
    }

    @Test
    void employeeFindAll() {
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertEquals(4, employees.size());
//        employees.get(0).getOffice().getAddress();
    }
    @Test
    void employeeFindAllDTO() {
        List<EmployeeDTO> employees = employeeRepository.findAllDataMapping();
        assertNotNull(employees);
        assertEquals(4, employees.size());
//		employees.get(0).getOffice().getAddress();
    }

    @Test
    void employeeFindFirst() {
        List<Employee> employees = employeeRepository.findFirst2ByName("Brian");
        assertNotNull(employees);
        assertEquals(1, employees.size());
    }

    @Test
    void employeePaging() {
        Page<Employee> employees = employeeRepository.findAllByOffice(em.getReference(Office.class, UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6")),
                PageRequest.of(0, 4));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }

    @Test
    void employeePagingNative() {
        Page<Employee> employees = employeeRepository.findAllByNativeQuery(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"),
                PageRequest.of(0, 2));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }

    @Test
    void employeePagingJpql() {
        Page<Employee> employees = employeeRepository.findAllByJpqlQuery(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"),
                PageRequest.of(0, 2));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }
    @Test
    void testSort() {
        List<Employee> employees = employeeRepository
                .findAll(Sort.by("id").descending());
        assertNotNull(employees);
        assertEquals(4L, employees.size());
    }

    @Test
    void testSortJoined() {
        List<Employee> employees = employeeRepository
                .findAll(Sort.by("id").descending().and(Sort.by("office.address").ascending()));
        assertNotNull(employees);
        assertEquals(4L, employees.size());
    }

    @Test
    void testSortJoinedList() {
        List<Employee> employees = employeeRepository
                .findAll(Sort.by(Arrays.asList(
                        Sort.Order.asc("id"),
                        Sort.Order.desc("office.address")
                )));
        assertNotNull(employees);
        assertEquals(4L, employees.size());
    }

    @Test
    void testTypedSort() {
        List<Employee> employees = employeeRepository
                .findAll(Sort.sort(Employee.class)
                        .by(Employee::getId)
                        .ascending()
                        .and(Sort.sort(Employee.class)
                                .by((Employee employee) -> employee.getOffice().getAddress()).ascending())
                );
        assertNotNull(employees);
        assertEquals(4L, employees.size());
    }

    @Test
    void testInClauseBad() {
        List<UUID> ids = Arrays.asList(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"),UUID.fromString("b1b6a3ec-b20d-4bf2-9a52-0e1386cccc39"));

        List<Employee> employees = new LinkedList<>();
        for (UUID id : ids) {
            employees.addAll(employeeRepository.findAllByOffice(id));
        }
        assertEquals(4L, employees.size());

    }

    @Test
    void testInClauseGood() {
        List<UUID> ids = Arrays.asList(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"),UUID.fromString("b1b6a3ec-b20d-4bf2-9a52-0e1386cccc39"));

        List<Employee> employees = employeeRepository.findAllByOffices(ids);
        assertEquals(4L, employees.size());
    }

    @Test
    void testInClauseChunks() {
        List<UUID> ids = Arrays.asList(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"),UUID.fromString("b1b6a3ec-b20d-4bf2-9a52-0e1386cccc39"));
        List<List<UUID>> chunks = Lists.partition(ids,1000);
        List<Employee> employees = new LinkedList<>();
        for (List<UUID> chunk : chunks) {
            employees.addAll(employeeRepository.findAllByOffices(chunk));
        }
        assertEquals(4L, employees.size());
    }

    @Test
    void deleteAll() {
        List<Employee> employee = employeeRepository
                .findAll();
        employeeRepository.deleteAll(employee);
        em.flush();
    }
    @Test
    void deleteAllInBatch() {
        // ver spring.jpa.properties.hibernate.jdbc.batch_size
        List<Employee> employee = employeeRepository
                .findAll();
        employeeRepository.deleteAllInBatch(employee);
    }

    @Test
    void deleteAllByName() {
        employeeRepository.deleteAllByName("Brian");
        assertEquals(3,employeeRepository.findAll().size());
    }

    @Test
    void deleteByQuery() {
        employeeRepository.deleteByQuery("Brian");
        assertEquals(3,employeeRepository.findAll().size());
    }

    @Test
    void saveAllAndFlush() {
        List<Employee> employees = employeeRepository
                .findAll();
        employees.forEach(employee -> {
            employee.setName("NO");
        });
        employeeRepository.saveAll(employees);
        em.flush();
    }

    @Test
    void updateReference() {
        Employee brian = employeeRepository.getReferenceById(UUID.fromString("bfd3a8cc-f24f-46f4-bd90-0aef8a178da6"));
        brian.setOffice(em.getReference(Office.class, UUID.fromString("b1b6a3ec-b20d-4bf2-9a52-0e1386cccc39")));
        employeeRepository.save(brian);
        em.flush();
    }

    @Test
    void updateByQuery() {
        employeeRepository.updateByQuery("Brian");
    }

    @Test
    void insertEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Emma");
        employeeRepository.save(employee);
        em.flush();
    }

    @Test
    void persistEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Emma");
        em.persist(employee);
        em.flush();
    }

    @Test
    void findDynamicQuery() {
        Office o = new Office();
        o.setAddress("Office A");
        Employee employee = new Employee();
        employee.setName("Brian");
        employee.setOffice(o);
        Example<Employee> employeeExample = Example.of(employee);
        List<Employee> employees = employeeRepository.findAll(employeeExample);
        assertEquals(1,employees.size());
    }


}
