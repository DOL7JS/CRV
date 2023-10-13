package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.dtos.OwnerOutDto;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerInDto;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    @BeforeEach
    void clearData() {
        ownerRepository.deleteAll();
    }

    @Test
    void addOwnerTest() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");

        Owner saveOwner = ownerService.addOwner(ownerInDto);
        Optional<Owner> getOwner = ownerRepository.findById(saveOwner.getId());

        assertNotNull(getOwner);
        assertEquals(saveOwner.getId(), getOwner.get().getId());
        assertEquals(saveOwner.getEmail(), getOwner.get().getEmail());
        assertEquals(saveOwner.getCity(), getOwner.get().getCity());
        assertEquals(saveOwner.getFirstName(), getOwner.get().getFirstName());
        assertEquals(saveOwner.getLastName(), getOwner.get().getLastName());

    }

    @Test
    void editOwnerTest() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");

        Owner saveOwner = ownerService.addOwner(ownerInDto);

        OwnerInDto ownerInDto2 = new OwnerInDto();
        ownerInDto2.setNumberOfHouse(1);
        ownerInDto2.setStreet("Street");
        ownerInDto2.setZipCode(12345);
        ownerInDto2.setCity("City");
        ownerInDto2.setBirthDate(LocalDate.now());
        ownerInDto2.setFirstName("FirstName_Change");
        ownerInDto2.setLastName("LastName");
        ownerInDto2.setEmail("email@email.com");

        Owner editOwner = ownerService.editOwner(saveOwner.getId(), ownerInDto2);

        assertNotNull(editOwner);
        assertEquals(editOwner.getFirstName(), "FirstName_Change");
    }

    @Test
    void getOwnerTest() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");
        Owner owner = ownerService.addOwner(ownerInDto);

        OwnerOutDto dto = ownerService.getOwner(owner.getId());

        assertNotNull(dto);
        assertEquals(owner.getId(), dto.getId());
    }

    @Test
    void getAllOwnersTest() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");

        OwnerInDto ownerInDto2 = new OwnerInDto();
        ownerInDto2.setNumberOfHouse(1);
        ownerInDto2.setStreet("Street");
        ownerInDto2.setZipCode(12345);
        ownerInDto2.setCity("City");
        ownerInDto2.setBirthDate(LocalDate.now());
        ownerInDto2.setFirstName("FirstName_Change");
        ownerInDto2.setLastName("LastName");
        ownerInDto2.setEmail("email@email.com");
        Owner saveOwner = ownerService.addOwner(ownerInDto);
        Owner saveOwner2 = ownerService.addOwner(ownerInDto2);

        List<OwnerOutDto> owners = ownerService.getAllOwners();

        assertEquals(2, owners.size());
        assertEquals(saveOwner.getId(), owners.get(0).getId());
        assertEquals(saveOwner2.getId(), owners.get(1).getId());

    }

    @Test
    void removeOwnerTest() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setNumberOfHouse(1);
        ownerInDto.setStreet("Street");
        ownerInDto.setZipCode(12345);
        ownerInDto.setCity("City");
        ownerInDto.setBirthDate(LocalDate.now());
        ownerInDto.setFirstName("FirstName_Change");
        ownerInDto.setLastName("LastName");
        ownerInDto.setEmail("email@email.com");
        Owner saveOwner = ownerService.addOwner(ownerInDto);

        Owner owner = ownerService.removeOwner(saveOwner.getId());
        
        assertEquals(saveOwner.getId(), owner.getId());

    }

}
