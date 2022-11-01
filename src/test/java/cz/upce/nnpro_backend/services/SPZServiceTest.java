package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.SPZ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SPZServiceTest {

    @Autowired
    private SPZService spzService;

    @Test
    void generateSPZTest() throws Exception {
        SPZ spz = spzService.generateSPZ();
        assertNotNull(spz);
    }

}
