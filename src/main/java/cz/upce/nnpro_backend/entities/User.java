package cz.upce.nnpro_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is mandatory.")
    private String username;
    private String email;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    private String jobPosition;
    @ManyToOne
    @JoinColumn(name = "branchOffice_id")
    @JsonIgnore
    private BranchOffice branchOffice;
    @OneToOne
    private Role role;

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public User() {
    }

    public User(Long id, String username, String email, String password, String jobPosition) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.jobPosition = jobPosition;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
