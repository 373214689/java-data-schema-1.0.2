package com.liuyang.data.util;

import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Container {
	private TreeMap<Row, Row> values = new TreeMap<Row, Row>();
    
	private Schema schema;
	
	private BiFunction<Container, ? super Row, Container> reduceAction = (a, b) -> 
	{ 
    	//System.out.print("a: " + a );
	    Schema s1 = a.schema();
	    Row value = s1.compute(b);
	    if (s1.getParent() != null) {
		    Row key = value.keys(s1);
		    if (!a.containesKey(key)) {
			    a.put(key, value);
		    } else {
			    a.get(key).reduce(value);
		    }
	    } else {
		    if (a.isEmpty()) {
			   
			    //System.out.println("key: " + value + " value: " + value);
			    a.put(value, value);
		    } else {
			    //System.out.println("  key: " + a.firstKey() + " value: " + value);
			    a.get(a.firstKey()).reduce(value);
		    }
	    }
	    return a;
    };
	private BinaryOperator<Container> mergeAction = (a, b) -> 
    {
        // 如果数据模型不一样，则返回a。不进行数据合并。
	    if (a.schema != b.schema) return a;
	    a.merge(b);
	    /*for (Row key : values.keySet()) {
		a.values.merge(key, values.get(key), (r1, r2) -> {
			return r1.reduce(r2);
		});
	    }*/
		return a;
	};
	
	public Container(Schema schema) {
		this.schema = schema;
		
	}
	
	protected void finalize() {
		
	}

	public final synchronized boolean containesKey(Row key) {
		
		return values.containsKey(key);
	}
	
	public final boolean isEmpty() {
		return values.isEmpty();
	}
	
	public Row firstKey() {
		return values.firstKey();
	}
	
	public final synchronized Row get(Row key) {
		return values.get(key);
	}
	
	public final synchronized void put(Row key, Row value) {
		values.put(key, value);
	}
	
	/**
	 * Container MapReduce中间计算过程
	 * @return 返回计算过程接口
	 */
	public final BiFunction<Container, ? super Row, Container> accumulator() 
	{
        return reduceAction;
		
	}
	/**
	 * Container MapReduce结果合并过程
	 * <br>
	 * 只在并发模式下会调用merge过程
	 * @return 返回合并过程接口
	 */
	public final BinaryOperator<Container> combiner() {
		return mergeAction;
	}

	/**
	 * 合并过程，必须使用线程同步
	 * @param other
	 */
	public final synchronized void merge(Container other) 
	{
	    if (other.schema != schema) return;
		for (Row key : values.keySet()) {
			if (other.containesKey(key)) {
			    values.merge(key, values.get(key), (r1, r2) -> {
				    return r1.reduce(r2);
			    });
			}
			//System.out.println("megre: key = " + key + " ,other.containsKey = " + other.containesKey(key));
		}
	}
	
	public Schema schema() {
		return schema;
	}
	
	public int size() {
		return values.size();
	}
	
	public String toString() {
		return values.toString();
	}
    
}
