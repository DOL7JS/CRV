package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.SPZ;
import cz.upce.nnpro_backend.repositories.CarRepository;
import cz.upce.nnpro_backend.repositories.SPZRepository;
import org.springframework.stereotype.Service;

@Service
public class SPZService {
    private final SPZRepository spzRepository;
    private final CarRepository carRepository;

    public SPZService(SPZRepository spzRepository, CarRepository carRepository) {
        this.spzRepository = spzRepository;
        this.carRepository = carRepository;
    }

    public SPZ generateSPZ() throws Exception {
        SPZ spz;
        if (spzRepository.count() > 0) {
            spz = spzRepository.findTopByOrderBySPZAsc();
            spzRepository.delete(spz);
        } else {
            Car car = carRepository.findFirstByOrderBySPZAsc();
            if (car == null) {
                spz = new SPZ("1E1 1111");
            } else {
                spz = new SPZ(addValueOfSPZ(car.getSPZ()));
            }
        }
        if (!spzRepository.existsBySPZ(addValueOfSPZ(spz.getSPZ()))) {
            spzRepository.save(new SPZ(addValueOfSPZ(spz.getSPZ())));
        }
        while (carRepository.existsBySPZ(spz.getSPZ())) {
            spz = spzRepository.findTopByOrderBySPZAsc();
            spzRepository.delete(spz);
            if (!spzRepository.existsBySPZ(addValueOfSPZ(spz.getSPZ()))) {
                spzRepository.save(new SPZ(addValueOfSPZ(spz.getSPZ())));
            }
        }
        return spz;

    }

    private String addValueOfSPZ(String spz) throws Exception {
        if (validateSPZ(spz)) {
            throw new Exception("Error in validating spz.");
        }
        String number = spz.substring(4, 8);
        if (!number.equals("9999")) {
            String s = spz.substring(0, 3) + " " + (Integer.parseInt(number) + 1);
            return s;
        }
        number = "1111";
        if (spz.charAt(2) != '9') {
            String s = spz.substring(0, 2) + (Integer.parseInt(String.valueOf(spz.charAt(2))) + 1) + " " + Integer.parseInt(number);
            return s;
        }
        String s = (Integer.parseInt(String.valueOf(spz.charAt(0))) + 1) + "E1 " + Integer.parseInt(number);
        return s;
    }

    private boolean validateSPZ(String spz) {
        if (!Character.isAlphabetic(spz.charAt(0))) {
            return false;
        } else if (!Character.isDigit(spz.charAt(1))) {
            return false;
        } else if (!Character.isAlphabetic(spz.charAt(2))) {
            return false;
        } else if (spz.charAt(3) != ' ') {
            return false;
        } else return Character.isDigit(spz.charAt(4)) && Character.isDigit(spz.charAt(5))
                && Character.isDigit(spz.charAt(6)) && Character.isDigit(spz.charAt(7));
    }
}
