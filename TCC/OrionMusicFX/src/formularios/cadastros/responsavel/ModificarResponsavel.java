package formularios.cadastros.responsavel;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Responsavel;
import ferramentas.Alerta;
import ferramentas.MaskTextField;
import ferramentas.Validacao;

public class ModificarResponsavel extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//Labels
	private Label lblNome = new Label("Nome: *");
	private Label lblTelefone = new Label("Telefone: *");
	private Label lblCelular = new Label("Celular: *");
		
	//Buttons
	private Button btnModificar = new Button("Modificar");
	
	//TextField
	private TextField txtNome = new TextField();
	
	//FormattedTextField
	private MaskTextField txtTelefone = new MaskTextField();
	private MaskTextField txtCelular = new MaskTextField();
	
	private Responsavel responsavel;
	
	public ModificarResponsavel(Responsavel responsavel) {
		
		this.responsavel = responsavel;
		
		//Label Nome
		this.layout.add(lblNome, 0,0);
		
		//Caixa de texto Nome
		this.layout.add(txtNome, 0,1);
		this.txtNome.setText(this.responsavel.getNome());
		
		//label Telefone
		this.layout.add(lblTelefone, 1,0);

		//Caixa de texto telefone
		this.layout.add(txtTelefone, 1,1);
		this.txtTelefone.setText(this.responsavel.getTelefone());
		
		//label celular
		this.layout.add(lblCelular, 0,2);
	
		//caixa de texto celular
		this.layout.add(txtCelular, 0,3);
		this.txtCelular.setText(this.responsavel.getCelular());
		
		
		//botao confirmar
		this.layout.add(btnModificar, 0,4);
		this.btnModificar.setOnMouseClicked(this);
		
		this.layout.add(new Label("* Campos Obrigatórios"), 1, 3);
		
		this.layout.setHgap(15);
		this.layout.setVgap(5);
		
		this.layout.setStyle("-fx-padding: 10px");
		
		this.setScene(cenaPrincipal);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.txtTelefone.setMask("NNNNNNNNNN");
		this.txtCelular.setMask("NNNNNNNNNNN");
		
		this.setResizable(false);
		
		this.setTitle("Modificação de Responsável");
		
		this.layout.setPadding(new Insets(5,5,5,5));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acionarBotaoModificar();
				}
				else if(event.getCode() == KeyCode.ESCAPE){
					close();
				}
			}
		});
	}
	
	private final void acionarBotaoModificar(){
		this.handle(new Event(this.btnModificar, null, null));
	}
	
private boolean verificarCampos(){
		
		if(txtNome.getText().equals("")){
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			txtNome.requestFocus();
			txtNome.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if (txtCelular.getText().equals("")) {
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");
			txtCelular.requestFocus();
			txtCelular.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(this.txtCelular.getText().length() != 11 && Validacao.valorVazio(this.txtTelefone.getText()) == false){
			Alerta.mostrarMensagemErro("O Celular não foi preenchido completamente!", "Celular Incompleto");
			txtTelefone.requestFocus();
			txtTelefone.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		} 
		else if (txtTelefone.getText().equals("")) {
			Alerta.mostrarMensagemErro("Você esqueceu de preencher o campo indicado!", "Campos Obrigatórios Não Preenchidos");	
			txtTelefone.requestFocus();
			txtTelefone.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(this.txtTelefone.getText().length() != 10 && Validacao.valorVazio(this.txtTelefone.getText()) == false){
			Alerta.mostrarMensagemErro("O Telefone não foi preenchido completamente!", "Telefone Incompleto");
			txtTelefone.requestFocus();
			txtTelefone.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}		
		else{
			return true;
		}
}

	@Override
	public void handle(Event e) {
		if (e.getSource() == this.btnModificar) {
			if(this.verificarCampos()){
				this.responsavel.setNome(this.txtNome.getText());
				this.responsavel.setTelefone(this.txtTelefone.getText());
				this.responsavel.setCelular(this.txtCelular.getText());
				if(this.responsavel.modificar())
					Alerta.mostrarMensagemSucesso("Responsável modificado com sucesso!","Sucesso");
				else
					Alerta.mostrarMensagemErro("Erro ao modificar responsável!", "Erro");
				this.close();				
			}
		}
	}
}
