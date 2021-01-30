package xadres;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.peca.Rei;
import xadres.peca.Torre;

public class PartidaXadres {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	public PartidaXadres() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		posicaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] possiveisMovimentos(PosicaoXadres posicaoOrigem){
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOriginal(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public PecaXadres executarMovimentoXadres(PosicaoXadres posicaoOrigem, PosicaoXadres posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOriginal(origem);
		ValidarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		proximoTurno();
		return (PecaXadres)pecaCapturada;
	}
		
	private Peca fazerMovimento(Posicao origem, Posicao destino){
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}	
	
	private void validarPosicaoOriginal(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadresExcecao("Não tem uma peça na posição de origem");
		}
		if ( jogadorAtual != ((PecaXadres)tabuleiro.peca(posicao)).getCor()) {
			throw new XadresExcecao("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).haAlgumPossivelMovimento()) {
			throw new XadresExcecao("Nao existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void ValidarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadresExcecao("A peca escolhida nao pode ser movida para a posicao de destino");
		}
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		// "?" = (então) e ":" = ( caso contrário)
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
