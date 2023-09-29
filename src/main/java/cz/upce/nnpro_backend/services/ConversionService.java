package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.*;
import cz.upce.nnpro_backend.dtos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConversionService {
    public static UserOutDto convertToUserDetailOutDto(User user) {
        UserOutDto userDto = new UserOutDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setJobPosition(user.getJobPosition());
        userDto.setRole(user.getRole());
        if (user.getBranchOffice() != null) {
            BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
            branchOfficeDto.setId(user.getBranchOffice().getId());
            branchOfficeDto.setRegion(user.getBranchOffice().getRegion());
            branchOfficeDto.setDistrict(user.getBranchOffice().getDistrict());
            userDto.setBranchOfficeDto(branchOfficeDto);
        }

        return userDto;
    }

    public static List<BranchOfficeDto> convertListToListOfficeDto(List<BranchOffice> old) {
        List<BranchOfficeDto> list = new ArrayList<>();
        for (BranchOffice office : old) {
            list.add(convertToOfficeDto(office));
        }
        return list;
    }

    public static BranchOfficeInDto convertToBranchOfficeInDto(BranchOfficeDto branchOfficeDto) {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setCity(branchOfficeDto.getCity());
        branchOfficeInDto.setRegion(branchOfficeDto.getRegion());
        branchOfficeInDto.setDistrict(branchOfficeDto.getDistrict());
        return branchOfficeInDto;
    }

    public static BranchOfficeDto convertToOfficeDto(BranchOffice old) {
        BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
        branchOfficeDto.setId(old.getId());
        branchOfficeDto.setCity(old.getCity());
        branchOfficeDto.setDistrict(old.getDistrict());
        branchOfficeDto.setRegion(old.getRegion());
        return branchOfficeDto;
    }

    public static Car convertToCar(CarInDto carInDto, String spz) {
        Car newCar = new Car();
        newCar.setVin(carInDto.getVin());
        newCar.setSPZ(spz);
        newCar.setColor(carInDto.getColor());
        newCar.setEnginePower(carInDto.getEnginePower());
        newCar.setInDeposit(carInDto.isInDeposit());
        newCar.setStolen(carInDto.isStolen());
        newCar.setManufacturer(carInDto.getManufacturer());
        newCar.setType(carInDto.getType());
        newCar.setEmissionStandard(carInDto.getEmissionStandard());
        newCar.setYearOfCreation(carInDto.getYearOfCreation());
        return newCar;
    }

    public static Car convertToCar(CarInDto carInDto, Car newCar) {
        newCar.setVin(carInDto.getVin());
        newCar.setColor(carInDto.getColor());
        newCar.setEnginePower(carInDto.getEnginePower());
        newCar.setInDeposit(carInDto.isInDeposit());
        newCar.setManufacturer(carInDto.getManufacturer());
        newCar.setStolen(carInDto.isStolen());

        newCar.setType(carInDto.getType());
        newCar.setEmissionStandard(carInDto.getEmissionStandard());
        newCar.setYearOfCreation(carInDto.getYearOfCreation());
        return newCar;
    }

    public static CarOutDto convertToCarDetailOutDto(Car car) {
        CarOutDto carOutDto = new CarOutDto();
        carOutDto.setId(car.getId());
        carOutDto.setVin(car.getVin());
        carOutDto.setSPZ(car.getSPZ());
        carOutDto.setColor(car.getColor());
        carOutDto.setEnginePower(car.getEnginePower());
        carOutDto.setTorque(car.getTorque());
        carOutDto.setInDeposit(car.isInDeposit());
        carOutDto.setStolen(car.isStolen());
        carOutDto.setManufacturer(car.getManufacturer());
        carOutDto.setType(car.getType());
        carOutDto.setEmissionStandard(car.getEmissionStandard());
        carOutDto.setYearOfCreation(car.getYearOfCreation());
        List<OwnerDto> owners = new ArrayList<>();
        for (CarOwner carOwner : car.getCarOwners().stream().toList()) {
            OwnerDto ownerDto = new OwnerDto();
            ownerDto.setFirstName(carOwner.getOwner().getFirstName());
            ownerDto.setLastName(carOwner.getOwner().getLastName());
            ownerDto.setCity(carOwner.getOwner().getCity());
            ownerDto.setStreet(carOwner.getOwner().getStreet());
            ownerDto.setEmail(carOwner.getOwner().getEmail());
            ownerDto.setNumberOfHouse(carOwner.getOwner().getNumberOfHouse());
            ownerDto.setZipCode(carOwner.getOwner().getZipCode());
            ownerDto.setBirthDate(carOwner.getOwner().getBirthDate());
            ownerDto.setId(carOwner.getOwner().getId());
            ownerDto.setStartOfSignUp(carOwner.getStartOfSignUp());
            ownerDto.setEndOfSignUp(carOwner.getEndOfSignUp());
            owners.add(ownerDto);
        }
        carOutDto.setOwners(owners);
        if (car.getBranchOffice() != null) {
            BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
            branchOfficeDto.setId(car.getBranchOffice().getId());
            branchOfficeDto.setDistrict(car.getBranchOffice().getDistrict());
            branchOfficeDto.setRegion(car.getBranchOffice().getRegion());
            carOutDto.setBranchOffice(branchOfficeDto);
        }

        return carOutDto;
    }

    public static Owner convertToOwner(OwnerInDto ownerInDto) {
        Owner newOwner = new Owner();
        newOwner.setFirstName(ownerInDto.getFirstName());
        newOwner.setLastName(ownerInDto.getLastName());
        newOwner.setBirthDate(ownerInDto.getBirthDate());
        newOwner.setEmail(ownerInDto.getEmail());
        newOwner.setCity(ownerInDto.getCity());
        newOwner.setStreet(ownerInDto.getStreet());
        newOwner.setZipCode(ownerInDto.getZipCode());
        newOwner.setNumberOfHouse(ownerInDto.getNumberOfHouse());
        return newOwner;
    }

    public static Owner convertToOwner(Owner newOwner, OwnerInDto ownerInDto) {

        newOwner.setFirstName(ownerInDto.getFirstName());
        newOwner.setLastName(ownerInDto.getLastName());
        newOwner.setBirthDate(ownerInDto.getBirthDate());
        newOwner.setCity(ownerInDto.getCity());
        newOwner.setStreet(ownerInDto.getStreet());
        newOwner.setZipCode(ownerInDto.getZipCode());
        newOwner.setNumberOfHouse(ownerInDto.getNumberOfHouse());
        return newOwner;
    }

    public static User convertToUser(UserInDto userInDto, Optional<Role> role) {
        User user = new User();
        user.setUsername(userInDto.getUsername());
        user.setEmail(userInDto.getEmail());
        user.setPassword(userInDto.getPassword());
        user.setJobPosition(userInDto.getJobPosition());
        role.ifPresent(user::setRole);
        return user;
    }

    public static User convertToUser(UserInDto userInDto, User user, Optional<Role> role) {
        user.setUsername(userInDto.getUsername());
        user.setEmail(userInDto.getEmail());
        user.setJobPosition(userInDto.getJobPosition());
        role.ifPresent(user::setRole);
        return user;
    }

//    public static OwnerDetailOutDto convertToOwnerDetailOutDto(Owner owner, Page<Car> carPage) {
//        OwnerDetailOutDto ownerDetailOutDto = new OwnerDetailOutDto();
//        ownerDetailOutDto.setId(owner.getId());
//        ownerDetailOutDto.setFirstName(owner.getFirstName());
//        ownerDetailOutDto.setLastName(owner.getLastName());
//        ownerDetailOutDto.setBirthDate(owner.getBirthDate());
//        ownerDetailOutDto.setCity(owner.getCity());
//        ownerDetailOutDto.setStreet(owner.getStreet());
//        ownerDetailOutDto.setZipCode(owner.getZipCode());
//        ownerDetailOutDto.setNumberOfHouse(owner.getNumberOfHouse());
//        ownerDetailOutDto.setCars(carPage);
//        return ownerDetailOutDto;
//    }

    public static OwnerOutDto convertToOwnerDetailOutDto(Owner owner) {
        OwnerOutDto ownerOutDto = new OwnerOutDto();
        ownerOutDto.setId(owner.getId());
        ownerOutDto.setFirstName(owner.getFirstName());
        ownerOutDto.setLastName(owner.getLastName());
        ownerOutDto.setBirthDate(owner.getBirthDate());
        ownerOutDto.setCity(owner.getCity());
        ownerOutDto.setStreet(owner.getStreet());
        ownerOutDto.setZipCode(owner.getZipCode());
        ownerOutDto.setNumberOfHouse(owner.getNumberOfHouse());
        List<CarDto> carDtos = new ArrayList<>();
        for (CarOwner carOwner : owner.getCarOwners().stream().toList()) {
            CarDto carDto = new CarDto();
            carDto.setId(carOwner.getCar().getId());
            carDto.setColor(carOwner.getCar().getColor());
            carDto.setEmissionStandard(carOwner.getCar().getEmissionStandard());
            carDto.setEnginePower(carOwner.getCar().getEnginePower());
            carDto.setManufacturer(carOwner.getCar().getManufacturer());
            carDto.setType(carOwner.getCar().getType());
            carDto.setSPZ(carOwner.getCar().getSPZ());
            carDto.setInDeposit(carOwner.getCar().isInDeposit());
            carDto.setVin(carOwner.getCar().getVin());
            carDto.setTorque(carOwner.getCar().getTorque());
            carDto.setYearOfCreation(carOwner.getCar().getYearOfCreation());

            carDto.setStartOfSignUp(carOwner.getStartOfSignUp());
            carDto.setEndOfSignUp(carOwner.getEndOfSignUp());

            carDtos.add(carDto);

        }
        ownerOutDto.setCars(carDtos);
        return ownerOutDto;
    }
}
