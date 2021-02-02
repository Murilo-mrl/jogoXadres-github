package xadres.peca;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.Cor;
import xadres.PartidaXadres;
import xadres.PecaXadres;

public class Rei extends PecaXadres {
	
	private PartidaXadres partidaXadres;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadres partidaXadres) {
		super(tabuleiro, cor);
		this.partidaXadres = partidaXadres;

	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadres p = (PecaXadres) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeRookCastling(Posicao posicao) {
		PecaXadres p = (PecaXadres)getTabuleiro().peca(posicao);
		return p !=  null && p instanceof Torre && p.getCor() == getCor() && p.getcontaMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao p = new Posicao(0, 0);

		// Acima

		p.setValor(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo

		p.setValor(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda

		p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita

		p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// NW

		p.setValor(posicao.getLinha() -1, posicao.getColuna() -1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// NE

		p.setValor(posicao.getLinha() -1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// SW

		p.setValor(posicao.getLinha() +1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// SE

		p.setValor(posicao.getLinha() +1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// #Movimento especial Castling(roque)
		if (getcontaMovimento() == 0 && !partidaXadres.getCheck()) {
			//# Movimento especial roque pequeno 
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() +3);
			if (testeRookCastling(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() +2] = true;
			}
		}
			//# Movimento especial roque grande 
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() -4);
			if (testeRookCastling(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() -2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() -3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() -2] = true;
				}
			}
		}
		

		return mat;
	}
		
}
