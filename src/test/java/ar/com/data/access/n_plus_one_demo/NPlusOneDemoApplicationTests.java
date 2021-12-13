package ar.com.data.access.n_plus_one_demo;

import ar.com.data.access.n_plus_one_demo.dto.EmployeeOfficeDto;
import ar.com.data.access.n_plus_one_demo.model.EmployeeOfficeView;
import ar.com.data.access.n_plus_one_demo.model.Office;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeOfficeViewRepository;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeRepository;
import ar.com.data.access.n_plus_one_demo.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class NPlusOneDemoApplicationTests {

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
		assertEquals(2,offices.size());
		offices.forEach(office -> {
			assertNotNull(office.getEmployeeList());
			assertEquals(2,office.getEmployeeList().size());
		});
	}

	@Test
	void loadOfficesJoin() {
		Set<Office> offices = officeRepository.findAllJoined();
		assertNotNull(offices);
		assertEquals(2,offices.size());
		offices.forEach(office -> {
			assertNotNull(office.getEmployeeList());
			assertEquals(2,office.getEmployeeList().size());
		});
	}

	@Test
	void loadView() {
		List<EmployeeOfficeView> view= employeeOfficeViewRepository.findAll();
		assertNotNull(view);
		assertEquals(4,view.size());
	}

	@Test
	void employeeRepository() {
		List<EmployeeOfficeDto> view = employeeRepository.findJoined(EmployeeOfficeDto.class);
		assertNotNull(view);
		assertEquals(4,view.size());

		Map<Long,List<EmployeeOfficeDto>> groupedByOfficeId = view.stream()
				.collect(groupingBy(EmployeeOfficeDto::getIdOffice));
		assertEquals(2,groupedByOfficeId.size());

	}
}
