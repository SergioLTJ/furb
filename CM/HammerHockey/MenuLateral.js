function MenuLateral(tabuleiro, contexto) {
    this.contexto = contexto;

    this.x = tabuleiro.matriz.length * configuracoes.TAMANHO_CELULA + 10;
    this.paddingInterno = 10;
    this.larguraBotoes = 100;
    this.alturaBotoes = 20;

    this.desenhar = function() {
        this.contexto.rect(this.x, 5, this.larguraBotoes, this.alturaBotoes);
        this.contexto.fillText("Qualquer coisa", this.x, 10);
    }

    this.verificarClique = function (x, y) {
        if (x >= this.x &&
            y >= 10) {
            alert("oi");
        }
    }
}