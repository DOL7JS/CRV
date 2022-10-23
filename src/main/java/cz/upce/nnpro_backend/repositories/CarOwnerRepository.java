package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.CarOwner;
import cz.upce.nnpro_backend.Entities.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {

    CarOwner findByCarIdAndOwnerId(Long carId, Long ownerId);

    CarOwner findByCarIdAndEndOfSignUpIsNull(Long carId);

    boolean existsByCarAndEndOfSignUpIsNull(Car car);

    @Query("select co.car from CarOwner co, Car c where co.car = c AND co.owner = ?1")
    Page<Car> findCarOwnerByOwner(Owner owner, Pageable pageable);

    @Query("select co.owner from CarOwner co, Car c where co.car = c AND co.car = ?1 ")
    Page<Owner> findOwnerByCar(Car car, Pageable pageable);
}
