package xadres;


import tabuleiroJogo.Peca;
import tabuleiroJogo.Tabuleiro;

public class PecaXadres extends Peca {
	
	private Cor cor;

	public PecaXadres(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

}
