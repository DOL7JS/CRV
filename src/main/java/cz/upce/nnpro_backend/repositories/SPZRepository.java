package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.entities.SPZ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPZRepository extends JpaRepository<SPZ, Long> {

    SPZ findTopByOrderBySPZAsc();

    boolean existsBySPZ(String spz);
}
