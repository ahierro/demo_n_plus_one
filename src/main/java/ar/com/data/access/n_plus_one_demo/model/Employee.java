package ar.com.data.access.n_plus_one_demo.model;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "findAllDataResultSetMapping",
        classes = @ConstructorResult(
                targetClass = EmployeeDTO.class,
                columns = {
                        @ColumnResult(name = "id",type = Long.class),
                        @ColumnResult(name = "name",type = String.class),
                }
        )
)
@NamedNativeQuery(name = "findAllDataMapping",
        resultClass = EmployeeDTO.class,
        resultSetMapping ="findAllDataResultSetMapping",
        query = "SELECT id,name FROM EMPLOYEE")
@Entity
public class Employee {
    @Id
    private Long id;
    private String name;
//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
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
