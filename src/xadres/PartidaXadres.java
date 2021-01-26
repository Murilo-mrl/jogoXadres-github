package xadres;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.peca.Rei;
import xadres.peca.Torre;

public class PartidaXadres {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadres() {
		tabuleiro = new Tabuleiro(8,8);
		posicaoInicial();
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

	private void posicaoInicial() {
		tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(0, 0));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(0, 4));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
	}
}
