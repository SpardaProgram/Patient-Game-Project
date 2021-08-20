package com.spardagames.main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import com.spardagames.exceptions.ComandoInvalidoException;
import com.spardagames.exceptions.MovimentoImpossivelException;
import com.spardagames.gameobjects.Baralho;
import com.spardagames.gameobjects.BaralhoMostrado;
import com.spardagames.gameobjects.Card;
import com.spardagames.gameobjects.MonteMesa;
import com.spardagames.gameobjects.MonteObjetivo;

public class Game {
	private static Baralho baralho;
	private static BaralhoMostrado baralhoMostrado;
	private static ArrayList<MonteMesa> montesMesa;
	private static ArrayList<MonteObjetivo> montesObjetivo;
	private static boolean jogoAcabou;
	private static Scanner scan;

	public static void main(String[] args) {
		scan = new Scanner(System.in);
		novoJogo();
		while (!jogoAcabou) {
			printBaralho();

			printMontesObjetivo();

			printMontesMesa();

			printBaralhoMostrado();

			Comando comandoAtual;
			try {
				comandoAtual = recebeComando();
				switch (comandoAtual.tipo) {
				case MOVIMENTO_MONTE_MONTE:
					int qtdCartas = comandoAtual.parametros.get(0);
					int monteOrigem = comandoAtual.parametros.get(1);
					int monteDestino = comandoAtual.parametros.get(2);
					moverMonteMonte(qtdCartas, monteOrigem, monteDestino);
					break;
				case MOVIMENTO_MONTE_MONTE_OBJETIVO:
					moverMesaObjetivo(comandoAtual.parametros.get(0), comandoAtual.parametros.get(1));
					break;
				case MOVIMENTO_MONTE_OBJETIVO_MONTE:
					moverObjetivoMonte(comandoAtual.parametros.get(0), comandoAtual.parametros.get(1));
					break;
				case MOVIMENTO_MOSTRADO_MONTE:
					moverMostradoMonte(comandoAtual.parametros.get(0));
					break;
				case MOVIMENTO_MOSTRADO_MONTE_OBJETIVO:
					moverMostradoObjetivo(comandoAtual.parametros.get(0));
					break;
				case SOLICITAR:
				default:
					solicitarCarta();
					break;

				}
				if (detectaFimDoJogo()) {
					jogoAcabou = true;
				}
			} catch (ComandoInvalidoException e) {
				System.out.println(e.getMessage());
			} catch (MovimentoImpossivelException e) {
				System.out.println("Movimento Impossível: " + e.getMessage());
			}

		}
		scan.close();
		System.out.println("Você Ganhou!!!!!!!!!!!!!!");
	}

	private static boolean detectaFimDoJogo() {
		for (MonteObjetivo o : montesObjetivo) {
			if (o.size() < 13) {
				return false;
			}
		}
		return true;

	}

	private static void moverMonteMonte(int qtdCartas, int monteOrigem, int monteDestino) {
		MonteMesa monteAtual = montesMesa.get(monteOrigem);
		if (monteAtual.isEmpty()) {
			throw new MovimentoImpossivelException("O monte selecionado está vazio!");
		}
		ArrayList<Card> cartasReveladas = new ArrayList<Card>();
		int qtdReveladas = 0;
		for (Card c : monteAtual) {
			if (c.mostrada) {
				qtdReveladas++;
				cartasReveladas.add(c);
			}

		}
		if (qtdCartas > qtdReveladas) {
			throw new MovimentoImpossivelException("Há cartas selecionadas não reveladas!");
		}
		Card cartaReferencia = cartasReveladas.get(qtdCartas - 1);
		MonteMesa monteFinal = montesMesa.get(monteDestino);
		if (monteFinal.isEmpty()) {
			if (cartaReferencia.valor != 13) {
				throw new MovimentoImpossivelException("Topo da pilha movida precisa ter valor 13!");
			}
		} else {
			Card topoFinal = monteFinal.peek();
			if (topoFinal.isVermelho() == cartaReferencia.isVermelho()) {
				throw new MovimentoImpossivelException("As cores das cartas devem ser diferentes!");
			}
			if (topoFinal.valor != cartaReferencia.valor + 1) {
				throw new MovimentoImpossivelException("A diferença entre os valores deve ser 1!");
			}
		}
		ArrayDeque<Card> deckTemporario = new ArrayDeque<Card>();
		for (int i = 0; i < qtdCartas; i++) {
			deckTemporario.push(monteAtual.pop());
		}
		for (int i = 0; i < qtdCartas; i++) {
			monteFinal.push(deckTemporario.pop());
		}
		if (!monteAtual.isEmpty()) {
			monteAtual.peek().mostrada = true;
		}

	}

	private static void moverMesaObjetivo(int monteMesaOrigem, int monteObjetivoDestino) {
		MonteMesa monteMesa = montesMesa.get(monteMesaOrigem);
		if (monteMesa.isEmpty()) {
			throw new MovimentoImpossivelException("O monte selecionado está vazio!");
		}
		Card topoMesa = monteMesa.peek();
		MonteObjetivo monteObjetivo = montesObjetivo.get(monteObjetivoDestino);
		if (monteObjetivo.isEmpty()) {
			if (topoMesa.valor != 1) {
				throw new MovimentoImpossivelException("A carta deve valer 1!");
			}
		} else {
			Card topoObjetivo = monteObjetivo.peek();
			if (topoObjetivo.isVermelho() != topoMesa.isVermelho()) {
				throw new MovimentoImpossivelException("As cartas devem ter naipes iguais!");
			}
			if (topoMesa.valor - 1 != topoObjetivo.valor) {
				throw new MovimentoImpossivelException("A diferença entre os valores deve ser 1!");
			}
		}
		monteObjetivo.push(monteMesa.pop());
		if (!monteMesa.isEmpty()) {
			monteMesa.peek().mostrada = true;
		}

	}

	private static void moverObjetivoMonte(int objetivoOrigem, int monteDestino) {
		MonteObjetivo monteObjetivo = montesObjetivo.get(objetivoOrigem);
		if (monteObjetivo.isEmpty()) {
			throw new MovimentoImpossivelException("O monte selecionado está vazio!");
		}
		Card topoObjetivo = monteObjetivo.peek();
		MonteMesa monteMesa = montesMesa.get(monteDestino);
		if (monteMesa.isEmpty()) {
			if (topoObjetivo.valor != 13) {
				throw new MovimentoImpossivelException("A carta a ser movida deve valer 13!");
			}
		} else {
			Card topoMesa = monteMesa.peek();
			if (topoObjetivo.valor + 1 != topoMesa.valor) {
				throw new MovimentoImpossivelException("A diferencça entre os valores da carta deve ser 1!");
			}
			if (topoObjetivo.isVermelho() == topoMesa.isVermelho()) {
				throw new MovimentoImpossivelException("As cartas devem ser de cores diferentes!");
			}
		}
		monteMesa.push(monteObjetivo.pop());
	}

	private static void moverMostradoObjetivo(int objetivoDestino) {
		if (baralhoMostrado.isEmpty()) {
			throw new MovimentoImpossivelException("O baralho mostrado está vazio!");
		}
		MonteObjetivo monteObjetivoDestino = montesObjetivo.get(objetivoDestino);
		Card topoMostrado = baralhoMostrado.peek();
		if (monteObjetivoDestino.isEmpty()) {
			if (topoMostrado.valor != 1) {
				throw new MovimentoImpossivelException("A carta deve valer 1");
			}
		} else {
			Card topoMonteObjetivo = monteObjetivoDestino.peek();
			if (topoMostrado.naipe != topoMonteObjetivo.naipe) {
				throw new MovimentoImpossivelException("Os naipes devem ser iguais!");
			}
			if (topoMostrado.valor - 1 != topoMonteObjetivo.valor) {
				throw new MovimentoImpossivelException("Diferença entre valores precisa ser 1!");
			}
		}
		monteObjetivoDestino.push(baralhoMostrado.pop());
	}

	private static void moverMostradoMonte(int monteDestino) {
		if (baralhoMostrado.isEmpty()) {
			throw new MovimentoImpossivelException("O baralho mostrado está vazio!");
		}
		MonteMesa monteMesaDestino = montesMesa.get(monteDestino);
		Card topoMostrado = baralhoMostrado.peek();
		if (monteMesaDestino.isEmpty()) {
			if (topoMostrado.valor != 13) {
				throw new MovimentoImpossivelException("A carta deve valer 13!");
			}
		} else {

			Card topoMonteMesa = monteMesaDestino.peek();
			if (topoMostrado.isVermelho() == topoMonteMesa.isVermelho()) {
				throw new MovimentoImpossivelException("As cores devem ser diferente!");
			}
			if (topoMostrado.valor + 1 != topoMonteMesa.valor) {
				throw new MovimentoImpossivelException("Diferença entre valores precisa ser 1!");
			}

		}
		monteMesaDestino.push(baralhoMostrado.pop());

	}

	private static void printBaralho() {
		System.out.println("qtd Cartas Baralho: " + baralho.size());

	}

	private static void novoJogo() {
		baralho = new Baralho();
		baralho.embaralhar();
		montesMesa = new ArrayList<MonteMesa>(7);
		montesObjetivo = new ArrayList<MonteObjetivo>(4);
		baralhoMostrado = new BaralhoMostrado();
		for (int i = 0; i < 7; i++) {
			MonteMesa monteAtual = new MonteMesa();
			for (int j = 0; j <= i; j++) {
				monteAtual.push(baralho.pop());

			}

			montesMesa.add(monteAtual);

		}
		for (MonteMesa m : montesMesa) {
			m.peek().mostrada = true;
		}

		for (int i = 0; i < 4; i++) {
			montesObjetivo.add(new MonteObjetivo());
		}
		jogoAcabou = false;

	}

	private static Comando recebeComando() throws ComandoInvalidoException {
		String nextLine = scan.nextLine();
		String[] split = nextLine.split(" ");
		TipoComando tipoComando = Config.mapaComandos.get(split[0]);
		if (tipoComando == null) {
			throw new ComandoInvalidoException("comando inválido");
		}
		ArrayList<Integer> parametros = new ArrayList<Integer>();

		for (int i = 1; i < split.length; i++) {

			try {
				int inteiro = Integer.parseInt(split[i]);
				parametros.add(inteiro);
			} catch (NumberFormatException e) {
				throw new ComandoInvalidoException("parâmetros inválidos");
			}
		}
		if (parametros.size() != tipoComando.qtdParametros) {
			throw new ComandoInvalidoException("quantidade de parâmetros inválida");
		}
		switch (tipoComando) {
		case MOVIMENTO_MONTE_MONTE:
			int qtdCartas = parametros.get(0);
			int monteOrigem = parametros.get(1);
			int monteDestino = parametros.get(2);
			if (qtdCartas < 1 || qtdCartas > 13) {
				throw new ComandoInvalidoException("O número de cartas a ser movido precisa estar entre 1 e 13!");
			}
			if (monteOrigem < 0 || monteOrigem > 6 || monteDestino < 0 || monteDestino > 6) {
				throw new ComandoInvalidoException("Os montes precisam estar entre 0 e 6!");
			}
			if (monteOrigem == monteDestino) {
				throw new ComandoInvalidoException("Os montes de origem e de destino precisam ser diferentes");
			}
			break;
		case MOVIMENTO_MONTE_MONTE_OBJETIVO:
			if (parametros.get(0) < 0 || parametros.get(0) > 6) {
				throw new ComandoInvalidoException("Monte Mesa precisa estar entre 0 e 6");
			}
			if (parametros.get(1) < 0 || parametros.get(1) > 3) {
				throw new ComandoInvalidoException("Monte Objetivo precisa estar entre 0 e 3!");
			}
			break;
		case MOVIMENTO_MONTE_OBJETIVO_MONTE:
			if (parametros.get(0) < 0 || parametros.get(0) > 3) {
				throw new ComandoInvalidoException("Monte Objetivo precisa estar entre 0 e 3!");
			}
			if (parametros.get(1) < 0 || parametros.get(1) > 6) {
				throw new ComandoInvalidoException("Monte Mesa precisa estar entre 0 e 6");
			}
			break;
		case MOVIMENTO_MOSTRADO_MONTE:
			if (parametros.get(0) < 0 || parametros.get(0) > 6) {
				throw new ComandoInvalidoException("Coluna precisa estar entre 0 e 6");
			}
			break;
		case MOVIMENTO_MOSTRADO_MONTE_OBJETIVO:
			if (parametros.get(0) < 0 || parametros.get(0) > 3) {
				throw new ComandoInvalidoException("Objetivo precisa estar entre 0 e 3");
			}
			break;
		case SOLICITAR:
		default:

		}
		return new Comando(tipoComando, parametros);
	}

	public static void printMontesMesa() {
		System.out.println("Montes Mesa");
		int i = 0;
		for (MonteMesa m : montesMesa) {
			System.out.println(i + "-> " + m);
			i++;
		}

	}

	public static void printMontesObjetivo() {
		System.out.println("Montes Objetivo");
		for (MonteObjetivo m : montesObjetivo) {
			System.out.println(m);
		}
	}

	public static void printBaralhoMostrado() {
		System.out.println("Restante do Baralho");
		System.out.println(baralhoMostrado);
	}

	public static void solicitarCarta() {
		if (Config.qtdMostradas <= baralho.size()) {
			for (int i = 0; i < Config.qtdMostradas; i++) {
				Card cartaRevelada = baralho.pop();
				cartaRevelada.mostrada = true;
				baralhoMostrado.push(cartaRevelada);

			}
		} else {
			if (baralho.size() == 0) {
				while (!baralhoMostrado.isEmpty()) {
					Card cartaEscondida = baralhoMostrado.pop();
					cartaEscondida.mostrada = false;
					baralho.push(cartaEscondida);
				}

			} else {
				while (!baralho.isEmpty()) {
					Card cartaRevelada = baralho.pop();
					cartaRevelada.mostrada = true;
					baralhoMostrado.push(cartaRevelada);
				}
			}
		}
	}

}
