package ar.com.data.access.n_plus_one_demo.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Office {
    @Id
    private UUID id;
    private String address;

    @OneToMany(mappedBy="office")
   // @BatchSize(size = 2)
    private List<Employee> employeeList;

    public Office() {
    }

    public Office(UUID id, String address) {
        this.id = id;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
