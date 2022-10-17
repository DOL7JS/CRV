package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> getAllByOwner(Owner owner);

    boolean existsBySPZ(String spz);

    Car findFirstByOrderBySPZAsc();
}
