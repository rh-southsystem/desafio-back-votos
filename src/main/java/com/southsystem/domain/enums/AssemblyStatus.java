package com.southsystem.domain.enums;

public enum AssemblyStatus {
	
	PENDING(1, "Pending"),
	STARTED(2, "Started"),
	FINISHED(3, "Finished"),
	CANCELED(4, "Canceled");

	private int id;
	private String description;
	
	private AssemblyStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static AssemblyStatus toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		
		for(AssemblyStatus x : AssemblyStatus.values()) {
			if(id.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + id);
	}

}
