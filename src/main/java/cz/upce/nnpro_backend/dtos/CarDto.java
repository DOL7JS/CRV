package cz.upce.nnpro_backend.dtos;

import java.time.LocalDate;

public class CarDto {

    private Long id;
    private String manufacturer;
    private String type;
    private String SPZ;
    private String color;
    private double enginePower;
    private double emissionStandard;
    private double torque;
    private boolean isInDeposit = false;
    private LocalDate yearOfCreation;
    private String vin;
    private LocalDate startOfSignUp;
    private LocalDate endOfSignUp;

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

    public boolean isInDeposit() {
        return isInDeposit;
    }

    public void setInDeposit(boolean inDeposit) {
        isInDeposit = inDeposit;
    }

    public LocalDate getYearOfCreation() {
        return yearOfCreation;
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

    public LocalDate getStartOfSignUp() {
        return startOfSignUp;
    }

    public void setStartOfSignUp(LocalDate startOfSignUp) {
        this.startOfSignUp = startOfSignUp;
    }

    public LocalDate getEndOfSignUp() {
        return endOfSignUp;
    }

    public void setEndOfSignUp(LocalDate endOfSignUp) {
        this.endOfSignUp = endOfSignUp;
    }
}
