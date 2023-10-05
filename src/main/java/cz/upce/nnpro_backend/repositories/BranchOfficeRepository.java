package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.entities.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long> {
    boolean existsByRegionAndDistrictAndCity(String region, String district, String city);

    boolean existsByRegionAndDistrictAndCityAndIdIsNot(String region, String district, String city, Long id);

    List<BranchOffice> findByRegion(String region);
}
