package ferramentas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

public class ConexaoFTP {
	private static FTPClient ftp = new FTPClient();
	private static final String servidor = "ftp.orionmusic.esy.es";
	private static final String usuario = "u403955231";
	private static final String senha = "142536";
	
	public static void conectar(){
		try {
			ftp.connect(servidor);
			ftp.login(usuario, senha);
			//Os arquivos precisam ser enviados no modo binário, ou as imagens correm o risco de se corromper
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void desconectar(){
		if(ftp.isConnected()){
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public static boolean enviarArquivo(String caminhoLocalArquivo, String diretorioFinal){
		if(ftp.isConnected()){			
			File arquivoParaEnviar = new File(caminhoLocalArquivo);
			
			try {
				ftp.changeWorkingDirectory(diretorioFinal);
				return ftp.storeFile(arquivoParaEnviar.getName(), new FileInputStream(arquivoParaEnviar));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public static boolean baixarArquivo(String caminhoDoArquivoArquivoNoServidor, String nomeDoArquivoNoServidor, String caminhoFinalDoArquivoLocal){
		if(ftp.isConnected()){
			try {
				ftp.changeWorkingDirectory(caminhoDoArquivoArquivoNoServidor);
				FileOutputStream arquivoBaixado = new FileOutputStream(caminhoFinalDoArquivoLocal);
				return ftp.retrieveFile(nomeDoArquivoNoServidor, arquivoBaixado);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}			
		}
		else {
			return false;
		}
	}
	
	public static List<String> listarArquivos(String diretorio){
		if(ftp.isConnected()){
			List<String> arquivos = new ArrayList<String>();
			
			try {
				ftp.changeWorkingDirectory(diretorio);
				try {
					for(String nomeArquivo : ftp.listNames()){
						arquivos.add(nomeArquivo);
					}					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return arquivos;			
		}
		else {
			return new ArrayList<String>();
		}
	}
	
	public static boolean deletarArquivo(String caminhoDoArquivoArquivoNoServidor, String nomeDoArquivoNoServidor){
		if(ftp.isConnected()){
				try {
					ftp.changeWorkingDirectory(caminhoDoArquivoArquivoNoServidor);
					return ftp.deleteFile(nomeDoArquivoNoServidor);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
		}
		else {
			return false;
		}
	}
}