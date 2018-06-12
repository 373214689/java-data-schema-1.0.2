package com.liuyang.data.filter;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface IntFilter {
	default boolean apply(double value) {
		return apply((int) value);
	}
	
	default boolean apply(float value) {
		return apply((int) value);
	}
	
	boolean apply(int value);
	
	default boolean apply(long value) {
		return apply((int) value);
	}
	
	default boolean apply(short value) {
		return apply((int) value);
	}
	
	default boolean apply(PrimitveValue value) {
		return apply(value.getInteger());
	}

}
