package xadres;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadres.peca.Bispo;
import xadres.peca.Cavalo;
import xadres.peca.Peao;
import xadres.peca.Rainha;
import xadres.peca.Rei;
import xadres.peca.Torre;

public class PartidaXadres {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadres vulneravelAEnPassant;
	private PecaXadres promovido;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadres() {
		tabuleiro = new Tabuleiro(8, 8);
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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaXadres getVulneravelAEnPassant() {
		return vulneravelAEnPassant;
	}
	
	public PecaXadres getPromovido() {
		return promovido;
	}

	public PecaXadres[][] getPeca() {
		PecaXadres[][] mat = new PecaXadres[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int l = 0; l < tabuleiro.getLinha(); l++) {
			for (int c = 0; c < tabuleiro.getColuna(); c++) {
				mat[l][c] = (PecaXadres) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}

	public boolean[][] possiveisMovimentos(PosicaoXadres posicaoOrigem) {
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

		if (testaCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadresExcecao("Voce nao pode se colocar em check");

		}
		
		PecaXadres pecaMovida =(PecaXadres)tabuleiro.peca(destino);
		
		promovido = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promovido = (PecaXadres)tabuleiro.peca(destino);
				promovido = substituirPecaPromovida("Q");
			}
		}
		
		
		
		check = (testaCheck(oponente(jogadorAtual))) ? true : false;

		if (testaCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		
		//#Movimento especial en passant
		if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() -2 || destino.getLinha() == origem.getLinha() +2)) {
			vulneravelAEnPassant = pecaMovida;
		}
		else {
			vulneravelAEnPassant = null;
		}

		return (PecaXadres) pecaCapturada;
	}
	
	public PecaXadres substituirPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Nao ha peca a ser promovida");
			
		}
		if (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")) {
			return promovido;
			
		}
		
		Posicao pos = promovido.getPosicaoXadres().paraPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadres  novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	private PecaXadres novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("N")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	
	
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadres p = (PecaXadres) tabuleiro.removerPeca(origem);
		p.incrementaMovimento();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// #Movimento especial roque pequeno
		if( p instanceof Rei && destino.getColuna() == origem.getColuna() +2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() +3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() +1);
			PecaXadres torre = (PecaXadres)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.incrementaMovimento();
		}
		// #Movimento especial roque grande
				if( p instanceof Rei && destino.getColuna() == origem.getColuna() -2) {
					Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() -4);
					Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() -1);
					PecaXadres torre = (PecaXadres)tabuleiro.removerPeca(origemT);
					tabuleiro.colocarPeca(torre, destinoT);
					torre.incrementaMovimento();
				}
				
				//#Movimento especial en passant
				if (p instanceof Peao) {
					if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
						Posicao posicaoPeao;
						if(p.getCor() == Cor.BRANCO) {
							posicaoPeao = new Posicao(destino.getLinha() +1, destino.getColuna());
							
						}
						else {
							posicaoPeao = new Posicao(destino.getLinha() -1, destino.getColuna());
														
						}
						pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
						pecasCapturadas.add(pecaCapturada);
						pecasNoTabuleiro.remove(pecaCapturada);
					}
				}
				
		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadres p = (PecaXadres) tabuleiro.removerPeca(destino);
		p.decrementaMovimento();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
		// #Movimento especial roque pequeno
		if( p instanceof Rei && destino.getColuna() == origem.getColuna() +2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() +3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() +1);
			PecaXadres torre = (PecaXadres)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementaMovimento();
		}
			// #Movimento especial roque grande
		if( p instanceof Rei && destino.getColuna() == origem.getColuna() -2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() -4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() -1);
			PecaXadres torre = (PecaXadres)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementaMovimento();
		} 
		
		//#Movimento especial en passant
		if (p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				PecaXadres peao = (PecaXadres)tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if(p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());
					
				}
				else {
					posicaoPeao = new Posicao(4, destino.getColuna());
												
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}	
		
	}

	private void validarPosicaoOriginal(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadresExcecao("Nao tem uma peça na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadres) tabuleiro.peca(posicao)).getCor()) {
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
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		// "?" = (então) e ":" = ( caso contrário)
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadres rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadres) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadres) p;
			}
		}
		throw new IllegalStateException("Nao ha um Rei da cor " + cor + " no tabuleiro");
	}

	private boolean testaCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadres().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadres) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testaCheckMate(Cor cor) {
		if (!testaCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadres) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinha(); i++) {
				for (int j = 0; j < tabuleiro.getColuna(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadres) p).getPosicaoXadres().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testaCheck = testaCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testaCheck) {
							return false;
						}

					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadres peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadres(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void posicaoInicial() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
