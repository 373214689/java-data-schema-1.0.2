package com.liuyang.data.util;

import java.io.IOException;
import java.io.OutputStream;

import com.liuyang.data.util.Schema.Type;


public final class BooleanValue extends PrimitveValue {
	
	/** data type */
	public final static Type DEFAULT_VALUE_TYPE = Type.BOOLEAN;
	/** default value*/
	public final static boolean DEFALUT_VALUE = false;
	
	boolean value;
	
    public BooleanValue(boolean value) {
    	this.value = value;
    }
    
    public BooleanValue() {
    	this(DEFALUT_VALUE);
    }
    
    @Override
    protected final void finalize() {
    	value = false;
    }
    
	@Override
	public final int compareTo(PrimitveValue other) {
		if (other == null) 
			throw new NullPointerException();
		if (!(other instanceof BooleanValue))
			throw new IllegalArgumentException("Illegal paramater: " + other + ", it is not " + getClass().getSimpleName());
		return Boolean.compare(value, other.getBoolean());
	}
	
    @Override
    public final BooleanValue clone() {
    	return new BooleanValue(value);
    }
	
	@Override
	public final boolean equals(Object anObject) {
		if (anObject == this) return true;
		if (anObject == null) return false;
		if (anObject instanceof BooleanValue) {
			return value == ((BooleanValue) anObject).value;
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
    	return new byte[] {(byte) (value ?  1 & 0xFF : 0 & 0xFF)};
    }
    
	@Override
	public final boolean getBoolean() {
		return value;
	}
	
	@Override
	public final byte[] getBinary() {
		return getBytes();
	}
	
	@Override
	public final double getDouble() {
		return value ? 1.00d : 0.00d;
	}
	
	@Override
	public final float getFloat() {
		return value ? 1.0f : 0.0f;
	}
	
	@Override
	public final int getInteger() {
		return value ? 1 : 0;
	}
	
	public final long getLong() {
		return value ? 1l : 0l;
	}
	
	public final short getShort() {
		return (short) (value ? 1 : 0);
	}
	
	@Override
	public final String getString() {
		return String.valueOf(value);
	}
    
    @Override
    public final Boolean getValue() {
    	return value;
    }
    
    @Override
    public int hashCode() {
    	return value ? 1231 : 1237;
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
    	return 8;
    }
    
    @Override
    public final void setValue(boolean value) {
    	this.value = value;
    }
    
    @Override
    public final String toString() {
      return String.valueOf(value);
    }
    
	@Override
	public final void writeValue(OutputStream o) throws IOException {
		o.write(getBytes());

	}
	
	/*@Override
	public final double doubleValue() {
		return value ? 1.00d : 0.00d;
	}

	@Override
	public final float floatValue() {
		return value ? 1.00f : 0.00f;
	}

	@Override
	public final int intValue() {
		return value ? 1 : 0;
	}

	@Override
	public final long longValue() {
		return value ? 1 : 0;
	}

	@Override
	public final short shortValue() {
		return (short) (value ? 1 : 0);
	}*/

}
