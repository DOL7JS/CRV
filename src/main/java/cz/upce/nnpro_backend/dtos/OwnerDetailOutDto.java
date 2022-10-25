package cz.upce.nnpro_backend.dtos;

import cz.upce.nnpro_backend.Entities.Car;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public class OwnerDetailOutDto {
    Long id;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String city;
    String street;
    int zipCode;
    int numberOfHouse;
    List<CarInOwnerDto> cars;

    public List<CarInOwnerDto> getCars() {
        return cars;
    }

    public void setCars(List<CarInOwnerDto> cars) {
        this.cars = cars;
    }

    public OwnerDetailOutDto() {

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
