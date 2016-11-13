function Tabuleiro(contextoGrafico) {
	this.contexto = contextoGrafico;

	this.turnoDisponivel = true;

	//this.matriz = [
	//	[0, 0, 0, 0, 0, 0, 0, 0],
	//	[0, 1, 2, 3, 4, 5, 6, 7],
	//	[0, 24, 0, 0, 0, 0, 0, 8],
	//	[0, 23, 0, 0, 0, 0, 0, 9],
	//	[0, 22, 0, 0, 0, 0, 0, 10],
	//	[0, 21, 0, 0, 0, 0, 0, 11],
	//	[0, 20, 0, 0, 0, 0, 0, 12],
	//	[0, 19, 18, 17, 16, 15, 14, 13],
	//];

	this.matriz = [
		[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
		[0, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49],
		[0, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 48],
		[0, 66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 47],
		[0, 1, 0, 0, 14, 15, 16, 17, 0, 0, 30, 31, 32, 33, 0, 0, 46],
		[0, 2, 0, 0, 13, 0, 0, 18, 0, 0, 29, 0, 0, 34, 0, 0, 45],
		[0, 3, 0, 0, 12, 0, 0, 19, 0, 0, 28, 0, 0, 35, 0, 0, 44],
		[0, 4, 0, 0, 11, 0, 0, 20, 0, 0, 27, 0, 0, 36, 0, 0, 43],
		[0, 5, 0, 0, 10, 0, 0, 21, 0, 0, 26, 0, 0, 37, 0, 0, 42],
		[0, 6, 7, 8, 9, 0,  0, 22, 23, 24, 25, 0, 0, 38, 39, 40, 41],
	];

	this.atualizar = function () {
		this.contexto.clearRect(0, 0, 1920, 1080);
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j]) {
					this.matriz[i][j].desenhar(this.contexto);
				}
			}
		}
	}

	this.adicionarJogador = function(posicao, cor) {
		var jogador = new Jogador(this.celulaInicial, posicao, cor);
		this.celulaInicial.atribuirJogador(jogador);
		return jogador;
	}

	this.verificarLinhaPreenchida = function(indice) {
		for (var i = 0; i < this.matriz[indice].length; i++) {
			if (this.matriz[indice][i] != 0) {
				return true;
			}
		}
		
		return false;
	}
	
	this.verificarColunaPreenchida = function(indice) {
		for (var i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i].length > indice && this.matriz[i][indice] != 0) {
				return true;
			}
		}
		
		return false;
	}
	
	this.determinarUltimaColunaPreenchida = function() {
		var ultimaColuna = -1;
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = this.matriz[i].length - 1; j > ultimaColuna; j--) {
				if (this.matriz[i][j] !== 0) {
					ultimaColuna = j;
					break;
				}
			}
		}
		return ultimaColuna;
	}
	
	this.determinarTamanhoTabuleiro = function() {
		var self = this;
		configuracoes.primeiraLinhaPreenchida = (function() { 
			for (var i = 0; i < self.matriz.length; i++) { 
				if (self.verificarLinhaPreenchida(i)) 
					return i;
			} 
			return -1;
		})();
		configuracoes.ultimaLinhaPreenchida = (function() { 
			for (var i = self.matriz.length - 1; i > -1; i--) { 
				if (self.verificarLinhaPreenchida(i)) 
					return i; 
			} 
			return -1;
		})();
		configuracoes.primeiraColunaPreenchida = (function() { 
			for (var i = 0; i < self.matriz.length; i++) { 
				if (self.verificarColunaPreenchida(i)) 
					return i; 
			} 
			return -1;
		})();
		configuracoes.ultimaColunaPreenchida = this.determinarUltimaColunaPreenchida();
	}
	
	this.inicializarTabuleiro = function() {
		var anterior = null;
		var posicaoFinal = this.determinarPosicaoFinal();
		for (var i = 1; i <= posicaoFinal; i++) {
			for (var j = 0; j < this.matriz.length; j++) {
				for (var k = 0; k < this.matriz[j].length; k++) {
					if (this.matriz[j][k] == i) {
						this.matriz[j][k] = new Celula(k, j, i);
						if (anterior != null) {
							anterior.sucessor = this.matriz[j][k];
						} else {
							this.celulaInicial = this.matriz[j][k];
						}
						anterior = this.matriz[j][k];
					}
				}
			}
		}
		
		this.determinarTamanhoTabuleiro();
	}
	
	this.determinarPosicaoFinal = function() {
		var maior = 0;
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j] > maior) {
					maior = this.matriz[i][j];
				}
			}
		}
		return maior;
	}
	
 	this.inicializarTabuleiro();
}