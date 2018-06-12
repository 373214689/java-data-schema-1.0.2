package com.liuyang.data.func;

import com.liuyang.data.util.PrimitveValue;

@FunctionalInterface
public interface ShortFunction {
	default short apply(double value) {
		return apply((short) value);
	}
	
	default short apply(float value) {
		return apply((long) value);
	}
	
	default short apply(int value) {
		return apply((short) value);
	}
	
	default short apply(long value) {
		return apply((short) value);
	}
	
	short apply(short value);
	
	default short apply(PrimitveValue value) {
		return apply(value.getShort());
	}

}
