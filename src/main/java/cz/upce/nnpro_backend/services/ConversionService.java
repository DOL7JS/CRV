package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.Entities.Role;
import cz.upce.nnpro_backend.Entities.User;
import cz.upce.nnpro_backend.dtos.*;

import java.util.Optional;

public class ConversionService {
    public static UserDetailOutDto convertToUserDetailOutDto(User user) {
        UserDetailOutDto userDto = new UserDetailOutDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setJobPosition(user.getJobPosition());
        userDto.setRole(user.getRole());
        BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
        branchOfficeDto.setId(user.getBranchOffice().getId());
        branchOfficeDto.setRegion(user.getBranchOffice().getRegion());
        branchOfficeDto.setDistrict(user.getBranchOffice().getDistrict());
        userDto.setBranchOfficeDto(branchOfficeDto);
        return userDto;
    }

    public static Car convertToCar(CreateCarDto createCarDto) {
        Car newCar = new Car();
        newCar.setVin(createCarDto.getVin());
        newCar.setSPZ(createCarDto.getSpz());
        newCar.setColor(createCarDto.getColor());
        newCar.setFuel(createCarDto.getFuel());
        newCar.setInDeposit(createCarDto.isInDeposit());
        newCar.setManufacturer(createCarDto.getManufacturer());
        newCar.setType(createCarDto.getType());
        newCar.setWeight(createCarDto.getWeight());
        newCar.setYearOfCreation(createCarDto.getYearOfCreation());
        return newCar;
    }

    public static Car convertToCar(CreateCarDto createCarDto, Car newCar) {
        newCar.setVin(createCarDto.getVin());
        newCar.setSPZ(createCarDto.getSpz());
        newCar.setColor(createCarDto.getColor());
        newCar.setFuel(createCarDto.getFuel());
        newCar.setInDeposit(createCarDto.isInDeposit());
        newCar.setManufacturer(createCarDto.getManufacturer());
        newCar.setType(createCarDto.getType());
        newCar.setWeight(createCarDto.getWeight());
        newCar.setYearOfCreation(createCarDto.getYearOfCreation());
        return newCar;
    }

    public static CarDetailOutDto convertToCarDetailOutDto(Car car) {
        CarDetailOutDto carDetailOutDto = new CarDetailOutDto();

        carDetailOutDto.setId(car.getId());
        carDetailOutDto.setVin(car.getVin());
        carDetailOutDto.setSPZ(car.getSPZ());
        carDetailOutDto.setColor(car.getColor());
        carDetailOutDto.setFuel(car.getFuel());
        carDetailOutDto.setInDeposit(car.isInDeposit());
        carDetailOutDto.setManufacturer(car.getManufacturer());
        carDetailOutDto.setType(car.getType());
        carDetailOutDto.setWeight(car.getWeight());
        carDetailOutDto.setYearOfCreation(car.getYearOfCreation());
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setFirstName(car.getOwner().getFirstName());
        ownerDto.setLastName(car.getOwner().getLastName());
        ownerDto.setCity(car.getOwner().getCity());
        ownerDto.setStreet(car.getOwner().getStreet());
        ownerDto.setNumberOfHouse(car.getOwner().getNumberOfHouse());
        ownerDto.setZipCode(car.getOwner().getZipCode());
        ownerDto.setBirthDate(car.getOwner().getBirthDate());
        ownerDto.setId(car.getOwner().getId());
        carDetailOutDto.setOwner(ownerDto);
        BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
        branchOfficeDto.setId(car.getBranchOffice().getId());
        branchOfficeDto.setDistrict(car.getBranchOffice().getDistrict());
        branchOfficeDto.setRegion(car.getBranchOffice().getRegion());
        carDetailOutDto.setBranchOffice(branchOfficeDto);
        return carDetailOutDto;
    }

    public static Owner convertToOwner(OwnerDto ownerDto) {
        Owner newOwner = new Owner();
        newOwner.setFirstName(ownerDto.getFirstName());
        newOwner.setLastName(ownerDto.getLastName());
        newOwner.setBirthDate(ownerDto.getBirthDate());
        newOwner.setCity(ownerDto.getCity());
        newOwner.setStreet(ownerDto.getStreet());
        newOwner.setZipCode(ownerDto.getZipCode());
        newOwner.setNumberOfHouse(ownerDto.getNumberOfHouse());
        return newOwner;
    }

    public static Owner convertToOwner(Owner newOwner, OwnerDto ownerDto) {

        newOwner.setFirstName(ownerDto.getFirstName());
        newOwner.setLastName(ownerDto.getLastName());
        newOwner.setBirthDate(ownerDto.getBirthDate());
        newOwner.setCity(ownerDto.getCity());
        newOwner.setStreet(ownerDto.getStreet());
        newOwner.setZipCode(ownerDto.getZipCode());
        newOwner.setNumberOfHouse(ownerDto.getNumberOfHouse());
        return newOwner;
    }

    public static User convertToUser(UserDto userDto, Optional<Role> role) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setJobPosition(userDto.getJobPosition());
        role.ifPresent(user::setRole);
        return user;
    }

    public static User convertToUser(UserDto userDto, User user, Optional<Role> role) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setJobPosition(userDto.getJobPosition());
        role.ifPresent(user::setRole);
        return user;
    }
}
