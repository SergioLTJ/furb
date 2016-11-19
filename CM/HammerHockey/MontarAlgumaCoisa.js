function Quadrado(xCentro, yCentro, id)
{
	this.x = xCentro;
	this.y = yCentro;
	this.tamanho = configuracoes.TAMANHO_QUADRADO_MONTAR_BLAH;
	this.id = id;
	this.deltaXAnterior = 0;
	this.deltaYAnterior = 0;
	this.selecionado = false;
	
	this.definirBoundingBox = function() 
	{		
		this.boundingBox = { };
		this.boundingBox.cima = this.y - (this.tamanho / 2);
		this.boundingBox.baixo = this.y + (this.tamanho / 2);
		this.boundingBox.esquerda = this.x - (this.tamanho / 2);
		this.boundingBox.direita = this.x + (this.tamanho / 2);
	}

	this.verificarOverlap = function(outro)
	{
		return (Math.abs(this.x - outro.x) < 5 &&
				Math.abs(this.y - outro.y) < 5 &&
				this.id == outro.id);
	}

	this.desenhar = function(contexto)
	{
		contexto.strokeRect(this.boundingBox.esquerda, this.boundingBox.cima, this.tamanho, this.tamanho);
	}

	this.mover = function(x, y)
	{
		this.x += x;
		this.y += y;
		this.definirBoundingBox();
	}

	this.definirBoundingBox();
}

function AreaJogador(jogador)
{
	this.jogador = jogador;
	this.boundingBox = {};	

	this.areasCorretas = [];
	this.areasControlaveis = [];

	this.areasSelecionadas = [];

	this.deltaXAnterior = 0;
	this.deltaYAnterior = 0;

	this.definirBoundingBox = function()
	{
		switch(this.jogador.posicao)
		{
			case 0:
				this.boundingBox.cima = Posicoes.TopoEsquerdo.y;
				this.boundingBox.baixo = Posicoes.Centro.y;
				this.boundingBox.esquerda = Posicoes.TopoEsquerdo.x;
				this.boundingBox.direita = Posicoes.Centro.x;
				break;
			case 1:
				this.boundingBox.cima = Posicoes.TopoEsquerdo.y;
				this.boundingBox.baixo = Posicoes.Centro.y;
				this.boundingBox.esquerda = Posicoes.Centro.x;
				this.boundingBox.direita = Posicoes.TopoDireito.x;
				break;
			case 2:
				this.boundingBox.cima = Posicoes.Centro.y;
				this.boundingBox.baixo = Posicoes.BaseEsquerda.y;
				this.boundingBox.esquerda = Posicoes.TopoEsquerdo.x;
				this.boundingBox.direita = Posicoes.Centro.x;
				break;
			case 3:
				this.boundingBox.cima = Posicoes.Centro.y;
				this.boundingBox.baixo = Posicoes.BaseEsquerda.y;
				this.boundingBox.esquerda = Posicoes.Centro.x;
				this.boundingBox.direita = Posicoes.TopoDireito.x;
				break;
		}
	};

	this.definirAreasCorretas = function() 
	{
		if (this.jogador.posicao == 0 ||
			this.jogador.posicao == 1)
		{
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.baixo - 50, 1));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2 - 75, this.boundingBox.baixo - 125, 2));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.baixo - 150, 3));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.baixo - 250, 4));
		} 
		else 
		{
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.cima + 50, 1));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2 + 75, this.boundingBox.cima + 125, 2));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.cima + 150, 3));
			this.areasCorretas.push(new Quadrado((this.boundingBox.esquerda + this.boundingBox.direita) / 2, this.boundingBox.cima + 250, 4));	
		}
	}

	this.verificarIdJaUsada = function(id)
	{
		for (var i = 0; i < this.areasControlaveis.length; i++)
		{
			if (this.areasControlaveis[i].id == id)
				return true;
		}

		return false;
	}

	this.gerarId = function()
	{
		var id = 0;
		
		do 
		{
			id = Math.floor((Math.random() * 4) + 1);
		} while (this.verificarIdJaUsada(id));

		return id;
	}

	this.inicializarAreasControlaveis = function()
	{
		var metadeQuadrado = configuracoes.TAMANHO_QUADRADO_MONTAR_BLAH / 2;

		var xCentro = (this.boundingBox.esquerda + this.boundingBox.direita) / 2

		if (this.jogador.posicao == 0 ||
			this.jogador.posicao == 1)
		{
			this.areasControlaveis.push(new Quadrado(this.boundingBox.esquerda + metadeQuadrado + 80, this.boundingBox.cima + metadeQuadrado + 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado((xCentro + this.boundingBox.esquerda) / 2 + 80, this.boundingBox.cima + metadeQuadrado + 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado((xCentro + this.boundingBox.direita) / 2 - 80, this.boundingBox.cima + metadeQuadrado + 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado(this.boundingBox.direita - metadeQuadrado - 80, this.boundingBox.cima + metadeQuadrado + 10, this.gerarId()));
		}
		else
		{
			this.areasControlaveis.push(new Quadrado(this.boundingBox.esquerda + metadeQuadrado + 80, this.boundingBox.baixo - metadeQuadrado - 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado((xCentro + this.boundingBox.esquerda) / 2 + 80, this.boundingBox.baixo - metadeQuadrado - 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado((xCentro + this.boundingBox.direita) / 2 - 80, this.boundingBox.baixo - metadeQuadrado - 10, this.gerarId()));
			this.areasControlaveis.push(new Quadrado(this.boundingBox.direita - metadeQuadrado - 80, this.boundingBox.baixo - metadeQuadrado - 10, this.gerarId()));
		}
	}

	this.desenhar = function(contexto)
	{
		contexto.save();

		for (var i = 0; i < this.areasCorretas.length; i++)
		{
			contexto.strokeStyle = 'green';
			this.areasCorretas[i].desenhar(contexto);
			contexto.strokeStyle = 'black';
			this.areasControlaveis[i].desenhar(contexto);
		}

		contexto.restore();
	}

	this.verificarArraste = function(evento)
	{
		for (var i = 0; i < this.areasControlaveis.length; i++)
		{
			if (this.areasControlaveis[i].selecionado && 
				Util.verificarBoundingBox(evento.center.x, evento.center.y, this.areasControlaveis[i].boundingBox))
			{
				this.areasControlaveis[i].mover(evento.deltaX - this.areasControlaveis[i].deltaXAnterior, evento.deltaY - this.areasControlaveis[i].deltaYAnterior);
				this.areasControlaveis[i].deltaXAnterior = evento.deltaX;
				this.areasControlaveis[i].deltaYAnterior = evento.deltaY;
				return;
			}
		}
	}

	this.verificarAperto = function(evento)
	{
		for (var i = 0; i < this.areasControlaveis.length; i++)
		{
			if (Util.verificarBoundingBox(evento.center.x, evento.center.y, this.areasControlaveis[i].boundingBox))
			{
				this.areasControlaveis[i].selecionado = true;
			}
		}
	}

	this.definirBoundingBox();
	this.definirAreasCorretas();
	this.inicializarAreasControlaveis();
}

function MontarAlgumaCoisa(jogo) 
{
	this.jogadores = jogo.jogadores;
	this.jogo = jogo;
	this.areas = [ ];	

	this.tipoEvento = TipoEvento.MINI_GAME;

	this.disparar = function(jogador) 
	{
		this.jogo.mudarModo(ModoJogo.MINI_GAME);		
		this.jogo.atribuirEvento(this);		
		
		for(var i = 0; i < this.jogadores.length; i++)
		{
			this.areas.push(new AreaJogador(this.jogadores[i]));
		}
	}

	this.desenhar = function(contexto)
	{
		contexto.clearRect(Posicoes.TopoEsquerdo.x - 1, Posicoes.TopoEsquerdo.y - 1, Posicoes.BaseDireita.x - Posicoes.TopoEsquerdo.x + 2, Posicoes.BaseDireita.y - Posicoes.TopoEsquerdo.y + 2);

		// Linha vertical centro
		contexto.beginPath();
		contexto.moveTo(Posicoes.Centro.x, Posicoes.TopoEsquerdo.y);
		contexto.lineTo(Posicoes.Centro.x, Posicoes.BaseEsquerda.y);
		contexto.stroke();

		// Linha horizontal centro
		contexto.beginPath();
		contexto.moveTo(Posicoes.TopoEsquerdo.x, Posicoes.Centro.y);
		contexto.lineTo(Posicoes.TopoDireito.x, Posicoes.Centro.y);
		contexto.stroke();

		for (var i = 0; i < this.areas.length; i++)
		{
			this.areas[i].desenhar(contexto);
		}
	}

	this.verificarArraste = function(evento)
	{
		for (var i = 0; i < this.areas.length; i++)
		{
			this.areas[i].verificarArraste(evento);
		}
	}

	this.verificarAperto = function(evento)
	{
		for (var i = 0; i < this.areas.length; i++)
		{
			this.areas[i].verificarAperto(evento);
		}
	}

	this.verificarSoltar = function(evento)
	{

	}
}