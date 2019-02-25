package models.components;

import models.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteContainer {
    private List<Vote> votes;

    public VoteContainer() {
        this.votes = new ArrayList<>();
    }

    public List<Vote> getVotes() {
        return this.votes;
    }
}