package principal;

import ferramentas.ConexaoFTP;
import ferramentas.TarefaGerarBackup;
import formularios.login.TelaLogin;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TarefaGerarBackup tarefaBackup = new TarefaGerarBackup();
		
		TelaCarregamentoInicial telaCarregamento = new TelaCarregamentoInicial();
		telaCarregamento.initStyle(StageStyle.UNDECORATED);
		telaCarregamento.showAndWait();
		
		tarefaBackup.executarTarefaBackup();
		
		//telaCarregamento.getTelaAdministrador().showAndWait();
		
		do {
			telaCarregamento.getTelaAdministrador().resetarLogoff();
			telaCarregamento.getTelaNormal().resetarLogoff();
			
			TelaLogin telaLogin = new TelaLogin();
			telaLogin.showAndWait();
			
			if(telaLogin.isLoginValido()){
				
				telaLogin.resetarLogin();
				
				if(telaLogin.getPerfilUser().equals("Administrador")){
					telaCarregamento.getTelaAdministrador().showAndWait();
				}
				else if(telaLogin.getPerfilUser().equals("Comum")) {
					telaCarregamento.getTelaNormal().showAndWait();
				}
			}			
		}while(telaCarregamento.getTelaAdministrador().fazerLogoff() == true || telaCarregamento.getTelaNormal().fazerLogoff() == true);
		
		ConexaoFTP.desconectar();
		
		this.stop();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}