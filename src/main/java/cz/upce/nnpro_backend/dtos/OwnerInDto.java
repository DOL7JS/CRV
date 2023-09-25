package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class OwnerInDto {
    @NotBlank(message = "First name is mandatory.")
    String firstName;
    @NotBlank(message = "Last name is mandatory.")
    String lastName;
    @NotNull(message = "Birth date is mandatory.")
    LocalDate birthDate;
    @NotBlank(message = "City is mandatory.")
    String city;
    @NotBlank(message = "Street is mandatory.")
    String street;
    @NotNull(message = "Zip code is mandatory.")
    int zipCode;
    @NotNull(message = "Number of house is mandatory.")
    int numberOfHouse;

    public OwnerInDto() {

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(int numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
    }
}
