package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CarInDto {
    @NotBlank(message = "Vin is mandatory.")
    String vin;
    @NotBlank(message = "Color is mandatory.")
    String color;
    @NotBlank(message = "Manufacturer is mandatory.")
    String manufacturer;
    @NotBlank(message = "Type is mandatory.")
    String type;


    @NotNull(message = "Year of creation is mandatory.")
    LocalDate yearOfCreation;
    boolean isInDeposit = false;
    boolean isStolen = false;
    @NotNull(message = "Engine power is mandatory.")
    private double enginePower;
    @NotNull(message = "Emission standard is mandatory.")
    private double emissionStandard;
    @NotNull(message = "Torque is mandatory.")
    private double torque;

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public LocalDate getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(LocalDate yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public boolean isInDeposit() {
        return isInDeposit;
    }

    public void setInDeposit(boolean inDeposit) {
        isInDeposit = inDeposit;
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
}
