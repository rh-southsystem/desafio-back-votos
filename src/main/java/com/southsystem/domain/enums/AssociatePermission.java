package com.southsystem.domain.enums;

public enum AssociatePermission {
	
	ABLE_TO_VOTE(1, "Able to Vote"),
	UNABLE_TO_VOTE(2, "Unable to Vote");

	private int id;
	private String description;
	
	private AssociatePermission(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static AssociatePermission toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for(AssociatePermission x : AssociatePermission.values()) {
			if(id.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + id);
	}
	
}
