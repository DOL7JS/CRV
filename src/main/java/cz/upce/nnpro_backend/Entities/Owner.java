package cz.upce.nnpro_backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is mandatory.")
    private String firstName;
    @NotBlank(message = "Last name is mandatory.")
    private String lastName;
    @NotNull(message = "Birth date is mandatory.")
    private LocalDate birthDate;
    @NotBlank(message = "City is mandatory.")
    private String city;
    @NotBlank(message = "Street is mandatory.")
    private String street;
    @NotNull(message = "Number of house is mandatory.")
    private int numberOfHouse;
    @NotNull(message = "Zip code is mandatory.")
    private int zipCode;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<CarOwner> carOwners;


    public Long getId() {
        return id;
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

    public int getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(int numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Set<CarOwner> getCarOwners() {
        return carOwners;
    }

    public void setCarOwners(Set<CarOwner> cars) {
        this.carOwners = cars;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
