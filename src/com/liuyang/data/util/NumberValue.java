package com.liuyang.data.util;

public interface NumberValue {

	public abstract double doubleValue();
	public abstract float floatValue();
    public abstract int  intValue();
    public abstract long longValue();
    public abstract short shortValue();
    //public abstract void compute (Function<? extends Number, ? extends Number> action);
    //public abstract boolean filter (Function<NumberValue, Boolean> action);
    default byte byteValue() {
        return (byte) intValue();
    }

}
