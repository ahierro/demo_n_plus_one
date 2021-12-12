package ar.com.data.access.n_plus_one_demo;

import ar.com.data.access.n_plus_one_demo.model.Office;
import ar.com.data.access.n_plus_one_demo.repository.EmployeeRepository;
import ar.com.data.access.n_plus_one_demo.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class NPlusOneDemoApplicationTests {

	@Autowired
	OfficeRepository officeRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	void loadOffices() {
		Iterable<Office> offices = officeRepository.findAll();
		assertNotNull(offices);
		offices.forEach(office -> {
			assertNotNull(office.getEmployeeList());
			assertEquals(2,office.getEmployeeList().size());
		});
	}

}
