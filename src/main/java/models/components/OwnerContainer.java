package models.components;

import models.Redditor;

public class OwnerContainer {
    private Redditor owner;

    public OwnerContainer(Redditor owner) {
        this.owner = owner;
    }

    public Redditor getOwner() {
        return this.owner;
    }

}
