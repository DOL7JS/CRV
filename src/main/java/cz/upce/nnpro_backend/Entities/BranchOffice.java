package cz.upce.nnpro_backend.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank(message = "District is mandatory")
    private String district;
    @NotBlank(message = "Region is mandatory")
    private String region;
    @NotBlank(message = "City is mandatory")
    private String city;

    @OneToMany(mappedBy = "branchOffice", cascade = CascadeType.REMOVE)
    private Set<User> users;

    @OneToMany(mappedBy = "branchOffice", cascade = CascadeType.REMOVE)
    private Set<Car> cars;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
