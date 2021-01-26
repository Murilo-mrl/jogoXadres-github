package xadres;

import tabuleiroJogo.Tabuleiro;

public class PartidaXadres {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadres() {
		tabuleiro = new Tabuleiro(8,8);
	}
	
	public  PecaXadres[][] getPeca(){
		PecaXadres[][] mat = new PecaXadres[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for( int l=0; l<tabuleiro.getLinha(); l++) {
			for( int c=0; c<tabuleiro.getColuna(); c++) {
				mat[l][c] = (PecaXadres) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}

	
}
