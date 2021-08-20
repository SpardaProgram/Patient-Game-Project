package com.spardagames.gameobjects;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;


public class Baralho extends ArrayDeque<Card> {
	
	
	
	private static final long serialVersionUID = 1L;

	public Baralho() {
		for(Naipe naipe : Naipe.values()) {
			for(int i = 1; i<=13; i++) {
				this.add(new Card(i,naipe));
			}
		}
	}
	
	public void embaralhar() {
		ArrayList <Card> baralho = new ArrayList<>();
		while(!this.isEmpty()) {
			Card x = this.pop();
			baralho.add(x);
			
		}
		Collections.shuffle(baralho);
		for(Card card : baralho) {
			this.push(card);
		}
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

