package cz.upce.nnpro_backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.entities.User;
import cz.upce.nnpro_backend.dtos.BranchOfficeUserDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeInDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.CarRepository;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BranchOfficeService {
    private final BranchOfficeRepository branchOfficeRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    public BranchOfficeService(BranchOfficeRepository branchOfficeRepository, UserRepository userRepository, CarRepository carRepository, OwnerRepository ownerRepository) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    public BranchOffice addOffice(BranchOfficeInDto branchOfficeDto) {
        if (branchOfficeRepository.existsByRegionAndDistrictAndCity(branchOfficeDto.getRegion(), branchOfficeDto.getDistrict(), branchOfficeDto.getCity())) {
            throw new IllegalArgumentException("The branch office already exists.");
        }
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setRegion(branchOfficeDto.getRegion());
        branchOffice.setDistrict(branchOfficeDto.getDistrict());
        branchOffice.setCity(branchOfficeDto.getCity());
        return branchOfficeRepository.save(branchOffice);
    }

    public BranchOffice removeOffice(Long officeId) {
        BranchOffice branchOffice = branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        userRepository.setUserOfficeToNUllByOffice(branchOffice);
        carRepository.setCarOfficeToNullByOffice(branchOffice);
        branchOfficeRepository.deleteById(officeId);
        return branchOffice;
    }

    public BranchOffice editOffice(Long officeId, BranchOfficeInDto officeDto) {

        BranchOffice branchOffice = branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        if (branchOfficeRepository.existsByRegionAndDistrictAndCityAndIdIsNot(officeDto.getRegion(), officeDto.getDistrict(), officeDto.getCity(), officeId)) {
            throw new IllegalArgumentException("The branch office already exists.");
        }
        branchOffice.setRegion(officeDto.getRegion());
        branchOffice.setDistrict(officeDto.getDistrict());
        branchOffice.setCity(officeDto.getCity());
        BranchOffice save = branchOfficeRepository.save(branchOffice);
        return save;
    }

    public BranchOfficeDto getOffice(Long officeId) {
        return ConversionService.convertToOfficeDto(branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!")));
        //return branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
    }

    public UserOutDto addUserToOffice(BranchOfficeUserDto branchOfficeUserDto) {

        User user = userRepository.findById(branchOfficeUserDto.getUserId()).orElseThrow(() -> new NoSuchElementException("User not found!"));
        user.setBranchOffice(branchOfficeRepository.findById(branchOfficeUserDto.getBranchOfficeId()).orElseThrow(() -> new NoSuchElementException("Branch office not found!")));
        User save = userRepository.save(user);
        return ConversionService.convertToUserDetailOutDto(save);
    }

    public String exportData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<Car> cars = carRepository.findAll();
        List<Owner> owners = ownerRepository.findAll();
        String jsonCars = mapper.writeValueAsString(cars);
        String jsonOwners = mapper.writeValueAsString(owners);

        return "{ \"cars\": " + jsonCars + ",\"owners\":" + jsonOwners + "}";
    }

    public void importData(List<Car> cars, List<Owner> owners) {
        cars.stream().filter(car -> !carRepository.existsByVin(car.getVin()) && !carRepository.existsBySPZ(car.getSPZ())).forEach(car -> car.setId(null));
        owners.forEach(owner -> owner.setId(null));
        carRepository.saveAll(cars);
        ownerRepository.saveAll(owners);
    }

    public List<BranchOfficeDto> getAllOffices() {
        return ConversionService.convertListToListOfficeDto(branchOfficeRepository.findAll());
    }

    public List<BranchOfficeDto> getOfficesByRegion(BranchOffice branchOffice) {
        return ConversionService.convertListToListOfficeDto(branchOfficeRepository.findByRegion(branchOffice.getRegion()));
    }
}
