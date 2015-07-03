package formularios.backup.restauracaoDeDados;

import java.util.List;

import recursos.FolhasEstilo;
import entidades.Aluno;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalAlunoRestauracao extends JanelaMaximizada implements EventHandler<Event>{
	
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	private TableView<Aluno> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Aluno, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaEndereco = new TableColumn<>("Endere�o"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaNumero = new TableColumn<>("N�mero"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaComplemento  = new TableColumn<>("Complemento"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaBairro = new TableColumn<>("Bairro"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaCidade = new TableColumn<>("Cidade"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaCEP = new TableColumn<>("CEP"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private TableColumn<Aluno, String> colunaCelular = new TableColumn<>("Celular"); //Cria a coluna celular
	private TableColumn<Aluno, String> colunaEmail = new TableColumn<>("E-mail"); //Cria a coluna telefone
	private TableColumn<Aluno, String> colunaDataNascimento = new TableColumn<>("Data de Nascimento"); //Cria a coluna data de nascimento
	private TableColumn<Aluno, String> colunaResponsavel = new TableColumn<>("Respons�vel"); //Cria a coluna respons�vel
	private List<Aluno> resultados; //Cria uma lista para guardar os alunos a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	
	@SuppressWarnings("unchecked")
	public PrincipalAlunoRestauracao() {
		this.setScene(cena);

		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		HBox linhaBotoes = new HBox(this.btnRestaurar);
		linhaBotoes.setSpacing(5);
		
		//Adiciona os componentes ao Layout
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(linhaBotoes,0,2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaNome,this.colunaEndereco, this.colunaNumero, this.colunaComplemento, this.colunaBairro, this.colunaCidade, this.colunaCEP, this.colunaTelefone,this.colunaCelular,this.colunaEmail,this.colunaDataNascimento,this.colunaResponsavel); //Adiciona as colunas virtuais criadas a tabela
		
		//Fica dentro do Catch pois a consulta de Respons�veis pode gerar Erro
		try {
			this.resultados = Aluno.consultar(); //Preenche a lista de resultados com todos os respons�veis do DB
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Preenche a tabela com a lista de resultados.
		// � necess�rio o FXCollections para transformar os resultados do tipo List para ObservableList, que � o tipo requisitado pelo setItems()
		
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //Indica que a tabela poder� ter multi-sele��es
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Aluno,String>("nome"));
		this.colunaEndereco.setCellValueFactory(new PropertyValueFactory<Aluno,String>("nomeLogradouro"));
		this.colunaNumero.setCellValueFactory(new PropertyValueFactory<Aluno,String>("numLogradouro"));
		this.colunaComplemento.setCellValueFactory(new PropertyValueFactory<Aluno,String>("complemento"));
		this.colunaBairro.setCellValueFactory(new PropertyValueFactory<Aluno,String>("bairro"));
		this.colunaCidade.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cidade"));
		this.colunaCEP.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cep"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Aluno,String>("telefone"));
		this.colunaCelular.setCellValueFactory(new PropertyValueFactory<Aluno,String>("celular"));
		this.colunaEmail.setCellValueFactory(new PropertyValueFactory<Aluno,String>("email"));
		this.colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<Aluno,String>("dataNascimento"));
		this.colunaResponsavel.setCellValueFactory(new PropertyValueFactory<Aluno,String>("responsavel"));
		
		//Adiciona eventos aos bot�es
		this.btnRestaurar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnRestaurar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.layout.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90)); 
		/*Define a altura que a tabela ter�. Nesse caso, dever� chamar a classe que trata a resolu��o da tela, para pegar 90% da altura da tela e atribuir
		 * a altura da tabela.
		 */
		
		//Pega 7% do valor da largura da tela e atribui como largura das colunas da tabela
		
		this.colunaBairro.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCelular.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCEP.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCidade.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCodigo.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaComplemento.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaDataNascimento.setMinWidth(ResolucaoTela.getLargura(7)); //Como "Data de Nascimento" � um nome enoooorme, vamos dar mais 0.3 % para n�o cortar
		this.colunaEmail.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaEndereco.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaNome.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaNumero.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaTelefone.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaResponsavel.setMinWidth(ResolucaoTela.getLargura(7));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Restaura��o de Registros - Aluno");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Aluno.consultarArquivoMorto(); //Consulta todos os registros
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
			
			List<Aluno> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
				for(Aluno exclusao : selecionados){ //Faz uma pesquisa nessa lista
					if(exclusao.restaurar() == false) //Manda cada objeto da lista ser excluso e verifica se deu erro
						Alerta.mostrarMensagemErro("Ocorreu um erro ao excluir a Aluno "+exclusao.getNome()+"!","Erro");
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