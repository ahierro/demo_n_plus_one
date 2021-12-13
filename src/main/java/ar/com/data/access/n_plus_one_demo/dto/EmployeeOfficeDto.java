package ar.com.data.access.n_plus_one_demo.dto;

public class EmployeeOfficeDto {
    private Long idEmployee;
    private Long idOffice;
    private String nameEmployee;
    private String addressOffice;

    public EmployeeOfficeDto(Long idEmployee, Long idOffice, String nameEmployee, String addressOffice) {
        this.idEmployee = idEmployee;
        this.idOffice = idOffice;
        this.nameEmployee = nameEmployee;
        this.addressOffice = addressOffice;
    }

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
}
