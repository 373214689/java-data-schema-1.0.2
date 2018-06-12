package com.liuyang.data.func;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface LongFunction {
	default long apply(double value) {
		return apply((long) value);
	}
	
	default long apply(float value) {
		return apply((long) value);
	}
	
	long apply(long value);
	
	default long apply(int value) {
		return apply((long) value);
	}
	
	default long apply(short value) {
		return apply((long) value);
	}
	
	default long apply(PrimitveValue value) {
		return apply(value.getLong());
	}

}
