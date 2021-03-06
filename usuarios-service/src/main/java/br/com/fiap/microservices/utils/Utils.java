package br.com.fiap.microservices.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {

	public static String buildCodigoAtivacao() {
		return String.format("%04d", new Random().nextInt(10000));
	}

	public static Date dataAtual() {
		return Calendar.getInstance().getTime();
	}
	
	public static Date addDiasDataAtual(int dias) {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, dias);
		
		return data.getTime();
	}
	
	public static String removeMaskCelular(String str) {
		return str.replace("(","").replace(")", "").replace(" ","").replace("-","");
	}

}
