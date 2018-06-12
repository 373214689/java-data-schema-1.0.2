package com.liuyang.data.util;

import java.io.IOException;
import java.io.OutputStream;

import com.liuyang.data.util.Schema.Type;

public abstract class PrimitveValue implements Comparable<PrimitveValue> {
	/** data type */
	public final static Type DEFAULT_TYPE = Type.PRIMITVE;
	
	public PrimitveValue cast(Type type) {
		if (type == getType()) return this;
		switch(type) {
	 	    case BOOL:
			case BOOLEAN : {
				return new BooleanValue(getBoolean());
			}
			case BINARY : {
				return new BinaryValue(getBinary());
			}
			case DOUBLE : {
				return new DoubleValue(getDouble());
			}
			case FLOAT : {
				return new FloatValue(getFloat());
			}
			case INT:
			case INTEGER : {
				return new IntValue(getInteger());
			}
			case BIGINT:
			case LONG: {
				return new LongValue(getLong());
			}
			case VARCHAR:
			case STRING: {
				return new TextValue(getString());
			}
		default:
			return this;
		}
	}
	
	/**
	 * Compares the two specified {@code PrimitveValue} values.
	 * @param other the {@code PrimitveValue} to be compared.
	 * @return 
	 */
	@Override
	public int compareTo(PrimitveValue other) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public PrimitveValue clone() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param expression contains: >, <, ==, !=, >=, <=, in (...) not in (...)
	 * @param value
	 * @return
	 */
	public boolean filter(String expression, int value) {
		throw new UnsupportedOperationException();
	}
	
	public boolean filter(String expression, PrimitveValue value) {
		throw new UnsupportedOperationException();
	}
	
	public Type getType() {
		return DEFAULT_TYPE;
	}
	
	public byte[] getBytes() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Get binary data.
	 * @return byte array data as {@code Binary}
	 */
	public byte[] getBinary() {
	    throw new UnsupportedOperationException();
	}
	
	public String getString() {
	    throw new UnsupportedOperationException();
	}
	
	public int getInteger() {
	    throw new UnsupportedOperationException();
	}
	
	public long getLong() {
	    throw new UnsupportedOperationException();
	}
	
	public boolean getBoolean() {
	    throw new UnsupportedOperationException();
	}
	
	public float getFloat() {
	    throw new UnsupportedOperationException();
	}
	
	public double getDouble() {
	    throw new UnsupportedOperationException();
	}

	public short getShort() {
	    throw new UnsupportedOperationException();
	}
	
	public Object getValue() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Get the length as this data used memery.
	 * the array and string data length will changed with content.
	 * <li>byte    : 1</li>
	 * <li>boolean : 1</li>
	 * <li>short   : 2</li>
	 * <li>int     : 4</li>
	 * <li>float   : 4</li>
	 * <li>long    : 8</li>
	 * <li>double  : 8</li>
	 * @return The length of data in memery.
	 */
	public int length() {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(int value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(long value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(double value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(float value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(boolean value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(byte[] value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(short value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(String value) {
		throw new UnsupportedOperationException();
	}
	
	public void setValue(Object value) {
		throw new UnsupportedOperationException();
	}
	

	/**
	 * Write data to the specified output stream.(该功能待完善)
	 * @param o the specified outputstream.
	 * @throws IOException
	 */
	abstract public void writeValue(OutputStream o) throws IOException;
	//abstract public void writeValue(RecordConsumer recordConsumer);
}
