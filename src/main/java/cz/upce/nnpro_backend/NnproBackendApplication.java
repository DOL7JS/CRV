package cz.upce.nnpro_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/api")
public class NnproBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NnproBackendApplication.class, args);
    }

}
