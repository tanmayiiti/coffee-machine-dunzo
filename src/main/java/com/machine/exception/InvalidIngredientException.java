package com.machine.exception;

import com.machine.models.Ingredient;

public class InvalidIngredientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Ingredient ingradient;


	public InvalidIngredientException(String message, Ingredient ingradient) {
		super(message);
		this.ingradient = ingradient;
	}


	public Ingredient getIngradient() {
		return ingradient;
	}	
	
	
	
}
