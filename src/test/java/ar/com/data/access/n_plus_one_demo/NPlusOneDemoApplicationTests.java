package ar.com.data.access.n_plus_one_demo;

import ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto;
import ar.com.data.access.n_plus_one_demo.model.Employee;
import ar.com.data.access.n_plus_one_demo.model.EmployeeOfficeView;
import ar.com.data.access.n_plus_one_demo.model.Office;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeOfficeViewRepository;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeRepository;
import ar.com.data.access.n_plus_one_demo.repository.OfficeRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.partition;
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
        List<EmployeeOfficeDto> view = employeeRepository.findJoined(EmployeeOfficeDto.class);
        assertNotNull(view);
        assertEquals(4, view.size());

        Map<Long, List<EmployeeOfficeDto>> groupedByOfficeId = view.stream()
                .collect(groupingBy(EmployeeOfficeDto::getIdOffice));
        assertEquals(2, groupedByOfficeId.size());
    }

    @Test
    void employeeFindAll() {
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertEquals(4, employees.size());
//		view.get(0).getOffice().getAddress();
    }

    @Test
    void employeePaging() {
        Page<Employee> employees = employeeRepository.findAllByOffice(em.getReference(Office.class, 1L),
                PageRequest.of(0, 4));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }

    @Test
    void employeePagingNative() {
        Page<Employee> employees = employeeRepository.findAllByNativeQuery(1L,
                PageRequest.of(0, 2));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }

    @Test
    void employeePagingJpql() {
        Page<Employee> employees = employeeRepository.findAllByJpqlQuery(1L,
                PageRequest.of(0, 2));
        assertNotNull(employees);
        assertEquals(2L, employees.getTotalElements());
        assertEquals(1, employees.getTotalPages());
        assertEquals(2, employees.getContent().size());
    }

    @Test
    void testInClauseBad() {
        List<Long> ids = Arrays.asList(1L,2L);

        List<Employee> employees = new LinkedList<>();
        for (Long id : ids) {
            employees.addAll(employeeRepository.findAllByOffice(id));
        }
        assertEquals(4L, employees.size());

    }

    @Test
    void testInClauseGood() {
        List<Long> ids = Arrays.asList(1L,2L);

        List<Employee> employees = employeeRepository.findAllByOffices(ids);
        assertEquals(4L, employees.size());
    }

    @Test
    void testInClauseChunks() {
        List<Long> ids = Arrays.asList(1L,2L);
        List<List<Long>> chunks = Lists.partition(ids,1000);
        List<Employee> employees = new LinkedList<>();
        for (List<Long> chunk : chunks) {
            employees.addAll(employeeRepository.findAllByOffices(chunk));
        }
        assertEquals(4L, employees.size());
    }

}
