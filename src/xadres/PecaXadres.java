package xadres;


import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;

public abstract class PecaXadres extends Peca {
	
	private Cor cor;
	private int contaMovimento;

	public PecaXadres(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	public int getcontaMovimento() {
		return contaMovimento;
	}
	
	public void incrementaMovimento() {
		contaMovimento++;
	}
	
	public void decrementaMovimento() {
		contaMovimento--;
	}
	
	public PosicaoXadres getPosicaoXadres() {
		return PosicaoXadres.daPosicao(posicao);
	}
	
	protected boolean existeUmaPecaOponente(Posicao posicao) {
		PecaXadres p = (PecaXadres)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
