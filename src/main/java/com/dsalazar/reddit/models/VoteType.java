package com.dsalazar.reddit.models;

import com.dsalazar.reddit.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {

    UP_VOTE(1), DOWN_VOTE(-1);

    private int direction;

    VoteType(int direction) {}

    public Integer getDirection()
    {
        return direction;
    }

    public static VoteType lookup(Integer direction)
    {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

}
