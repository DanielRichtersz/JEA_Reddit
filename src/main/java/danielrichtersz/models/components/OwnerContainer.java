package danielrichtersz.models.components;

import danielrichtersz.models.Redditor;

public class OwnerContainer {
    private Redditor owner;

    public OwnerContainer(Redditor owner) {
        this.owner = owner;
    }

    public Redditor getOwner() {
        return this.owner;
    }

}
