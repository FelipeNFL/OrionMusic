package ferramentas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

public class TarefaGerarBackup implements Runnable {
	
	private ProcessBuilder comandoBackup;
	private String nomeDoArquivoGerado = "";
	
	public TarefaGerarBackup() {
		
		String nomeDoArquivoDeBackup = new DateTime().toString();
		nomeDoArquivoDeBackup = nomeDoArquivoDeBackup.replace('T', '-');
		nomeDoArquivoDeBackup = nomeDoArquivoDeBackup.replace(':', '-');
		nomeDoArquivoDeBackup = nomeDoArquivoDeBackup.substring(0, 16);
		this.nomeDoArquivoGerado = nomeDoArquivoDeBackup;
		
		List<String> comandos = new ArrayList<>();
		comandos.add("mysqldump.exe");
		comandos.add("--no-defaults");
		comandos.add("--host=localhost");
		comandos.add("--port=3307");
		comandos.add("--user=root");
		comandos.add("--password=1234");
		comandos.add("OrionMusic");
		comandos.add("--result-file=tmp/backup/"+nomeDoArquivoDeBackup+".sql");
		this.comandoBackup = new ProcessBuilder(comandos);
	}
	
	@Override
	public void run() {
		try {
			this.comandoBackup.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ConexaoFTP.enviarArquivo("tmp/backup/"+this.nomeDoArquivoGerado+".sql", "/public_html/BACKUP");
		
		// Deixando apenas 20 backups no servidor
		
		List<String> listaDeBackups = ConexaoFTP.listarArquivos("/public_html/BACKUP");
				
		int numBackups = listaDeBackups.size(); 
		if(numBackups > 20){
			Collections.reverse(listaDeBackups);
			
			for (String arquivo : listaDeBackups.subList(21, numBackups)) {
				ConexaoFTP.deletarArquivo("/public_html/BACKUP", arquivo);
			}
		}
		
		// Deixando apenas 20 backups na máquina local
		
		List<String> listaDeBackupsLocal = new ArrayList<>();
		
		for (String arquivo : new File("tmp/backup").list()) {
			listaDeBackupsLocal.add(arquivo);
		}
		
		if(listaDeBackupsLocal.size() > 20){
			Collections.reverse(listaDeBackupsLocal);
			
			for (String arquivo : listaDeBackupsLocal.subList(21, listaDeBackupsLocal.size())) {
				new File("tmp/backup/"+arquivo).delete();
			}
		}
	}
	
	public void executarTarefaBackup(){
		Thread tarefa = new Thread(this);
		tarefa.start();
	}
}
