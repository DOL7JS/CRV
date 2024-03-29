package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserInDto {
    @NotBlank(message = "Username is mandatory.")
    private String username;
    private String email;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    private String jobPosition;
    @NotNull(message = "Role id is mandatory.")
    private Long role;


    @NotNull(message = "Office id is mandatory.")
    private Long office;

    public Long getRole() {
        return role;
    }

    public Long getOffice() {
        return office;
    }

    public void setOffice(Long office) {
        this.office = office;
    }

    public void setRole(Long role) {
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
}
