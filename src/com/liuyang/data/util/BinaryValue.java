package com.liuyang.data.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import com.liuyang.data.util.Schema.Type;

public final class BinaryValue extends PrimitveValue{
	/** data type */
	public final static Type DEFAULT_VALUE_TYPE = Type.BINARY;
	/** default value*/
	public final static byte[] DEFALUT_VALUE = new byte[] {};
	
    private byte[] value;
    
    public BinaryValue(byte[] value) {
    	this.value = value;
    }
    
    public BinaryValue() {
    	this(DEFALUT_VALUE);
    }
    
    @Override
    protected final void finalize() {
    	value = null;
    }
    
    @Override
    public final BinaryValue clone() {
    	return new BinaryValue(value.clone());
    }
    
	@Override
	public final int compareTo(PrimitveValue other) {
		// byte数组不进行比较
		if (other == null) 
			throw new NullPointerException();
		if (!(other instanceof BinaryValue))
			throw new IllegalArgumentException("Illegal paramater: " + other + ", it is not " + getClass().getSimpleName());
		return 0;
	}
	
	@Override
	public final boolean equals(Object anObject) {
		if (anObject == this) return true;
		if (anObject == null) return false;
		if (anObject instanceof BinaryValue) {
			BinaryValue other = (BinaryValue) anObject;
			if (other.value == value) return true;
			return Arrays.equals(other.value, value);
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
    	return value;
    }
	
	@Override
	public final boolean getBoolean() {
		return (value == null) ? false : (value[0] == 0 ? false : true);
	}
	
	@Override
	public final byte[] getBinary() {
		return getBytes();
	}
	
	@Override
	public final double getDouble() {
		long x = getLong();
        if ((x > 0x7ff0000000000001L && x <= 0x7fffffffffffffffL) 
        		|| (x >= 0xfff0000000000001L && x <= 0xffffffffffffffffL)) {
        	return Double.longBitsToDouble(x); 
        } else {
        	return (double) x;
        }
	}
	
	@Override
	public final float getFloat() {
		int x = getInteger();
        if ((x >= 0x7f800001 && x <= 0x7fffffff) || (x >= 0xff800001 && x <= 0xffffffff)) {
        	return Float.intBitsToFloat(x); 
        } else {
        	return (float) x;
        }
	}
	
	@Override
	public final int getInteger() {
		int length = 4;
		byte[] bytes = new byte[length];
		if (value.length > length) {
			System.arraycopy(value, value.length - length, bytes, 0, length);
		} else if (value.length < length) {
			System.arraycopy(value, 0, bytes, length - value.length, value.length);
		} else if (value.length == length) {
			bytes = value;
		}
		int x = (0xff & bytes[3]) 
	            | (0xff00 & (bytes[2] << 8)) 
				| (0xff0000 & (bytes[1] << 16)) 
				| (0xff000000 & (bytes[0] << 24));
        if ((x >= 0x7f800001 && x <= 0x7fffffff) || (x >= 0xff800001 && x <= 0xffffffff)) {
        	return (int) Float.intBitsToFloat(x); 
        } else {
        	return x;
        } 
	}
	
	public final long getLong() {
		int length = 8;
		byte[] bytes = new byte[length];
		if (value.length > length) {
			System.arraycopy(value, value.length - length, bytes, 0, length);
		} else if (value.length < length) {
			System.arraycopy(value, 0, bytes, length - value.length, value.length);
		} else if (value.length == length) {
			bytes = value;
		}
		
		long x = 0;  
        for (int i = 0; i < length; i++) {
            x <<= length;
            x |= (bytes[i] & 0xff);
        }
        if ((x > 0x7ff0000000000001L && x <= 0x7fffffffffffffffL) 
        		|| (x >= 0xfff0000000000001L && x <= 0xffffffffffffffffL)) {
        	return (long) Double.longBitsToDouble(x); 
        } else {
        	return x;
        }
	}
	
	@Override
	public final short getShort() {
		int length = 2;
		byte[] bytes = new byte[length];
		if (value.length > length) {
			System.arraycopy(value, value.length - length, bytes, 0, length);
		} else if (value.length < length) {
			System.arraycopy(value, 0, bytes, length - value.length, value.length);
		} else if (value.length == length) {
			bytes = value;
		}
		return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
	}
	
	@Override
	public final String getString() {
		return new String(value);
	}
    
    @Override
    public byte[] getValue() {
    	return value;
    }
    
    @Override
    public final int hashCode() {
    	return value.hashCode();
    }
    
    @Override
    public final int length() {
    	return value.length;
    }
    
    @Override
    public final void setValue(byte[] value) {
    	this.value = value;
    }
    
    @Override
    public final String toString() {
    	return new String(value);
    }


    
	@Override
	public final void writeValue(OutputStream o) throws IOException {
		o.write(getBytes());
	}
}
