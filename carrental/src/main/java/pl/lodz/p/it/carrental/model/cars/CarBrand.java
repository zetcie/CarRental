package pl.lodz.p.it.carrental.model.cars;

import pl.lodz.p.it.carrental.model.TimestampedAbstractEntity;

import javax.persistence.*;

@Entity
public class CarBrand extends TimestampedAbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String name;

    public CarBrand() {
    }

    public String getName() {
        return this.name;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
