package xadres.peca;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.Cor;
import xadres.PecaXadres;

public class Peao extends PecaXadres {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
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
			
		}
		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}
	

}
