package com.spardagames.gameobjects;



public class Card {
	
	public final int valor;
	public final Naipe naipe;
	public boolean mostrada;
	

	public Card(int valor, Naipe naipe) {
		
		this.mostrada = false;
		this.valor = valor;
		this.naipe = naipe;
		
		
	}
	
	public boolean isVermelho() {
		return naipe.vermelho;
	}
	@Override
	public String toString() {
		if(mostrada) {
			return "("+valor+naipe.toString()+")";
		}else {
			
			return "(**)";
		
		}
		
		
	}
	
	

}
