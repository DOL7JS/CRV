package cz.upce.nnpro_backend.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarOwnerKey implements Serializable {

    private static final long serialVersionUID = 3081573224322873863L;
    @Column(name = "car_id")
    Long carId;

    @Column(name = "owner_id")
    Long ownerId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarOwnerKey that = (CarOwnerKey) o;
        return Objects.equals(carId, that.carId) && Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, ownerId);
    }
}
