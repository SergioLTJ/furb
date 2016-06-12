package com.web.helper;

import com.google.gson.JsonElement;

public class GsonHelper {

	public static String obterValorOuNull(JsonElement elemento) {
		if (elemento == null || elemento.isJsonNull()) {		
			return null;
		}
		
		return elemento.getAsString();
	}
	
}
