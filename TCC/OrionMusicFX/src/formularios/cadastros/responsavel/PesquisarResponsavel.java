package formularios.cadastros.responsavel;

import java.awt.GraphicsEnvironment;
import java.util.List;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Responsavel;
import ferramentas.ResolucaoTela;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PesquisarResponsavel extends Stage implements EventHandler<Event>{
	
	private TableView<Responsavel> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Responsavel, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Responsavel, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Responsavel, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private TableColumn<Responsavel, String> colunaCelular = new TableColumn<>("Celular"); //Cria a coluna celular
	private List<Responsavel> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Label lblNome = new Label("Nome:");
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnSelecionar = new Button("Selecionar");
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(this.layout);
	
	public Responsavel getResponsavelSelecionado(){
		return this.tabela.getSelectionModel().getSelectedItem();
	}
	
	@SuppressWarnings("unchecked")
	public PesquisarResponsavel() {
		
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(new HBox(5, this.lblNome,this.txtPesquisa), 0, 0);
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(new HBox(5, this.btnSelecionar,this.btnCadastrar), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaNome,colunaTelefone,colunaCelular); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("nome"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("telefoneFormatado"));
		this.colunaCelular.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("celularFormatado"));
		
		//Adiciona eventos aos bot�es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnSelecionar.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnSelecionar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);
		
		this.lblNome.setMinWidth(ResolucaoTela.getLargura(10));
		
		this.colunaCelular.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaNome.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaTelefone.setPrefWidth(ResolucaoTela.getLargura(20));
		
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(90));
		
		this.lblNome.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		
		this.setScene(cenaPrincipal);

		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		this.setTitle("Sele��o de Respons�vel");
	}
	
	private void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Responsavel.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarResponsavel cadastro = new CadastrarResponsavel();
			cadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnSelecionar){
			this.close();
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome est� vazio
				//Se o txtNome n�o estiver tenta pesquisar Respons�veis com o conte�do digitado
				try {
					this.resultados = Responsavel.consultar("nome like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Respons�veis
					this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona os novos resultados na tabela
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				//Se o txtNome estiver vazio, preenche a tabela com todos os resultados do banco de dados
				try {
					this.preencherTabelaComTodosResultados();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnSelecionar.setDisable(true); 
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se s� existe um item selecionado
					this.btnSelecionar.setDisable(false);
				}
				else {
					this.btnSelecionar.setDisable(true);
				}
			}
		}
	}
}