package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadres.PartidaXadres;
import xadres.PecaXadres;
import xadres.PosicaoXadres;
import xadres.XadresExcecao;

public class ProgramaJogoXadres {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadres partidaXadres1 = new PartidaXadres();
		List<PecaXadres> capturada = new ArrayList<>();

		while (!partidaXadres1.getCheckMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadres1, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadres origem = UI.lerPosicaoXadres(sc);

				boolean[][] possiveisMovimentos = partidaXadres1.possiveisMovimentos(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaXadres1.getPeca(), possiveisMovimentos);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadres destino = UI.lerPosicaoXadres(sc);

				PecaXadres pecaCapturada = partidaXadres1.executarMovimentoXadres(origem, destino);
				
				if(pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
				if (partidaXadres1.getPromovido() != null) {
					System.out.print("Informe a peca para promocao (B/N/R/Q): ");
					String tipo = sc.nextLine();
					partidaXadres1.substituirPecaPromovida(tipo);
				}
			} 
			catch (XadresExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partidaXadres1, capturada);

	}

}
