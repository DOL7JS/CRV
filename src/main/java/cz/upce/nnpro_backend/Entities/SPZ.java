package cz.upce.nnpro_backend.Entities;

import javax.persistence.*;

@Entity
public class SPZ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String SPZ;

    public SPZ(String SPZ) {
        this.SPZ = SPZ;
    }

    public SPZ() {

    }

    public String getSPZ() {
        return SPZ;
    }

    public void setSPZ(String SPZ) {
        this.SPZ = SPZ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
