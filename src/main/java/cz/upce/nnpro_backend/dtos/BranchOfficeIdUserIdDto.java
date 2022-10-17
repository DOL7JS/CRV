package cz.upce.nnpro_backend.dtos;

public class BranchOfficeIdUserIdDto {
    Long userId;
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
