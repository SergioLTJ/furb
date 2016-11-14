function ClienteSocket(jogo)
{
	this.jogo = jogo;
	this.socket = new WebSocket("ws://localhost/Laputa");
	
	this.ligarEventos = function() 
	{
		var self = this;

		this.socket.onopen = function (e) 
		{
    		console.log('conexão aberta');
  			self.socket.send('oi');
  		};
	
  		this.socket.onmessage = function (e) 
  		{
  		};
	}

	this.ligarEventos();

	
}