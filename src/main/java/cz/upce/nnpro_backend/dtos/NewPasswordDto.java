package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;

public class NewPasswordDto {
    @NotBlank(message = "Old password id is mandatory.")
    private String oldPassword;
    @NotBlank(message = "New password id is mandatory.")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
