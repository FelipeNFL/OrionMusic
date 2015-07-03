package formularios.cronograma.turma;

import java.util.Calendar;
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

public class ModificarTurma extends Stage implements EventHandler<Event> {
	
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
	private Button btnModificar = new Button("Modificar");
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
	
	private Turma turma = new Turma();
	
	public ModificarTurma(Turma turma) {
		
		this.popularCmbDisciplina();
		
		this.escola = turma.getEscola();
		this.turma = turma;
		
		this.txtEscola.setText(this.escola.getNome());
		this.cmbDiaSemana.getSelectionModel().select(this.turma.getDiaSemana());
		this.txtDescricao.setText(this.turma.getDescricaoTurma());
		
		Calendar hora = Calendar.getInstance();
		
		hora.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Integer.parseInt(this.turma.getHoraFinal()), Integer.parseInt(this.turma.getMinutoFinal()));
		this.horaFinal.setCalendar(hora);
		
		hora = Calendar.getInstance();
		
		hora.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Integer.parseInt(this.turma.getHoraInicial()), Integer.parseInt(this.turma.getMinutoInicial()));
		this.horaInicial.setCalendar(hora);
		
		this.cmbDisciplina.getSelectionModel().select(this.turma.getDisciplina());
		
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
		this.layout.add(this.btnModificar, 0, 5);
		
		this.layout.setHgap(15);
		this.layout.setVgap(5);
		
		this.layout.setStyle("-fx-padding: 10px");
		
		this.btnModificar.setOnMouseClicked(this);
		this.btnCadastrarNovaDisciplina.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.setResizable(false);
		
		this.setTitle("Modificação de Turma");
		
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
		this.handle(new Event(this.btnModificar, null, null));
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
		else if(Validacao.valorVazio(this.horaInicial.toString())){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			horaInicial.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;	
		}
		else if(Validacao.valorVazio(this.horaFinal.toString())){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			horaFinal.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(this.horaInicial.getCalendar().getTime().getHours() > this.horaFinal.getCalendar().getTime().getHours()){
			Alerta.mostrarMensagemErro("Você preencheu o horário de forma inválida!", "Horário Inválido");
			cmbDiaSemana.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
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
		if (e.getSource() == this.btnModificar) {
			if(validar()){
				turma.setHoraInicial(Integer.toString(this.horaInicial.getCalendar().getTime().getHours()));
				turma.setMinutoInicial(Integer.toString(this.horaInicial.getCalendar().getTime().getMinutes()));
				turma.setHoraFinal(Integer.toString(this.horaFinal.getCalendar().getTime().getHours()));
				turma.setMinutoFinal(Integer.toString(this.horaFinal.getCalendar().getTime().getMinutes()));
				turma.setDescricaoTurma(this.txtDescricao.getText());
				turma.setDiaSemana(this.cmbDiaSemana.getSelectionModel().getSelectedItem());
				turma.setEscola(this.escola);
				turma.setDisciplina(this.cmbDisciplina.getSelectionModel().getSelectedItem());
				if(turma.modificar())
					Alerta.mostrarMensagemSucesso("A turma "+turma.getDescricaoTurma()+" foi modificada com sucesso!", "Sucesso");
				else
					Alerta.mostrarMensagemErro("Ocorreu um erro ao modificar a turma "+turma.getDescricaoTurma()+"!", "Erro");
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
