package ar.com.data.access.n_plus_one_demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Office {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String address;

    @OneToMany(mappedBy="office")
    private List<Employee> employeeList;

    public Office() {
    }

    public Office(Long id, String address) {
        this.id = id;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
