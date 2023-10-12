package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeInDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeUserDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.User;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.CarRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BranchOfficeService {
    private final BranchOfficeRepository branchOfficeRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public BranchOfficeService(BranchOfficeRepository branchOfficeRepository, UserRepository userRepository, CarRepository carRepository) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
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
        return branchOfficeRepository.save(branchOffice);
    }

    public BranchOfficeDto getOffice(Long officeId) {
        return ConversionService.convertToOfficeDto(branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!")));
    }

    public List<BranchOfficeDto> getAllOffices() {
        return ConversionService.convertListToListOfficeDto(branchOfficeRepository.findAll());
    }

    public List<BranchOfficeDto> getOfficesByRegion(BranchOffice branchOffice) {
        return ConversionService.convertListToListOfficeDto(branchOfficeRepository.findByRegion(branchOffice.getRegion()));
    }

    public List<BranchOfficeDto> getOfficesByRegion(String region) {
        return ConversionService.convertListToListOfficeDto(branchOfficeRepository.findByRegion(region));
    }
}
