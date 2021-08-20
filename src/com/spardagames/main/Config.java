package com.spardagames.main;

import java.util.HashMap;

public class Config {

	public static final int qtdMostradas = 1;
	public static HashMap<String, TipoComando> mapaComandos = new HashMap<>();
	static {
		mapaComandos.put("s", TipoComando.SOLICITAR);
		mapaComandos.put("msm", TipoComando.MOVIMENTO_MOSTRADO_MONTE);
		mapaComandos.put("mo", TipoComando.MOVIMENTO_MONTE_MONTE_OBJETIVO);
		mapaComandos.put("om", TipoComando.MOVIMENTO_MONTE_OBJETIVO_MONTE);
		mapaComandos.put("mso", TipoComando.MOVIMENTO_MOSTRADO_MONTE_OBJETIVO);
		mapaComandos.put("mm", TipoComando.MOVIMENTO_MONTE_MONTE);

	}
}
