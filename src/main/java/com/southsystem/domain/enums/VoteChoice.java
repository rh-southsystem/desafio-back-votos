package com.southsystem.domain.enums;

public enum VoteChoice {

	YES(1, "Yes"),
	NO(2, "No");

	private int id;
	private String description;
	
	private VoteChoice(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static VoteChoice toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for(VoteChoice x : VoteChoice.values()) {
			if(id.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + id);
	}
	
}
