package formularios.cronograma.turma;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import formularios.cronograma.turma.CadastrarTurma;
import formularios.cronograma.turma.ModificarTurma;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalTurma extends GridPane implements EventHandler<Event>{
	
	private TableView<Turma> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Turma, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Turma, String> colunaDiaSemana = new TableColumn<>("Dia da Semana"); //Cria a coluna nome
	private TableColumn<Turma, String> colunaEscola = new TableColumn<>("Escola"); //Cria a coluna telefone
	private TableColumn<Turma, String> colunaDisciplina = new TableColumn<>("Disciplina"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraInicial = new TableColumn<>("Hora Inicial"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaHoraFinal = new TableColumn<>("Hora Final"); //Cria a coluna celular
	private TableColumn<Turma, String> colunaDescricao = new TableColumn<>("Descri��o"); //Cria a coluna celular
	private List<Turma> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnModificar = new  Button("Modificar");
	private HBox linhaPesquisa = new HBox();
	private ComboBox<String> cmbFiltrosPesquisa = new ComboBox<>(FXCollections.observableArrayList("C�digo","Dia da Semana","Escola","Disciplina","Hora Inicial","Hora Final","Descri��o"));
	
	@SuppressWarnings("unchecked")
	public PrincipalTurma() {

		this.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.add(linhaPesquisa, 0, 0);
		this.add(tabela, 0, 1);
		this.add(new HBox(5, this.btnCadastrar,this.btnModificar,this.btnExcluir), 0, 2);
		
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
		this.colunaHoraInicial.setCellValueFactory(new PropertyValueFactory<Turma,String>("horaInicialFormatada"));
		this.colunaHoraFinal.setCellValueFactory(new PropertyValueFactory<Turma,String>("horaFinalFormatada"));
		this.colunaDescricao.setCellValueFactory(new PropertyValueFactory<Turma,String>("descricaoTurma"));
		
		//Adiciona eventos aos bot�es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnModificar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.setId("principalCronograma");

		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
		
		this.cmbFiltrosPesquisa.setMinWidth(ResolucaoTela.getLargura(20));
		this.cmbFiltrosPesquisa.setPromptText("Crit�rio de Pesquisa");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnExcluir, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Turma.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarTurma cadastro = new CadastrarTurma();
			cadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			List<Turma> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Turma exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.excluir(); //Manda cada objeto da lista ser excluso
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
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
		}
		else if(e.getSource() == this.btnModificar){
			List<Turma> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados

			for(Turma atualizar : selecionados){ //Faz uma pesquisa nessa lista
				ModificarTurma janelaModificacao = new ModificarTurma(atualizar);
				janelaModificacao.showAndWait();
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
				//Ap�s a a��o, a tabela perde a sele��o, portanto os bot�es devem ser desativados
				this.btnExcluir.setDisable(true);
				this.btnModificar.setDisable(true);
			}
			this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es				
			
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				this.btnExcluir.setDisable(false);
				this.btnModificar.setDisable(false);
			}
		}
	}
}