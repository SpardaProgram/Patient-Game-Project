package com.spardagames.gameobjects;

public enum Naipe {
	Copas(true), Ouros(true), Paus(false), Espadas(false);
	public final boolean vermelho;
	private Naipe(boolean vermelho) {
		this.vermelho=vermelho;
	}

	@Override
	public String toString() {
		switch(this) {
		case Copas:
			return "\u2665";
		case Espadas:
			return "\u2660";
		case Ouros:
			return "\u2666";
		case Paus:
		default:
			return "\u2663";
		}
	
	}
}
