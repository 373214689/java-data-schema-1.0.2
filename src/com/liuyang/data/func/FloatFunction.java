package com.liuyang.data.func;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface FloatFunction {
	default float apply(double value) {
		return apply((float) value);
	}
	
	float apply(float value);
	
	default double apply(int value) {
		return apply((double) value);
	};
	
	default double apply(long value) {
		return apply((double) value);
	}
	
	default double apply(short value) {
		return apply((double) value);
	}
	
	default double apply(PrimitveValue value) {
		return apply(value.getDouble());
	}

}
