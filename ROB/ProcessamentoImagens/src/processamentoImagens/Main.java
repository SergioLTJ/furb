package processamentoImagens;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.JFrame;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class Main {

	private static Direcao orientacao = Direcao.Oeste;
	private static NXTRegulatedMotor motor1 = Motor.A;
	private static NXTRegulatedMotor motor2 = Motor.B;
	
	public static void main(String[] args) {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
		
		PlanarImage imagem = JAI.create("fileload", "C:\\Cenario.gif");

		Point posicaoRobo = encontrarPosicaoRobo(imagem);
		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(imagem);
		pb.add(100.0);
		
		PlanarImage novaImagem = JAI.create("binarize", pb);
		
		float[] estrutura = {
				0, 0, 0, 0, 0,
				0, 1, 1, 1, 0,
				0, 1, 1, 1, 0,
				0, 1, 1, 1, 0,
				0, 0, 0, 0, 0,
		};
		
		KernelJAI kernel = new KernelJAI(5, 5, estrutura);
		
		pb = new ParameterBlock();
		pb.addSource(novaImagem);
		pb.add(kernel);
		novaImagem = JAI.create("erode", pb);
		
		pb = new ParameterBlock();
		pb.addSource(novaImagem);
		pb.add(kernel);
		novaImagem = JAI.create("dilate", pb);

		int[][] matriz = new int[6][7];
		
		int tamanhoLinha = novaImagem.getHeight() / 6;
		int tamanhoColuna = novaImagem.getWidth() / 7;

		BufferedImage imagemJava = novaImagem.getAsBufferedImage();
		RandomIter iterator = RandomIterFactory.create(imagemJava, null);
		int[] pixel = new int[3];
		
		int quantidadePontoCelula = tamanhoLinha * tamanhoColuna;
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				
				int inicialLinha = tamanhoLinha * i;
				int inicialColuna = tamanhoColuna * j;
				int quantidadePontosPretos = 0;
				
				for (int k = inicialLinha; k < inicialLinha + tamanhoLinha; k++) {
					for (int l = inicialColuna; l < inicialColuna + tamanhoColuna; l++) {
						iterator.getPixel(l, k, pixel); 
						if (pixel[0] == 0) {
							quantidadePontosPretos++;
						}
					}
				}
				
				if (quantidadePontosPretos > (quantidadePontoCelula * 0.1)) {
					matriz[i][j] = 1;
				} else {
					matriz[i][j] = 0;
				}
			}
		}

		int posicaoXRoboto = posicaoRobo.x / tamanhoLinha;
		int posicaoYRoboto = posicaoRobo.y / tamanhoColuna;

		Point posicaoRoboMatriz = new Point(posicaoXRoboto, posicaoYRoboto);
		
		matriz[posicaoXRoboto][posicaoYRoboto] = 255;
		
		Point objetivo = new Point(1, 0);
		
		matriz[objetivo.x][objetivo.y] = 2; // Objetivo
		
		LinkedList<Point> pontos = new LinkedList<>();
		pontos.add(objetivo);
		
		while(!pontos.isEmpty()) {
			Point ponto = pontos.pollFirst();
			processarPonto(matriz, pontos, ponto, ponto.x, ponto.y + 1);
			processarPonto(matriz, pontos, ponto, ponto.x + 1, ponto.y);
			processarPonto(matriz, pontos, ponto, ponto.x, ponto.y - 1);
			processarPonto(matriz, pontos, ponto, ponto.x - 1, ponto.y);
		}
		
		List<Movimento> movimentos = gerarMovimentos(matriz, posicaoRoboMatriz, objetivo); 
		
		System.out.println(movimentos);
		
		JFrame frame = new JFrame("teste");
		frame.getContentPane().add(new DisplayTwoSynchronizedImages(imagem, novaImagem));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static List<Movimento> gerarMovimentos(int[][] matriz, Point posicaoInicial, Point objetivo) {
		List<Point> pontos = new ArrayList<>();
		
		Point posicaoAtual = posicaoInicial;
		pontos.add(posicaoInicial);
		
		while(posicaoAtual.x != objetivo.x ||
			  posicaoAtual.y != objetivo.y) {
			// Cria um ponto inexistente para facilitar o processamento.
			Point posicaoProcessada = new Point(-1, -1);

			posicaoProcessada = processarPosicao(matriz, posicaoProcessada, posicaoAtual.x, posicaoAtual.y + 1);
			posicaoProcessada = processarPosicao(matriz, posicaoProcessada, posicaoAtual.x + 1, posicaoAtual.y);
			posicaoProcessada = processarPosicao(matriz, posicaoProcessada, posicaoAtual.x, posicaoAtual.y - 1);
			posicaoProcessada = processarPosicao(matriz, posicaoProcessada, posicaoAtual.x - 1, posicaoAtual.y);
			
			pontos.add(posicaoProcessada);
			posicaoAtual = posicaoProcessada;
		}
		
		List<Movimento> movimentos = new ArrayList<>();

		for (int i = 0; i < pontos.size() - 1; i++) {
			Direcao direcao = definirDirecaoEntrePontos(pontos.get(i), pontos.get(i + 1));
			Movimento movimento = definirMovimento(direcao);
			movimentos.add(movimento);
		}
		
		return movimentos;
	}

	private static Point processarPosicao(int[][] matriz, Point posicaoProcessada, int novoX, int novoY) {
		if (novoX > -1 && novoX < matriz.length && 
			novoY > -1 && novoY < matriz[novoX].length && 
			matriz[novoX][novoY] > 1 && 
			(posicaoProcessada.x == -1 || matriz[novoX][novoY] < matriz[posicaoProcessada.x][posicaoProcessada.y])) {
			return new Point(novoX, novoY);
		}
		return posicaoProcessada;
	}

	private static void processarPonto(int[][] matriz, LinkedList<Point> pontos, Point pontoAtual, int novoX, int novoY) {
		if (novoX > -1 && novoX < matriz.length &&
			novoY > -1 && novoY < matriz[novoX].length &&
			matriz[novoX][novoY] == 0) {
			matriz[novoX][novoY] = matriz[pontoAtual.x][pontoAtual.y] + 1;
			pontos.add(new Point(novoX, novoY));
		}
	}

	private static Point encontrarPosicaoRobo(PlanarImage imagem) {
		BufferedImage imagemJava = imagem.getAsBufferedImage();
		Raster raster = imagemJava.getRaster();
		int[] pixel = new int[3];
		
		for (int i = 0; i < imagem.getWidth(); i++) {
			for (int j = 0; j < imagem.getHeight(); j++) {
				
				raster.getPixel(i, j, pixel);
				if (pixel[0] > 250) {
					boolean encontrou = true;
					for (int k = i; k < i + 7; k++) {
						for (int l = j; l < j + 7; l++) {
							if (k < imagem.getWidth() &&
								l < imagem.getHeight()) {
								raster.getPixel(k, l, pixel);
								if (pixel[0] <= 250) {
									encontrou = false;
								}								
							}
						}
					}
					if (encontrou) {
						return new Point(j, i);
					}
				}
			}
		}
		
		return null;
	}

	public static void executarMovimento(Movimento movimento) {
		switch(movimento.getTipoMovimento()) {
		
		case DESLOCAMENTO:
			executarDeslocamento();
			break;
			
		case CURVA:
			executarRotacao(movimento.getDirecao());
			// Executa um deslocamento depois de cada rotação, para evitar que o
			// robô analise duas vezes os mesmos nodos sempre que executar uma
			// rotação.
			Movimento movimentoDeslocamento = new Movimento(TipoMovimento.DESLOCAMENTO, Direcao.Norte);
			executarMovimento(movimentoDeslocamento);				
			break;
		
		}
	}
	
	public static void executarRotacao(Direcao direcaoRotacao) {
		if (direcaoRotacao == Direcao.Leste) {
			motor1.rotate(-ConstantesRobo.ROTACAO, true);
			motor2.rotate(ConstantesRobo.ROTACAO);
		} else {
			motor1.rotate(ConstantesRobo.ROTACAO, true);
			motor2.rotate(-ConstantesRobo.ROTACAO);
		}
	}
	
	public static void executarDeslocamento() {
		motor1.rotate(ConstantesRobo.DESLOCAMENTO, true);
		motor2.rotate(ConstantesRobo.DESLOCAMENTO);	
	}
	
	public static Movimento definirMovimento(Direcao direcaoNovoNodo) {
		if (orientacao == direcaoNovoNodo) {
			return new Movimento(TipoMovimento.DESLOCAMENTO, Direcao.Norte);
		} else {
			Direcao direcaoRotacao = MapeamentoDirecoes.definirDirecaoRotacao(orientacao, direcaoNovoNodo);
			orientacao = MapeamentoDirecoes.obterNovaDirecao(orientacao, direcaoRotacao);
			return new Movimento(TipoMovimento.CURVA, direcaoRotacao);
		}
	}
	
	public static Direcao definirDirecaoEntrePontos(Point ponto1, Point ponto2) {
		if (ponto1.getX() < ponto2.getX()) {
			return Direcao.Sul;
		} else if (ponto1.getX() > ponto2.getX()) {
			return Direcao.Norte;
		} else if (ponto1.getY() < ponto2.getY()) {
			return Direcao.Leste;
		} else {
			return Direcao.Oeste;
		}
		
	}
	
}
