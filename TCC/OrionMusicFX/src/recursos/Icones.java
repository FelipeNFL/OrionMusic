package recursos;

import java.io.File;

public class Icones {

	private static String separadorDePastas = File.separator;
	
	private static File iconeTelaInicial = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"IconeTelaInicial.png");
	private static File iconeFormularioCorreto = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"iconeFormularioCorreto.png");
	private static File iconeFormularioIncorreto = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"iconeFormularioIncorreto.png");
	private static File logoInicial = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"iconePrincipalSemCirculo.png");

	//menu
	
	private static File iconeAcompanhamento = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnAcompanhamento.png");
	private static File iconeCadastro = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnCadastros.png");
	private static File iconeBackup = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnBackup.png");
	private static File iconeCronogramaAdministrador = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnCronograma_administrador.png");
	private static File iconeTeoria = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnTeoria.png");
	private static File iconeUsuarios = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnUsuarios.png");
	private static File iconeCronogramaComum = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnCronograma_comum.png");
	private static File iconePesquisarAdministrador = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnPesquisar_administrador.png");
	private static File iconePesquisarComum = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnPesquisar_comum.png");
	private static File iconeSite = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnSite.png");
	private static File iconeLogoff = new File("resources"+separadorDePastas+"images"+separadorDePastas+"icons"+separadorDePastas+"btnLogoff.png");
	
	public static String getCaminhoIconePrincipal(){
		return "file:///" + logoInicial.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoIconeFormularioCorreto(){
		return "file:///" + iconeFormularioCorreto.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoIconeFormularioIncorreto(){
		return "file:///" + iconeFormularioIncorreto.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getCaminhoLogoTelaInicial(){
		return "file:///" + iconeTelaInicial.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	//Métodos menu
	
	public static String getIconeAcompanhamento(){
		return "file:///" + iconeAcompanhamento.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeCadastro(){
		return "file:///" + iconeCadastro.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeBackup(){
		return "file:///" + iconeBackup.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeCronogramaAdministrador(){
		return "file:///" + iconeCronogramaAdministrador.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeTeoria(){
		return "file:///" + iconeTeoria.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeUsuarios(){
		return "file:///" + iconeUsuarios.getAbsolutePath().replace(separadorDePastas, "/");
	}

	public static String getIconeCronogramaComum(){
		return "file:///" + iconeCronogramaComum.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconePesquisarAdministrador(){
		return "file:///" + iconePesquisarAdministrador.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconePesquisarComum(){
		return "file:///" + iconePesquisarComum.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeSite(){
		return "file:///" + iconeSite.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	public static String getIconeLogoff(){
		return "file:///" + iconeLogoff.getAbsolutePath().replace(separadorDePastas, "/");
	}
}