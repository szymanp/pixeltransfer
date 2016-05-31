package com.metapx.pixeltransfer.repository.local;

public enum ObjectState {
	ADD(1),
	COMMITTED(2),
	SYNCED(3),
	REMOVE(4);
	
	private byte value;
	
	ObjectState(int value) {
		this.value = (byte)value;
	}
	
	public byte toByte() {
		return this.value;
	}
	
	public static ObjectState valueOf(byte value) {
		for(ObjectState enumValue : ObjectState.values()) {
			if (enumValue.value == value) {
				return enumValue;
			}
		}
		throw new RuntimeException("Invalid enum value " + value);
	}
}
