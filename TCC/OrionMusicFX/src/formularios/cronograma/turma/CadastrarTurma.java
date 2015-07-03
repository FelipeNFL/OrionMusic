package formularios.cronograma.turma;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimeTextField;
import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Disciplina;
import entidades.Escola;
import entidades.Turma;
import ferramentas.Alerta;
import ferramentas.Validacao;
import formularios.cadastros.escola.PesquisarEscola;
import formularios.cronograma.disciplina.CadastrarDisciplina;

public class CadastrarTurma extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//Labels
	private Label lblDiaSemana = new Label("Dia da Semana: *");
	private Label lblDisciplina = new Label("Disciplina: *");
	private Label lblEscola = new Label("Escola: *");
	private Label lblHoraInicial = new Label("Horário Inicial: *");
	private Label lblHoraFinal = new Label("Horário Final: *");
	private Label lblDescricao = new Label("Descrição: *");
		
	//Buttons
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnLimparCampos = new Button("Limpar Campos");
	private Button btnCadastrarNovaDisciplina = new Button("Cadastrar Nova Disciplina");
	private Button btnProcurarEscola = new Button("Procurar Escola");
	
	//ComboBox
	private ComboBox<Disciplina> cmbDisciplina = new ComboBox<Disciplina>();
	private CalendarTimeTextField horaInicial = new CalendarTimeTextField();
	private CalendarTimeTextField horaFinal = new CalendarTimeTextField();
	private ComboBox<String> cmbDiaSemana = new ComboBox<>(FXCollections.observableArrayList("Segunda-Feira","Terça-Feira","Quarta-Feira","Quinta-Feira","Sexta-Feira","Sábado","Domingo"));
	
	//TextArea
	private TextArea txtDescricao = new TextArea();
	
	private TextField txtEscola = new TextField();
	
	private Escola escola = new Escola();
	
	public CadastrarTurma() {
		
		this.popularCmbDisciplina();
		
		this.layout.add(this.lblDiaSemana,0,0);
		this.layout.add(this.cmbDiaSemana,1,0);
		this.layout.add(this.lblEscola,0,1);
		this.layout.add(this.txtEscola,1,1);
		this.layout.add(this.btnProcurarEscola, 2, 1);
		this.layout.add(this.lblDisciplina,0,2);
		this.layout.add(this.cmbDisciplina, 1, 2);
		this.layout.add(this.btnCadastrarNovaDisciplina, 2, 2);
		this.layout.add(this.lblHoraInicial,0,3);
		this.layout.add(this.horaInicial,1,3);
		this.layout.add(this.lblHoraFinal,2,3);
		this.layout.add(this.horaFinal,3,3);
		this.layout.add(this.lblDescricao, 0, 4);
		this.layout.add(this.txtDescricao, 1, 4,3,1);
		this.layout.add(new Label("* Campos Obrigatórios"), 1, 5);
		this.layout.add(this.btnCadastrar, 0, 5);
		
		this.layout.setHgap(15);
		this.layout.setVgap(5);
		
		this.layout.setStyle("-fx-padding: 10px");
		
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		this.btnCadastrarNovaDisciplina.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.setResizable(false);
		
		this.setTitle("Cadastrar Turma");
		
		this.cmbDiaSemana.setPromptText("Escolha um dia da semana");
		this.cmbDisciplina.setPromptText("Escolha uma disciplina");
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acionarBotaoCadastro();
				}
				else if(event.getCode() == KeyCode.ESCAPE){
					close();
				}
			}
		});
		
		this.horaFinal.setMaxWidth(30);
		this.horaInicial.setMaxWidth(30);
		
		this.horaFinal.setOnMouseClicked(this);
		this.horaInicial.setOnMouseClicked(this);
		this.btnProcurarEscola.setOnMouseClicked(this);
		
		this.txtEscola.setEditable(false);
	}
	
	private final void acionarBotaoCadastro(){
		this.handle(new Event(this.btnCadastrar, null, null));
	}
	
	private void popularCmbDisciplina(){
		try {
			List<Disciplina> disciplinas = Disciplina.consultar();
			this.cmbDisciplina.setItems(FXCollections.observableArrayList(disciplinas));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean validar(){
		
		this.txtDescricao.setStyle("");
		this.txtEscola.setStyle("");
		this.cmbDiaSemana.setStyle("");
		this.cmbDisciplina.setStyle("");
		this.horaFinal.setStyle("");
		this.horaInicial.setStyle("");
		
		if(this.cmbDiaSemana.getSelectionModel().isEmpty()){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			cmbDiaSemana.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(Validacao.valorVazio(this.txtEscola.getText())){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			txtEscola.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(this.cmbDiaSemana.getSelectionModel().isEmpty()){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			cmbDisciplina.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}

		try {
			this.horaInicial.getCalendar();
		}
		catch(Exception e){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			this.horaInicial.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
		}
		
		try {
			this.horaInicial.getCalendar();
		}
		catch(Exception e){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			this.horaInicial.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		
		try {
			this.horaFinal.getCalendar();
		}
		catch(Exception e){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			this.horaFinal.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		
		if(this.horaInicial.getCalendar().getTime().getHours() > this.horaFinal.getCalendar().getTime().getHours()){
			Alerta.mostrarMensagemErro("Você preencheu o horário de forma inválida!", "Horário Inválido");
			return false;
		}
		else if(Validacao.valorVazio(this.txtDescricao.getText())){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			txtDescricao.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else {
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(Event e) {
		if (e.getSource() == this.btnCadastrar) {
			if(validar()){
				Turma turma = new Turma();
				turma.setHoraInicial(Integer.toString(this.horaInicial.getCalendar().getTime().getHours()));
				turma.setMinutoInicial(Integer.toString(this.horaInicial.getCalendar().getTime().getMinutes()));
				turma.setHoraFinal(Integer.toString(this.horaFinal.getCalendar().getTime().getHours()));
				turma.setMinutoFinal(Integer.toString(this.horaFinal.getCalendar().getTime().getMinutes()));
				turma.setDescricaoTurma(this.txtDescricao.getText());
				turma.setDiaSemana(this.cmbDiaSemana.getSelectionModel().getSelectedItem());
				turma.setEscola(this.escola);
				turma.setDisciplina(this.cmbDisciplina.getSelectionModel().getSelectedItem());
				if(turma.criar())
					Alerta.mostrarMensagemSucesso("A turma "+turma.getDescricaoTurma()+" foi cadastrada com sucesso!", "Sucesso");
				else
					Alerta.mostrarMensagemErro("Ocorreu um erro ao cadastrar a turma "+turma.getDescricaoTurma()+"!", "Erro");
				this.close();				
			}
		}
		else if(e.getSource() == this.btnCadastrarNovaDisciplina){
			CadastrarDisciplina telaDisciplina = new CadastrarDisciplina();
			telaDisciplina.showAndWait();
			this.popularCmbDisciplina();
			this.cmbDisciplina.getSelectionModel().select(telaDisciplina.getDisciplina());
		}
		else if(e.getSource() == this.btnProcurarEscola){
			PesquisarEscola telaPesquisaEscola = new PesquisarEscola();
			telaPesquisaEscola.showAndWait();
			this.txtEscola.setText(telaPesquisaEscola.getEscolaSelecionada().getNome());
			this.escola = telaPesquisaEscola.getEscolaSelecionada();
		}
	}
}
