package ar.com.data.access.n_plus_one_demo.model;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;

    public Employee() {
    }

    public Employee(Long id, String name, Office office) {
        this.id = id;
        this.name = name;
        this.office = office;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
