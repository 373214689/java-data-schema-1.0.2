package com.liuyang.data.func;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface IntFunction {
	default int apply(double value) {
		return apply((int) value);
	}
	
	default int apply(float value) {
		return apply((int) value);
	}
	
	int apply(int value);
	
	default int apply(long value) {
		return apply((int) value);
	}
	
	default int apply(short value) {
		return apply((int) value);
	}
	
	default int apply(PrimitveValue value) {
		return apply(value.getInteger());
	}

}
