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

import javax.swing.JOptionPane;

import recursos.FolhasEstilo;
import entidades.Turma;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalTurmaRestauracao extends JanelaMaximizada implements EventHandler<Event>{
	
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	private TableView<Turma> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Turma, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Turma, String> colunaDiaSemana = new TableColumn<>("Dia da Semana"); //Cria a coluna nome
	private TableColumn<Turma, String> colunaEscola = new TableColumn<>("Escola"); //Cria a coluna telefone
	private TableColumn<Turma, String> colunaDisciplina = new TableColumn<>("Disciplina"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraInicial = new TableColumn<>("Hora Inicial"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraFinal = new TableColumn<>("Hora Final"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaDescricao = new TableColumn<>("Descri��o"); //Cria a coluna celular
	private List<Turma> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnRestaurar = new Button("Restaurar");
	
	@SuppressWarnings("unchecked")
	public PrincipalTurmaRestauracao() {

		setScene(cena);
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(tabela, 0, 1);
		this.layout.add(new HBox(5,this.btnRestaurar), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaDiaSemana,this.colunaEscola,this.colunaDisciplina,this.colunaHoraInicial,this.colunaHoraFinal,this.colunaDescricao); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poder�o ser selecionados m�ltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Turma,String>("codTurma"));
		this.colunaDiaSemana.setCellValueFactory(new PropertyValueFactory<Turma,String>("diaSemana"));
		this.colunaEscola.setCellValueFactory(new PropertyValueFactory<Turma,String>("escola"));
		this.colunaDisciplina.setCellValueFactory(new PropertyValueFactory<Turma,String>("disciplina"));
		this.colunaHoraInicial.setCellValueFactory(new PropertyValueFactory<Turma,String>("horarioInicial"));
		this.colunaHoraFinal.setCellValueFactory(new PropertyValueFactory<Turma,String>("horarioFinal"));
		this.colunaDescricao.setCellValueFactory(new PropertyValueFactory<Turma,String>("descricaoTurma"));
		
		//Adiciona eventos aos bot�es
		this.btnRestaurar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnRestaurar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principalCronograma");

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDescricao.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDiaSemana.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaDisciplina.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaEscola.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaHoraFinal.setPrefWidth(ResolucaoTela.getLargura(11.75));
		this.colunaHoraInicial.setPrefWidth(ResolucaoTela.getLargura(11.75));
		
		this.setTitle("Restaura��o de Registros - Turmas");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Turma.consultarArquivoMorto(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		 if(e.getSource() == this.btnRestaurar){
			List<Turma> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Turma exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.restaurar(); //Manda cada objeto da lista ser excluso
				}
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
			}
			catch(Exception erro){
				erro.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao excluir respons�veis!","Erro",JOptionPane.ERROR_MESSAGE);	
			}
			finally {
				this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es				
				//Ap�s a a��o, a tabela perde a sele��o, portanto os bot�es devem ser desativados
				this.btnRestaurar.setDisable(true); 
			}
		}
		
			
		
		if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnRestaurar.setDisable(true); 
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				this.btnRestaurar.setDisable(false);
			}
		}
	}
}