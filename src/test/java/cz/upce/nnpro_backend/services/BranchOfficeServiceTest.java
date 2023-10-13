package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeInDto;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.repositories.BranchOfficeRepository;
import cz.upce.nnpro_backend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BranchOfficeServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BranchOfficeRepository branchOfficeRepository;


    @AfterEach
    @BeforeEach
    void clearData() {
        userRepository.deleteAll();
        branchOfficeRepository.deleteAll();
    }

    @Test
    void addOfficeTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setCity("City");
        branchOfficeInDto.setDistrict("District");

        BranchOffice office = branchOfficeService.addOffice(branchOfficeInDto);
        assertNotNull(office);
        assertEquals(branchOfficeInDto.getCity(), office.getCity());
        assertEquals(branchOfficeInDto.getRegion(), office.getRegion());
        assertEquals(branchOfficeInDto.getDistrict(), office.getDistrict());
    }

    @Test
    void editOfficeTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region2");
        branchOfficeInDto.setCity("City2");
        branchOfficeInDto.setDistrict("District2");

        BranchOffice saveOffice = branchOfficeService.addOffice(branchOfficeInDto);

        BranchOfficeInDto editBranchOfficeInDto = new BranchOfficeInDto();
        editBranchOfficeInDto.setRegion("Region3");
        editBranchOfficeInDto.setCity("City3");
        editBranchOfficeInDto.setDistrict("District3");

        BranchOffice branchOffice = branchOfficeService.editOffice(saveOffice.getId(), editBranchOfficeInDto);

        assertEquals(editBranchOfficeInDto.getRegion(), branchOffice.getRegion());
        assertEquals(editBranchOfficeInDto.getCity(), branchOffice.getCity());
        assertEquals(editBranchOfficeInDto.getDistrict(), branchOffice.getDistrict());
    }

    @Test
    void getOfficeTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region4");
        branchOfficeInDto.setCity("City4");
        branchOfficeInDto.setDistrict("District4");
        BranchOffice saveOffice = branchOfficeService.addOffice(branchOfficeInDto);

        BranchOfficeDto branchOffice = branchOfficeService.getOffice(saveOffice.getId());

        assertEquals(saveOffice.getId(), branchOffice.getId());
        assertEquals(saveOffice.getCity(), branchOffice.getCity());
        assertEquals(saveOffice.getDistrict(), branchOffice.getDistrict());
        assertEquals(saveOffice.getRegion(), branchOffice.getRegion());
    }


    @Test
    @Order(4)
    void getAllOfficesTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region2");
        branchOfficeInDto.setCity("City2");
        branchOfficeInDto.setDistrict("District2");


        BranchOfficeInDto branchOfficeInDto2 = new BranchOfficeInDto();
        branchOfficeInDto2.setRegion("Region3");
        branchOfficeInDto2.setCity("City3");
        branchOfficeInDto2.setDistrict("District3");
        BranchOffice saveOffice1 = branchOfficeService.addOffice(branchOfficeInDto);
        BranchOffice saveOffice2 = branchOfficeService.addOffice(branchOfficeInDto2);


        List<BranchOfficeDto> list = branchOfficeService.getAllOffices();

        assertEquals(2, list.size());

        assertEquals(saveOffice1.getId(), list.get(0).getId());
        assertEquals(saveOffice2.getId(), list.get(1).getId());
    }

    @Test
    @Order(6)
    void removeOfficeTest() {
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region2");
        branchOfficeInDto.setCity("City2");
        branchOfficeInDto.setDistrict("District2");
        BranchOffice saveOffice1 = branchOfficeService.addOffice(branchOfficeInDto);

        BranchOffice office = branchOfficeService.removeOffice(saveOffice1.getId());

        assertNotNull(office);
    }
}
