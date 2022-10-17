package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.Entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
}
