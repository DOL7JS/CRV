package cz.upce.nnpro_backend.dtos;

import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;

import java.util.List;

public class CarsOwnersDto {
    List<Car> cars;
    List<Owner> owners;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
