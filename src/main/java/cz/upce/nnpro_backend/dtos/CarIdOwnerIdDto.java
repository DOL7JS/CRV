package cz.upce.nnpro_backend.dtos;

public class CarIdOwnerIdDto {
    Long ownerId;
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
