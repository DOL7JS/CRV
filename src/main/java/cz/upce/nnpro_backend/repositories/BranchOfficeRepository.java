package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.Entities.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long> {
    boolean existsByRegionAndDistrictAndCity(String region, String district, String city);

    boolean existsByRegionAndDistrictAndCityAndIdIsNot(String region, String district, String city, Long id);
}
