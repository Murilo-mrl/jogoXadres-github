package tabuleiroJogo;

public class Tabuleiro {

	private int linha;
	private int coluna;
	private Peca[][] pecas;
	
	
	public Tabuleiro(int linha, int coluna) {
		if(linha < 1 || coluna < 1) {
			throw new TabuleiroExcecao("Erro ao criar o tabuleiro: deve ter no minimo 1 linha e 1 coluna");
		}
		this.linha = linha;
		this.coluna = coluna;
		pecas = new Peca[linha][coluna];
	}


	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new TabuleiroExcecao("N�o existe essa posi��o no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("N�o existe essa posi��o no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Peca peca1, Posicao posicao1) {
		if (temUmaPeca(posicao1)) {
			throw new TabuleiroExcecao("J� existe uma pe�a nessa posi��o: " + posicao1);
		}
		pecas[posicao1.getLinha()][posicao1.getColuna()] = peca1;
		peca1.posicao = posicao1;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < this.linha && coluna >= 0 && coluna < this.coluna;

		}
	
	public boolean posicaoExiste( Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean temUmaPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("N�o existe essa posi��o no tabuleiro");
		}
		return peca(posicao) != null;
	}

}
