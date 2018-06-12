package com.liuyang.data.util;

import java.io.IOException;
import java.io.OutputStream;

import com.liuyang.data.util.Schema.Type;

public final class TextValue extends PrimitveValue {
	/** data type */
	public final static Type DEFAULT_VALUE_TYPE = Type.STRING;
	/** default value*/
	public final static String DEFALUT_VALUE = "";
	
    String value;
    
    public TextValue(Object value) {
    	this.value = String.valueOf(value);
    }
    
    public TextValue(String value) {
    	this.value = value;
    }
    
    public TextValue() {
    	this(DEFALUT_VALUE);
    }
    
    @Override
    protected void finalize() {
    	value = null;
    }
    
    @Override
    public final TextValue clone() {
    	return new TextValue(value);
    }
    
	@Override
	public final int compareTo(PrimitveValue other) {
		if (other == null) 
			throw new NullPointerException();
		if (!(other instanceof TextValue))
			throw new IllegalArgumentException("Illegal paramater: " + other + ", it is not " + getClass().getSimpleName());
		return value.compareTo(other.getString());
	}
	
	@Override
	public final boolean equals(Object anObject) {
		if (anObject == this) return true;
		if (anObject == null) return false;
		if (anObject instanceof TextValue) {
			TextValue other = (TextValue) anObject;
			if (value == other.value) return true;
			return value.equals(((TextValue) anObject).value);
		}
		return false;
	}
    
	@Override
	public final Type getType() {
		return DEFAULT_VALUE_TYPE;
	}
	
    @Override
    public final byte[] getBytes() {
    	return value.getBytes();
    }
    
	@Override
	public final boolean getBoolean() {
		return Boolean.parseBoolean(value);
	}
	
	@Override
	public final byte[] getBinary() {
		return getBytes();
	}
	
	@Override
	public final double getDouble() {
		return Double.parseDouble(value);
	}
	
	@Override
	public final float getFloat() {
		return Float.parseFloat(value);
	}
	
	@Override
	public final int getInteger() {
		return Integer.parseInt(value);
	}
	
	public final long getLong() {
		return Long.parseLong(value);
	}
	
	public final short getShort() {
		return Short.parseShort(value);
	}
	
	@Override
	public final String getString() {
		return value;
	}
	
	@Override
	public final String getValue() {
		return value;
	}
	
    @Override
    public final int hashCode() {
    	return (value == null) ? 0 : value.hashCode();
    }
    
    @Override
    public final int length() {
    	return value.getBytes().length;
    }

    @Override
    public final synchronized void setValue(Object value) {
    	this.value = String.valueOf(value);
    }
    
    @Override
    public final synchronized void setValue(String value) {
    	this.value = String.valueOf(value);
    }
    
    @Override
    public final String toString() {
    	return value;
    }
    
	@Override
	public final synchronized void writeValue(OutputStream o) throws IOException {
		o.write(value.getBytes());
	}
}
