
_init_matrix:

;BatalhaNaval.c,41 :: 		void init_matrix()
;BatalhaNaval.c,46 :: 		for (i = 0; i < 15; i++)
	CLRF        init_matrix_i_L0+0 
	CLRF        init_matrix_i_L0+1 
L_init_matrix0:
	MOVLW       128
	XORWF       init_matrix_i_L0+1, 0 
	MOVWF       R0 
	MOVLW       128
	SUBWF       R0, 0 
	BTFSS       STATUS+0, 2 
	GOTO        L__init_matrix35
	MOVLW       15
	SUBWF       init_matrix_i_L0+0, 0 
L__init_matrix35:
	BTFSC       STATUS+0, 0 
	GOTO        L_init_matrix1
;BatalhaNaval.c,47 :: 		for (j = 0; j < 15; j++)
	CLRF        init_matrix_j_L0+0 
	CLRF        init_matrix_j_L0+1 
L_init_matrix3:
	MOVLW       128
	XORWF       init_matrix_j_L0+1, 0 
	MOVWF       R0 
	MOVLW       128
	SUBWF       R0, 0 
	BTFSS       STATUS+0, 2 
	GOTO        L__init_matrix36
	MOVLW       15
	SUBWF       init_matrix_j_L0+0, 0 
L__init_matrix36:
	BTFSC       STATUS+0, 0 
	GOTO        L_init_matrix4
;BatalhaNaval.c,48 :: 		matriz[i][j] = 0;
	MOVLW       30
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        init_matrix_i_L0+0, 0 
	MOVWF       R4 
	MOVF        init_matrix_i_L0+1, 0 
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _matriz+0
	ADDWF       R0, 0 
	MOVWF       R3 
	MOVLW       hi_addr(_matriz+0)
	ADDWFC      R1, 0 
	MOVWF       R4 
	MOVF        init_matrix_j_L0+0, 0 
	MOVWF       R0 
	MOVF        init_matrix_j_L0+1, 0 
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        R0, 0 
	ADDWF       R3, 0 
	MOVWF       FSR1 
	MOVF        R1, 0 
	ADDWFC      R4, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,47 :: 		for (j = 0; j < 15; j++)
	INFSNZ      init_matrix_j_L0+0, 1 
	INCF        init_matrix_j_L0+1, 1 
;BatalhaNaval.c,48 :: 		matriz[i][j] = 0;
	GOTO        L_init_matrix3
L_init_matrix4:
;BatalhaNaval.c,46 :: 		for (i = 0; i < 15; i++)
	INFSNZ      init_matrix_i_L0+0, 1 
	INCF        init_matrix_i_L0+1, 1 
;BatalhaNaval.c,48 :: 		matriz[i][j] = 0;
	GOTO        L_init_matrix0
L_init_matrix1:
;BatalhaNaval.c,49 :: 		}
L_end_init_matrix:
	RETURN      0
; end of _init_matrix

_gerar_seed:

;BatalhaNaval.c,51 :: 		void gerar_seed()
;BatalhaNaval.c,54 :: 		srand(10);
	MOVLW       10
	MOVWF       FARG_srand_x+0 
	MOVLW       0
	MOVWF       FARG_srand_x+1 
	CALL        _srand+0, 0
;BatalhaNaval.c,55 :: 		}
L_end_gerar_seed:
	RETURN      0
; end of _gerar_seed

_gerar_aleatorio:

;BatalhaNaval.c,57 :: 		short gerar_aleatorio(short limite)
;BatalhaNaval.c,59 :: 		return rand() % limite;
	CALL        _rand+0, 0
	MOVF        FARG_gerar_aleatorio_limite+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       FARG_gerar_aleatorio_limite+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Div_16x16_S+0, 0
	MOVF        R8, 0 
	MOVWF       R0 
	MOVF        R9, 0 
	MOVWF       R1 
;BatalhaNaval.c,60 :: 		}
L_end_gerar_aleatorio:
	RETURN      0
; end of _gerar_aleatorio

_validar_posicoes_vazias:

;BatalhaNaval.c,62 :: 		short validar_posicoes_vazias(short x, short y, short tamanho)
;BatalhaNaval.c,66 :: 		for (i = x; i < x + tamanho; i++)
	MOVF        FARG_validar_posicoes_vazias_x+0, 0 
	MOVWF       validar_posicoes_vazias_i_L0+0 
L_validar_posicoes_vazias6:
	MOVF        FARG_validar_posicoes_vazias_tamanho+0, 0 
	ADDWF       FARG_validar_posicoes_vazias_x+0, 0 
	MOVWF       R1 
	MOVLW       0
	BTFSC       FARG_validar_posicoes_vazias_x+0, 7 
	MOVLW       255
	MOVWF       R2 
	MOVLW       0
	BTFSC       FARG_validar_posicoes_vazias_tamanho+0, 7 
	MOVLW       255
	ADDWFC      R2, 1 
	MOVLW       128
	BTFSC       validar_posicoes_vazias_i_L0+0, 7 
	MOVLW       127
	MOVWF       R0 
	MOVLW       128
	XORWF       R2, 0 
	SUBWF       R0, 0 
	BTFSS       STATUS+0, 2 
	GOTO        L__validar_posicoes_vazias40
	MOVF        R1, 0 
	SUBWF       validar_posicoes_vazias_i_L0+0, 0 
L__validar_posicoes_vazias40:
	BTFSC       STATUS+0, 0 
	GOTO        L_validar_posicoes_vazias7
;BatalhaNaval.c,67 :: 		if (matriz[i][y] == 1)
	MOVLW       30
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        validar_posicoes_vazias_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       validar_posicoes_vazias_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _matriz+0
	ADDWF       R0, 0 
	MOVWF       R3 
	MOVLW       hi_addr(_matriz+0)
	ADDWFC      R1, 0 
	MOVWF       R4 
	MOVF        FARG_validar_posicoes_vazias_y+0, 0 
	MOVWF       R0 
	MOVLW       0
	BTFSC       FARG_validar_posicoes_vazias_y+0, 7 
	MOVLW       255
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        R0, 0 
	ADDWF       R3, 0 
	MOVWF       FSR0 
	MOVF        R1, 0 
	ADDWFC      R4, 0 
	MOVWF       FSR0H 
	MOVF        POSTINC0+0, 0 
	MOVWF       R1 
	MOVF        POSTINC0+0, 0 
	MOVWF       R2 
	MOVLW       0
	XORWF       R2, 0 
	BTFSS       STATUS+0, 2 
	GOTO        L__validar_posicoes_vazias41
	MOVLW       1
	XORWF       R1, 0 
L__validar_posicoes_vazias41:
	BTFSS       STATUS+0, 2 
	GOTO        L_validar_posicoes_vazias9
;BatalhaNaval.c,68 :: 		return 0;
	CLRF        R0 
	GOTO        L_end_validar_posicoes_vazias
L_validar_posicoes_vazias9:
;BatalhaNaval.c,66 :: 		for (i = x; i < x + tamanho; i++)
	INCF        validar_posicoes_vazias_i_L0+0, 1 
;BatalhaNaval.c,68 :: 		return 0;
	GOTO        L_validar_posicoes_vazias6
L_validar_posicoes_vazias7:
;BatalhaNaval.c,70 :: 		return 1;
	MOVLW       1
	MOVWF       R0 
;BatalhaNaval.c,71 :: 		}
L_end_validar_posicoes_vazias:
	RETURN      0
; end of _validar_posicoes_vazias

_posicionar_horizontal:

;BatalhaNaval.c,73 :: 		void posicionar_horizontal(struct Acerto *posicoes, short tamanho)
;BatalhaNaval.c,76 :: 		do
L_posicionar_horizontal10:
;BatalhaNaval.c,78 :: 		x = gerar_aleatorio(15 - tamanho);
	MOVF        FARG_posicionar_horizontal_tamanho+0, 0 
	SUBLW       15
	MOVWF       FARG_gerar_aleatorio_limite+0 
	CALL        _gerar_aleatorio+0, 0
	MOVF        R0, 0 
	MOVWF       posicionar_horizontal_x_L0+0 
;BatalhaNaval.c,79 :: 		y = gerar_aleatorio(15 - tamanho);
	MOVF        FARG_posicionar_horizontal_tamanho+0, 0 
	SUBLW       15
	MOVWF       FARG_gerar_aleatorio_limite+0 
	CALL        _gerar_aleatorio+0, 0
	MOVF        R0, 0 
	MOVWF       posicionar_horizontal_y_L0+0 
;BatalhaNaval.c,80 :: 		} while(!validar_posicoes_vazias(x, y, tamanho));
	MOVF        posicionar_horizontal_x_L0+0, 0 
	MOVWF       FARG_validar_posicoes_vazias_x+0 
	MOVF        R0, 0 
	MOVWF       FARG_validar_posicoes_vazias_y+0 
	MOVF        FARG_posicionar_horizontal_tamanho+0, 0 
	MOVWF       FARG_validar_posicoes_vazias_tamanho+0 
	CALL        _validar_posicoes_vazias+0, 0
	MOVF        R0, 1 
	BTFSC       STATUS+0, 2 
	GOTO        L_posicionar_horizontal10
;BatalhaNaval.c,82 :: 		for (i = x; i < x + tamanho; i++)
	MOVF        posicionar_horizontal_x_L0+0, 0 
	MOVWF       posicionar_horizontal_i_L0+0 
L_posicionar_horizontal13:
	MOVF        FARG_posicionar_horizontal_tamanho+0, 0 
	ADDWF       posicionar_horizontal_x_L0+0, 0 
	MOVWF       R1 
	MOVLW       0
	BTFSC       posicionar_horizontal_x_L0+0, 7 
	MOVLW       255
	MOVWF       R2 
	MOVLW       0
	BTFSC       FARG_posicionar_horizontal_tamanho+0, 7 
	MOVLW       255
	ADDWFC      R2, 1 
	MOVLW       128
	BTFSC       posicionar_horizontal_i_L0+0, 7 
	MOVLW       127
	MOVWF       R0 
	MOVLW       128
	XORWF       R2, 0 
	SUBWF       R0, 0 
	BTFSS       STATUS+0, 2 
	GOTO        L__posicionar_horizontal43
	MOVF        R1, 0 
	SUBWF       posicionar_horizontal_i_L0+0, 0 
L__posicionar_horizontal43:
	BTFSC       STATUS+0, 0 
	GOTO        L_posicionar_horizontal14
;BatalhaNaval.c,83 :: 		matriz[i][y] = 1;
	MOVLW       30
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        posicionar_horizontal_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       posicionar_horizontal_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _matriz+0
	ADDWF       R0, 0 
	MOVWF       R3 
	MOVLW       hi_addr(_matriz+0)
	ADDWFC      R1, 0 
	MOVWF       R4 
	MOVF        posicionar_horizontal_y_L0+0, 0 
	MOVWF       R0 
	MOVLW       0
	BTFSC       posicionar_horizontal_y_L0+0, 7 
	MOVLW       255
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        R0, 0 
	ADDWF       R3, 0 
	MOVWF       FSR1 
	MOVF        R1, 0 
	ADDWFC      R4, 0 
	MOVWF       FSR1H 
	MOVLW       1
	MOVWF       POSTINC1+0 
	MOVLW       0
	MOVWF       POSTINC1+0 
;BatalhaNaval.c,82 :: 		for (i = x; i < x + tamanho; i++)
	INCF        posicionar_horizontal_i_L0+0, 1 
;BatalhaNaval.c,83 :: 		matriz[i][y] = 1;
	GOTO        L_posicionar_horizontal13
L_posicionar_horizontal14:
;BatalhaNaval.c,85 :: 		for (i = 0; i < tamanho; i++)
	CLRF        posicionar_horizontal_i_L0+0 
L_posicionar_horizontal16:
	MOVLW       128
	XORWF       posicionar_horizontal_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       128
	XORWF       FARG_posicionar_horizontal_tamanho+0, 0 
	SUBWF       R0, 0 
	BTFSC       STATUS+0, 0 
	GOTO        L_posicionar_horizontal17
;BatalhaNaval.c,87 :: 		posicoes[i].col = x;
	MOVF        posicionar_horizontal_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       0
	BTFSC       posicionar_horizontal_i_L0+0, 7 
	MOVLW       255
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        FARG_posicionar_horizontal_posicoes+0, 0 
	ADDWF       R0, 1 
	MOVF        FARG_posicionar_horizontal_posicoes+1, 0 
	ADDWFC      R1, 1 
	MOVLW       1
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	MOVF        posicionar_horizontal_x_L0+0, 0 
	MOVWF       POSTINC1+0 
;BatalhaNaval.c,88 :: 		posicoes[i].lin = y;
	MOVF        posicionar_horizontal_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       0
	BTFSC       posicionar_horizontal_i_L0+0, 7 
	MOVLW       255
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        R0, 0 
	ADDWF       FARG_posicionar_horizontal_posicoes+0, 0 
	MOVWF       FSR1 
	MOVF        R1, 0 
	ADDWFC      FARG_posicionar_horizontal_posicoes+1, 0 
	MOVWF       FSR1H 
	MOVF        posicionar_horizontal_y_L0+0, 0 
	MOVWF       POSTINC1+0 
;BatalhaNaval.c,89 :: 		posicoes[i].acertou = 0;
	MOVF        posicionar_horizontal_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       0
	BTFSC       posicionar_horizontal_i_L0+0, 7 
	MOVLW       255
	MOVWF       R1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	RLCF        R0, 1 
	BCF         R0, 0 
	RLCF        R1, 1 
	MOVF        FARG_posicionar_horizontal_posicoes+0, 0 
	ADDWF       R0, 1 
	MOVF        FARG_posicionar_horizontal_posicoes+1, 0 
	ADDWFC      R1, 1 
	MOVLW       2
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,85 :: 		for (i = 0; i < tamanho; i++)
	INCF        posicionar_horizontal_i_L0+0, 1 
;BatalhaNaval.c,90 :: 		}
	GOTO        L_posicionar_horizontal16
L_posicionar_horizontal17:
;BatalhaNaval.c,91 :: 		}
L_end_posicionar_horizontal:
	RETURN      0
; end of _posicionar_horizontal

_setup:

;BatalhaNaval.c,93 :: 		void setup()
;BatalhaNaval.c,96 :: 		UART1_Init(8200);
	MOVLW       60
	MOVWF       SPBRG+0 
	BSF         TXSTA+0, 2, 0
	CALL        _UART1_Init+0, 0
;BatalhaNaval.c,99 :: 		init_matrix();
	CALL        _init_matrix+0, 0
;BatalhaNaval.c,101 :: 		for (i = 0; i < 2; i++)
	CLRF        setup_i_L0+0 
L_setup19:
	MOVLW       128
	XORWF       setup_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       128
	XORLW       2
	SUBWF       R0, 0 
	BTFSC       STATUS+0, 0 
	GOTO        L_setup20
;BatalhaNaval.c,103 :: 		posicionar_horizontal(encouracados[i].posicoes, 5);
	MOVLW       21
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _encouracados+0
	ADDWF       R0, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+0 
	MOVLW       hi_addr(_encouracados+0)
	ADDWFC      R1, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+1 
	MOVLW       5
	MOVWF       FARG_posicionar_horizontal_tamanho+0 
	CALL        _posicionar_horizontal+0, 0
;BatalhaNaval.c,104 :: 		encouracados[i].afundado = 0;
	MOVLW       21
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _encouracados+0
	ADDWF       R0, 1 
	MOVLW       hi_addr(_encouracados+0)
	ADDWFC      R1, 1 
	MOVLW       20
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,101 :: 		for (i = 0; i < 2; i++)
	INCF        setup_i_L0+0, 1 
;BatalhaNaval.c,105 :: 		}
	GOTO        L_setup19
L_setup20:
;BatalhaNaval.c,107 :: 		for (i = 0; i < 1; i++)
	CLRF        setup_i_L0+0 
L_setup22:
	MOVLW       128
	XORWF       setup_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       128
	XORLW       1
	SUBWF       R0, 0 
	BTFSC       STATUS+0, 0 
	GOTO        L_setup23
;BatalhaNaval.c,109 :: 		posicionar_horizontal(porta_avioes[i].posicoes, 6);
	MOVLW       25
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _porta_avioes+0
	ADDWF       R0, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+0 
	MOVLW       hi_addr(_porta_avioes+0)
	ADDWFC      R1, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+1 
	MOVLW       6
	MOVWF       FARG_posicionar_horizontal_tamanho+0 
	CALL        _posicionar_horizontal+0, 0
;BatalhaNaval.c,110 :: 		porta_avioes[i].afundado = 0;
	MOVLW       25
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _porta_avioes+0
	ADDWF       R0, 1 
	MOVLW       hi_addr(_porta_avioes+0)
	ADDWFC      R1, 1 
	MOVLW       24
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,107 :: 		for (i = 0; i < 1; i++)
	INCF        setup_i_L0+0, 1 
;BatalhaNaval.c,111 :: 		}
	GOTO        L_setup22
L_setup23:
;BatalhaNaval.c,113 :: 		for (i = 0; i < 4; i++)
	CLRF        setup_i_L0+0 
L_setup25:
	MOVLW       128
	XORWF       setup_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       128
	XORLW       4
	SUBWF       R0, 0 
	BTFSC       STATUS+0, 0 
	GOTO        L_setup26
;BatalhaNaval.c,115 :: 		posicionar_horizontal(submarinos[i].posicoes, 1);
	MOVLW       5
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _submarinos+0
	ADDWF       R0, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+0 
	MOVLW       hi_addr(_submarinos+0)
	ADDWFC      R1, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+1 
	MOVLW       1
	MOVWF       FARG_posicionar_horizontal_tamanho+0 
	CALL        _posicionar_horizontal+0, 0
;BatalhaNaval.c,116 :: 		submarinos[i].afundado = 0;
	MOVLW       5
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _submarinos+0
	ADDWF       R0, 1 
	MOVLW       hi_addr(_submarinos+0)
	ADDWFC      R1, 1 
	MOVLW       4
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,113 :: 		for (i = 0; i < 4; i++)
	INCF        setup_i_L0+0, 1 
;BatalhaNaval.c,117 :: 		}
	GOTO        L_setup25
L_setup26:
;BatalhaNaval.c,119 :: 		for (i = 0; i < 3; i++)
	CLRF        setup_i_L0+0 
L_setup28:
	MOVLW       128
	XORWF       setup_i_L0+0, 0 
	MOVWF       R0 
	MOVLW       128
	XORLW       3
	SUBWF       R0, 0 
	BTFSC       STATUS+0, 0 
	GOTO        L_setup29
;BatalhaNaval.c,121 :: 		posicionar_horizontal(cruzadores[i].posicoes, 2);
	MOVLW       9
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _cruzadores+0
	ADDWF       R0, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+0 
	MOVLW       hi_addr(_cruzadores+0)
	ADDWFC      R1, 0 
	MOVWF       FARG_posicionar_horizontal_posicoes+1 
	MOVLW       2
	MOVWF       FARG_posicionar_horizontal_tamanho+0 
	CALL        _posicionar_horizontal+0, 0
;BatalhaNaval.c,122 :: 		cruzadores[i].afundado = 0;
	MOVLW       9
	MOVWF       R0 
	MOVLW       0
	MOVWF       R1 
	MOVF        setup_i_L0+0, 0 
	MOVWF       R4 
	MOVLW       0
	BTFSC       setup_i_L0+0, 7 
	MOVLW       255
	MOVWF       R5 
	CALL        _Mul_16X16_U+0, 0
	MOVLW       _cruzadores+0
	ADDWF       R0, 1 
	MOVLW       hi_addr(_cruzadores+0)
	ADDWFC      R1, 1 
	MOVLW       8
	ADDWF       R0, 0 
	MOVWF       FSR1 
	MOVLW       0
	ADDWFC      R1, 0 
	MOVWF       FSR1H 
	CLRF        POSTINC1+0 
;BatalhaNaval.c,119 :: 		for (i = 0; i < 3; i++)
	INCF        setup_i_L0+0, 1 
;BatalhaNaval.c,123 :: 		}
	GOTO        L_setup28
L_setup29:
;BatalhaNaval.c,124 :: 		}
L_end_setup:
	RETURN      0
; end of _setup

_main_loop:

;BatalhaNaval.c,126 :: 		short main_loop()
;BatalhaNaval.c,128 :: 		UART_Write_Text("oi");
	MOVLW       ?lstr1_BatalhaNaval+0
	MOVWF       FARG_UART_Write_Text_uart_text+0 
	MOVLW       hi_addr(?lstr1_BatalhaNaval+0)
	MOVWF       FARG_UART_Write_Text_uart_text+1 
	CALL        _UART_Write_Text+0, 0
;BatalhaNaval.c,129 :: 		}
L_end_main_loop:
	RETURN      0
; end of _main_loop

_main:

;BatalhaNaval.c,131 :: 		void main()
;BatalhaNaval.c,135 :: 		setup();
	CALL        _setup+0, 0
;BatalhaNaval.c,137 :: 		do
L_main31:
;BatalhaNaval.c,139 :: 		terminou = main_loop();
	CALL        _main_loop+0, 0
;BatalhaNaval.c,140 :: 		} while (!terminou);
	MOVF        R0, 1 
	BTFSC       STATUS+0, 2 
	GOTO        L_main31
;BatalhaNaval.c,141 :: 		}
L_end_main:
	GOTO        $+0
; end of _main
