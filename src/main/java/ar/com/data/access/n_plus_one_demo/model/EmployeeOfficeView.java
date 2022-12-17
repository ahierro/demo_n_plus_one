package ar.com.data.access.n_plus_one_demo.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class EmployeeOfficeView {
    @EmbeddedId
    private EmployeeOfficeViewId id;
    private String nameEmployee;
    private String addressOffice;

    public EmployeeOfficeViewId getId() {
        return id;
    }

    public void setId(EmployeeOfficeViewId id) {
        this.id = id;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getAddressOffice() {
        return addressOffice;
    }

    public void setAddressOffice(String addressOffice) {
        this.addressOffice = addressOffice;
    }

    @Embeddable
    public static class EmployeeOfficeViewId implements Serializable {
        private UUID idEmployee;
        private UUID idOffice;

        public UUID getIdEmployee() {
            return idEmployee;
        }

        public void setIdEmployee(UUID idEmployee) {
            this.idEmployee = idEmployee;
        }

        public UUID getIdOffice() {
            return idOffice;
        }

        public void setIdOffice(UUID idOffice) {
            this.idOffice = idOffice;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployeeOfficeViewId that = (EmployeeOfficeViewId) o;
            return idEmployee.equals(that.idEmployee) && idOffice.equals(that.idOffice);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idEmployee, idOffice);
        }
    }
}
