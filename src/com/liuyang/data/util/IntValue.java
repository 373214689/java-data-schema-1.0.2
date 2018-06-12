package com.liuyang.data.util;

import java.io.IOException;
import java.io.OutputStream;

import com.liuyang.data.filter.IntFilter;
import com.liuyang.data.func.IntFunction;
import com.liuyang.data.util.Schema.Type;


public final class IntValue extends PrimitveValue implements NumberValue {
	
	/** data type */
	public final static Type DEFAULT_VALUE_TYPE = Type.INTEGER;
	/** default value*/
	public final static int DEFALUT_VALUE = 0;
	
	//private Function<Integer, Integer> computeAction = null;
	
	int value;
	
    public IntValue(int value) {
    	this.value = value;
    }
    
    public IntValue() {
    	this(DEFALUT_VALUE);
    }
    
    @Override
    protected final void finalize() {
    	value = 0;
    }
    
    @Override
    public final IntValue clone() {
    	return new IntValue(value);
    }
    
	@Override
	public final int compareTo(PrimitveValue other) {
		if (other == null) 
			throw new NullPointerException();
		if (!(other instanceof IntValue))
			throw new IllegalArgumentException("Illegal paramater: " + other + ", it is not " + getClass().getSimpleName());
		return Integer.compare(value, other.getInteger());
	}
	
	@Override
	public final boolean equals(Object anObject) {
		if (anObject == this) return true;
		if (anObject == null) return false;
		if (anObject instanceof IntValue) {
			return value == ((IntValue) anObject).value;
		}
		/*if (anObject instanceof IntValue) {
			return value == ((IntValue) anObject).value;
		} else if (anObject instanceof LongValue) {
			return value == ((LongValue) anObject).value;
		} else if (anObject instanceof ShortValue) {
			return value == ((ShortValue) anObject).value;
		} else if (anObject instanceof Integer) {
			return value == (Integer) anObject;
		} else if (anObject instanceof Long) {
			return value == (Long) anObject;
		} else if (anObject instanceof Short) {
			return value == (Short) anObject;
		}*/
		return false;
	}
	
	@Override
	public final Type getType() {
		return DEFAULT_VALUE_TYPE;
	}
	
    @Override
    public final byte[] getBytes() {
    	return new byte[] {  
                (byte) ((value >> 24) & 0xFF),  
                (byte) ((value >> 16) & 0xFF),     
                (byte) ((value >> 8) & 0xFF),     
                (byte) (value & 0xFF)  
            };
    }
    
	@Override
	public final boolean getBoolean() {
		return (value == 0) ? false : true;
	}
	
	@Override
	public final byte[] getBinary() {
		return getBytes();
	}
	
	@Override
	public final double getDouble() {
		return (double) value;
	}
	
	@Override
	public final float getFloat() {
		return (float) value;
	}
	
	@Override
	public final int getInteger() {
		return (int) value;
	}
	
	public final long getLong() {
		return (long) value;
	}
	
	public final short getShort() {
		return (short) value;
	}
	
	@Override
	public final String getString() {
		return String.valueOf(value);
	}
    
    @Override
    public final Integer getValue() {
    	return new Integer(value);
    }
    
    @Override
    public int hashCode() {
    	return Integer.hashCode(value);
    }
    
    /*@Override
    public final ShortValue modifyValue(Mode mode, short value) {
        switch (mode) {
        case INCREASE: this.value += value; break;
        case SUBTRACT: this.value -= value; break;
        case MULTIPLY: this.value *= value; break;
        case DIVISION: this.value /= value; break;
        case MODULO:   this.value %= value; break;
        case EXPONENT: this.value ^= value; break;
        case LOGARTHM: this.value = (short) (Math.log(this.value) / Math.log(value)); break;
		default:
			break;
        }
        return this;
    }*/
    
    @Override
    public final int length() {
    	return 4;
    }
    
    @Override
    public final synchronized void setValue(double value) {
    	this.value = (int) value;
    }
    
    @Override
    public final synchronized void setValue(float value) {
    	this.value = (int) value;
    }
    
    @Override
    public final synchronized void setValue(int value) {
    	this.value = (int) value;
    }
    
    @Override
    public final synchronized void setValue(long value) {
    	this.value = (int) value;
    }
    
    @Override
    public final synchronized void setValue(short value) {
    	this.value = (int) value;
    }
    
    
    @Override
    public final String toString() {
      return String.valueOf(value);
    }
    
	@Override
	public final synchronized void writeValue(OutputStream o) throws IOException {
		o.write(getBytes());

	}
	
	@Override
	public final double doubleValue() {
		return (double) value;
	}

	@Override
	public final float floatValue() {
		return (float) value;
	}

	@Override
	public final int intValue() {
		return (int) value;
	}

	@Override
	public final long longValue() {
		return (long) value;
	}

	@Override
	public final short shortValue() {
		return (short) value;
	}

	//@Override
	public final int compute (IntFunction action) {
		//if (computeAction != action) computeAction = action;
		//value = (computeAction == action) ? computeAction.apply(value) : (computeAction = action).apply(value);
		//return value = computeAction.apply(value);
		return value = action.apply(value);
		//return this;
	}
	
	public final boolean filter(IntFilter filter) {
		return filter.apply(value);
	}
	
	//public final IntValue comput()
}
