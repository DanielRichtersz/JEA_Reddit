package danielrichtersz.models.components;

import danielrichtersz.models.Redditor;

import javax.persistence.*;

@Deprecated
//@Entity
public class OwnerContainer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Redditor owner;

    public OwnerContainer() {

    }

    public OwnerContainer(Redditor owner) {
        this.owner = owner;
    }

    public Redditor getOwner() {
        return this.owner;
    }

}
