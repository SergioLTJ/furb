#line 1 "C:/git-furb/MicroCon/BatalhaNaval/BatalhaNaval.c"
int matriz[15][15] = {0};

struct Acerto
{
 short lin;
 short col;
 short acertou;
 char caractere_desenho;
};

struct
{
 struct Acerto posicoes[6];
 short afundado;
} porta_avioes[1];

struct
{
 struct Acerto posicoes[5];
 short afundado;
} encouracados[2];

struct
{
 struct Acerto posicoes[4];
 short afundado;
} hidro_avioes[5];

struct
{
 struct Acerto posicoes[1];
 short afundado;
} submarinos[4];

struct
{
 struct Acerto posicoes[2];
 short afundado;
} cruzadores[3];

void init_matrix()
{
 int i;
 int j;

 for (i = 0; i < 15; i++)
 for (j = 0; j < 15; j++)
 matriz[i][j] = 0;
}

void gerar_seed()
{

 srand(10);
}

short gerar_aleatorio(short limite)
{
 return rand() % limite;
}

short validar_posicoes_vazias(short x, short y, short tamanho)
{
 short i;

 for (i = x; i < x + tamanho; i++)
 if (matriz[i][y] == 1)
 return 0;

 return 1;
}

void posicionar_horizontal(struct Acerto *posicoes, short tamanho)
{
 short x, y, i;
 do
 {
 x = gerar_aleatorio(15 - tamanho);
 y = gerar_aleatorio(15 - tamanho);
 } while(!validar_posicoes_vazias(x, y, tamanho));

 for (i = x; i < x + tamanho; i++)
 matriz[i][y] = 1;

 for (i = 0; i < tamanho; i++)
 {
 posicoes[i].col = x;
 posicoes[i].lin = y;
 posicoes[i].acertou = 0;
 }
}

void setup()
{
 short i;
 UART1_Init(8200);


 init_matrix();

 for (i = 0; i < 2; i++)
 {
 posicionar_horizontal(encouracados[i].posicoes, 5);
 encouracados[i].afundado = 0;
 }

 for (i = 0; i < 1; i++)
 {
 posicionar_horizontal(porta_avioes[i].posicoes, 6);
 porta_avioes[i].afundado = 0;
 }

 for (i = 0; i < 4; i++)
 {
 posicionar_horizontal(submarinos[i].posicoes, 1);
 submarinos[i].afundado = 0;
 }

 for (i = 0; i < 3; i++)
 {
 posicionar_horizontal(cruzadores[i].posicoes, 2);
 cruzadores[i].afundado = 0;
 }
}

short main_loop()
{
 UART_Write_Text("oi");
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
