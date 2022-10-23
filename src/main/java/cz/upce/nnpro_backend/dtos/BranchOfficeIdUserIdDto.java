package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;

public class BranchOfficeIdUserIdDto {
    @NotBlank(message = "User id is mandatory.")
    Long userId;
    @NotBlank(message = "Branch office id is mandatory.")
    Long branchOfficeId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBranchOfficeId() {
        return branchOfficeId;
    }

    public void setBranchOfficeId(Long branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
    }
}
