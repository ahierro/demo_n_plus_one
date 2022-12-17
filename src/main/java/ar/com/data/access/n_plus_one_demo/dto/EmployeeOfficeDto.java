package ar.com.data.access.n_plus_one_demo.dto;

import java.util.UUID;

public class EmployeeOfficeDto {
    private UUID idEmployee;
    private UUID idOffice;
    private String nameEmployee;
    private String addressOffice;

    public EmployeeOfficeDto(UUID idEmployee, UUID idOffice, String nameEmployee, String addressOffice) {
        this.idEmployee = idEmployee;
        this.idOffice = idOffice;
        this.nameEmployee = nameEmployee;
        this.addressOffice = addressOffice;
    }

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
