package com.metapx.pixeltransfer.common;

public class Opt<T> {
	final boolean hasValue;
	final T value;
	
	private Opt(boolean hasValue, T value) {
		this.hasValue = hasValue;
		this.value = value;
	}
	
	public static <T> Opt<T> of(T value) {
		return new Opt<T>(true, value);
	}
	
	public static <T> Opt<T> empty() {
		return new Opt<T>(false, null);
	}

	public boolean hasValue() {
		return hasValue;
	}

	public T getValue() {
		return value;
	}
}
