package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadres.Cor;
import xadres.PartidaXadres;
import xadres.PecaXadres;
import xadres.PosicaoXadres;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void limparTela() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadres lerPosicaoXadres(Scanner sc) {

		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadres(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler PosicaoXadres. Valores validos sao de a1 a h8.");
		}
	}

	public static void imprimirPartida(PartidaXadres partidaXadres, List<PecaXadres> capturada) {
		imprimirTabuleiro(partidaXadres.getPeca());
		System.out.println();
		imprimirPacasCapturadas(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaXadres.getTurno());
		if(!partidaXadres.getCheckMate()) {
			System.out.println("Aguardando jogador: " + partidaXadres.getJogadorAtual());
			if (partidaXadres.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaXadres.getJogadorAtual());
		}
	}

	public static void imprimirTabuleiro(PecaXadres[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void imprimirTabuleiro(PecaXadres[][] pecas, boolean[][] possiveisMovimentos) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void imprimirPeca(PecaXadres peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void imprimirPacasCapturadas(List<PecaXadres> capturada) {
		List<PecaXadres> branco = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaXadres> preto = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Pecas capturadas: ");
		System.out.print("Branca: ");
		System.out.print(ANSI_WHITE);
		System.out.print(Arrays.deepToString(branco.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Preta: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.deepToString(preto.toArray()));
		System.out.println(ANSI_RESET);
	}

}
