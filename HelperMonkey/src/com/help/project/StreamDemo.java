package com.help.project;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDemo {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Stream<Integer> streamIterated = Stream.iterate(1, n -> n + 3).limit(20);
//
//		Map<Integer, Long> obj = streamIterated.filter(item -> item%2 == 0 )
//				.collect(Collectors.groupingBy(item -> item%4, Collectors.counting()) );
//		
//		System.out.println(obj);
//		//.forEach(System.out::println);

		
//		long start = System.currentTimeMillis();
//		IntStream s = IntStream.range(0, 20);
//		//System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
//		s.parallel().forEach(i -> {
//		    try { Thread.sleep(100); } catch (Exception ignore) {}
//		    System.out.print((System.currentTimeMillis() - start) + " ");
//		});

		StreamDemo strDemo = new StreamDemo();
		
		final int parallelism = 8; //Runtime.getRuntime().availableProcessors();
		System.out.println("Parallelism: " + parallelism);
		ForkJoinPool forkJoinPool = null;
		long start = System.currentTimeMillis();
		try {
		    forkJoinPool = new ForkJoinPool(parallelism);
		    final List<Integer> primes = forkJoinPool.submit(() ->
		        // Parallel task here, for example
		        IntStream.range(1, 30000000)
		        		.parallel()
		                .filter( item  -> strDemo.isPrime(item))
		                .boxed().collect(Collectors.toList())
		    ).get();
		    System.out.println((System.currentTimeMillis() - start) + " ");
		    System.out.println(primes.size());
		} catch (InterruptedException | ExecutionException e) {
		    throw new RuntimeException(e);
		} finally {
		    if (forkJoinPool != null) {
		        forkJoinPool.shutdown();
		    }
		}
		
	}
	
	private boolean isPrime(int n) {
	    return n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(divisor -> n % divisor == 0);
	}	

}
