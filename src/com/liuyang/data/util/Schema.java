package com.liuyang.data.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Schema
 * 
 * <li>2018/6/4 created by liuyang</li>
 * @author liuyang
 * @version 1.0.2
 *
 */
public final class Schema {
	
    private final static String nil(String value, String defaultValue) {
    	if (value == null) return defaultValue;
    	if (value.length() == 0) return defaultValue;
    	return value;
    }
    
	/**
	 * Schema Type Defined
	 * <ul>
	 * Use one of follow
	 * <li> {@code BINARY} </li>
	 * <li> {@code BOOLEAN} </li>
	 * <li> {@code DOUBLE} </li>
	 * <li> {@code FLOAT} </li>
	 * <li> {@code INT} </li>
	 * <li> {@code LONG} </li>
	 * <li> {@code SHORT} </li>
	 * <li> {@code STRING} </li>
	 * <li> {@code STRUCT} </li>
	 * </ul>
	 * @version 1.0.2
	 * @author liuyang
	 *
	 */
	public static enum Type {
    	BYTE("byte", true),
    	BYTEARRAY("bytearray", true),
    	BINARY("binary", true),
    	BOOL("bool", true),
    	BOOLEAN("boolean", true),
    	CHAR("char", true),
    	DECIMAL("decimal", true),
    	DOUBLE("double", true),
    	FLOAT("float", true),
    	/** 字段*/
    	TINYINT("tinyint", true),
    	SMALLINT("smallint", true),
    	INT("int", true),
    	INTEGER("integer", true),
    	LIST("list", false),
    	BIGINT("bigint", true),
    	LONG("long", true),
    	MAP("map", false),
    	PRIMITVE("primitve", true),
    	STRUCT("struct", false),
    	/**短整数: short, smallint, 取值范围: -32768 - 32768*/
    	SHORT("short", true),
    	/**字符串: string, str*/
    	STRING("string", true),
    	UNION("union", false),
    	VARCHAR("varchar", true);
    	
    	private String name = null;
    	private boolean isPrimitive = false;
    	
    	private Type(String name, boolean isPrimitive) {
    		this.name = name;
    		this.isPrimitive = isPrimitive;
    	}

    	public String getName() {
    		return this.name;
    	}
    	
    	public boolean isPrimitive() {
    	      return isPrimitive;
    	}
    	
    	public Object parseString(String value) {
    		switch(this) {
        	case BINARY: {
        		return nil(value, "").getBytes();
        	}
        	case BOOL:
        	case BOOLEAN: {
        		return Boolean.parseBoolean(nil(value, "false"));
        	}
        	case DOUBLE: {
        		return Double.parseDouble(nil(value, "0.00"));
        	}
        	case FLOAT: {
        		return Float.parseFloat(nil(value, "0.00"));
        	}
        	case INT:
        	case INTEGER: {
        		return Integer.parseInt(nil(value, "0"));
        	}
        	case BIGINT:
        	case LONG: {
        		return Long.parseLong(nil(value, "0"));
        	}
        	case TINYINT:
        	case SMALLINT:
        	case SHORT: {
        		return Short.parseShort(nil(value, "0"));
        	}
        	case VARCHAR:
        	case STRING:
        	default:
        		return value;
        	}
    	}
    	
    	public PrimitveValue parseStringValue(String value) {
    		switch(this) {
        	case BINARY: {
        		return new BinaryValue(nil(value, "").getBytes());
        	}
        	case BOOL:
        	case BOOLEAN: {
        		return new BooleanValue(Boolean.parseBoolean(nil(value, "false")));
        	}
        	case DOUBLE: {
        		return new DoubleValue(Double.parseDouble(nil(value, "0.00")));
        	}
        	case FLOAT: {
        		return new FloatValue(Float.parseFloat(nil(value, "0.00")));
        	}
        	case INT:
        	case INTEGER: {
        		return new IntValue(Integer.parseInt(nil(value, "0")));
        	}
        	case BIGINT:
        	case LONG: {
        		return new LongValue(Long.parseLong(nil(value, "0")));
        	}
        	case TINYINT:
        	case SMALLINT:
        	case SHORT: {
        		return new ShortValue(Short.parseShort(nil(value, "0")));
        	}
        	case VARCHAR:
        	case STRING:
        	default:
        		return new TextValue(value);
        	}
    	}

    	public Object parseObjectValue(Object value) {
    		if (value == null) throw new NullPointerException();
    		switch(this) {
        	case BINARY: {
        		if (!(value instanceof byte[])) 
        			throw new IllegalArgumentException(
        					"Illegal paramater <" + this + ">: " + value + ", type is not byte[].");
        		return (byte[]) value;
        	}
        	case BOOL:
        	case BOOLEAN: {
        		if (!(value instanceof Boolean)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is not boolean.");
        		return (boolean) value;
        	}
        	case DOUBLE: {
        		if (!(value instanceof Double)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is not double.");
        		return (double) value;
        	}
        	case FLOAT: {
        		if (!(value instanceof Float)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is not float.");
        		return (float) value;
        	}
        	case INT:
        	case INTEGER: {
        		if (!(value instanceof Integer)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is not integer.");
        		return (int) value;
        	}
        	case BIGINT:
        	case LONG: {
        		if (!(value instanceof Long)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is not long.");
        		return (long) value;
        	}
        	case TINYINT:
        	case SMALLINT:
        	case SHORT: {
        		if (!(value instanceof Short)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: "+ value + ", type is not short.");
        		return (short) value;
        	}
        	case VARCHAR:
        	case STRING: {
        		if (!(value instanceof String)) 
        			throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: "+ value + ", type is not string.");
        		return (String) value;
        	}
        	default:
        		throw new IllegalArgumentException("Illegal paramater[" + value.getClass() + "]: " + value + ", type is undefined.");
        	}
    	}
    	
    	
    	@Override
    	public String toString() {
    		return "{name: " + name + ", isPrimitive: " + isPrimitive + "}";
    	}
    	
    	/**
    	 * Try find the type of name
    	 * @param name the type name, as {@code String}
    	 * @return the type {@link Schema.Type}
    	 * @exception IllegalArgumentException if the type of name undefined.
    	 */
    	public final static Type lookup(String name) {
    		String lowerName = name.toLowerCase();
    		for(Type value: values()) {
    			if (value.name.equals(lowerName)) return value;
    		}
    		throw new IllegalArgumentException("Illegal paramater[" + lowerName + "], type is undefined.");
    	}

    }
	
	public final static Schema create(String fieldName, String typeName) {
		return new Schema(fieldName, Type.lookup(typeName));
	}
	
	public final static Schema createStruct(String fieldName) {
		return new Schema(fieldName, Type.STRUCT);
	}
	
	/** id default value is -1.*/
	private int id = -1;
	private Type type = null; 
	private String name = null;
	private Schema parent = null;
	private List<String> fieldNames = null;
	private List<Schema> children = null;
	private int maxLength = 0;
	private int precision = 0;
	private int scale = 0;
	
	private Function<Row, PrimitveValue> valueComputeAction = null;
	private BiFunction<PrimitveValue, Row, PrimitveValue> valueReduceAction;
	private Predicate<Row> valueFilterAction = e -> {return e != null;};
	private Function<Row, Row> rowComputeAction = e -> {return e;};
	private Predicate<Row> rowFilterAction = e -> {return e != null;};
	
	private Schema(String fieldName, Type fieldType, int precision, int scale) {
    	this.name = fieldName;
    	this.type = fieldType;
    	this.precision = precision;
    	this.scale = scale;
        if (type.isPrimitive == false) children = new ArrayList<>();
        if (type == Type.STRUCT) fieldNames = new ArrayList<>();
	}
	
	private Schema(String fieldName, Type fieldType) {
    	this(fieldName, fieldType, 0, 0);
	}
	
	@Override
	protected void finalize() {
		if (fieldNames != null) fieldNames.clear();
		if (children != null) children.clear();
		id = 0;
		type = null;
		name = null;
		parent = null;
		fieldNames = null;
		children = null;
		maxLength = 0;
		precision = 0;
		scale = 0;
	}
	
    public Schema addField(Schema field) {
        if (type != Type.STRUCT) {
            throw new IllegalArgumentException("Can only add fields to struct type and not " + type);
        }
        if (children.contains(field))
        	throw new IllegalArgumentException("Can not append  the repeat field " + field);
        children.add(field);
        fieldNames.add(field.name);
        field.id = children.size() - 1;
        field.parent = this;
        return this;
    }
    
    public Schema addField(String fieldName, Type type, int precision, int scale) {
    	return addField(new Schema(fieldName, type, precision, scale));
    }
    
    public Schema addField(String fieldName, Type type) {
    	return addField(new Schema(fieldName, type, 0, 0));
    }
    
    public boolean contains(Schema other) {
    	if (other == null) return false;
    	if (this == other) return true;
    	if (other.type == Type.STRUCT && type == Type.STRUCT) {
    		return children.contains(other);
    	} else {
    		return other.type.equals(type) 
		    		&& other.name.equals(name) 
		    		&& other.precision == precision 
		    		&& other.scale == scale;
    	}
    }
    
    @Override
    public Schema clone() {
    	Schema retval = new Schema(name, type);
        if (type == Type.STRUCT) {
        	for(Schema field : children) {
        		retval.addField(field.clone());
        	}
        } else {
        	retval.id = id;
        	retval.scale = scale;
        	retval.precision = precision;
        	retval.maxLength = maxLength;
        	retval.parent = parent;
        }
        return retval;
    }
    
    public Row createRow() {
    	return new Row(this);
    }
    
    @Override
    public boolean equals(Object anObject) {
		if (anObject == null) return false;
		if (anObject == this) return true;
		if (anObject instanceof Schema) {
			Schema other = (Schema) anObject;
			if (other.type == Type.STRUCT && type == Type.STRUCT) {
				return other.children.stream().filter(children::contains).count() == children.size();
			} else {
			    return other.type.equals(type) 
			    		&& other.name.equals(name) 
			    		&& other.precision == precision 
			    		&& other.scale == scale;
			}
		}
    	return false;
    }
    
    public Schema getField(int index) {
    	return (type == Type.STRUCT) ? children.get(index) : this;
    }
    
    public Schema getField(String fieldName) {
    	return lookup(fieldName);
    }
    
    /**
     * Get the id of children schema with specified index.
     * @param index the index of children.
     * @return the id of children schema.
     * @exception NullPointerException if can not find a schema.
     */
    public int getFieldId(int index) {
    	Schema result = children.get(index);
    	if (result == null) throw new NullPointerException();
    	return result.getFieldId();
    }
    
    /**
     * Get the id of children schema with specified field name.
     * @param index the name of children.
     * @return the id of children schema.
     * @exception NullPointerException if can not find a schema.
     */
    public int getFieldId(String fieldName) {
    	Schema result = lookup(fieldName);
    	if (result == null) throw new NullPointerException();
    	return result.getFieldId();
    }
    
    /**
     * Get the id of this schema.
     * @return the id of this schema.
     */
    public int getFieldId() {
    	return id;
    }
    
    public String getFieldName(int index) {
    	return children.get(index).name;
    }
    
    public String getFieldName() {
    	return name;
    }
    
    public Schema getParent() {
    	return parent;
    }
    
    /**
     * Get the precision of the decimal type.
     * @return the number of digits for the precision.
     */
    public int getPrecision() {
      return precision;
    }

    /**
     * Get the scale of the decimal type.
     * @return the number of digits for the scale.
     */
    public int getScale() {
      return scale;
    }
    
    /**
     * Get the type of schema with specified index.
     * @param index the index of children.
     * @return the type of this schema. see {@link Schema.Type}
     */
    public Type getType(int index) {
    	if (index <= -1) return type;
    	return (type == Type.STRUCT) ? children.get(index).type : type;
    }
	
    /**
     * Get the type of schema.
     * @return the type of this schema. see {@link Schema.Type}
     */
    public Type getType() {
    	return type;
    }
	
    
    /**
     * For struct types, get the list of field names.
     * @return the list of field names.
     */
    public List<String> getFieldNames() {
        return Collections.unmodifiableList(fieldNames);
    }
    
    /**
     * Get the subtypes of this type.
     * @return the list of children types
     */
    public List<Schema> getChildren() {
    	return children == null ? null : Collections.unmodifiableList(children);
    }
    
	public Schema groupby(List<String> fieldNames) {
		if (fieldNames == null) return this;
		if (fieldNames.size() == 0) return this;
		if ("*".equals(fieldNames.get(0))) return this;
		if (type == Type.STRUCT) {
			parent = select(fieldNames);
			parent.name = name + ".groupby";
		}
		return this;
	}
	
	public Schema groupby(String... fieldNames) {
		return groupby(Arrays.asList(fieldNames));
	}
	
    @Override
    public int hashCode() {
      long result = type.ordinal() * 4241 + maxLength + precision * 13 + scale;
      if (children != null) {
        for(Schema child: children) {
          result = result * 6959 + child.hashCode();
        }
      }
      return (int) result;
    }
    
    public Schema lookup(String fieldName) {
    	if (fieldName == null) return this;
    	if (type != Type.STRUCT) return fieldName.equals(name) ? this : null;
		for(Schema value : children) {
			if (fieldName.equals(value.name)) return value;
		}
    	return null;
    }
    
	public Schema select(List<String> fieldNames) {
		if (fieldNames == null) return this;
		if (fieldNames.size() == 0) return this;
		if ("*".equals(fieldNames.get(0))) return this;
    	if (type == Type.STRUCT) {
    		Schema retval = new Schema(name, Type.STRUCT);
    		for(String fieldName : fieldNames) {
    			//Schema tmp = children.stream().filter(e -> e.name.equals(fieldName)).findFirst().orElse(null);
    			Schema tmp = lookup(fieldName);
    			if (tmp != null) {
    				retval.addField(tmp.clone());
    			}
    		}
    		return retval;
    	} else {
    		return fieldNames.contains(name) ? this : null;
    	}
    }
	
	public Schema select(String... fieldNames) {
		if (fieldNames == null) return this;
		return select(Arrays.asList(fieldNames));
	}
	
    public void setRowCompute(Function<Row, Row> action) {
    	rowComputeAction = action;
    }
    
    public void setRowFilter(Predicate<Row> filter) {
    	rowFilterAction = filter;
    }
    
    // compute与reduce的区别：
    //    1、compute会操作一个Row然后输出一个最终结果。（线性）
    //    2、reduce会输入上一个结果，以及本次放入的数据，然后输出合并后的终终结果。（并发）
    // 设置字段值compute计算表达式
    public void setValueCompute(Function<Row, PrimitveValue> action) {
    	valueComputeAction = action;
    }
    
    // 设置字段值的Reduce运算表达式
    public void setValueReduce(BiFunction<PrimitveValue, Row, PrimitveValue> action) {
    	
    	valueReduceAction = action;
    }
    
    public void setValueFilter(Predicate<Row> filter) {
    	valueFilterAction = filter;
    }
    
    public boolean where(Row row) {
    	return rowFilterAction.test(row);
    }
    
    public Row compute(Row row) {
    	//System.out.println(this);
    	Row retval = createRow();
    	
    	for (int i = 0, length = retval.size(); i < length; i++) {
    		if (children.get(i).valueComputeAction != null) {
    			retval.set(i, children.get(i).valueComputeAction.apply(row));
    		} else {
    			retval.set(i, row.get(getFieldName(i)).clone());
    		}
    		
    	}
    	return retval;
    }
    
    public Row reduce(Row a, Row b) {
    	//System.out.println(a + " <<>> " + b);
    	//System.out.println("schema <<>> " + this);
    	for (int i = 0, length = a.size(); i < length; i++) {
    		if (children.get(i).valueReduceAction != null ) {
    			//System.out.println("i:" + i + " <<>> children(i): " + children.get(i) + ", a(i)=" + a.get(i) + " ,a(1).type=" + a.get(i).getType() );
        		PrimitveValue value = children.get(i).valueReduceAction.apply(a.get(i), b);
        		//System.out.println("valueReduceAction: " + valueReduceAction + " a:" + a + " v: " + value );
        		a.set(i, value);
    		}
    	}
    	return a;
    }
    
    //public Private 
	
	public int size() {
	    return (type == Type.STRUCT) ? children.size() : 1;
	}
	
    @Override
    public String toString() {
    	if (type == Type.STRUCT) {
    		return (parent == null ? "" : parent + " => ") + "struct " + name + " {" + 
    	            String.join(", ", children.stream().map(e -> e.toString()).toArray(s -> new String[s])) + "}";
    	} else {
    		return name + " : " + type.getName();
    	}
    }
	
}
