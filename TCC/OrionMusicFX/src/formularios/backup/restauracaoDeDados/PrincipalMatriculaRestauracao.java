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
	private TableColumn<Matricula, String> colunaCodigo = new TableColumn<>("Código de Matrícula"); //Cria a coluna código
	private TableColumn<Matricula, String> colunaTurma = new TableColumn<>("Turma"); //Cria a coluna turma
	private TableColumn<Matricula, String> colunaAluno = new TableColumn<>("Aluno"); //Cria a coluna aluno
	private TableColumn<Matricula, String> colunaValorAula = new TableColumn<>("Valor da Aula"); //Cria a coluna valor da aula
	private List<Matricula> resultados; //Cria uma lista para guarda os responsáveis a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	@SuppressWarnings("unchecked")
	public PrincipalMatriculaRestauracao() {
		
		setScene(cena);
		this.layout.setHgap(15); // Define uma distância horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma distância vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		//Os dois componentes ficarão no mesmo lugar e suas visibilidades serão trocadas de acordo com a necessidade
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(new HBox(5,this.btnRestaurar), 0, 2);
		
		//Construção da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaTurma,colunaAluno,colunaValorAula); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poderão ser selecionados múltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Responsável elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Matricula,String>("cod"));
		this.colunaTurma.setCellValueFactory(new PropertyValueFactory<Matricula,String>("turma"));
		this.colunaAluno.setCellValueFactory(new PropertyValueFactory<Matricula,String>("aluno"));
		this.colunaValorAula.setCellValueFactory(new PropertyValueFactory<Matricula,String>("valorFormatado"));
		
		//Adiciona eventos aos botões
		this.btnRestaurar.setOnMouseClicked(this);
		
		//Adiciona evento à tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro é selecionado, portanto os botões podem ficar bloqueados
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
		
		this.setTitle("Restauração de Registros - Matrícula");
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
			if(Alerta.mostrarMensagemSimNao("Você deseja realmente excluir os registros selecionados?", "Confirmação de Exclusão") == false){
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
				Alerta.mostrarMensagemErro("Erro ao restaurar matrículas!","Erro");	
			}
			finally {
				this.tabela.getSelectionModel().clearSelection(); //Limpa as seleções				
				//Após a ação, a tabela perde a seleção, portanto os botões devem ser desativados
				this.btnRestaurar.setDisable(true); 
			}
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleção está vazia
				//Se estiver bloqueia os botões
				this.btnRestaurar.setDisable(true); 
			}
			else { //Se não estiver os botões serão desbloqueados, exceto o de Visualizar Matrículas de Dependentes, pois só pode ter um responsável selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se só existe um item selecionado
					// Se tiver, desbloqueia o botão
				}
				else {
					//Se houver mais de um, bloqueia o botão
				}
				this.btnRestaurar.setDisable(false);
			}
		}
	}
}
