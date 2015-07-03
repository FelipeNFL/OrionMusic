package formularios.backup.restauracaoDeDados;

import java.util.List;

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
import recursos.FolhasEstilo;
import entidades.Matricula;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;

public class PrincipalMatriculaRestauracao extends JanelaMaximizada implements EventHandler<Event>{

	private TableView<Matricula> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Matricula, String> colunaCodigo = new TableColumn<>("C�digo de Matr�cula"); //Cria a coluna c�digo
	private TableColumn<Matricula, String> colunaTurma = new TableColumn<>("Turma"); //Cria a coluna turma
	private TableColumn<Matricula, String> colunaAluno = new TableColumn<>("Aluno"); //Cria a coluna aluno
	private TableColumn<Matricula, String> colunaValorAula = new TableColumn<>("Valor da Aula"); //Cria a coluna valor da aula
	private List<Matricula> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	@SuppressWarnings("unchecked")
	public PrincipalMatriculaRestauracao() {
		
		setScene(cena);
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		//Os dois componentes ficar�o no mesmo lugar e suas visibilidades ser�o trocadas de acordo com a necessidade
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(new HBox(5,this.btnRestaurar), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaTurma,colunaAluno,colunaValorAula); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poder�o ser selecionados m�ltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Matricula,String>("cod"));
		this.colunaTurma.setCellValueFactory(new PropertyValueFactory<Matricula,String>("turma"));
		this.colunaAluno.setCellValueFactory(new PropertyValueFactory<Matricula,String>("aluno"));
		this.colunaValorAula.setCellValueFactory(new PropertyValueFactory<Matricula,String>("valorFormatado"));
		
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
		
		this.colunaAluno.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaTurma.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaValorAula.setPrefWidth(ResolucaoTela.getLargura(16));

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Restaura��o de Registros - Matr�cula");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Matricula.consultarArquivoMorto(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		
		 if(e.getSource() == this.btnRestaurar){
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente excluir os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			List<Matricula> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Matricula exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.restaurar(); //Manda cada objeto da lista ser excluso
				}
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
			}
			catch(Exception erro){
				erro.printStackTrace();
				Alerta.mostrarMensagemErro("Erro ao restaurar matr�culas!","Erro");	
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
					// Se tiver, desbloqueia o bot�o
				}
				else {
					//Se houver mais de um, bloqueia o bot�o
				}
				this.btnRestaurar.setDisable(false);
			}
		}
	}
}
