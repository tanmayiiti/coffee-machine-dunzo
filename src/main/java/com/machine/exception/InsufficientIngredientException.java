package com.machine.exception;

import com.machine.models.Ingredient;

public class InsufficientIngredientException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Ingredient ingradient;

	public InsufficientIngredientException(String message, Ingredient ingradient) {
		super(message);
		this.ingradient = ingradient;
	}

	public Ingredient getIngradient() {
		return ingradient;
	}
	
}
