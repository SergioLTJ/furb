var idJogador = 0;

function Jogador(x, y, posicao, cor) {
	this.x = x;
	this.y = y;
	this.posicao = posicao;
	this.cor = cor;
	
	this.desenhar = function (contexto) {
		contexto.fillStyle = this.cor;

		var posicaoX = this.definirX();
		var posicaoY = this.definirY();

		contexto.beginPath();
		contexto.arc(posicaoX, posicaoY, configuracoes.TAMANHO_JOGADOR / 2, 0, 2 * Math.PI);
		contexto.fill();
	}

	this.definirX = function() {
		var umQuartoCelula = configuracoes.TAMANHO_CELULA / 4;
		if (this.posicao == 1 || 
			this.posicao == 3) 
			return (this.x * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		else 
			return (this.x * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
	}

	this.definirY = function() {
		var umQuartoCelula = configuracoes.TAMANHO_CELULA / 4;
		if (this.posicao == 1 || 
			this.posicao == 2) 
			return (this.x * configuracoes.TAMANHO_CELULA) + (umQuartoCelula);
		else 
			return (this.x * configuracoes.TAMANHO_CELULA) + (configuracoes.TAMANHO_CELULA - umQuartoCelula);
	}

	this.gerarNovoIdentificador = function() {
		idJogador++;
		this.identificador = idJogador;
	}

	this.identificador = this.gerarNovoIdentificador();
}