package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarIdOwnerIdDto {
    @NotNull(message = "Owner id is mandatory.")
    Long ownerId;
    @NotNull(message = "Car id is mandatory.")
    Long carId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
