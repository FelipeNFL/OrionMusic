package formularios.backup.restauracaoDeDados;

import java.awt.GraphicsEnvironment;
import java.util.List;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Usuario;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalUsuarioRestauracao extends JanelaMaximizada implements EventHandler<Event>{
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(this.layout);
	
	private TableView<Usuario> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Usuario, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Usuario, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Usuario, String> colunaLogin = new TableColumn<>("Login"); //Cria a coluna telefone
	private TableColumn<Usuario, String> colunaPerfil = new TableColumn<>("Perfil"); //Cria a coluna celular
	private List<Usuario> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	
	@SuppressWarnings("unchecked")
	public PrincipalUsuarioRestauracao() {

		this.setScene(this.cenaPrincipal);
		
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(new HBox(5,this.btnRestaurar), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaNome,colunaLogin,colunaPerfil); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poder�o ser selecionados m�ltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Usuario,String>("codUsuario"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Usuario,String>("nome"));
		this.colunaLogin.setCellValueFactory(new PropertyValueFactory<Usuario,String>("login"));
		this.colunaPerfil.setCellValueFactory(new PropertyValueFactory<Usuario,String>("perfil"));
		
		//Adiciona eventos aos bot�es
		this.btnRestaurar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnRestaurar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);
		
		this.colunaPerfil.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaNome.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaLogin.setPrefWidth(ResolucaoTela.getLargura(23));

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());

		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.setTitle("Restaura��o de Registros - Usu�rios");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Usuario.consultarArquivoMorto(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		 if(e.getSource() == this.btnRestaurar){
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente restaurar os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			List<Usuario> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Usuario exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.restaurar(); //Manda cada objeto da lista ser excluso
				}
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
			}
			catch(Exception erro){
				erro.printStackTrace();
				Alerta.mostrarMensagemErro("Erro ao excluir respons�veis!","Erro");	
			}
			finally {
				this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es				
				//Ap�s a a��o, a tabela perde a sele��o, portanto os bot�es devem ser desativados
				this.btnRestaurar.setDisable(true); 
			}
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnRestaurar.setDisable(true); 
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se s� existe um item selecionado
				}
				else {
				}
				this.btnRestaurar.setDisable(false);
			}
		}
	}
}