package ar.com.data.access.n_plus_one_demo.model;

import java.util.UUID;

public class EmployeeDTO {
    private UUID id;
    private String name;

    public EmployeeDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
