package com.south.voting.enums;

public enum VoteEnum {
    SIM("Sim"),
    NAO("Não");

    private final String valueVote;

    VoteEnum(String value) {
      this.valueVote = value;
    }
}
