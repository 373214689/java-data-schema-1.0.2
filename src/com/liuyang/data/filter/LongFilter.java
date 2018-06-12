package com.liuyang.data.filter;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface LongFilter {
	default boolean apply(double value) {
		return apply((long) value);
	}
	
	default boolean apply(float value) {
		return apply((long) value);
	}
	
	boolean apply(long value);
	
	default boolean apply(int value) {
		return apply((long) value);
	}
	
	default boolean apply(short value) {
		return apply((long) value);
	}
	
	default boolean apply(PrimitveValue value) {
		return apply(value.getLong());
	}

}
