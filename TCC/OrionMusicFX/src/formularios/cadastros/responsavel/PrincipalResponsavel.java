package formularios.cadastros.responsavel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import recursos.FolhasEstilo;
import entidades.Responsavel;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.relatorios.AbridorDeJasper;
import formularios.relatorios.TelaCarregamentoRelatorio;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PrincipalResponsavel extends GridPane implements EventHandler<Event>{
	
	private TableView<Responsavel> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Responsavel, String> colunaCodigo = new TableColumn<>("C�digo"); //Cria a coluna c�digo
	private TableColumn<Responsavel, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Responsavel, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private TableColumn<Responsavel, String> colunaCelular = new TableColumn<>("Celular"); //Cria a coluna celular
	private List<Responsavel> resultados; //Cria uma lista para guarda os respons�veis a serem exibidos
	private Label lblNome = new Label("Nome:");
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnModificar = new  Button("Modificar");
	private Button btnVisualizarMatriculasDependentes = new Button("Gerar Relat�rio de Matr�culas de Dependentes");
	
	@SuppressWarnings("unchecked")
	public PrincipalResponsavel() {

		this.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.add(new HBox(5, this.lblNome,this.txtPesquisa), 0, 0);
		this.add(this.tabela, 0, 1);
		this.add(new HBox(5, this.btnCadastrar,this.btnModificar,this.btnExcluir,this.btnVisualizarMatriculasDependentes), 0, 2);
		
		//Constru��o da Tabela
		this.tabela.getColumns().addAll(colunaCodigo,colunaNome,colunaTelefone,colunaCelular); //Adiciona as colunas virtuais criadas a tabela
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poder�o ser selecionados m�ltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("nome"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("telefoneFormatado"));
		this.colunaCelular.setCellValueFactory(new PropertyValueFactory<Responsavel,String>("celularFormatado"));
		
		//Adiciona eventos aos bot�es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnModificar.setOnMouseClicked(this);
		this.btnVisualizarMatriculasDependentes.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnVisualizarMatriculasDependentes.setDisable(true);
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);
		
		this.lblNome.setMinWidth(ResolucaoTela.getLargura(10));
		
		this.colunaCelular.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaNome.setPrefWidth(ResolucaoTela.getLargura(20));
		this.colunaTelefone.setPrefWidth(ResolucaoTela.getLargura(20));
		
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(77.5));
		
		this.lblNome.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnExcluir, null, null));
	}
	
	public void preencherTabelaComTodosResultados(){
		try {
			this.resultados = Responsavel.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarResponsavel cadastro = new CadastrarResponsavel();
			cadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente excluir os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			List<Responsavel> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Responsavel exclusao : selecionados){ //Faz uma pesquisa nessa lista
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
				this.btnVisualizarMatriculasDependentes.setDisable(true);
			}
		}
		else if(e.getSource() == this.btnModificar){
			List<Responsavel> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados

			for(Responsavel atualizar : selecionados){ //Faz uma pesquisa nessa lista
				ModificarResponsavel janelaModificacao = new ModificarResponsavel(atualizar);
				janelaModificacao.showAndWait();
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
				//Ap�s a a��o, a tabela perde a sele��o, portanto os bot�es devem ser desativados
				this.btnExcluir.setDisable(true);
				this.btnModificar.setDisable(true);
				this.btnVisualizarMatriculasDependentes.setDisable(true);
			}
			this.tabela.getSelectionModel().clearSelection(); //Limpa as sele��es				
			
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome est� vazio
				//Se o txtNome n�o estiver tenta pesquisar Respons�veis com o conte�do digitado
				try {
					this.resultados = Responsavel.consultar("nome like '%"+this.txtPesquisa.getText()+"%'"); // Consulta Respons�veis
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
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a sele��o est� vazia
				//Se estiver bloqueia os bot�es
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
				this.btnVisualizarMatriculasDependentes.setDisable(true);
			}
			else { //Se n�o estiver os bot�es ser�o desbloqueados, exceto o de Visualizar Matr�culas de Dependentes, pois s� pode ter um respons�vel selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se s� existe um item selecionado
					this.btnVisualizarMatriculasDependentes.setDisable(false); // Se tiver, desbloqueia o bot�o
					this.btnModificar.setDisable(false);
				}
				else {
					this.btnVisualizarMatriculasDependentes.setDisable(true); //Se houver mais de um, bloqueia o bot�o
					this.btnModificar.setDisable(true);
				}
				this.btnExcluir.setDisable(false);
			}
		}
		else if(e.getSource() == this.btnVisualizarMatriculasDependentes){
			
			final TelaCarregamentoRelatorio telaCarregamentoRelatorio = new TelaCarregamentoRelatorio();
			telaCarregamentoRelatorio.show();
			
			Task<Void> taskCarregadorRelatorio = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/OrionMusic?user=root&password=1234");
						JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("reports/MatriculasResponsavel.jasper");
						
						HashMap<String,Object> parametros = new HashMap<String, Object>();
						parametros.put("codResponsavel", tabela.getSelectionModel().getSelectedItem().getCod());
						
						JasperPrint jp = JasperFillManager.fillReport(jr, parametros, con);
						JasperExportManager.exportReportToPdfFile(jp, "reports/matriculasResponsavel.pdf");
						
						new AbridorDeJasper(jp,"Matr�culas de Dependente");
						
					} catch (JRException e1) {
							e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void succeeded() {
					telaCarregamentoRelatorio.close();
				}
			};
			
			Thread tarefaCarregaRelatorio = new Thread(taskCarregadorRelatorio);
			tarefaCarregaRelatorio.setDaemon(true);
			tarefaCarregaRelatorio.start();
		}
	}
}