package cz.upce.nnpro_backend.security;

import cz.upce.nnpro_backend.entities.BranchOffice;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailDto extends User {

    Long id;
    BranchOffice branchOffice;

    public UserDetailDto(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, BranchOffice branchOffice) {
        super(username, password, authorities);
        this.id = id;
        this.branchOffice = branchOffice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }
}
