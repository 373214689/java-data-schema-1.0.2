package com.liuyang.test;

import java.io.FileNotFoundException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import com.liuyang.data.filter.IntFilter;
import com.liuyang.data.func.IntFunction;
import com.liuyang.data.util.Container;
import com.liuyang.data.util.IntValue;
import com.liuyang.data.util.LongValue;
import com.liuyang.data.util.Row;
import com.liuyang.data.util.Schema;
import com.liuyang.data.util.Schema.Type;

public class ValueTest {

	public static void main(String[] args) {
		double s = System.nanoTime(), u = 0.00;
		String filepath = "D:\\root\\test\\cache\\LTE_GZ_YDGZG04100_103000120604_20180427113048.txt";
		LongValue counter = new LongValue();
		
		// 定义原始数据表结构
		Schema schema = getHttpSchema();
		Row row = schema.createRow(); // 声明一个row，主要用于数据缓存
		
		
		Schema resultSchema = Schema.createStruct("result");
		// 定义结果容器，最终结果将会存储到该容器
		Container container = new Container(resultSchema);
		// 定义结果表结构
		resultSchema.addField("local_city", Type.SMALLINT);
		resultSchema.addField("apptype", Type.SMALLINT);
		resultSchema.addField("appsubtype", Type.SMALLINT);
		resultSchema.addField("appserver_ipv4", Type.STRING);
		//resultSchema.addField("enodebid", Type.INT);
		resultSchema.addField("host", Type.STRING);
		resultSchema.addField("ulthroughput", Type.BIGINT);
		resultSchema.addField("dlthroughput", Type.BIGINT);
		

		
		// 选定汇聚map字段, 汇聚的字段越多，会消耗越多的计算时间和储存空间
		//resultSchema.groupby("local_city", "apptype", "appsubtype", "appserver_ipv4", "enodebid");
		resultSchema.groupby("local_city", "apptype", "appsubtype", "appserver_ipv4", "host");
		//resultSchema.groupby("local_city", "imsi");
		//resultSchema.groupby("local_city", "appserver_ipv4");
		
		// compute与reduce区别：
		// 1、compute优先于reduce，是该字段值的生成方式，会调取传入的row，从中取值进行计算；
		// 2、reduce是对compute结果的合并，会调用compute所生成的值，而不会调用row的数据。
		
		// 定义字段的compute过程
		/*resultSchema.getField("enodebid").setValueCompute(e -> {
			int x = e.getInteger("cellid"); // 用clone保护原始数据不被变更         
			return new IntValue((x - x % 256) / 256); // 对数据的值进行计算后封装到IntValue（对应Type.INT）
		});*/
		
		// 定义汇聚的reduce过程
		resultSchema.getField("ulthroughput").setValueReduce((a, b) -> {
			((LongValue) a).compute(x -> x + b.getLong("ulthroughput"));
			return a;
		});
		resultSchema.getField("dlthroughput").setValueReduce((a, b) -> {
			((LongValue) a).compute(x -> x + b.getLong("dlthroughput"));
			return a;
		});
		
        // row.parseLine(e, "|")
		try {
			UDF.readTextFile(filepath)
			   //.parallel() // 设置是否开启多并发模式 ****目前并发模式对于多字段的支持并不好，需要再优化。
			   .map(e -> row.parseLine(e, "|"))   // 将文本行转换为数据行
			   .filter(e -> e.schema().where(e))  // 对数据行进行过滤，前置是要设置schema的rowFilterAction
			   .reduce(container, container.accumulator(), container.combiner()); // 使用container保存mapreduce计算结果
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        u = System.nanoTime() - s;
		System.out.println(String.format("%.3fms %s %s", u / 1000000, row.getId(), counter));
		System.out.println(String.format("container %s", container));

	}
	
	public void test1() {
		double s = System.nanoTime(), u = 0.00;
		IntValue a = new IntValue(1);
		
		IntFunction action = e -> (e + 1000) % 99;
		IntFilter filter = e -> e > 100;
		for(long i = 0; i < 10000000; i++) {
			a.compute(e -> (e + 1000) % 99);
			//a.filter(e -> e > 100);
			//int x = a.getInteger();
			//x = (x + 1000) % 100;
			//a.setValue(x);
			//x = x % 100;
			//a.setValue((a.intValue() + 1000) % 100);
			//a.compute(e -> e % 100);
		}

		u = System.nanoTime() - s;
		
		
		System.out.println(String.format("%.3fms %s", u / 1000000, a));
		
		System.out.println(action.apply(9.7890));
		System.out.println(filter.apply(22.5678));
	}

	public static Schema getHttpSchema() {
		Schema schema = Schema.createStruct("s1u_http");
		schema.addField(Schema.create("length", "int"));
		schema.addField(Schema.create("local_province", "smallint"));
		schema.addField(Schema.create("local_city", "smallint"));
		schema.addField(Schema.create("owner_province", "smallint"));
		schema.addField(Schema.create("owner_city", "smallint"));
		schema.addField(Schema.create("roaming_type", "smallint"));
		schema.addField(Schema.create("interface", "smallint"));
		schema.addField(Schema.create("xdrid", "string"));
		schema.addField(Schema.create("rat", "smallint"));
		schema.addField(Schema.create("imsi", "string"));
		schema.addField(Schema.create("imei", "string"));
		schema.addField(Schema.create("msisdn", "string"));
		schema.addField(Schema.create("iptype", "smallint"));
		schema.addField(Schema.create("sgw_or_ggsn_ip", "string"));
		schema.addField(Schema.create("enb_or_sgsn_ip", "string"));
		schema.addField(Schema.create("pgw_add", "string"));
		schema.addField(Schema.create("sgw_or_ggsn_port", "int"));
		schema.addField(Schema.create("enb_or_sgsn_port", "int"));
		schema.addField(Schema.create("pgw_port", "int"));
		schema.addField(Schema.create("enb_or_sgsn_u_teid", "bigint"));
		schema.addField(Schema.create("sgw_or_ggsn_u_teid", "bigint"));
		schema.addField(Schema.create("tac", "int"));
		schema.addField(Schema.create("cellid", "bigint"));
		schema.addField(Schema.create("apn", "string"));
		schema.addField(Schema.create("apptype_code", "smallint"));
		schema.addField(Schema.create("startdate", "bigint"));
		schema.addField(Schema.create("enddate", "bigint"));
		schema.addField(Schema.create("lon", "double"));
		schema.addField(Schema.create("lat", "double"));
		schema.addField(Schema.create("protocoltype", "int"));
		schema.addField(Schema.create("apptype", "int"));
		schema.addField(Schema.create("appsubtype", "int"));
		schema.addField(Schema.create("appcontent", "smallint"));
		schema.addField(Schema.create("appstatus", "smallint"));
		schema.addField(Schema.create("ip_address_type", "string"));
		schema.addField(Schema.create("useripv4", "string"));
		schema.addField(Schema.create("useripv6", "string"));
		schema.addField(Schema.create("userport", "int"));
		schema.addField(Schema.create("l4_protocal", "smallint"));
		schema.addField(Schema.create("appserver_ipv4", "string"));
		schema.addField(Schema.create("appserver_ipv6", "string"));
		schema.addField(Schema.create("appserver_port", "int"));
		schema.addField(Schema.create("ulthroughput", "bigint"));
		schema.addField(Schema.create("dlthroughput", "bigint"));
		schema.addField(Schema.create("ulpackets", "bigint"));
		schema.addField(Schema.create("dlpackets", "bigint"));
		schema.addField(Schema.create("updura", "bigint"));
		schema.addField(Schema.create("downdura", "bigint"));
		schema.addField(Schema.create("ultcp_disorder_packets", "bigint"));
		schema.addField(Schema.create("dltcp_disorder_packets", "bigint"));
		schema.addField(Schema.create("ultcp_retransfer_packets", "bigint"));
		schema.addField(Schema.create("dltcp_retransfer_packets", "bigint"));
		schema.addField(Schema.create("ultcp_responsetime", "bigint"));
		schema.addField(Schema.create("dltcp_responsetime", "bigint"));
		schema.addField(Schema.create("ultcp_flag_packets", "bigint"));
		schema.addField(Schema.create("dltcp_flag_packets", "bigint"));
		schema.addField(Schema.create("tcplink_responsetime1", "bigint"));
		schema.addField(Schema.create("tcplink_responsetime2", "bigint"));
		schema.addField(Schema.create("windowsize", "bigint"));
		schema.addField(Schema.create("msssize", "bigint"));
		schema.addField(Schema.create("tcplink_count", "smallint"));
		schema.addField(Schema.create("tcplink_state", "smallint"));
		schema.addField(Schema.create("session_end", "smallint"));
		schema.addField(Schema.create("tcp_syn_ack_mum", "smallint"));
		schema.addField(Schema.create("tcp_ack_num", "smallint"));
		schema.addField(Schema.create("tcp12__status", "smallint"));
		schema.addField(Schema.create("tcp23__status", "smallint"));
		schema.addField(Schema.create("repetition", "smallint"));
		schema.addField(Schema.create("version", "smallint"));
		schema.addField(Schema.create("transtype", "smallint"));
		schema.addField(Schema.create("code", "smallint"));
		schema.addField(Schema.create("responsetime", "bigint"));
		schema.addField(Schema.create("lastpacket_delay", "bigint"));
		schema.addField(Schema.create("lastpacketack_delay", "bigint"));
		schema.addField(Schema.create("host", "string"));
		schema.addField(Schema.create("urilength", "int"));
		schema.addField(Schema.create("uri", "string"));
		schema.addField(Schema.create("x_online_host", "string"));
		schema.addField(Schema.create("useragentlength", "int"));
		schema.addField(Schema.create("useragent", "string"));
		schema.addField(Schema.create("content_type", "string"));
		schema.addField(Schema.create("refer_urilength", "int"));
		schema.addField(Schema.create("refer_uri", "string"));
		schema.addField(Schema.create("cookielength", "int"));
		schema.addField(Schema.create("cookie", "string"));
		schema.addField(Schema.create("contentlength", "int"));
		schema.addField(Schema.create("keyword", "string"));
		schema.addField(Schema.create("action", "int"));
		schema.addField(Schema.create("finish", "smallint"));
		schema.addField(Schema.create("delay", "int"));
		schema.addField(Schema.create("browse_tool", "int"));
		schema.addField(Schema.create("portals", "int"));
		schema.addField(Schema.create("locationlength", "int"));
		schema.addField(Schema.create("location", "string"));
		schema.addField(Schema.create("firstrequest", "smallint"));
		schema.addField(Schema.create("useraccount", "bigint"));
		return schema;
	}
}
