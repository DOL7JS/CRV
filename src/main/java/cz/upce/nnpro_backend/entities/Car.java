package cz.upce.nnpro_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Manufacturer is mandatory")
    private String manufacturer;
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotBlank(message = "SPZ is mandatory.")
    @Length(min = 8, max = 8, message = "SPZ has to have 8 symbols XXX XXXX")
    private String SPZ;
    @NotBlank(message = "Color is mandatory.")
    private String color;
    @NotNull(message = "Engine power is mandatory.")
    private double enginePower;
    @NotNull(message = "Emission standard is mandatory.")
    private double emissionStandard;

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

    @NotNull(message = "Torque is mandatory.")
    private double torque;

    private boolean isInDeposit = false;
    private boolean isStolen = false;
    @NotNull(message = "Year of creation is mandatory.")
    private LocalDate yearOfCreation;
    @NotBlank(message = "Vin is mandatory.")
    private String vin;
    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<CarOwner> carOwners;

    @ManyToOne
    @JoinColumn(name = "branchOffice_id")
    @JsonIgnore
    private BranchOffice branchOffice;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public Set<CarOwner> getCarOwners() {
        return carOwners;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    @JsonIgnore
//    private Owner owner;


    public void setCarOwners(Set<CarOwner> carOwners) {
        this.carOwners = carOwners;
    }

//    public Owner getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Owner owner) {
//        this.owner = owner;
//    }

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

    public void setEnginePower(double fuel) {
        this.enginePower = fuel;
    }

    public boolean isInDeposit() {
        return isInDeposit;
    }

    public void setInDeposit(boolean inDeposit) {
        isInDeposit = inDeposit;
    }

    public double getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(double weight) {
        this.emissionStandard = weight;
    }

    public LocalDate getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(LocalDate yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }
}
