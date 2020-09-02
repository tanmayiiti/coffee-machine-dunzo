package com.machine.models;

import java.util.Map;

public class Beverage {
	
	private String name;
	private Map<Ingredient, Integer> ingredientes;
	
	
	public Beverage(String name, Map<Ingredient, Integer> ingredientes) {
		super();
		this.name = name;
		this.ingredientes = ingredientes;
	}


	public String getName() {
		return name;
	}


	public Map<Ingredient, Integer> getIngredientes() {
		return ingredientes;
	}
	
}
