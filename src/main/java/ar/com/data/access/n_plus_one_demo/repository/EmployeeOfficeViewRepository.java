package ar.com.data.access.n_plus_one_demo.repository;

import ar.com.data.access.n_plus_one_demo.model.EmployeeOfficeView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeOfficeViewRepository extends JpaRepository<EmployeeOfficeView,
        EmployeeOfficeView.EmployeeOfficeViewId> {
}
