package formularios.cadastros.escola;

import javafx.event.Event;
import javafx.event.EventHandler;
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
import entidades.Escola;
import ferramentas.Alerta;
import ferramentas.MaskTextField;
import ferramentas.Validacao;

public class CadastrarEscola extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//Labels
	private Label lblNome = new Label("Nome: *");
	private Label lblEndereco = new Label("Endereço:");
	private Label lblNumero = new Label("Número:");
	private Label lblComplemento = new Label("Complemento:");
	private Label lblBairro = new Label("Bairro:");
	private Label lblCidade = new Label("Cidade:");
	private Label lblCEP = new Label("CEP:");
	private Label lblTelefone = new Label("Telefone:");
		
	//Buttons
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnLimparCampos = new Button("Limpar Campos");
	
	//TextField
	private TextField txtNome = new TextField();
	private TextField txtEndereco = new TextField();
	private MaskTextField txtNumero = new MaskTextField();
	private TextField txtComplemento = new TextField();
	private TextField txtBairro = new TextField();
	private TextField txtCidade = new TextField();
	private MaskTextField txtCEP = new MaskTextField();
	private MaskTextField txtTelefone = new MaskTextField();
	
	public CadastrarEscola() {
		
		//Adiciona os componentes ao Layout
		
		this.layout.add(this.lblNome,0,0);
		this.layout.add(this.txtNome, 1, 0,3,1); //Além de colocar as coordenadas, faz o TextField ocupar 3 colunas
		
		this.layout.add(this.lblEndereco, 0, 1);
		this.layout.add(this.txtEndereco, 1, 1);
		this.layout.add(this.lblNumero, 2, 1);
		this.layout.add(this.txtNumero, 3, 1);
		
		this.layout.add(this.lblComplemento, 0, 2);
		this.layout.add(this.txtComplemento, 1,2);
		this.layout.add(this.lblBairro, 2, 2);
		this.layout.add(this.txtBairro, 3, 2);
		
		this.layout.add(this.lblCidade, 0, 3);
		this.layout.add(this.txtCidade, 1, 3);
		this.layout.add(this.lblCEP, 2, 3);
		this.layout.add(this.txtCEP, 3, 3);
		
		this.layout.add(this.lblTelefone, 0, 4);
		this.layout.add(this.txtTelefone, 1, 4);
		
		this.layout.add(new Label("* Campos Obrigatórios"), 0, 5);
		
		this.layout.add(this.btnCadastrar, 0, 6);
		this.layout.add(this.btnLimparCampos, 1, 6);
		
		this.layout.setHgap(15); //Determina uma distância horizontal de 15 pixels entre cada componente
		this.layout.setVgap(5); //Determina uma distância vertical de 15 pixels entre cada componente
		
		this.layout.setStyle("-fx-padding: 10px"); //Define uma distância de 10 pixels entre os componentes e as bordas da janela
		
		this.txtCEP.setMask("NNNNNNNN");
		this.txtTelefone.setMask("NNNNNNNNNN");
		this.txtNumero.setMask("NNNNNNNNNNNNN");
		
		//Adiciona evento ao botões
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal); //Adiciona o Scene ao Stage
		
		this.setResizable(false); //Evita que a janela seja redimensionada
		
		this.setTitle("Cadastro de Escola"); //Determina um título para a janela
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
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
	
	private void limparCampos(){
		this.txtBairro.setText("");
		this.txtCEP.setText("");
		this.txtCidade.setText("");
		this.txtComplemento.setText("");
		this.txtEndereco.setText("");
		this.txtNome.setText("");
		this.txtNumero.setText("");
		this.txtTelefone.setText("");
	}
	
	private boolean verificarCampos(){
		
		Validacao.voltarCampoAoNormal(this.txtNome);
		Validacao.voltarCampoAoNormal(this.txtCEP);
		Validacao.voltarCampoAoNormal(this.txtTelefone);
		
		if(Validacao.valorVazio(this.txtNome.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			Validacao.realcarCampo(this.txtNome);
			return false;
		}
		else if(this.txtCEP.getText().length() != 8 && Validacao.valorVazio(this.txtCEP.getText()) == false){
			Alerta.mostrarMensagemErro("O CEP não foi preenchido completamente", "CEP Incompleto");
			Validacao.realcarCampo(this.txtCEP);
			return false;
		}
		else if(this.txtTelefone.getText().length() != 8 && Validacao.valorVazio(this.txtCEP.getText()) == false){
			Alerta.mostrarMensagemErro("O Telefone não foi preenchido completamente", "Telefone Incompleto");
			Validacao.realcarCampo(this.txtTelefone);
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public void handle(Event e) {
		if (e.getSource() == this.btnCadastrar) {
			if(this.verificarCampos()){
				Escola escola = new Escola();
				
				escola.setBairro(this.txtBairro.getText());
				escola.setCep(this.txtCEP.getText());
				escola.setCidade(this.txtCidade.getText());
				escola.setComplemento(this.txtComplemento.getText());
				escola.setEndereco(this.txtEndereco.getText());
				escola.setNome(this.txtNome.getText());
				escola.setNumEndereco(this.txtNumero.getText());
				escola.setTelefone(this.txtTelefone.getText());
				
				if(escola.cadastrar()){
					Alerta.mostrarMensagemSucesso("Cadastro efetuado com sucesso!", "Sucesso");
				}
				else{
					Alerta.mostrarMensagemErro("Erro ao efetuar cadastro!", "Erro");
				}
				this.close();				
			}
		}
		else if(e.getSource() == this.btnLimparCampos){
			this.limparCampos();
		}
	}
}
