package ferramentas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;

public class Validacao {

	public static boolean validarEmail(String email) {

		/* Créditos: Marco Figueiredo */

		if (email.equals("")) {
			return true;
		}

		Pattern p = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean valorVazio(String valor) {
		if (valor.equals("")) {
			return true;
		} else {
			for (char caractere : valor.toCharArray()) {
				if (caractere != ' ')
					return false;
			}
			return true;
		}
	}
	
	public static void realcarCampo(Node campo){
		campo.requestFocus();
		campo.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
	}
	
	public static void voltarCampoAoNormal(Node campo){
		campo.setStyle("");
	}
}
