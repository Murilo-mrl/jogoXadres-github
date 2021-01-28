package aplicacao;

import java.util.Scanner;

import xadres.PartidaXadres;
import xadres.PecaXadres;
import xadres.PosicaoXadres;

public class ProgramaJogoXadres {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
	PartidaXadres partidaXadres1 = new PartidaXadres();
	
	while(true) {
		UI.imprimirTabuleiro(partidaXadres1.getPeca());
		System.out.println();
		System.out.print("Origem: ");
		PosicaoXadres origem = UI.lerPosicaoXadres(sc);
		
		System.out.println();
		System.out.print("Destino: ");
		PosicaoXadres destino = UI.lerPosicaoXadres(sc);
		
		PecaXadres pecaCapturada = partidaXadres1.executarMovimentoXadres(origem, destino);
	}
	
	
	}
	
}
