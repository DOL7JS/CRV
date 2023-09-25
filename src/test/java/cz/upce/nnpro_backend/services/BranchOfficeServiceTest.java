package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.dtos.BranchOfficeUserDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeInDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchOfficeServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Test
    @Order(1)
    void addOfficeTest(){
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region");
        branchOfficeInDto.setCity("City");
        branchOfficeInDto.setDistrict("District");

        BranchOffice office = branchOfficeService.addOffice(branchOfficeInDto);
        assertNotNull(office);
    }

    @Test
    @Order(2)
    void editOfficeTest(){
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
        assertEquals("Region3",branchOffice.getRegion());
    }

    @Test
    @Order(3)
    void getOfficeTest(){
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region4");
        branchOfficeInDto.setCity("City4");
        branchOfficeInDto.setDistrict("District4");

        BranchOffice saveOffice = branchOfficeService.addOffice(branchOfficeInDto);
        BranchOffice branchOffice = branchOfficeService.getOffice(saveOffice.getId());
        assertEquals(saveOffice.getId(), branchOffice.getId());
    }

    @Test
    @Order(5)
    void addUserToOfficeTest(){
        BranchOfficeInDto branchOfficeInDto = new BranchOfficeInDto();
        branchOfficeInDto.setRegion("Region6");
        branchOfficeInDto.setCity("City6");
        branchOfficeInDto.setDistrict("District6");

        BranchOffice saveOffice = branchOfficeService.addOffice(branchOfficeInDto);

        BranchOfficeUserDto dto = new BranchOfficeUserDto();
        dto.setBranchOfficeId(saveOffice.getId());
        dto.setUserId(1L);
        branchOfficeService.addUserToOffice(dto);
        assertEquals(saveOffice.getId(), userService.getUser(1L).getBranchOfficeDto().getId());
    }

    @Test
    @Order(4)
    void getAllOfficesTest(){
        List<BranchOffice> list = branchOfficeService.getAllOffices();
        assertEquals(3, list.size());
    }

    @Test
    @Order(6)
    void removeOfficeTest(){
        BranchOffice office = branchOfficeService.removeOffice(1L);
        assertNotNull(office);
    }
}
