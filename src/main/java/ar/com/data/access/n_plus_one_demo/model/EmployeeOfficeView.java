package ar.com.data.access.n_plus_one_demo.model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

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
        private Long idEmployee;
        private Long idOffice;

        public Long getIdEmployee() {
            return idEmployee;
        }

        public void setIdEmployee(Long idEmployee) {
            this.idEmployee = idEmployee;
        }

        public Long getIdOffice() {
            return idOffice;
        }

        public void setIdOffice(Long idOffice) {
            this.idOffice = idOffice;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EmployeeOfficeViewId that = (EmployeeOfficeViewId) o;

            if (idEmployee != null ? !idEmployee.equals(that.idEmployee) : that.idEmployee != null) return false;
            return idOffice != null ? idOffice.equals(that.idOffice) : that.idOffice == null;
        }

        @Override
        public int hashCode() {
            int result = idEmployee != null ? idEmployee.hashCode() : 0;
            result = 31 * result + (idOffice != null ? idOffice.hashCode() : 0);
            return result;
        }
    }
}
