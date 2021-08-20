package com.spardagames.main;

public enum TipoComando {
SOLICITAR(0),
MOVIMENTO_MONTE_MONTE(3),
MOVIMENTO_MONTE_MONTE_OBJETIVO(2),
MOVIMENTO_MOSTRADO_MONTE(1),
MOVIMENTO_MOSTRADO_MONTE_OBJETIVO(1),
MOVIMENTO_MONTE_OBJETIVO_MONTE(2);

public final int qtdParametros;
private TipoComando(int qtdParametros) {
	this.qtdParametros=qtdParametros;
	
}
}