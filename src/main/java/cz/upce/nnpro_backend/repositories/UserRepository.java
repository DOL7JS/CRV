package cz.upce.nnpro_backend.repositories;

import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.branchOffice = null  where u.branchOffice = ?1")
    void setUserOfficeToNUllByOffice(BranchOffice branchOffice);

    List<User> findByBranchOffice_Region(String region);
    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

    User findByUsername(String username);

    boolean existsByRoleName(String name);
}
