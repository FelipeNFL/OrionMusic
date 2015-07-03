package formularios.backup;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import ferramentas.Alerta;
import ferramentas.ConexaoFTP;
import ferramentas.ResolucaoTela;
import formularios.BaseMenu;

public class Backup extends BaseMenu implements EventHandler<Event>{
	
	private Label lblExplicacao = new Label("Clique no botão abaixo para iniciar o download do Backup, \napós baixado o backup será automaticamente instalado.");
	private Button btnFazerBackup = new Button("Instalar Backup");
	private ListView<String> lstBackupsDisponivel = new ListView<>();
	private ProgressBar progresso = new ProgressBar(0);
	
	public Backup() {
		
		this.lstBackupsDisponivel.setMaxWidth(ResolucaoTela.getLargura(45));
		
		this.getChildren().add(this.lblExplicacao);
		this.getChildren().add(this.lstBackupsDisponivel);
		this.getChildren().add(this.btnFazerBackup);
		this.getChildren().add(this.progresso);
		
		this.progresso.setVisible(false);
		this.progresso.setMinWidth(ResolucaoTela.getLargura(50));
		
		this.btnFazerBackup.setOnMouseClicked(this);
	}
	
	public void preencherTabela(){
		this.lstBackupsDisponivel.getItems().addAll(FXCollections.observableArrayList(ConexaoFTP.listarArquivos("/public_html/BACKUP")));
		
		this.lstBackupsDisponivel.getItems().remove(".");
		this.lstBackupsDisponivel.getItems().remove("..");
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnFazerBackup){
			
			if(Alerta.mostrarMensagemSimNao("Você deseja realmente instalar o Backup? Essa ação não pode ser desfeita!", "Confirmação de Backup") == false){
				return;
			}
			
			Alerta.mostrarMensagemAtencao("Não feche o programa enquanto a ação não for concluída! Será exibida uma mensagem quando o Backup for concluído.", "Não feche o programa");
			
			final String backupEscolhido = this.lstBackupsDisponivel.getSelectionModel().getSelectedItem();
			
			this.progresso.setVisible(true);
			this.progresso.setDisable(false);
			this.btnFazerBackup.setDisable(true);
			this.lstBackupsDisponivel.setDisable(true);
			
			Task<Boolean> tarefaInstalaBackup = new Task<Boolean>() {
				@Override
				protected Boolean call() throws Exception {
					updateProgress(0.25, 1);
					
					int tentativas = 0;
					boolean sucesso = false;
					
					while(tentativas < 5 && sucesso == false){
						sucesso = ConexaoFTP.baixarArquivo("/public_html/BACKUP", backupEscolhido , "tmp/backup/backupBaixado.sql");
						tentativas++;
					}
					
					if(sucesso == false){
						return false;
					}
					
					updateProgress(0.25, 1);
					
					//String comando = "mysql --user=root --password=1234 --port=3307 --host=localhost OrionMusic < tmp/backup/backupBaixado.sql";
					
					try {
						Runtime.getRuntime().exec("cmd.exe /c "+new File("mysql.exe").getAbsolutePath()+" --user=root --password=1234 --port=3307 --host=localhost OrionMusic < \""+new File("tmp/backup/backupBaixado.sql").getAbsolutePath()+"\"");
					}
					catch(Exception e) {
						e.printStackTrace();
						return false;
					}
					
					updateProgress(0.25, 1);
					
					return true;
				}
				@Override
				protected void succeeded() {
					progresso.setProgress(1);
					if(getValue()){
						Alerta.mostrarMensagemSucesso("O Backup foi instalado com sucesso! Feche o programa e abra-o novamente para que as ações tenham efeito.", "Backup efetuado com sucesso");						
					}
					else {
						Alerta.mostrarMensagemErro("Ocorreu um erro ao instalar o Backup! Provavelmente houve algum erro de conectividade.", "Erro");
					}
				}
			};
			
			Thread executarBackup = new Thread(tarefaInstalaBackup);
			executarBackup.setDaemon(true);
			executarBackup.start();
			
			tarefaInstalaBackup.progressProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
					
					progresso.setProgress(newValue.doubleValue());
				}
			});
		}
	}
}