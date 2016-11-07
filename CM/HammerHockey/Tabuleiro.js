function Tabuleiro(contextoGrafico) {
	this.contexto = contextoGrafico;

	this.turnoDisponivel = true;

	this.jogadorAtual = 0;

	this.matriz = [
		[0, 0, 0, 0, 0, 0, 0, 0],
		[0, 1, 0, 0, 8, 9, 10, 0],
		[0, 2, 3, 0, 7, 0, 11, 12],
		[0, 0, 4, 5, 6, , 0, 13],
		[0, 0, 0, 0, 0, , 0, 14],
		[0, 0, 0, 0, 18, 17, 16, 15],
		[0, 0, 0, 0, 19, 0, 0, 0],
		[0, 0, 0, 0, 20, 21, 22, 23],
	];

	this.atualizar = function () {
		this.contexto.clearRect(0, 0, 1920, 1080);
		for (var i = 0; i < this.matriz.length; i++) {
			for (var j = 0; j < this.matriz[i].length; j++) {
				if (this.matriz[i][j]) {
					this.matriz[i][j].desenhar(contextoGrafico);
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