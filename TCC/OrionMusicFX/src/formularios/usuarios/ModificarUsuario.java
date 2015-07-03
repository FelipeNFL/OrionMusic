package formularios.usuarios;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Usuario;
import ferramentas.Alerta;
import ferramentas.Criptografia;
import ferramentas.Validacao;

public class ModificarUsuario extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//Labels
	private Label lblNome = new Label("Nome: *");
	private Label lblLogin = new Label("Login: *");
	private Label lblSenha = new Label("Senha: *"); 
	private Label lblPerfil = new Label("Perfil de Usuário: *");
	private Label lblVerificarSenha = new Label("Verificar Senha: *");
	private Label lblCamposObrigatorios = new Label("* Campos Obrigatórios");
	private Label lblPerguntaSecreta = new Label("Pergunta Secreta: *");
	private Label lblResposta = new Label("Resposta: *");
		
	//Buttons
	private Button btnModificar = new Button("Modificar");
	
	//TextField
	private TextField txtNome = new TextField();
	private TextField txtLogin = new TextField();
	private TextField txtPergunta = new TextField();
	private TextField txtResposta = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private PasswordField txtVerificarSenha = new PasswordField();
	private ComboBox<String> cmbPerfilUsuario = new ComboBox<String>();
	
	private Usuario usuario = new Usuario();
	
	public ModificarUsuario(Usuario usuarioASerModificado) {
		
		this.usuario = usuarioASerModificado;
		this.txtLogin.setText(this.usuario.getLogin());
		this.txtNome.setText(this.usuario.getNome());
		this.cmbPerfilUsuario.getSelectionModel().select(this.usuario.getPerfil());
		
		this.layout.add(lblNome, 0,0);
		this.layout.add(txtNome, 0,1);

		this.layout.add(lblSenha, 0,2);
		this.layout.add(txtSenha, 0,3);
		
		this.layout.add(lblLogin, 1,0);
		this.layout.add(txtLogin, 1,1);
		
		this.layout.add(lblVerificarSenha, 1, 2);
		this.layout.add(txtVerificarSenha, 1, 3);
		
		this.layout.add(lblPerfil, 0, 4);
		this.layout.add(cmbPerfilUsuario, 0, 5);
		
		this.layout.add(lblPerguntaSecreta, 1, 4);
		this.layout.add(txtPergunta, 1, 5);
		this.txtPergunta.setText(this.usuario.getPerguntaSecreta());
		
		this.layout.add(lblResposta, 0, 6);
		this.layout.add(txtResposta, 0, 7);
		
		this.layout.add(lblCamposObrigatorios, 1, 7);
		
		//botao confirmar
		this.layout.add(btnModificar, 0,8);
		this.btnModificar.setOnMouseClicked(this);
		
		this.layout.setHgap(15);
		this.layout.setVgap(5);
		
		this.layout.setStyle("-fx-padding: 10px");
		
		this.setScene(cenaPrincipal);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.setResizable(false);
		
		this.setTitle("Modificação de Usuário");
		
		this.cmbPerfilUsuario.setPromptText("Escolha o Perfil de Usuário");
		
		this.cmbPerfilUsuario.setItems(FXCollections.observableArrayList("Comum","Administrador"));
		
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
		
		this.txtSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.CONTROL){
					//Bloqueia o uso do control
				}
			}
		});
		
		this.txtVerificarSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.CONTROL){
					//Bloqueia o uso do control
				}
			}
		});
	}
	
	private final void acionarBotaoCadastro(){
		this.handle(new Event(this.btnModificar, null, null));
	}
	
	private boolean validar(){
		
		this.txtLogin.setStyle("");
		this.txtNome.setStyle("");
		this.txtSenha.setStyle("");
		this.txtVerificarSenha.setStyle("");
		this.cmbPerfilUsuario.setStyle("");
		
		if(Validacao.valorVazio(this.txtNome.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtNome.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			txtNome.requestFocus();
			return false;
		}
		else if(Validacao.valorVazio(this.txtLogin.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtLogin.requestFocus();
			txtLogin.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(Validacao.valorVazio(this.txtSenha.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtSenha.requestFocus();
			txtSenha.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(Validacao.valorVazio(this.txtVerificarSenha.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtVerificarSenha.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			txtVerificarSenha.requestFocus();
			return false;
		}
		else if(this.txtSenha.getText().equals(this.txtVerificarSenha.getText()) == false){
			Alerta.mostrarMensagemErro("As senhas digitadas não correspondem","Senhas Conflitantes");
			txtSenha.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			txtVerificarSenha.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(this.cmbPerfilUsuario.getSelectionModel().isEmpty()){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			cmbPerfilUsuario.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(Validacao.valorVazio(this.txtPergunta.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtPergunta.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			txtPergunta.requestFocus();
			return false;
		}
		else if(Validacao.valorVazio(this.txtResposta.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			txtResposta.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			txtResposta.requestFocus();
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void handle(Event e) {
		if (e.getSource() == this.btnModificar) {
			if(this.validar()){
				try {
					this.usuario.setLogin(this.txtLogin.getText());
					this.usuario.setSenha(Criptografia.criptografarDado(this.txtSenha.getText()));
					this.usuario.setRespostaSecreta(Criptografia.criptografarDado(this.txtResposta.getText().toUpperCase()));
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				this.usuario.setNome(this.txtNome.getText());
				this.usuario.setPerfil(this.cmbPerfilUsuario.getSelectionModel().getSelectedItem());
				this.usuario.setPerguntaSecreta(this.txtPergunta.getText());
				
				if(this.usuario.modificar() == true){
					Alerta.mostrarMensagemSucesso("O usuário "+this.usuario.getNome()+" foi modificado com sucesso!","Sucesso");
				}
				else {
					Alerta.mostrarMensagemErro("Erro ao modificar usuário","Erro");
				}
				this.close();				
			}
		}
	}
}