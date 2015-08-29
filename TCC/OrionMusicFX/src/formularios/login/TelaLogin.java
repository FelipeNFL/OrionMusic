package formularios.login;

import java.util.List;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Usuario;
import ferramentas.Alerta;
import ferramentas.Criptografia;
import formularios.Dialogo;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

public class TelaLogin extends Dialogo implements EventHandler<Event> {

	private Button btnLogar = new Button("Login");
	private Button btnLimparCampos = new Button("Limpar Campos");
	private Button btnEsqueciMinhaSenha = new Button("Esqueci Minha Senha");
	private TextField txtLogin = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private Label lblLogin = new Label("Login: ");
	private Label lblSenha = new Label("Senha: ");
	
	private int tentativasLogin = 0;

	private boolean loginValido = false;
	private boolean fechouTela = false;
	
	private String perfilUser;
	
	public String getPerfilUser(){
		return this.perfilUser;
	}
	
	public boolean fechouTela(){
		return this.fechouTela;
	}
	
	public void resetarLogin(){
		this.loginValido = false;
	}
	
	public boolean isLoginValido(){
		return this.loginValido;
	}
	
	public TelaLogin() {

		this.layout.add(lblLogin, 0, 0);
		this.layout.add(txtLogin, 1, 0);
		
		this.layout.add(lblSenha, 0, 1);
		this.layout.add(txtSenha, 1, 1);
		
		this.layout.add(new HBox(5, this.btnLogar,this.btnEsqueciMinhaSenha,this.btnLimparCampos), 0, 2,2,1);
		
		this.setTitle("OrionMusic - Login");
		this.setResizable(false);
		
		this.btnLogar.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		this.btnEsqueciMinhaSenha.setOnMouseClicked(this);
		
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				fechouTela = true;
				close();
			}
		});
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acionarBotaoLogin();
				}
			}
		});
	}
	
	public void limparCampos(){
		txtLogin.setText("");
		txtSenha.setText("");
	}
	
	public void acionarBotaoLogin(){
		this.handle(new Event(this.btnLogar, null, null));
	}
	
	@Override
	public void handle(Event e){
		if (this.btnLimparCampos == e.getSource()) {
			this.limparCampos();
		}
		else if (this.btnLogar == e.getSource()) {
			this.tentativasLogin++;
			if(this.tentativasLogin > 3){
				Alerta.mostrarMensagemErro("O número de tentativas foram excedidas!", "Erro");
				this.close();
			}
			else {
				try {
					String usuario = this.txtLogin.getText();
					String senha = Criptografia.criptografarDado(this.txtSenha.getText());
					
					List<Usuario> usuariosRetornados = Usuario.consultar("login = '"+usuario+"' AND senha = '"+senha+"'");
					
					if(usuariosRetornados.isEmpty()){
						this.loginValido = false;
						Alerta.mostrarMensagemErro("Usuário e/ou senha inválidos!", "Erro");
						this.handle(new Event(this.btnLimparCampos,null,null));
						return;
					}
					else {
						this.loginValido = true;
						this.perfilUser = usuariosRetornados.get(0).getPerfil();
						this.close();
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
 			}
		}
		else if(e.getSource() == this.btnEsqueciMinhaSenha){
			new TelaEsqueciMinhaSenha().showAndWait();
			this.limparCampos();
		}
	}
}
