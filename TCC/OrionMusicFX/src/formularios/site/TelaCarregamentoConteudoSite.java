package formularios.site;

import java.io.File;
import java.util.List;
import ferramentas.Alerta;
import ferramentas.ConexaoFTP;
import formularios.TelaCarregamento;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class TelaCarregamentoConteudoSite extends TelaCarregamento {
	
	private boolean sucesso = true;
	
	public boolean getSucesso(){
		return this.sucesso;
	}
	
	public TelaCarregamentoConteudoSite(final List<File> listaDeArquivosParaSeremCarregados){
		
		this.lblStatus.setText("Enviando arquivos para o site");
		
		this.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				
				Task<Void> tarefaCarregaArquivos = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						for(File arquivo : listaDeArquivosParaSeremCarregados){
							if(ConexaoFTP.enviarArquivo(arquivo.getPath(), "/public_html/GALERIA") == false){
								sucesso = false;
							}
						}
						
						return null;
					}
					@Override
					protected void succeeded() {
						close();
						if(sucesso){
							Alerta.mostrarMensagemSucesso("Os arquivos foram enviados com sucesso!", "Sucesso ao excluir arquivos");
						}
						else {
							Alerta.mostrarMensagemErro("Possivelmente alguns arquivos não foram enviados com sucesso!","Erro ao excluir arquivos");
						}
					}
				};
				
				Thread executarTarefaBanco = new Thread(tarefaCarregaArquivos);
				executarTarefaBanco.setDaemon(true);
				executarTarefaBanco.start();
			}
		});
	}

	public TelaCarregamentoConteudoSite(final ObservableList<String> listaDeCoisasASeremExcluidas) {
		this.lblStatus.setText("Excluindo arquivos do site");
		
		this.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				
				Task<Void> tarefaCarregaArquivos = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						for(String arquivo : listaDeCoisasASeremExcluidas){
							 if(ConexaoFTP.deletarArquivo("/public_html/GALERIA", arquivo) == false){
								 sucesso = false;
							 }
						}
						return null;
					}
					@Override
					protected void succeeded() {
						close();
						if(sucesso){
							Alerta.mostrarMensagemSucesso("Os arquivos foram excluídos com sucesso!", "Sucesso ao excluir arquivos");
						}
						else {
							Alerta.mostrarMensagemErro("Possivelmente alguns arquivos não foram excluídos com sucesso!","Erro ao excluir arquivos");
						}
					}
				};
				
				Thread executarTarefaBanco = new Thread(tarefaCarregaArquivos);
				executarTarefaBanco.setDaemon(true);
				executarTarefaBanco.start();
			}
		});
	}
}
