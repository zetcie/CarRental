package pl.lodz.p.it.carrental.model.rents;

import javax.persistence.Entity;

@Entity
public class Rent extends AbstractRent {
    public Rent(AbstractRent abstractRent) {
        super(abstractRent);
    }
    public Rent() {
        super();
    }
}
