package com.helper.ws;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public void add(double weight, E result) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
        //return this;
    }
    
    public boolean isEmpty() {
    	return map.isEmpty();
    }
    
    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
    
	public static void main(String args[]) {
		RandomCollection<String> rc = new RandomCollection<>();
				rc.add(2, "dog");
				rc.add(6, "cat");
				rc.add(2, "horse");

		for (int i = 0; i < 10; i++) {
			System.out.println(rc.next());
		}
	}
}