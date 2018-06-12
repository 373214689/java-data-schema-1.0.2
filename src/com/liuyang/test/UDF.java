package com.liuyang.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Spliterator;
import java.util.Spliterator.OfInt;
import java.util.Spliterator.OfLong;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UDF {
	
	public static IntStream range(int a, int b, int step, int split, boolean parallel) {
		if (a < 0) 
			throw new IllegalArgumentException("the a can not be set 0.");
		if (b < 0) 
			throw new IllegalArgumentException("the b can not be set 0.");
		return StreamSupport.intStream(
				 () -> new IntRanger(a, b, step, split)
				,Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED
				,parallel);
	}
	
	/**
	 * Create an range from a to b.
	 * @param a
	 * @param b
	 * @return range from a to b
	 */
	public static IntStream range(int a, int b, int step, boolean parallel) {
		return range(a, b, step, 10000, parallel);
	}
	
	public static LongStream range(long a, long b, int step, int split, boolean parallel) {
		return StreamSupport.longStream(
				 () -> new LongRanger(a, b, step, split)
				,Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED
				,parallel);
	}
	
	public static LongStream range(long a, long b, int step, boolean parallel) {
		return range(a, b, step, 10000, parallel);
	}
	
	@SuppressWarnings("resource")
	public static Stream<String> readTextFile(String filepath) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filepath)).lines();
	}
	
	public static class IntRanger implements OfInt {
		int min;
		int max;
		int step;
		int split;
		int cursor;
		
		public IntRanger(int a, int b, int step, int spilt) {
			this.max = Math.max(a, b);
			this.min = Math.min(a, b);
			this.cursor = min;
			this.split = spilt;
			this.step = step;
		}
		
		@Override
		public long estimateSize() {
			return max - cursor;
		}

		@Override
		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}

		@Override
		public OfInt trySplit() {
			int remain = max - cursor, lo = cursor, next = cursor + split;
			if (remain <= split) return null;
			return new IntRanger(lo, cursor = next, step, split);
		}

		@Override
		public boolean tryAdvance(IntConsumer action) {
			boolean hasNext = cursor < max;
			if (hasNext) action.accept(cursor++);
			return hasNext;
		}
		
		@Override
        public void forEachRemaining(Consumer<? super Integer> action) {
			do { } while(tryAdvance(action));
		}
		
	}
	
	/**
	 * create range of long
	 * @author liuyang
	 *
	 */
	public static class LongRanger implements OfLong {
		long min;
		long max;
		int step;
		int split;
		long cursor;
		
		public LongRanger(long a, long b, int step, int split) {
			this.max = Math.max(a, b);
			this.min = Math.min(a, b);
			this.cursor = min;
			this.step = step;
			this.split = split;
		}
		
		@Override
		public long estimateSize() {
			return max - cursor;
		}

		@Override
		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}

		@Override
		public OfLong trySplit() {
			long remain = max - cursor, next = cursor + split;
			if (remain < split) return null;
			return new LongRanger(cursor = next, next, step, split);
		}

		@Override
		public boolean tryAdvance(LongConsumer action) {
			boolean hasNext = cursor < max;
			if (hasNext) action.accept(cursor++);
			return hasNext;
		}
		
		@Override
		public void forEachRemaining(LongConsumer action) {
            do { } while (tryAdvance(action));
        }
		
	}
}
