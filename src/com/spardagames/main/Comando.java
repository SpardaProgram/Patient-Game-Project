package com.spardagames.main;

import java.util.ArrayList;

public class Comando {
	public final TipoComando tipo;
	public final ArrayList<Integer> parametros;

	public Comando(TipoComando tipo, ArrayList<Integer> parametros) {
		this.tipo = tipo;
		this.parametros = new ArrayList<Integer>(parametros);
	}
}
