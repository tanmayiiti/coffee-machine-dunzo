package com.machine.runner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.machine.exception.InsufficientIngredientException;
import com.machine.exception.InvalidIngredientException;
import com.machine.models.Beverage;
import com.machine.models.CoffeeMachine;


public class CoffeeMaker {
	
	private CoffeeMachine coffeeMachine;
	private ExecutorService pool;
	
	public CoffeeMaker(CoffeeMachine coffeeMachine, ExecutorService pool) {
		super();
		this.coffeeMachine = coffeeMachine;
		this.pool = pool;
	}
	
	public Future<Beverage> addAnOrder(final String beverageName) {
		
		Callable<Beverage> order = new Callable<Beverage>() {

			@Override
			public Beverage call() throws Exception {
				try {
					
					Beverage beverage = coffeeMachine.prepareBeverage(beverageName);
					
					System.out.println(beverageName + " is prepared");
					
					return beverage;
					
				} catch (InvalidIngredientException e) {
					
					System.out.println( beverageName +  " cannot be prepared because " +  e.getIngradient().getName() + " is not available");
					
				} catch (InsufficientIngredientException e) {
					System.out.println( beverageName + " cannot be prepared because item "  + e.getIngradient().getName() + " is not sufficient");
				}
				return null;
			}
			
		};
		
		Future<Beverage> future = pool.submit(order);
		
		return future;		
	}


}
