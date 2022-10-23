package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.*;
import cz.upce.nnpro_backend.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConversionService {
    public static UserDetailOutDto convertToUserDetailOutDto(User user) {
        UserDetailOutDto userDto = new UserDetailOutDto();
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

    public static Car convertToCar(CreateCarDto createCarDto,String spz) {
        Car newCar = new Car();
        newCar.setVin(createCarDto.getVin());
        newCar.setSPZ(spz);
        newCar.setColor(createCarDto.getColor());
        newCar.setEnginePower(createCarDto.getEnginePower());
        newCar.setInDeposit(createCarDto.isInDeposit());
        newCar.setManufacturer(createCarDto.getManufacturer());
        newCar.setType(createCarDto.getType());
        newCar.setEmissionStandard(createCarDto.getEmissionStandard());
        newCar.setYearOfCreation(createCarDto.getYearOfCreation());
        return newCar;
    }

    public static Car convertToCar(CreateCarDto createCarDto, Car newCar) {
        newCar.setVin(createCarDto.getVin());
        newCar.setColor(createCarDto.getColor());
        newCar.setEnginePower(createCarDto.getEnginePower());
        newCar.setInDeposit(createCarDto.isInDeposit());
        newCar.setManufacturer(createCarDto.getManufacturer());
        newCar.setType(createCarDto.getType());
        newCar.setEmissionStandard(createCarDto.getEmissionStandard());
        newCar.setYearOfCreation(createCarDto.getYearOfCreation());
        return newCar;
    }

    public static CarDetailOutDto convertToCarDetailOutDto(Car car) {
        CarDetailOutDto carDetailOutDto = new CarDetailOutDto();
        carDetailOutDto.setId(car.getId());
        carDetailOutDto.setVin(car.getVin());
        carDetailOutDto.setSPZ(car.getSPZ());
        carDetailOutDto.setColor(car.getColor());
        carDetailOutDto.setEnginePower(car.getEnginePower());
        carDetailOutDto.setTorque(car.getTorque());
        carDetailOutDto.setInDeposit(car.isInDeposit());
        carDetailOutDto.setManufacturer(car.getManufacturer());
        carDetailOutDto.setType(car.getType());
        carDetailOutDto.setEmissionStandard(car.getEmissionStandard());
        carDetailOutDto.setYearOfCreation(car.getYearOfCreation());
        List<OwnerInCarDto> owners = new ArrayList<>();
        for (CarOwner carOwner : car.getCarOwners().stream().toList()) {
            OwnerInCarDto ownerDto = new OwnerInCarDto();
            ownerDto.setFirstName(carOwner.getOwner().getFirstName());
            ownerDto.setLastName(carOwner.getOwner().getLastName());
            ownerDto.setCity(carOwner.getOwner().getCity());
            ownerDto.setStreet(carOwner.getOwner().getStreet());
            ownerDto.setNumberOfHouse(carOwner.getOwner().getNumberOfHouse());
            ownerDto.setZipCode(carOwner.getOwner().getZipCode());
            ownerDto.setBirthDate(carOwner.getOwner().getBirthDate());
            ownerDto.setId(carOwner.getOwner().getId());
            ownerDto.setStartOfSignUp(carOwner.getStartOfSignUp());
            ownerDto.setEndOfSignUp(carOwner.getEndOfSignUp());
            owners.add(ownerDto);
        }
        carDetailOutDto.setOwners(owners);
        if (car.getBranchOffice() != null) {
            BranchOfficeDto branchOfficeDto = new BranchOfficeDto();
            branchOfficeDto.setId(car.getBranchOffice().getId());
            branchOfficeDto.setDistrict(car.getBranchOffice().getDistrict());
            branchOfficeDto.setRegion(car.getBranchOffice().getRegion());
            carDetailOutDto.setBranchOffice(branchOfficeDto);
        }

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

    public static OwnerDetailOutDto convertToOwnerDetailOutDto(Owner owner, Page<Car> carPage) {
        OwnerDetailOutDto ownerDetailOutDto = new OwnerDetailOutDto();
        ownerDetailOutDto.setId(owner.getId());
        ownerDetailOutDto.setFirstName(owner.getFirstName());
        ownerDetailOutDto.setLastName(owner.getLastName());
        ownerDetailOutDto.setBirthDate(owner.getBirthDate());
        ownerDetailOutDto.setCity(owner.getCity());
        ownerDetailOutDto.setStreet(owner.getStreet());
        ownerDetailOutDto.setZipCode(owner.getZipCode());
        ownerDetailOutDto.setNumberOfHouse(owner.getNumberOfHouse());
        ownerDetailOutDto.setCars(carPage);
        return ownerDetailOutDto;
    }
}
