package com.machine.models;

import java.util.HashMap;
import java.util.Map;

import com.machine.exception.InsufficientIngredientException;
import com.machine.exception.InvalidIngredientException;
import com.machine.service.InventoryStateManager;

public class CoffeeMachine {
	
	private Integer numberOfOutlets;
	private Map<String, Beverage> beverages;
	private HashMap<Ingredient, Integer> inventory;
	
	private static CoffeeMachine coffeeMachine;
	
	private CoffeeMachine(Integer numberOfOutlets, Map<String, Beverage> beverages, HashMap<Ingredient, Integer> inventory) {
		super();
		this.numberOfOutlets = numberOfOutlets;
		this.beverages = beverages;
		this.inventory = inventory;
	}


	public Integer getNumberOfOutlets() {
		return numberOfOutlets;
	}


	public Map<String, Beverage> getBeverages() {
		return beverages;
	}
	
	public static CoffeeMachine getInstance(Integer numberOfOutlets, Map<String, Beverage> beverages, HashMap<Ingredient, Integer> inventory) {
		if(coffeeMachine == null) {
			coffeeMachine = new CoffeeMachine(numberOfOutlets, beverages, inventory);
		}
		return coffeeMachine;
	}
	
	
	public Beverage prepareBeverage(String beverageName) throws InvalidIngredientException, InsufficientIngredientException {
		
		System.out.println("preparing............. "+ Thread.currentThread().getName() + " " + beverageName);
		
		Beverage beverage = beverages.get(beverageName);
		
		Map<Ingredient, Integer> ingredients = beverage.getIngredientes();
		
		InventoryStateManager inventoryStateManager = new InventoryStateManager();
		
		synchronized (inventory) {
			
			Memento memento = getMemento(inventory);
			
			inventoryStateManager.add(memento);
			
			try {
				for(Ingredient ingredient : ingredients.keySet()) {
					
					Integer requiredQuatity = ingredients.get(ingredient);
					
					if(!inventory.containsKey(ingredient)) {
						throw new InvalidIngredientException("Invalid ingradient provided", ingredient);
					}
					
					if(inventory.get(ingredient) < requiredQuatity) {
						throw new InsufficientIngredientException("Invalid ingradient provided", ingredient);
					}
					
					Integer currQuantity = inventory.get(ingredient);
					
					inventory.put(ingredient, currQuantity-requiredQuatity);
					
				}
			}
			catch (InvalidIngredientException | InsufficientIngredientException e) {
			
				Memento previousState = inventoryStateManager.get(0);
				inventory = (HashMap<Ingredient, Integer>) previousState.getInventory();
				throw e;
				
			}
			finally {
				inventoryStateManager.clear();
			}
		}
		
		
		return beverage;
	}


	private Memento getMemento(HashMap<Ingredient, Integer> inventory2) {
		
		@SuppressWarnings("unchecked")
		Map<Ingredient, Integer> inv = (Map<Ingredient, Integer>) inventory2.clone();
		
		return new Memento(inv);
	}

}
