package formularios.backup.restauracaoDeDados;

import java.util.List;

import recursos.FolhasEstilo;
import entidades.Escola;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalEscolaRestauracao extends JanelaMaximizada implements EventHandler<Event>{
	
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	private TableView<Escola> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Escola, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Escola, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaEndereco = new TableColumn<>("Endere�o"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaNumero = new TableColumn<>("N�mero"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaComplemento  = new TableColumn<>("Complemento"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaBairro = new TableColumn<>("Bairro"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaCidade = new TableColumn<>("Cidade"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaCEP = new TableColumn<>("CEP"); //Cria a coluna nome
	private TableColumn<Escola, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private List<Escola> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	
	@SuppressWarnings("unchecked")
	public PrincipalEscolaRestauracao() {

		setScene(cena);
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		//Adiciona os componentes ao Layout
		this.layout.add(tabela, 0,1);
		this.layout.add(new HBox(5, this.btnRestaurar),0,2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaNome,this.colunaEndereco, this.colunaNumero, this.colunaComplemento, this.colunaBairro, this.colunaCidade, this.colunaCEP, this.colunaTelefone); //Adiciona as colunas virtuais criadas a tabela
		
		//Fica dentro do Catch pois a consulta de Respons�veis pode gerar Erro
		try {
			this.resultados = Escola.consultar(); //Preenche a lista de resultados com todos os respons�veis do DB
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Preenche a tabela com a lista de resultados.
		// � necess�rio o FXCollections para transformar os resultados do tipo List para ObservableList, que � o tipo requisitado pelo setItems()
		
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //Indica que a tabela poder� ter multi-sele��es
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Escola,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Escola,String>("nome"));
		this.colunaEndereco.setCellValueFactory(new PropertyValueFactory<Escola,String>("endereco"));
		this.colunaNumero.setCellValueFactory(new PropertyValueFactory<Escola,String>("numEndereco"));
		this.colunaComplemento.setCellValueFactory(new PropertyValueFactory<Escola,String>("complemento"));
		this.colunaBairro.setCellValueFactory(new PropertyValueFactory<Escola,String>("bairro"));
		this.colunaCidade.setCellValueFactory(new PropertyValueFactory<Escola,String>("cidade"));
		this.colunaCEP.setCellValueFactory(new PropertyValueFactory<Escola,String>("cep"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Escola,String>("telefone"));
		
		//Adiciona eventos aos bot�es
		this.btnRestaurar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnRestaurar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.layout.setId("principalCadastro");
		
		this.colunaBairro.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaCEP.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaCidade.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaComplemento.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaEndereco.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaNome.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaNumero.setPrefWidth(ResolucaoTela.getLargura(9));
		this.colunaTelefone.setPrefWidth(ResolucaoTela.getLargura(9));
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(ResolucaoTela.getLargura(98.5));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Restaura��o de Registros - Escolas");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Escola.consultarArquivoMorto(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.getItems().clear();
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		
		if(e.getSource() == this.btnRestaurar){
			
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente excluir os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			List<Escola> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
				for(Escola exclusao : selecionados){ //Faz uma pesquisa nessa lista
					if(exclusao.restaurar() == false) //Manda cada objeto da lista ser excluso e verifica se deu erro
						Alerta.mostrarMensagemErro("Ocorreu um erro ao excluir a escola "+exclusao.getNome()+"!","Erro");
				}
				this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es				
				//Ap�s a a��o, a tabela perde a sele��o, portanto os bot�es devem ser desativados
				this.btnRestaurar.setDisable(true); 
				this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnRestaurar.setDisable(true); 
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){
				}
				else{
				}
				this.btnRestaurar.setDisable(false);
			}
		}
	}
}