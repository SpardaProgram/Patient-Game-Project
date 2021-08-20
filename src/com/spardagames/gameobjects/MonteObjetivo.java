package com.spardagames.gameobjects;

import java.util.ArrayDeque;

public class MonteObjetivo extends ArrayDeque<Card> {
	
	private static final long serialVersionUID = 1L;

	public MonteObjetivo() {
		
	}

	@Override
	public String toString() {
		
		if(this.isEmpty()) {
			return "(  )";
		}
		String stringResultado = "";
		for(Card card:this) {
			stringResultado+=card;
		}
		
		stringResultado+= "  -  topo  -"+this.peek();
		return stringResultado ;
	}
}
