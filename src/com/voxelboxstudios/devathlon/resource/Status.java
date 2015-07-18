package com.voxelboxstudios.devathlon.resource;

public enum Status {

	/** Enumeration **/
	
	SUCCESSFULLY_LOADED(0), DECLINED(1), FAILED_DOWNLOAD(2), ACCEPTED(3);
	
	
	/** ID **/
	
	private int id;
	
	
	/** Constructor **/
	
	private Status(int id) {
		this.id = id;
	}
	
	
	/** Get status by ID **/
	
	public static Status byID(int id) {
		/** Array **/
		
		for(int i = 0; i < values().length; i++) {
			/** Status **/
			
			Status s = values()[i];
			
			
			/** Check ID **/
			
			if(s.id == id) {
				return s;
			}
		}
		
		
		/** Return default **/
		
		return DECLINED;
	}
}
