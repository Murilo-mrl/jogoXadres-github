package xadres.peca;

import tabuleiroJogo.Tabuleiro;
import xadres.Cor;
import xadres.PecaXadres;

public class Rei extends PecaXadres {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	
	}

	@Override
	public String toString() {
		return "K";
	}
}
