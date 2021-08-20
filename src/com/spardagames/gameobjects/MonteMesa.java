package com.spardagames.gameobjects;

import java.util.ArrayDeque;


public class MonteMesa extends ArrayDeque<Card> {
	

	private static final long serialVersionUID = 1L;

	public MonteMesa() {
		
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
		return stringResultado;
	}
}
