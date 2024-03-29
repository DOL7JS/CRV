package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //List<Car> getAllByCarOwners(CarOwner carOwner);

    boolean existsBySPZ(String spz);

    boolean existsByVin(String vin);

    boolean existsByVinAndIdIsNot(String vin, Long id);

    Car findFirstBySPZNotNullOrderBySPZAsc();


    @Transactional
    @Modifying
    @Query("update Car c set c.branchOffice = null  where c.branchOffice = ?1")
    void setCarOfficeToNullByOffice(BranchOffice branchOffice);

    @Transactional
    @Modifying
    @Query("update Car c set c.SPZ = null where c = ?1")
    void setSPZNullByCar(Car car);

    Optional<Car> findByVin(String vin);

    Optional<Car> findBySPZ(String spz);


}
