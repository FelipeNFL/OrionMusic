package formularios.login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import entidades.Usuario;
import ferramentas.Alerta;
import ferramentas.Criptografia;
import formularios.Dialogo;

public class TelaEsqueciMinhaSenha extends Dialogo {
	
	private Label lblDigiteSeuLogin = new Label("Digite seu Login:");
	private Label lblPerguntaSecreta = new Label("Pergunta Secreta: ");
	private Label lblResposta = new Label("Resposta: ");
	private Label lblNovaSenha = new Label("Nova Senha: ");
	private Label lblConfirmarNovaSenha = new Label("Confirmar Senha: ");
	
	private TextField txtLogin = new TextField();
	private TextField txtPerguntaSecreta = new TextField();
	private TextField txtResposta = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private PasswordField txtConfirmarSenha = new PasswordField();
	
	private Button btnOK = new Button("OK");
	
	private Usuario usuario;
	
	public TelaEsqueciMinhaSenha() {
		this.layout.add(lblDigiteSeuLogin, 0, 0);
		this.layout.add(txtLogin, 1, 0);
		
		this.layout.add(lblPerguntaSecreta, 0, 1);
		this.layout.add(txtPerguntaSecreta, 1, 1);
		
		this.layout.add(lblResposta, 0, 2);
		this.layout.add(txtResposta, 1, 2);
		
		this.layout.add(lblNovaSenha, 0, 3);
		this.layout.add(txtSenha, 1, 3);
		
		this.layout.add(lblConfirmarNovaSenha, 0, 4);
		this.layout.add(txtConfirmarSenha, 1, 4);
		
		this.layout.add(btnOK, 0, 5,2,1);
		
		this.txtConfirmarSenha.setDisable(true);
		this.txtPerguntaSecreta.setDisable(true);
		this.txtResposta.setDisable(true);
		this.txtSenha.setDisable(true);
		this.btnOK.setDisable(true);
		
		this.setTitle("Recuperação de Senha");
		
		this.txtLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					try {
						List<Usuario> resultados = Usuario.consultar("login = '"+txtLogin.getText()+"'");
						
						if(resultados.isEmpty()){
							Alerta.mostrarMensagemErro("O Login digitado não existe!", "Login Inexistente");
							close();
						}
						else {
							usuario = resultados.get(0);
							txtPerguntaSecreta.setText(usuario.getPerguntaSecreta());
							txtResposta.setDisable(false);
							txtLogin.setDisable(true);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			}
		});
		
		this.txtResposta.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					try {
						String respostaFinal = Criptografia.criptografarDado(txtResposta.getText().toUpperCase());
						if(respostaFinal.equals(usuario.getRespostaSecreta())){
							txtSenha.setDisable(false);
							txtConfirmarSenha.setDisable(false);
							txtResposta.setDisable(true);
							btnOK.setDisable(false);
						}
						else {
							Alerta.mostrarMensagemErro("A resposta secreta está incorreta!", "Resposta Incorreta");
							close();
						}
					} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
						e.printStackTrace();
					}					
				}
			}
		});
		
		this.btnOK.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				acaoBtnOK();
			}
		});
		
		this.txtConfirmarSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acaoBtnOK();
				}
			}
		});
		
		this.txtSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acaoBtnOK();
				}
			}
		});
	}
	
	private void acaoBtnOK(){
		if(this.txtSenha.getText().equals(this.txtConfirmarSenha.getText())){
			try {
				this.usuario.setSenha(Criptografia.criptografarDado(this.txtSenha.getText()));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			this.usuario.modificar();
			close();
		}
		else {
			Alerta.mostrarMensagemErro("As senhas não correspondem", "Senhas Incompatíveis");
			this.txtSenha.setText("");
			this.txtConfirmarSenha.setText("");
		}
	}
}
