package xadres;

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
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadres peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadres(coluna, linha).paraPosicao());
	}

	private void posicaoInicial() {
		
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
		
		
	}
}
