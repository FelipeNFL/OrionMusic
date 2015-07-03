package formularios.cadastros.escola;

import java.awt.GraphicsEnvironment;
import java.util.List;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Escola;
import ferramentas.ResolucaoTela;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/* Essa classe herda GridPane pois ela funcionarï¿½ como um painel que serï¿½ exibido dentro da Janela PrincipalCadastros */
public class PesquisarEscola extends Stage implements EventHandler<Event>{
	
	private TableView<Escola> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Escola, String> colunaCodigo = new TableColumn<>("Código"); //Cria a coluna cï¿½digo
	private TableColumn<Escola, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaEndereco = new TableColumn<>("Endereço"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaNumero = new TableColumn<>("Número"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaComplemento  = new TableColumn<>("Complemento"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaBairro = new TableColumn<>("Bairro"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaCidade = new TableColumn<>("Cidade"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaCEP = new TableColumn<>("CEP"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private List<Escola> resultados; //Cria uma lista para guarda os responsï¿½veis a serem exibidos
	private ComboBox<String> cmbFiltrosPesquisa = new ComboBox<>(FXCollections.observableArrayList("Código","Nome","Endereço","Bairro","Cidade"));
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnSelecionar = new  Button("Selecionar");
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	@SuppressWarnings("unchecked")
	public PesquisarEscola() {

		this.setScene(cenaPrincipal);
		
		this.layout.setHgap(15); // Define uma distï¿½ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma distï¿½ncia vertical de 15 pixels entre os componentes do painel
		
		//Adiciona os componentes ao Layout
		this.layout.add(new HBox(5, this.cmbFiltrosPesquisa,this.txtPesquisa),0,0);
		this.layout.add(tabela, 0,1);
		this.layout.add(new HBox(5, this.btnSelecionar,this.btnCadastrar),0,2);
		
		//Construï¿½ï¿½o da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaNome,this.colunaEndereco, this.colunaNumero, this.colunaComplemento, this.colunaBairro, this.colunaCidade, this.colunaCEP, this.colunaTelefone); //Adiciona as colunas virtuais criadas a tabela
		
		//Fica dentro do Catch pois a consulta de Responsï¿½veis pode gerar Erro
		try {
			this.resultados = Escola.consultar(); //Preenche a lista de resultados com todos os responsï¿½veis do DB
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Preenche a tabela com a lista de resultados.
		// ï¿½ necessï¿½rio o FXCollections para transformar os resultados do tipo List para ObservableList, que ï¿½ o tipo requisitado pelo setItems()
		
		//Indica para as colunas com qual atributo do Responsï¿½vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Escola,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Escola,String>("nome"));
		this.colunaEndereco.setCellValueFactory(new PropertyValueFactory<Escola,String>("endereco"));
		this.colunaNumero.setCellValueFactory(new PropertyValueFactory<Escola,String>("numEndereco"));
		this.colunaComplemento.setCellValueFactory(new PropertyValueFactory<Escola,String>("complemento"));
		this.colunaBairro.setCellValueFactory(new PropertyValueFactory<Escola,String>("bairro"));
		this.colunaCidade.setCellValueFactory(new PropertyValueFactory<Escola,String>("cidade"));
		this.colunaCEP.setCellValueFactory(new PropertyValueFactory<Escola,String>("cepFormatado"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Escola,String>("telefoneFormatado"));
		
		//Adiciona eventos aos botï¿½es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnSelecionar.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento ï¿½ tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro ï¿½ selecionado, portanto os botï¿½es podem ficar bloqueados
		this.btnSelecionar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.layout.setId("principalCadastro");
		
		this.cmbFiltrosPesquisa.setPromptText("Critério de pesquisa");
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(80));
		this.cmbFiltrosPesquisa.setMinWidth(ResolucaoTela.getLargura(20));
		
		this.colunaBairro.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaCEP.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaCidade.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaComplemento.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaEndereco.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaNome.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaNumero.setPrefWidth(ResolucaoTela.getLargura(12));
		this.colunaTelefone.setPrefWidth(ResolucaoTela.getLargura(12));
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(ResolucaoTela.getLargura(100));
		
		this.setTitle("Pesquisa de Escola");
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
	}
	
	public Escola getEscolaSelecionada(){
		return this.tabela.getSelectionModel().getSelectedItem();
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Escola.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.getItems().clear();
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarEscola janelaCadastro = new CadastrarEscola();
			janelaCadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnSelecionar){
			this.close();
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome estï¿½ vazio
				//Se o txtNome nï¿½o estiver tenta pesquisar Responsï¿½veis com o conteï¿½do digitado
				try {
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Código"){
						this.resultados = Escola.consultar("cod = "+Integer.parseInt(this.txtPesquisa.getText())+""); // Consulta Responsï¿½veis
					}
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Nome"){
						this.resultados = Escola.consultar("nome like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Responsï¿½veis
					}
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Endereço"){
						this.resultados = Escola.consultar("endereco like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Responsï¿½veis
					}
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Bairro"){
						this.resultados = Escola.consultar("bairro like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Responsï¿½veis
					}
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Cidade"){
						this.resultados = Escola.consultar("cidade like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Responsï¿½veis
					}
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
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleï¿½ï¿½o estï¿½ vazia
				//Se estiver bloqueia os botï¿½es
				this.btnSelecionar.setDisable(true);
			}
			else { //Se nï¿½o estiver os botï¿½es serï¿½o desbloqueados, exceto o de Visualizar Matrï¿½culas de Dependentes, pois sï¿½ pode ter um responsï¿½vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){
					this.btnSelecionar.setDisable(false);
				}
				else{
					this.btnSelecionar.setDisable(true);
				}
			}
		}
	}
}