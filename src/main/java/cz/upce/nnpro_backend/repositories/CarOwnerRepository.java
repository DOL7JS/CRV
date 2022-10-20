package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {

    void deleteByCarIdAndOwnerId(Long carId, Long ownerId);

    CarOwner findByCarIdAndOwnerId(Long carId, Long ownerId);

    boolean existsByCarAndEndOfSignUpIsNull(Car car);
}
