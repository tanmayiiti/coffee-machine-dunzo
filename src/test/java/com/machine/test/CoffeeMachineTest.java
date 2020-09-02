package com.machine.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.machine.input.ConfigInput;
import com.machine.models.Beverage;
import com.machine.models.CoffeeMachine;
import com.machine.models.Ingredient;
import com.machine.runner.CoffeeMaker;

public class CoffeeMachineTest {

	private CoffeeMaker coffeeMaker;

	@Before
	public void instantiateMachine() {
		
		Integer numberOfOutlets = null;
		HashMap<Ingredient, Integer> inventory = new HashMap<>();
		Map<String, Beverage> beverages = new HashMap<>();		
       
        ConfigInput input  = null;
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
		try {
			jsonData = Files.readAllBytes(Paths.get("src/main/resources/input.json"));
			input = objectMapper.readValue(jsonData, ConfigInput.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		numberOfOutlets = input.getMachine().getOutlets().getCountN();
        
        Map<String, Object> totalItems = input.getMachine().getTotalItemsQuantity().getAdditionalProperties();
  
        for(String itemName : totalItems.keySet()) {
        	
        	Ingredient ingredient = new Ingredient(itemName);
        	
        	Integer quantity = (Integer) totalItems.get(itemName);
        	
        	inventory.put(ingredient, quantity);
        	
        }
        
        Map<String, Object>  beveragesConfig =  input.getMachine().getBeverages().getAdditionalProperties();
        
        for(String beverageName : beveragesConfig.keySet()) {
        	
        	@SuppressWarnings("unchecked")
			HashMap<String, Integer> data = (HashMap<String, Integer>) beveragesConfig.get(beverageName);
        	
        	Map<Ingredient, Integer> ingredientQuantity  = new HashMap<>();
        	
        	for(String ing : data.keySet()) {
        		Ingredient ingredien = new Ingredient(ing);
        		Integer quantity = data.get(ing);
        		ingredientQuantity.put(ingredien, quantity);
        	}
        	
        	Beverage beverage = new Beverage(beverageName, ingredientQuantity);
        	
        	beverages.put(beverageName, beverage);
        	
        }
		
		final CoffeeMachine coffeeMachine = CoffeeMachine.getInstance(numberOfOutlets, beverages, inventory);
		
		ExecutorService pool = Executors.newFixedThreadPool(numberOfOutlets);
		
		coffeeMaker = new CoffeeMaker(coffeeMachine, pool);
	}
	
	@Test
    public void test1() {
		
		// testing number of drink can be prepared by giving input and updating inventory in input.json file in resources folder
		
		List<String> beverageNames = new ArrayList<>();
		
		beverageNames.add("black_tea");
		beverageNames.add("hot_tea");
		beverageNames.add("hot_coffee");
		beverageNames.add("black_tea");		
		
		int count = 0;
		
		for(final String beverageName : beverageNames) {
			
			Future<Beverage> future = coffeeMaker.addAnOrder(beverageName);
			
			try {
				Beverage beverage = future.get();
				if(beverage != null) {
					count++;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		assertEquals(4, count);
		
		System.out.println("\n\n");
    }
	

}
