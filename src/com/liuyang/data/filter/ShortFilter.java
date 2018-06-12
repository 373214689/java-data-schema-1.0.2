package com.liuyang.data.filter;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface ShortFilter {
	default boolean apply(double value) {
		return apply((short) value);
	}
	
	default boolean apply(float value) {
		return apply((long) value);
	}
	
	default boolean apply(int value) {
		return apply((short) value);
	}
	
	default boolean apply(long value) {
		return apply((short) value);
	}
	
	boolean apply(short value);
	
	default boolean apply(PrimitveValue value) {
		return apply(value.getShort());
	}

}
