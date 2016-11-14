function Resposta(texto, correta) {
	this.texto = texto;
	this.correta = correta;
	this.selecionada = false;
	this.boundingBox = {};
}

function Pergunta(texto, respostas, jogo) 
{
	this.texto = texto;
	this.respostas = respostas;
	this.jogo = jogo;

	this.jogador;

	this.xInicial;
	this.yInicial;

	this.tipoEvento = TipoEvento.PERGUNTA;
	this.botaoConfirmar = 
	{
		boundingBox: {},
	};

	this.selecionouResposta = false;

	this.terminou = false;
	this.acertou;

	this.altura;

	this.disparar = function(jogador) 
	{
		this.jogador = jogador;
		this.jogo.mudarModo(ModoJogo.PERGUNTA);		
		this.jogo.atribuirEvento(this);

		var xCentro = (Posicoes.TopoEsquerdo.x + Posicoes.BaseDireita.x) / 2;
		var yCentro = (Posicoes.TopoEsquerdo.y + Posicoes.BaseDireita.y) / 2;

		this.xInicial = xCentro - configuracoes.LARGURA_TELA_PERGUNTAS / 2;
		this.yInicial = yCentro - yCentro / 2;
	}

	this.desenhar = function(contexto)
	{
		contexto.save();
		
		this.desenharTelaPergunta(contexto);		

		contexto.restore();
	}

	this.desenharTelaPergunta = function(contexto)
	{
		if (this.altura)
		{
			contexto.clearRect(
				this.xInicial,
				this.yInicial,
				configuracoes.LARGURA_TELA_PERGUNTAS,
				this.altura - this.yInicial
			);
		}

		var offsetY = this.yInicial + 5;

		contexto.lineWidth = 2;		

		contexto.fillStyle = 'black';
		contexto.font = '18px Arial bold';
		contexto.textAlign  = 'center';
		contexto.textBaseline = "top"; 
		contexto.fillText('PERGUNTA', this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS / 2, offsetY);
		offsetY += 27;

		this.desenharDivisoria(contexto, offsetY);

		offsetY += 5;

		contexto.textAlign = 'left';
		contexto.font = '14px Arial';
		offsetY = this.wrapText(contexto, this.texto, this.xInicial + 5, offsetY, configuracoes.LARGURA_TELA_PERGUNTAS - 10, 20);
		offsetY += 23;

		this.desenharDivisoria(contexto, offsetY);

		offsetY += 5;

		for (var i = 0; i < this.respostas.length; i++) 
		{			
			offsetY = this.desenharResposta(contexto, this.respostas[i], offsetY);
		}

		if (!this.terminou)
		{
			offsetY = this.desenharBotaoConfirmacao(contexto, offsetY);
		} 
		else 
		{
			offsetY = this.desenharTelaFinal(contexto, offsetY);
		}
		
		this.altura = offsetY;

		contexto.strokeRect(
			this.xInicial,
			this.yInicial,
			configuracoes.LARGURA_TELA_PERGUNTAS,
			offsetY - this.yInicial
		);
	}	

	this.desenharBotaoConfirmacao = function(contexto, offsetY)
	{
		contexto.fillStyle = this.selecionouResposta ? 'green' : 'gray';

		contexto.strokeRect(
			this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS - 105,
			offsetY,
			100,
			25
		);

		contexto.fillRect(
			this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS - 105,
			offsetY,
			100,
			25
		);

		this.botaoConfirmar.boundingBox.esquerda = this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS - 105;
		this.botaoConfirmar.boundingBox.cima = offsetY;
		this.botaoConfirmar.boundingBox.baixo = offsetY + 25;
		this.botaoConfirmar.boundingBox.direita = this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS - 5;

		offsetY += 3;

		contexto.fillStyle = 'black';
		contexto.textAlign = 'center'		
		contexto.fillText('Confirmar', this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS - 52.5, offsetY);

		offsetY += 28;

		return offsetY;
	}

	this.desenharTelaFinal = function(contexto, offsetY) 
	{
		offsetY -= 5;

		contexto.fillStyle = this.acertou ? 'green' : 'red';
		contexto.fillRect(
			this.xInicial,
			offsetY,
			configuracoes.LARGURA_TELA_PERGUNTAS,
			50
		);

		contexto.fillStyle = 'black';
		contexto.font = '18px Arial bold';
		contexto.textAlign  = 'center';
		contexto.textBaseline = "top"; 
		var texto = this.acertou
						? 'RESPOSTA CORRETA!'
						: 'RESPOSTA ERRADA!';
		contexto.fillText(texto, this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS / 2, offsetY);
		offsetY += 27;

		contexto.font = '14px Arial';
		contexto.textAlign  = 'left';
		texto = this.acertou 
					   ? 'Você avançará uma casa por acertar a pergunta.'
					   : 'Você voltará uma casa por errar a pergunta.';
		contexto.fillText(texto, this.xInicial + 5, offsetY);
		offsetY += 23;

		if ((new Date().getTime() - this.tempoFinal) > 4000)
		{			
			if (this.acertou)		
				this.jogador.mover();
			else		
				this.jogador.retroceder();

			this.jogo.mudarModo(ModoJogo.NORMAL);		
		}

		return offsetY;
	}

	this.desenharResposta = function(contexto, resposta, offsetY) 
	{
		var offsetYInicial = offsetY;

		offsetY = this.wrapText(contexto, resposta.texto, this.xInicial + configuracoes.TAMANHO_CIRCULO_RESPOSTAS + 21, offsetY, configuracoes.LARGURA_TELA_PERGUNTAS - 27, 20);
		offsetY += 23;

		contexto.lineWidth = 1;

		if (resposta.selecionada)
		{
			contexto.fillStyle = 'green';
			contexto.beginPath();
			contexto.arc(this.xInicial + configuracoes.TAMANHO_CIRCULO_RESPOSTAS / 2 + 9, (offsetYInicial + offsetY) / 2 - 2, configuracoes.TAMANHO_CIRCULO_RESPOSTAS, 0, 2 * Math.PI);
			contexto.fill();
			contexto.fillStyle = 'black';		
		}

		contexto.beginPath();
		contexto.arc(this.xInicial + configuracoes.TAMANHO_CIRCULO_RESPOSTAS / 2 + 9, (offsetYInicial + offsetY) / 2 - 2, configuracoes.TAMANHO_CIRCULO_RESPOSTAS, 0, 2 * Math.PI);
		contexto.stroke();

		contexto.lineWidth = 2;

		var xDivisoriaHorizontal = this.xInicial + configuracoes.TAMANHO_CIRCULO_RESPOSTAS + 18;

		contexto.beginPath();
		contexto.moveTo(xDivisoriaHorizontal, offsetYInicial - 5);
		contexto.lineTo(xDivisoriaHorizontal, offsetY);
		contexto.stroke();

		this.desenharDivisoria(contexto, offsetY);

		resposta.boundingBox.esquerda = this.xInicial;
		resposta.boundingBox.cima = offsetYInicial - 5;
		resposta.boundingBox.baixo = offsetY;
		resposta.boundingBox.direita = xDivisoriaHorizontal;

		offsetY += 5;

		return offsetY;
	}

	this.desenharDivisoria = function(contexto, y)
	{
		contexto.beginPath();
		contexto.moveTo(this.xInicial, y);
		contexto.lineTo(this.xInicial + configuracoes.LARGURA_TELA_PERGUNTAS, y);
		contexto.stroke();
	}

	this.wrapText = function(context, text, x, y, maxWidth, lineHeight) 
	{
        var words = text.split(' ');
        var line = '';

        for(var n = 0; n < words.length; n++) {
          var testLine = line + words[n] + ' ';
          var metrics = context.measureText(testLine);
          var testWidth = metrics.width;
          if (testWidth > maxWidth && n > 0) {
            context.fillText(line, x, y);
            line = words[n] + ' ';
            y += lineHeight;
          }
          else {
            line = testLine;
          }
        }
        context.fillText(line, x, y);
        return y;
     }

	this.verificarClique = function(eventoClique, jogo) 
	{
		if (this.terminou)
			return;

		for (var i = 0; i < this.respostas.length; i++) 
		{
			if (this.verificarBoundingBox(eventoClique, this.respostas[i].boundingBox))
			{
				this.respostas[i].selecionada = true;
				this.selecionouResposta = true;
				this.desmarcarOutrasRespostas(i);				
			}
		}

		if (this.selecionouResposta &&
			this.verificarBoundingBox(eventoClique, this.botaoConfirmar.boundingBox))
		{
			this.terminou = true;
			for (var i = 0; i < this.respostas.length; i++) 
			{
				if (this.respostas[i].selecionada)
				{
					this.tempoFinal = new Date().getTime();
					this.acertou = this.respostas[i].correta;
				}
			}
		}
	}

	this.desmarcarOutrasRespostas = function(indiceRespostaMarcada) 
	{
		for (var i = 0; i < indiceRespostaMarcada; i++) 
			this.respostas[i].selecionada = false;
		for (var i = indiceRespostaMarcada + 1; i < this.respostas.length; i++) 
			this.respostas[i].selecionada = false;
	}

	this.verificarBoundingBox = function(eventoClique, boundingBox) 
	{
		if (eventoClique.offsetX > boundingBox.esquerda &&
			eventoClique.offsetX < boundingBox.direita &&
			eventoClique.offsetY > boundingBox.cima &&
			eventoClique.offsetY < boundingBox.baixo)
			return true;

		return false;
	}

	this.ordenarRespostas = function() 
	{
		var currentIndex = this.respostas.length, temporaryValue, randomIndex;

  		// While there remain elements to shuffle...
  		while (0 !== currentIndex) 
  		{
    		// Pick a remaining element...
    		randomIndex = Math.floor(Math.random() * currentIndex);
    		currentIndex -= 1;
		
	    	// And swap it with the current element.
	    	temporaryValue = this.respostas[currentIndex];
	    	this.respostas[currentIndex] = this.respostas[randomIndex];
    		this.respostas[randomIndex] = temporaryValue;
  		}
	}	

	this.ordenarRespostas();
}
