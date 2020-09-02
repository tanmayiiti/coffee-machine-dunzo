package com.machine.service;

import java.util.ArrayList;
import java.util.List;

import com.machine.models.Memento;

public class InventoryStateManager {
	
	private List<Memento> mementos = new ArrayList<>();
	
	public InventoryStateManager() {
		super();
	}

	public void add(Memento memento) {
		mementos.add(memento);
	}

	public Memento get(int index) {
		if(index >= mementos.size()) {
			return null;
		}
		return mementos.get(index);
	}
	
	public void clear() {
		mementos.clear();
	}
	
}
