package cz.upce.nnpro_backend.dtos;

import java.time.LocalDate;
import java.util.List;

public class CarOutDto {
    private Long id;
    private String manufacturer;
    private String type;
    private String SPZ;
    private String color;
    private double enginePower;
    private double emissionStandard;
    private double torque;
    private boolean isInDeposit = false;
    private boolean isStolen = false;
    private LocalDate yearOfCreation;
    private String vin;
    private List<OwnerDto> owners;
    private BranchOfficeDto branchOffice;

    public boolean isSigned() {
        return owners.stream().anyMatch(item -> item.getEndOfSignUp() == null);
    }

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSPZ() {
        return SPZ;
    }

    public void setSPZ(String SPZ) {
        this.SPZ = SPZ;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public boolean isInDeposit() {
        return isInDeposit;
    }

    public void setInDeposit(boolean inDeposit) {
        isInDeposit = inDeposit;
    }


    public LocalDate getYearOfCreation() {
        return yearOfCreation;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public double getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(double emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public void setYearOfCreation(LocalDate yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<OwnerDto> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerDto> owners) {
        this.owners = owners;
    }

    public BranchOfficeDto getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOfficeDto branchOffice) {
        this.branchOffice = branchOffice;
    }
}
