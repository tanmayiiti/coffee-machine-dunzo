package com.machine.models;

import java.util.Map;

public class Memento {
	
	private Map<Ingredient, Integer> inventory;

	public Memento(Map<Ingredient, Integer> inventory) {
		super();
		this.inventory = inventory;
	}

	public Map<Ingredient, Integer> getInventory() {
		return inventory;
	}

}
