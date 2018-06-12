package com.liuyang.data.filter;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface FloatFilter {
	default boolean apply(double value) {
		return apply((float) value);
	}
	
	boolean apply(float value);
	
	default boolean apply(int value) {
		return apply((double) value);
	};
	
	default boolean apply(long value) {
		return apply((double) value);
	}
	
	default boolean apply(short value) {
		return apply((double) value);
	}
	
	default boolean apply(PrimitveValue value) {
		return apply(value.getDouble());
	}

}
