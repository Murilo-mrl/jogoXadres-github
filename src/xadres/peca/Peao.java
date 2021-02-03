package xadres.peca;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.Cor;
import xadres.PartidaXadres;
import xadres.PecaXadres;

public class Peao extends PecaXadres {
	
	private PartidaXadres partidaXadres;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadres partidaXadres) {
		super(tabuleiro, cor);
		this.partidaXadres = partidaXadres;
		
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
		
		Posicao p = new Posicao(0, 0)
				;
		if(getCor() == Cor.BRANCO) {
			p.setValor(posicao.getLinha() -1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getcontaMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() -1, posicao.getColuna() -1);
			if(getTabuleiro().posicaoExiste(p) && existeUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			p.setValor(posicao.getLinha() -1, posicao.getColuna() +1);
			if(getTabuleiro().posicaoExiste(p) && existeUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			//#Movimento especial en passant branco
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().posicaoExiste(esquerda) && existeUmaPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadres.getVulneravelAEnPassant()) {
					mat[esquerda.getLinha() -1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().posicaoExiste(direita) && existeUmaPecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadres.getVulneravelAEnPassant()) {
					mat[direita.getLinha() -1][direita.getColuna()] = true;
				}
			}
		}
		
		else {
			p.setValor(posicao.getLinha() +1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() +2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getcontaMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValor(posicao.getLinha() +1, posicao.getColuna() -1);
			if(getTabuleiro().posicaoExiste(p) && existeUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			p.setValor(posicao.getLinha() +1, posicao.getColuna() +1);
			if(getTabuleiro().posicaoExiste(p) && existeUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			//#Movimento especial en passant preto
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().posicaoExiste(esquerda) && existeUmaPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadres.getVulneravelAEnPassant()) {
					mat[esquerda.getLinha() +1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().posicaoExiste(direita) && existeUmaPecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadres.getVulneravelAEnPassant()) {
					mat[direita.getLinha() +1][direita.getColuna()] = true;
				}
			}
			
		}
		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}
	

}
