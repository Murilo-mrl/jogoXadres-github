package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;
import xadres.PartidaXadres;
import xadres.PecaXadres;
import xadres.PosicaoXadres;
import xadres.XadresExcecao;

public class ProgramaJogoXadres {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
	PartidaXadres partidaXadres1 = new PartidaXadres();
	
	while(true) {
		try {
			UI.limparTela();
			UI.imprimirTabuleiro(partidaXadres1.getPeca());
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
		}
		catch(XadresExcecao e) {
			System.out.println(e.getMessage());
			sc.nextLine();
		}
		catch(InputMismatchException e) {
			System.out.println(e.getMessage());
			sc.nextLine();
		}
	}
	
	
	}
	
}
