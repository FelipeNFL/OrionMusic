package principal;

import ferramentas.ConexaoFTP;
import ferramentas.GerenciadorEntidades;
import formularios.TelaCarregamento;
import formularios.telaInicial.TelaPerfilAdministrador;
import formularios.telaInicial.TelaPerfilNormal;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class TelaCarregamentoInicial extends TelaCarregamento {
	
	private TelaPerfilNormal telaNormal;
	private TelaPerfilAdministrador telaAdm;
	
	public TelaPerfilAdministrador getTelaAdministrador(){
		return this.telaAdm;
	}
	
	public TelaPerfilNormal getTelaNormal(){
		return this.telaNormal;
	}
	
	public TelaCarregamentoInicial() {
		
		this.lblStatus.setText("Carregando Universo da Música");
		
		videoCarregamento.setCycleCount(0);
		
		this.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				
				Task<String> tarefaCarregaBanco = new Task<String>() {
					@Override
					protected String call() throws Exception {
						GerenciadorEntidades.aquecerMotores();
						try {
							ConexaoFTP.conectar();							
						}
						catch(Exception e){
							//Não precisa fazer nada, só continue executando...
							//Talvez o cliente esteja sem internet
							e.getStackTrace();
						}
						return null;
					}
					@Override
					protected void succeeded() {
						lblStatus.setText("Executando últimos ajustes");
						
						Task<Void> tarefaInstanciaJanelas = new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								return null;
							}
							
							@Override
							protected void succeeded() {
								telaAdm = new TelaPerfilAdministrador();
								telaNormal = new TelaPerfilNormal();
								close();
								super.succeeded();
							}
						};
						
						Thread tarefaParaTaskInstanciaJanela = new Thread(tarefaInstanciaJanelas);
						tarefaParaTaskInstanciaJanela.setDaemon(true);
						tarefaParaTaskInstanciaJanela.start();
						
						
					}
				};
				
				Thread executarTarefaBanco = new Thread(tarefaCarregaBanco);
				executarTarefaBanco.setDaemon(true);
				executarTarefaBanco.start();
			}
		});
	}
}
