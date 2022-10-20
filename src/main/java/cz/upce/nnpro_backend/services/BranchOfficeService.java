package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.BranchOffice;
import cz.upce.nnpro_backend.Entities.User;
import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeIdUserIdDto;
import cz.upce.nnpro_backend.dtos.UserDetailOutDto;
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

    public BranchOffice addOffice(BranchOfficeDto branchOfficeDto) {
        if (branchOfficeRepository.existsByRegionAndDistrictAndCity(branchOfficeDto.getRegion(), branchOfficeDto.getDistrict(), branchOfficeDto.getCity())) {
            throw new IllegalArgumentException("The branch office already exists.");
        }
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setRegion(branchOfficeDto.getRegion());
        branchOffice.setDistrict(branchOfficeDto.getDistrict());
        branchOffice.setCity(branchOfficeDto.getCity());
        BranchOffice save = branchOfficeRepository.save(branchOffice);
        return save;
    }

    public BranchOffice removeOffice(Long officeId) {
        BranchOffice branchOffice = branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        userRepository.setUserOfficeToNUllByOffice(branchOffice);
        carRepository.setCarOfficeToNullByOffice(branchOffice);
        branchOfficeRepository.deleteById(officeId);
        return branchOffice;
    }

    public BranchOffice editOffice(Long officeId, BranchOfficeDto officeDto) {

        BranchOffice branchOffice = branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        if (branchOfficeRepository.existsByRegionAndDistrictAndCityAndIdIsNot(officeDto.getRegion(), officeDto.getDistrict(), officeDto.getCity(), officeId)) {
            throw new IllegalArgumentException("The branch office already exists.");
        }
        branchOffice.setRegion(officeDto.getRegion());
        branchOffice.setDistrict(officeDto.getDistrict());
        BranchOffice save = branchOfficeRepository.save(branchOffice);
        return save;
    }

    public BranchOffice getOffice(Long officeId) {
        BranchOffice branchOffice = branchOfficeRepository.findById(officeId).orElseThrow(() -> new NoSuchElementException("Branch office not found!"));
        return branchOffice;
    }

    public UserDetailOutDto addUserToOffice(BranchOfficeIdUserIdDto branchOfficeIdUserIdDto) {

        User user = userRepository.findById(branchOfficeIdUserIdDto.getUserId()).orElseThrow(() -> new NoSuchElementException("User not found!"));
        user.setBranchOffice(branchOfficeRepository.findById(branchOfficeIdUserIdDto.getBranchOfficeId()).orElseThrow(() -> new NoSuchElementException("Branch office not found!")));
        User save = userRepository.save(user);
        UserDetailOutDto detailOutDto = ConversionService.convertToUserDetailOutDto(save);
        return detailOutDto;
    }

    public List<BranchOffice> getOffices() {
        List<BranchOffice> branchOffices = branchOfficeRepository.findAll();
        return branchOffices;
    }
}
