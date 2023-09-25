package cz.upce.nnpro_backend.dtos;

import java.time.LocalDate;

public class OwnerDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    LocalDate birthDate;
    String city;
    String street;
    int zipCode;
    int numberOfHouse;
    LocalDate startOfSignUp;
    LocalDate endOfSignUp;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public OwnerDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
