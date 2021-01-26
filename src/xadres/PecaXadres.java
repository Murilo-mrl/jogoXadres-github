package xadres;

import java.awt.Color;
import tabuleiroJogo.Peca;
import tabuleiroJogo.Tabuleiro;

public class PecaXadres extends Peca {
	
	private Color color;

	public PecaXadres(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
