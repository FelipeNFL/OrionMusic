package formularios.cronograma.turma;

import java.awt.GraphicsEnvironment;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Turma;
import ferramentas.ResolucaoTela;

/* Essa classe herda GridPane pois ela funcionará como um painel que será exibido dentro da Janela PrincipalCadastros */
public class PesquisarTurma extends Stage implements EventHandler<Event>{
	
	private TableView<Turma> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Turma, String> colunaCodigo = new TableColumn<>("Código"); //Cria a coluna código
	private TableColumn<Turma, String> colunaDiaSemana = new TableColumn<>("Dia da Semana"); //Cria a coluna nome
	private TableColumn<Turma, String> colunaEscola = new TableColumn<>("Escola"); //Cria a coluna telefone
	private TableColumn<Turma, String> colunaDisciplina = new TableColumn<>("Disciplina"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraInicial = new TableColumn<>("Hora Inicial"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraFinal = new TableColumn<>("Hora Final"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaDescricao = new TableColumn<>("Descrição"); //Cria a coluna celular
	private List<Turma> resultados; //Cria uma lista para guarda os responsáveis a serem exibidos
	private Button btnSelecionarTurma = new Button("Selecionar Turma");
	private Button btnCadastrarNovaTurma = new Button("Cadastrar Nova Turma");
	private HBox linhaPesquisa = new HBox();
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	private Turma turma;
	
	public Turma getTurma(){
		return this.turma;
	}
	
	@SuppressWarnings("unchecked")
	public PesquisarTurma() {

		this.layout.setHgap(15); // Define uma distância horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma distância vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(linhaPesquisa, 0, 0);
		this.layout.add(tabela, 0, 1);
		this.layout.add(new HBox(5, this.btnSelecionarTurma,this.btnCadastrarNovaTurma), 0, 2);
		
		//Construção da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaDiaSemana,this.colunaEscola,this.colunaDisciplina,this.colunaHoraInicial,this.colunaHoraFinal,this.colunaDescricao); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica para as colunas com qual atributo do Responsável elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Turma,String>("codTurma"));
		this.colunaDiaSemana.setCellValueFactory(new PropertyValueFactory<Turma,String>("diaSemana"));
		this.colunaEscola.setCellValueFactory(new PropertyValueFactory<Turma,String>("escola"));
		this.colunaDisciplina.setCellValueFactory(new PropertyValueFactory<Turma,String>("disciplina"));
		this.colunaHoraInicial.setCellValueFactory(new PropertyValueFactory<Turma,String>("horaInicialFormatada"));
		this.colunaHoraFinal.setCellValueFactory(new PropertyValueFactory<Turma,String>("horaFinalFormatada"));
		this.colunaDescricao.setCellValueFactory(new PropertyValueFactory<Turma,String>("descricaoTurma"));
		
		//Adiciona eventos aos botões
		this.btnSelecionarTurma.setOnMouseClicked(this);
		this.btnCadastrarNovaTurma.setOnMouseClicked(this);
		
		//Adiciona evento à tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro é selecionado, portanto os botões podem ficar bloqueados
		this.btnSelecionarTurma.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principal");

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(80));
		
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDescricao.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDiaSemana.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDisciplina.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaEscola.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaHoraFinal.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaHoraInicial.setPrefWidth(ResolucaoTela.getLargura(11.75));
		
		this.setScene(cenaPrincipal);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.layout.setId("principalCadastro");
		
		this.setTitle("Pesquisa de Turma");
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnSelecionarTurma, null, null));
	}
	
	private void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Turma.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnSelecionarTurma){
			this.turma = this.tabela.getSelectionModel().getSelectedItem();
			this.close();
		}
		else if(e.getSource() == this.btnCadastrarNovaTurma){
			CadastrarTurma telaCadastroTurma = new CadastrarTurma();
			telaCadastroTurma.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleção está vazia
				//Se estiver bloqueia os botões
				this.btnSelecionarTurma.setDisable(true); 
			}
			else { //Se não estiver os botões serão desbloqueados, exceto o de Visualizar Matrículas de Dependentes, pois só pode ter um responsável selecionado por vez
				this.btnSelecionarTurma.setDisable(false);
			}
		}
	}
}