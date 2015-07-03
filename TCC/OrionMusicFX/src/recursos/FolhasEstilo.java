package recursos;

import java.io.File;

public class FolhasEstilo {
	
	private static String separadorDePastas = File.separator;
	
	private static File styleForms = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleForms.css");
	private static File styleJanelas = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleJanelas.css");
	private static File styleMainAdministrador = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleMainAdministrador.css");
	private static File styleMainComum = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleMainComum.css");
	private static File styleDialogos = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleDialogos.css");
	private static File styleTelaLogin = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleTelaLogin.css");
	private static File styleTelaCarregamento = new File("resources"+separadorDePastas+"files"+separadorDePastas+"styleTelaCarregamento.css");
	
	public static String getCaminhoStyleForms(){
		return "file:///" + styleForms.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleJanelas(){
		return "file:///" + styleJanelas.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleMainComum(){
		return "file:///" + styleMainComum.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleMainAdministrador(){
		return "file:///" + styleMainAdministrador.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleDialogo(){
		return "file:///" + styleDialogos.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleTelaLogin(){
		return "file:///" + styleTelaLogin.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoStyleTelaCarregamento(){
		return "file:///" + styleTelaCarregamento.getAbsolutePath().replace(separadorDePastas, "/");
	}
}