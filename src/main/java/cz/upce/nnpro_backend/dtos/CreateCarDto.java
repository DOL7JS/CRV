package cz.upce.nnpro_backend.dtos;

import java.time.LocalDate;
import java.util.Date;

public class CreateCarDto {

    String vin;
    String spz;
    String color;
    String fuel;
    String manufacturer;
    String type;
    LocalDate yearOfCreation;
    boolean isInDeposit;
    double weight;

    public CreateCarDto(String vin, String spz, String color, String fuel, String manufacturer, String type, LocalDate yearOfCreation, boolean isInDeposit, double weight) {

        this.vin = vin;
        this.spz = spz;
        this.color = color;
        this.fuel = fuel;
        this.manufacturer = manufacturer;
        this.type = type;
        this.yearOfCreation = yearOfCreation;
        this.isInDeposit = isInDeposit;
        this.weight = weight;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


}
