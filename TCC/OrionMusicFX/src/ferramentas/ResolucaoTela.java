package ferramentas;

import java.awt.Toolkit;

public class ResolucaoTela {
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit(); //Instancia um kit de ferramentas do AWT, com esse kit é possível resgatar o tamanho da tela
	
	public static int getAltura(){
		return toolkit.getScreenSize().height;
	}
	
	// Retorna o tamanho de tantos porcento da altura
	public static double getAltura(double porcentagem){
		return getAltura() * porcentagem / 100;
	}
	
	public static int getLargura(){
		return toolkit.getScreenSize().width;
	}
	
	// Retorna o tamanho de tantos porcento da largura
	public static double getLargura(double porcentagem){
		return getLargura() * porcentagem / 100;
	}
}
