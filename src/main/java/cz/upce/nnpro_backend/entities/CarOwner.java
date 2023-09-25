package cz.upce.nnpro_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class CarOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonIgnore
    private Car car;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;
    @NotNull(message = "Start of sign up is mandatory.")
    private LocalDate startOfSignUp;
    private LocalDate endOfSignUp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarOwner() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
