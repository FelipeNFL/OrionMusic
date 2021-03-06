package formularios.lembrete;

import java.util.List;

import recursos.FolhasEstilo;
import entidades.Lembrete;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalLembretes extends JanelaMaximizada implements EventHandler<Event>{

	private TableView<Lembrete> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Lembrete, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Lembrete, String> colunaNomeAluno = new TableColumn<>("Nome do Aluno"); //Cria a coluna nome
	private TableColumn<Lembrete, String> colunaAnotacao = new TableColumn<>("Anota��o"); //Cria a coluna telefone
	private TableColumn<Lembrete, String> colunaTurma = new TableColumn<>("Descri��o da turma");
	private List<Lembrete> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnModificar = new  Button("Modificar");
	
	@SuppressWarnings("unchecked")
	public PrincipalLembretes() {
		
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(this.tabela, 0, 1);
		this.layout.add(new HBox(5, this.btnCadastrar,this.btnModificar,this.btnExcluir), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaNomeAluno,colunaAnotacao,colunaTurma); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poder�o ser selecionados m�ltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Lembrete,String>("cod"));
		this.colunaNomeAluno.setCellValueFactory(new PropertyValueFactory<Lembrete,String>("nomeAluno"));
		this.colunaAnotacao.setCellValueFactory(new PropertyValueFactory<Lembrete,String>("anotacoesGerais"));
		this.colunaTurma.setCellValueFactory(new PropertyValueFactory<Lembrete,String>("descricaoTurma"));
		
		//Adiciona eventos aos bot�es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnModificar.setOnMouseClicked(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);
		
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaNomeAluno.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaAnotacao.setPrefWidth(ResolucaoTela.getLargura(23));
		this.colunaTurma.setPrefWidth(ResolucaoTela.getLargura(23));

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Lembretes");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnExcluir, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Lembrete.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarLembrete cadastro = new CadastrarLembrete();
			cadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente excluir os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			List<Lembrete> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Lembrete exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.excluir(); //Manda cada objeto da lista ser excluso
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
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
		}
		else if(e.getSource() == this.btnModificar){
			ModificarLembrete telaParaModificarLembrete = new ModificarLembrete(this.tabela.getSelectionModel().getSelectedItem());
			telaParaModificarLembrete.showAndWait();
			this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es	
			this.btnModificar.setDisable(true);
			this.btnExcluir.setDisable(true);
		}
		else if(e.getSource() == this.tabela){
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se s� existe um item selecionado
					this.btnModificar.setDisable(false);
				}
				else {
					this.btnModificar.setDisable(true);
				}
				this.btnExcluir.setDisable(false);
			}
		}
	}
}