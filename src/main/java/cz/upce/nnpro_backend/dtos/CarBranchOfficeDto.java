package cz.upce.nnpro_backend.dtos;

import javax.validation.constraints.NotNull;

public class CarBranchOfficeDto {
    @NotNull(message = "Car id is mandatory.")
    Long carId;
    @NotNull(message = "Branch office id is mandatory.")
    Long officeId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }
}
