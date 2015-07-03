package formularios.cadastros.aluno;

import java.util.Calendar;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Aluno;
import entidades.Responsavel;
import ferramentas.Alerta;
import ferramentas.MaskTextField;
import ferramentas.Validacao;
import formularios.cadastros.responsavel.PesquisarResponsavel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CadastrarAluno extends Stage implements EventHandler<Event> {
	
	/* Botões */
	private Button btnCadastrar = new Button("Cadastrar"); // Botão para confirmar a ação do Formulário
	private Button btnLimparCampos = new Button("Limpar Campos"); // Botão para limpar os campos
	private Button btnProcurarResponsavel = new Button("Procurar Responsável"); // Botão para abrir a janela de responsáveis
	
	/*Labels*/
	private Label lblNome = new Label("Nome Completo: *");
	private Label lblResponsavel = new Label("Responsável:");
	private Label lblEndereco = new Label("Endereço:");
	private Label lblNumero = new Label("Número:");
	private Label lblComplemento = new Label("Complemento:");
	private Label lblBairro = new Label("Bairro:");
	private Label lblCidade = new Label("Cidade:");
	private Label lblCep = new Label("CEP:");
	private Label lblTelefone = new Label("Telefone:");
	private Label lblCelular = new Label("Celular:");
	private Label lblDataNascimento = new Label("Data de Nascimento:");
	private Label lblEmail = new Label("E-mail:");
	
	/*Caixas de Texto*/
	private TextField txtNome = new TextField();
	private TextField txtResponsavel = new TextField();
	private TextField txtEndereco = new TextField();
	private MaskTextField txtNumero = new MaskTextField();
	private TextField txtComplemento = new TextField();
	private TextField txtBairro = new TextField();
	private TextField txtCidade = new TextField();
	private TextField txtEmail = new TextField();
	
	private MaskTextField txtCep = new MaskTextField();
	private MaskTextField txtTelefone = new MaskTextField();
	private MaskTextField txtCelular = new MaskTextField();
	private ComboBox<String> cmbDia = new ComboBox<>();
	private ComboBox<String> cmbMes = new ComboBox<>();
	private ComboBox<String> cmbAno = new ComboBox<>();
	private HBox containerDataNascimento = new HBox(3,this.cmbDia,this.cmbMes,this.cmbAno);
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	private Responsavel responsavel;
	
	public boolean esqueceuDeCadastrarAluno = false;
	
	public CadastrarAluno() {
		this.layout.add(lblNome,0,0);
		this.layout.add(txtNome,1,0,4,1);
		
		this.layout.add(lblResponsavel,0,1);
		this.layout.add(txtResponsavel,1,1);
		this.layout.add(btnProcurarResponsavel,2,1);
		this.txtResponsavel.setEditable(false);
		this.btnProcurarResponsavel.setOnMouseClicked(this);
		
		this.layout.add(lblEndereco,0,2);
		this.layout.add(txtEndereco,1,2);
		this.layout.add(lblNumero,2,2);
		this.layout.add(txtNumero,3,2);
		
		this.layout.add(lblComplemento,0,3);
		this.layout.add(txtComplemento,1,3);
		this.layout.add(lblBairro,2,3);
		this.layout.add(txtBairro,3,3);
		
		this.layout.add(lblCidade,0,4);
		this.layout.add(txtCidade,1,4);
		this.layout.add(lblCep,2,4);
		this.layout.add(txtCep,3,4);
		
		this.layout.add(lblTelefone,0,5);
		this.layout.add(txtTelefone,1,5);
		this.layout.add(lblCelular,2,5);
		this.layout.add(txtCelular,3,5);
		
		this.layout.add(lblEmail,0,6);
		this.layout.add(txtEmail,1,6);
		this.layout.add(lblDataNascimento,2,6);
		this.layout.add(containerDataNascimento, 3, 6);
		
		this.layout.add(new Label("* Campos Obrigatórios"), 0, 7);
		
		this.layout.add(btnCadastrar,0,8);
		this.layout.add(btnLimparCampos,1,8);
		
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		
		this.layout.setVgap(15);
		this.layout.setHgap(15);
		
		this.setScene(cenaPrincipal);
		
		this.txtCep.setMask("NNNNNNNN");
		this.txtTelefone.setMask("NNNNNNNNNN");
		this.txtCelular.setMask("NNNNNNNNNNN");
		this.txtNumero.setMask("NNNNNNNNNNNNN");
		
		this.setTitle("Cadastro de Aluno");
		this.setResizable(false);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.preencherComboBox();
		
		this.cmbAno.setOnMouseClicked(this);
		this.cmbDia.setOnMouseClicked(this);
		this.cmbMes.setOnMouseClicked(this);
		
		this.cmbAno.setPromptText("Ano");
		this.cmbDia.setPromptText("Dia");
		this.cmbMes.setPromptText("Mês");
		
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
	}
	
	private final void acionarBotaoCadastro(){
		this.handle(new Event(this.btnCadastrar,null,null));
	}
	
	private String getDataNascimentoPreenchida(){
		if(this.cmbDia.getSelectionModel().isEmpty() && this.cmbMes.getSelectionModel().isEmpty() && this.cmbAno.getSelectionModel().isEmpty()){
			return "";
		}
		else
		{
			String dia = this.cmbDia.getSelectionModel().getSelectedItem().toString();
			String mes = Integer.toString(this.cmbMes.getSelectionModel().getSelectedIndex() + 1);
			String ano = this.cmbAno.getSelectionModel().getSelectedItem().toString();
			
			if(dia.length() == 1){
				dia = "0"+dia;
			}
			
			if(mes.length() == 1){
				mes = "0"+mes;
			}
			
			return dia+"/"+mes+"/"+ano;
		}
	}
	
	private void preencherComboBox(){
		for(int i = 1; i <= 31; i++){
			this.cmbDia.getItems().add(Integer.toString(i));
		}
		
		this.cmbMes.getItems().addAll("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro");
		
		for(int i = Calendar.getInstance().get(Calendar.YEAR); i > 1900; i--){
			this.cmbAno.getItems().add(Integer.toString(i));
		}
	}
	
	private boolean verificarCampos() {
		
		Validacao.voltarCampoAoNormal(this.txtNome);
		Validacao.voltarCampoAoNormal(this.txtEmail);
		Validacao.voltarCampoAoNormal(this.txtTelefone);
		Validacao.voltarCampoAoNormal(this.txtCelular);
		Validacao.voltarCampoAoNormal(this.txtCep);
		
		if (Validacao.valorVazio(this.txtNome.getText())) {
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			Validacao.realcarCampo(this.txtNome);
			return false;
		} 
		else if(Validacao.validarEmail(this.txtEmail.getText()) == false){
			Alerta.mostrarMensagemErro("O e-mail digitado é inválido!", "E-mail Inválido");
			Validacao.realcarCampo(this.txtEmail);
			return false;
		}
		else if(this.txtTelefone.getText().length() != 10 && Validacao.valorVazio(this.txtTelefone.getText()) == false){
			Alerta.mostrarMensagemErro("O Telefone não foi preenchido completamente!", "Telefone Incompleto");
			Validacao.realcarCampo(this.txtTelefone);
			return false;
		}
		else if(this.txtCelular.getText().length() != 11 && Validacao.valorVazio(this.txtTelefone.getText()) == false){
			Alerta.mostrarMensagemErro("O Celular não foi preenchido completamente!", "Celular Incompleto");
			Validacao.realcarCampo(this.txtCelular);
			return false;
		}
		else if(this.txtCep.getText().length() != 8 && Validacao.valorVazio(this.txtCep.getText()) == false){
			Alerta.mostrarMensagemErro("O CEP não foi preenchido completamente", "CEP Incompleto");
			Validacao.realcarCampo(this.txtCep);
			return false;
		}
		
		boolean dataPreenchidaPelaMetade = false;
		
		try {
			this.getDataNascimentoPreenchida();
		}
		catch(Exception e) {
			dataPreenchidaPelaMetade = true;
		}
		
		if(dataPreenchidaPelaMetade){
			Alerta.mostrarMensagemErro("A data de nascimento não foi preenchida completamente!", "Data de Nascimento Incompleta");
			if(this.cmbDia.getSelectionModel().isEmpty()){
				cmbDia.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			}
			else if(this.cmbMes.getSelectionModel().isEmpty()){
				cmbMes.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			}
			else if(this.cmbAno.getSelectionModel().isEmpty()){
				cmbAno.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			}
			return false;
		}
		else {			
			return true;
		}
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			if(this.verificarCampos()){
				Aluno aluno = new Aluno();
				aluno.setBairro(this.txtBairro.getText());
				aluno.setCelular(this.txtCelular.getText());
				aluno.setCep(this.txtCep.getText());
				aluno.setCidade(this.txtCidade.getText());
				aluno.setComplemento(this.txtComplemento.getText());
				aluno.setDataNascimento(this.getDataNascimentoPreenchida());
				aluno.setEmail(this.txtEmail.getText());
				aluno.setNome(this.txtNome.getText());
				aluno.setNomeLogradouro(this.txtEndereco.getText());
				aluno.setNumLogradouro(this.txtNumero.getText());
				aluno.setTelefone(this.txtTelefone.getText());
				aluno.setResponsavel(responsavel);
				if(aluno.cadastrar() == true){
					Alerta.mostrarMensagemSucesso("O Aluno "+aluno.getNome()+" foi cadastrado com sucesso!","Sucesso");
					this.close();
				}
				else {
					Alerta.mostrarMensagemErro("Ocorreu um erro ao concluir cadastro!", "Erro");
				}
			}
		}
		else if(e.getSource() == this.btnProcurarResponsavel){
			PesquisarResponsavel telaPesquisarResponsavel = new PesquisarResponsavel();
			telaPesquisarResponsavel.showAndWait();
			this.txtResponsavel.setText(telaPesquisarResponsavel.getResponsavelSelecionado().getNome());
			this.responsavel = telaPesquisarResponsavel.getResponsavelSelecionado();
		}
		else if(e.getSource() == this.btnLimparCampos){
			this.txtBairro.setText("");
			this.txtCelular.setText("");
			this.txtCep.setText("");
			this.txtCidade.setText("");
			this.txtComplemento.setText("");
			this.txtEmail.setText("");
			this.txtEndereco.setText("");
			this.txtNome.setText("");
			this.txtNumero.setText("");
			this.txtResponsavel.setText("");
			this.txtTelefone.setText("");
		}
	}
}