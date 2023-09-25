package cz.upce.nnpro_backend.dtos;

import cz.upce.nnpro_backend.entities.Role;

public class UserOutDto {
    private Long id;
    private String username;
    private String email;
    private String jobPosition;
    private Role role;
    private BranchOfficeDto branchOfficeDto;

    public BranchOfficeDto getBranchOfficeDto() {
        return branchOfficeDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBranchOfficeDto(BranchOfficeDto branchOfficeDto) {
        this.branchOfficeDto = branchOfficeDto;
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


    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }
}
