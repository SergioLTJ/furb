package com.furb.ia.trabalhofinal;

import java.util.ArrayList;
import java.util.List;

import com.furb.ia.trabalhofinal.dados.Clonavel;

public class Utils {

	public static <T extends Clonavel<T>> List<T> clonarLista(List<T> listaOriginal) {
		List<T> listaNova = new ArrayList<>(listaOriginal.size());
		
		for (T elemento : listaOriginal) {
			listaNova.add(elemento.clonar());
		}
		
		return listaNova;
	}
}
