package formularios.pesquisa;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import ferramentas.Alerta;
import formularios.Dialogo;
import formularios.backup.PrincipalBackup;
import formularios.cadastros.PrincipalCadastros;
import formularios.cronograma.PrincipalCronograma;
import formularios.lembrete.PrincipalLembretes;
import formularios.site.PrincipalSite;
import formularios.teoria.PrincipalTeoria;
import formularios.usuarios.PrincipalUsuario;

public class PrincipalPesquisa extends Dialogo implements EventHandler<Event>{

	private Label lblPesquisaFuncao = new Label("Pesquisar Função:");
	private TextField txtPesquisa = new TextField();
	private Button btnPesquisa = new Button("Pesquisar");
	
	private boolean logadoComoAdministrador = true;
	
	public PrincipalPesquisa(boolean logadoComoAdministrador) {
		this.logadoComoAdministrador = logadoComoAdministrador;
		
		this.layout.add(lblPesquisaFuncao, 0, 0);
		this.layout.add(txtPesquisa, 1, 0);
		this.layout.add(btnPesquisa, 2, 0);
		
		this.setTitle("Pesquisa de Funcionalidades");
		
		this.btnPesquisa.setOnMouseClicked(this);
		
		this.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				txtPesquisa.setText("");
			}
		});
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acionarBotaoPesquisa();
				}
			}
		});
	}
	
	private void acionarBotaoPesquisa() {
		this.handle(new Event(this.btnPesquisa, null, null));
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnPesquisa){
			String resultado = this.txtPesquisa.getText().toUpperCase();

			if(resultado.compareTo("ESCOLA") == 0){
				PrincipalCadastros cadastros = new PrincipalCadastros();
				cadastros.prepararParaExibirFormEscola();
				cadastros.show();
				close();
			}
			else if(resultado.compareTo("ALUNO") == 0){
				PrincipalCadastros cadastros = new PrincipalCadastros();
				cadastros.prepararParaExibirFormAluno();
				cadastros.show();
				close();
			}
			else if(resultado.compareTo("RESPONSAVEL") == 0){
				PrincipalCadastros cadastros = new PrincipalCadastros();
				cadastros.prepararParaExibirFormResponsavel();
				cadastros.show();
				close();
			}
			else if(resultado.compareTo("MATRICULA") == 0){
				PrincipalCadastros cadastros = new PrincipalCadastros();
				cadastros.prepararParaExibirFormMatricula();
				cadastros.show();
				close();
			}
			else if(resultado.compareTo("CRONOGRAMA") == 0){
				PrincipalCronograma cronograma = new PrincipalCronograma();
				cronograma.prepararCronogramaParaExibir();
				cronograma.show();
				close();
			}
			else if(resultado.compareTo("DISCIPLINA") == 0){
				PrincipalCronograma cronograma = new PrincipalCronograma();
				cronograma.prepararDisciplinaParaExibir();
				cronograma.show();
				close();
			}
			else if(resultado.compareTo("TURMA") == 0){
				PrincipalCronograma cronograma = new PrincipalCronograma();
				cronograma.prepararTurmaParaExibir();
				cronograma.show();
				close();
			}
			else if(resultado.compareTo("LEMBRETE") == 0){
				PrincipalLembretes lembretes = new PrincipalLembretes();
				lembretes.show();
				close();
			}
			else if(resultado.compareTo("CONTEUDO") == 0){
				PrincipalTeoria teoria = new PrincipalTeoria();
				teoria.prepararFormConteudo();
				teoria.show();
				close();
			}
			else if(this.logadoComoAdministrador){
				if(resultado.compareTo("SITE") == 0){
					PrincipalSite site = new PrincipalSite();
					site.show();
					close();
				}
				else if(resultado.compareTo("BACKUP") == 0){
					PrincipalBackup backup = new PrincipalBackup();
					backup.prepararFormBackup();
					backup.show();
					close();
				}
				else if(resultado.compareTo("RESTAURACAO") == 0){
					PrincipalBackup backup = new PrincipalBackup();
					backup.prepararFormRestauracao();
					backup.show();
					close();
				}
				else if(resultado.compareTo("USUARIO") == 0){
					PrincipalUsuario usuario = new PrincipalUsuario();
					usuario.show();
					close();
				}
				else {
					Alerta.mostrarMensagemErro("Não foram encontradas funções com o texto digitado. Digite brevemente os itens que deseja buscar, por exemplo: Aluno. Também busque evitar o uso de acentos e procure utilizar o singular.", "Função não encontrada");
				}
			}
			else {
				Alerta.mostrarMensagemErro("Não foram encontradas funções com o texto digitado. Digite brevemente os itens que deseja buscar, por exemplo: Aluno. Também busque evitar o uso de acentos e procure utilizar o singular.", "Função não encontrada");
			}
		}
	}
}