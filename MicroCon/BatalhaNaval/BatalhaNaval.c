short municao = 10;

typedef struct Acerto
{
	short lin;
	short col;
	short acertou;
	char caractere_desenho;
	short vazia;
} Acerto;

Acerto matriz[15][15] = {0};

struct PortaAviao
{
	Acerto posicoes[6];
	short afundado;
} porta_avioes[1];

struct Encouracado
{
	Acerto posicoes[5];
	short afundado;
} encouracados[2];

struct HidroAviao
{
	Acerto posicoes[3];
	short afundado;
} hidro_avioes[5];

struct Submarino
{
	Acerto posicoes[1];
	short afundado;
} submarinos[4];

struct Cruzador
{
	Acerto posicoes[2];
	short afundado;
} cruzadores[3];

void inicializar_matriz()
{
	short i;
	short j;

	for (i = 0; i < 15; i++)
		for (j = 0; j < 15; j++)
			matriz[i][j].vazia = 1;
}

void gerar_seed()
{
	// TODO ajustar
	//srand(10);
}

short gerar_aleatorio(short limite)
{
	return rand() % limite;
}

short validar_posicoes_vazias(short x, short y, short tamanho)
{
	short i;

	for (i = x; i < x + tamanho; i++)
		if (matriz[i][y].vazia)
			return 0;

	return 1;
}

void posicionar_horizontal(Acerto *posicoes, short tamanho, char caractere_desenho)
{
	short x, y, i, posicao_matriz;
	do
	{
		x = gerar_aleatorio(14 - tamanho);
		y = gerar_aleatorio(14);
	} while(!validar_posicoes_vazias(x, y, tamanho));

	for (i = 0; i < tamanho; i++)
	{
		posicao_matriz = x + i;
		posicoes[i].col = posicao_matriz;
		posicoes[i].lin = y;
		posicoes[i].acertou = 0;
		posicoes[i].caractere_desenho = caractere_desenho;
		matriz[posicao_matriz][y] = posicoes[i];
	}
}

short validar_posicao_hidro_aviao(short x, short y)
{
	if (y == 0) return 0;

	if (matriz[x][y].vazia &&
	    matriz[x + 1][y - 1].vazia &&
	    matriz[x + 2][y].vazia)
		return 1;

	return 0;
}

Acerto preencher_posicao_hidro_aviao(Acerto posicao, short x, short y)
{
	posicao.lin = x;
	posicao.col = y;
	posicao.acertou = 0;
	posicao.caractere_desenho = 'H';
	matriz[x][y] = posicao;
	return posicao;
}

void posicionar_hidro_aviao(Acerto *posicoes)
{
	short x, y;

	do
	{
		x = gerar_aleatorio(11);
		y = gerar_aleatorio(14);
	} while(!validar_posicao_hidro_aviao(x, y));

	posicoes[0] = preencher_posicao_hidro_aviao(posicoes[0], x, y);
	posicoes[1] = preencher_posicao_hidro_aviao(posicoes[0], x + 1, y - 1);
	posicoes[2] = preencher_posicao_hidro_aviao(posicoes[0], x + 2, y);
}

void setup()
{
	short i;

	UART1_Init(8200);
	gerar_seed();
	inicializar_matriz();

	for (i = 0; i < 2; i++)
	{
		posicionar_horizontal(encouracados[i].posicoes, 5, 'E');
		encouracados[i].afundado = 0;
	}

	for (i = 0; i < 1; i++)
	{
		posicionar_horizontal(porta_avioes[i].posicoes, 6, 'P');
		porta_avioes[i].afundado = 0;
	}

	for (i = 0; i < 4; i++)
	{
		posicionar_horizontal(submarinos[i].posicoes, 1, 'S');
		submarinos[i].afundado = 0;
	}

	for (i = 0; i < 3; i++)
	{
		posicionar_horizontal(cruzadores[i].posicoes, 2, 'C');
		cruzadores[i].afundado = 0;
	}

	for (i = 0; i < 5; i++)
	{
		posicionar_hidro_aviao(hidro_avioes[i].posicoes);
		hidro_avioes[i].afundado = 0;
	}
}

short calcular_acertos_encouracados()
{
	short i, j, acertos = 0;
	for (i = 0; i < 2; i++)
	{
		for (j = 0; j < 5; j++)
		{
			if (encouracados[i].posicoes[j].acertou)
			{
				acertos++;
			}
		}
	}
	return acertos;
}

short calcular_encouracados_restantes()
{
	short i, restantes;

	for (i = 0; i < 2; i++)
		if (!encouracados[i].afundado)
			restantes++;

	return restantes;
}

short calcular_acertos_porta_avioes()
{
	short i, j, acertos = 0;

	for (j = 0; j < 6; j++)
	{
		if (porta_avioes[0].posicoes[j].acertou)
		{
			acertos++;
		}
	}

	return acertos;
}

short calcular_porta_avioes_restantes()
{
	short i, restantes;

	if (!porta_avioes[0].afundado)
		restantes++;

	return restantes;
}

short calcular_acertos_hidro_avioes()
{
	short i, j, acertos = 0;
	for (i = 0; i < 5; i++)
	{
		for (j = 0; j < 3; j++)
		{
			if (hidro_avioes[i].posicoes[j].acertou)
			{
				acertos++;
			}
		}
	}
	return acertos;
}

short calcular_hidro_avioes_restantes()
{
	short i, restantes;

	for (i = 0; i < 2; i++)
		if (!hidro_avioes[i].afundado)
			restantes++;

	return restantes;
}

short calcular_acertos_submarinos()
{
	short i, j, acertos = 0;
	for (i = 0; i < 4; i++)
	{
		if (submarinos[i].posicoes[0].acertou)
		{
			acertos++;
		}
	}
	return acertos;
}

short calcular_submarinos_restantes()
{
	short i, restantes;

	for (i = 0; i < 2; i++)
		if (!submarinos[i].afundado)
			restantes++;

	return restantes;
}

short calcular_acertos_cruzadores()
{
	short i, j, acertos = 0;
	for (i = 0; i < 3; i++)
	{
		for (j = 0; j < 2; j++)
		{
			if (cruzadores[i].posicoes[j].acertou)
			{
				acertos++;
			}
		}
	}
	return acertos;
}

short calcular_cruzadores_restantes()
{
	short i, restantes;

	for (i = 0; i < 2; i++)
		if (!cruzadores[i].afundado)
			restantes++;

	return restantes;
}

void escrever_linha(short acertos, short restantes, const char *formato)
{
	char linha[64];
	char texto_acertos[3];
	char texto_restantes[3];

	sprintf(texto_acertos, acertos > 10 ? "%d" : " %d", acertos);
	sprintf(texto_restantes, restantes > 10 ? "%d" : " %d", restantes);

	sprintf(linha, formato, texto_acertos, texto_restantes);
	UART_Write_Text(linha);
	memset(texto_acertos, 0, sizeof(texto_acertos));
	memset(texto_restantes, 0, sizeof(texto_restantes));
	memset(linha, 0, sizeof(linha));
}

void desenhar_placar()
{
	short i,
		  acertos_e, acertos_p, acertos_h, acertos_s, acertos_c, acertos_total,
		  restantes_e, restantes_p, restantes_h, restantes_s, restantes_c, restantes_total;

	UART_Write_Text("-------------------------------------------\n");
	UART_Write_Text("Quant. Inicial | Formato | Acertos | Restam\n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_e = calcular_acertos_encouracados();
	restantes_e = calcular_encouracados_restantes();
	escrever_linha(acertos_e, restantes_e, "      2	| EEEEE   | %s      | %s    \n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_p = calcular_acertos_porta_avioes();
	restantes_p = calcular_porta_avioes_restantes();
	escrever_linha(acertos_p, restantes_p, "      1	| PPPPPP  | %s      | %s    \n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_h = calcular_acertos_hidro_avioes();
	restantes_h = calcular_hidro_avioes_restantes();
	escrever_linha(acertos_h, restantes_h, "      5	|   H     | %s      | %s    \n");
	UART_Write_Text("	       |  H H    |	 |       \n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_s = calcular_acertos_submarinos();
	restantes_s = calcular_submarinos_restantes();
	escrever_linha(acertos_s, restantes_s, "      4	|   S     | %s      | %s    \n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_c = calcular_acertos_cruzadores();
	restantes_c = calcular_cruzadores_restantes();
	escrever_linha(acertos_c, restantes_c, "      3	|  CC     | %s      | %s    \n");
	UART_Write_Text("-------------------------------------------\n");

	acertos_total = acertos_e + acertos_p + acertos_h + acertos_s + acertos_c;
	restantes_total = restantes_e + restantes_p + restantes_h + restantes_s + restantes_c;
	escrever_linha(acertos_total, restantes_total, "      15       |  TOTAL  | %s      | %s    \n");
	UART_Write_Text("-------------------------------------------\n\n");
}

void desenhar_mapa()
{
	short i, j;

	for (i = 0; i < 15; i++)
	{
		for (j = 0; j < 15; j++)
			if (matriz[i][j].acertou)
				UART_Write(matriz[i][j].caractere_desenho);
			else
				UART_Write('~');
		
		UART_Write('\n');
	}
	
	UART_Write('\n');
}

short validar_final()
{
	if (calcular_porta_avioes_restantes() > 0)
		return 0;
	if (calcular_encouracados_restantes() > 0)
		return 0;
	if (calcular_hidro_avioes_restantes() > 0)
		return 0;
	if (calcular_submarinos_restantes() > 0)
		return 0;
	if (calcular_cruzadores_restantes() > 0)
		return 0;
	
	return 1;
}

short ler_posicao_uart()
{
	while(!UART1_Data_Ready());	
}

void ler_input()
{
	char canhao[21];
	short xAcerto, yAcerto;
	
	sprintf(canhao, "Munição: %d", municao);
	UART_Write_Text(canhao);
	memset(canhao, 0, sizeof(canhao));
	
	UART_Write_Text("Insira a posição X onde você deseja atirar: ");
	xAcerto = ler_posicao_uart();
	UART_Write_Text("Insira a posição X onde você deseja atirar: ");
}

short main_loop()
{
	desenhar_placar();
	desenhar_mapa();
	if (validar_final())
	{
		return 1;
	}
	ler_input();
}

void main()
{
	short terminou;

	setup();

	do
	{
		terminou = main_loop();
	} while (!terminou);
}