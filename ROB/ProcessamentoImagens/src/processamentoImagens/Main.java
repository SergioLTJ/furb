package processamentoImagens;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.JFrame;

public class Main {

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
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				
				int inicialLinha = tamanhoLinha * i;
				int inicialColuna = tamanhoColuna * j;
				int quantidadePontoCelula = tamanhoLinha * tamanhoColuna;
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

		int posicaoXRoboto = posicaoRobo.x / tamanhoColuna;
		int posicaoYRoboto = posicaoRobo.y / tamanhoLinha;
		
		JFrame frame = new JFrame("teste");
		frame.getContentPane().add(new DisplayTwoSynchronizedImages(imagem, novaImagem));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		

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
						return new Point(i, j);
					}
				}
			}
		}
		
		return null;
	}

	private static void desenharMatriz(BufferedImage imagemB) {
		int[] vermelho = new int[] { 255, 0, 0 };
		
		int tamanhoColuna = imagemB.getWidth() / 7;
		int tamanhoLinha = imagemB.getHeight() / 7;

		WritableRaster raster = imagemB.getRaster();
		
		for(int k = 0; k < imagemB.getHeight(); k++) {
			for(int i = 0; i < imagemB.getWidth() - tamanhoColuna; i += tamanhoColuna) {
				for(int j = i > 0 ? i - 3 : i; j < i + 3; j++) {
					raster.setPixel(j, k, vermelho);
				}
			}	
		}
		
		try {
			File arq = new File("C:\\Teste\\Teste.gif");
			if(arq.exists()) arq.createNewFile();
			ImageIO.write(imagemB, "GIF", arq);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
