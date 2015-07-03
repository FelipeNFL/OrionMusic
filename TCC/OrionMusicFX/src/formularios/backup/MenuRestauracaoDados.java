package formularios.backup;

import formularios.BaseMenu;
import formularios.backup.restauracaoDeDados.PrincipalAlunoRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalDisciplinaRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalEscolaRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalMatriculaRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalResponsavelRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalTurmaRestauracao;
import formularios.backup.restauracaoDeDados.PrincipalUsuarioRestauracao;
import formularios.lembrete.PrincipalLembretes;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class MenuRestauracaoDados extends BaseMenu implements EventHandler<Event> {
	
	private Label lblAlunos = new Label("Alunos");
	private Label lblResponsavel = new Label("Responsável");
	private Label lblEscola = new Label("Escola");
	private Label lblMatricula = new Label("Matrícula");
	private Label lblDisciplina = new Label("Disciplina");
	private Label lblTurmas = new Label("Turmas");
	private Label lblLembrete = new Label("Lembretes");
	private Label lblUsuario = new Label("Usuário");
	
	private PrincipalAlunoRestauracao telaAlunoRestauracao = new PrincipalAlunoRestauracao();
	private PrincipalDisciplinaRestauracao telaDisciplinaRestauracao = new PrincipalDisciplinaRestauracao();
	private PrincipalResponsavelRestauracao telaResponsavelRestauracao = new PrincipalResponsavelRestauracao();
	private PrincipalEscolaRestauracao telaEscolaRestauracao = new PrincipalEscolaRestauracao();
	private PrincipalTurmaRestauracao telaTurmaRestauracao = new PrincipalTurmaRestauracao();
	private PrincipalMatriculaRestauracao telaMatriculaRestauracao = new PrincipalMatriculaRestauracao();
	private PrincipalUsuarioRestauracao telaUsuarioRestauracao = new PrincipalUsuarioRestauracao();
	private PrincipalLembretes telaLembreteRestauracao = new PrincipalLembretes();
	
	public MenuRestauracaoDados() {
		this.getChildren().addAll(lblLembrete,lblAlunos,lblDisciplina,lblEscola,lblMatricula,lblResponsavel,lblTurmas,lblUsuario);
		
		this.lblLembrete.setOnMouseClicked(this);
		this.lblAlunos.setOnMouseClicked(this);
		this.lblDisciplina.setOnMouseClicked(this);
		this.lblEscola.setOnMouseClicked(this);
		this.lblMatricula.setOnMouseClicked(this);
		this.lblResponsavel.setOnMouseClicked(this);
		this.lblTurmas.setOnMouseClicked(this);
		this.lblUsuario.setOnMouseClicked(this);
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.lblLembrete){
			this.telaLembreteRestauracao.preencherTabelaComTodosResultados();
			this.telaLembreteRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblAlunos){
			this.telaAlunoRestauracao.preencherTabelaComTodosResultados();
			this.telaAlunoRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblDisciplina){
			this.telaDisciplinaRestauracao.construirTabela();
			this.telaDisciplinaRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblEscola){
			this.telaEscolaRestauracao.preencherTabelaComTodosResultados();
			this.telaEscolaRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblMatricula){
			this.telaMatriculaRestauracao.preencherTabelaComTodosResultados();
			this.telaMatriculaRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblResponsavel){
			this.telaResponsavelRestauracao.preencherTabelaComTodosResultados();
			this.telaResponsavelRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblTurmas){
			this.telaTurmaRestauracao.preencherTabelaComTodosResultados();
			this.telaTurmaRestauracao.showAndWait();
		}
		else if(e.getSource() == this.lblUsuario){
			this.telaUsuarioRestauracao.preencherTabelaComTodosResultados();
			this.telaUsuarioRestauracao.showAndWait();
		}
	}
}
