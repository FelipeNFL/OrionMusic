package formularios.cadastros.matricula;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import recursos.FolhasEstilo;
import entidades.Aluno;
import entidades.Matricula;
import entidades.Turma;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.relatorios.AbridorDeJasper;
import formularios.relatorios.TelaCarregamentoRelatorio;

public class PrincipalMatricula extends GridPane implements EventHandler<Event>{

	private TableView<Matricula> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Matricula, String> colunaCodigo = new TableColumn<>("Código de Matrícula"); //Cria a coluna código
	private TableColumn<Matricula, String> colunaTurma = new TableColumn<>("Turma"); //Cria a coluna turma
	private TableColumn<Matricula, String> colunaAluno = new TableColumn<>("Aluno"); //Cria a coluna aluno
	private TableColumn<Matricula, String> colunaValorAula = new TableColumn<>("Valor da Aula"); //Cria a coluna valor da aula
	private List<Matricula> resultados = new ArrayList<>(); //Cria uma lista para guarda os responsáveis a serem exibidos
	private ComboBox<String> cmbFiltrosPesquisa = new ComboBox<>();
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnModificar = new  Button("Modificar");
	private Button btnGerarRelatorioGeralMatricula = new Button("Gerar Balancete Mensal de Matrículas");
	private GridPane layoutLinhaPesquisa = new GridPane();
	
	@SuppressWarnings("unchecked")
	public PrincipalMatricula() {

		this.setHgap(15); // Define uma distância horizontal de 15px entre os componentes do painel
		this.setVgap(15); // Define uma distância vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);
		
		this.layoutLinhaPesquisa.add(this.cmbFiltrosPesquisa, 0, 0);
		this.layoutLinhaPesquisa.add(this.txtPesquisa, 1, 0);
		
		this.layoutLinhaPesquisa.setHgap(5);
		
		//Adiciona os componentes ao Layout
		this.add(this.layoutLinhaPesquisa, 0, 0);
		//Os dois componentes ficarão no mesmo lugar e suas visibilidades serão trocadas de acordo com a necessidade
		this.add(this.tabela, 0, 1);
		this.add(new HBox(5, this.btnCadastrar,this.btnModificar,this.btnExcluir,this.btnGerarRelatorioGeralMatricula), 0, 2);
		
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
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnModificar.setOnMouseClicked(this);
		this.btnGerarRelatorioGeralMatricula.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento à tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro é selecionado, portanto os botões podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);
		
		this.colunaAluno.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaCodigo.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaTurma.setPrefWidth(ResolucaoTela.getLargura(16));
		this.colunaValorAula.setPrefWidth(ResolucaoTela.getLargura(16));
		
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(70));
		
		this.cmbFiltrosPesquisa.getItems().addAll("Nome do Aluno","Descrição da Turma");
		this.cmbFiltrosPesquisa.setOnHidden(this);
		this.cmbFiltrosPesquisa.setPromptText("Critério de pesquisa");
		this.cmbFiltrosPesquisa.setMinWidth(ResolucaoTela.getLargura(20));

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
			this.resultados = Matricula.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarMatricula cadastro = new CadastrarMatricula();
			cadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			if(Alerta.mostrarMensagemSimNao("Você deseja realmente excluir os registros selecionados?", "Confirmação de Exclusão") == false){
				return;
			}
			
			List<Matricula> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
			
			try {
				for(Matricula exclusao : selecionados){ //Faz uma pesquisa nessa lista
					exclusao.excluir(); //Manda cada objeto da lista ser excluso
				}
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
			}
			catch(Exception erro){
				erro.printStackTrace();
				Alerta.mostrarMensagemErro("Erro ao excluir matrículas!","Erro");	
			}
			finally {
				this.tabela.getSelectionModel().clearSelection(); //Limpa as seleções				
				//Após a ação, a tabela perde a seleção, portanto os botões devem ser desativados
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
		}
		else if(e.getSource() == this.btnModificar){
			List<Matricula> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados

			for(Matricula atualizar : selecionados){ //Faz uma pesquisa nessa lista
				ModificarMatricula janelaModificacao = new ModificarMatricula(atualizar);
				janelaModificacao.showAndWait();
				this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
				//Após a ação, a tabela perde a seleção, portanto os botões devem ser desativados
				this.btnExcluir.setDisable(true);
				this.btnModificar.setDisable(true);
			}
			this.tabela.getSelectionModel().clearSelection(); //Limpa as seleções				
			
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome está vazio
				//Se o txtNome não estiver tenta pesquisar Responsáveis com o conteúdo digitado
				try {
					this.resultados.clear();
					
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Nome do Aluno"){
						List<Aluno> alunosPesquisados = Aluno.consultar("nome like '%"+this.txtPesquisa.getText()+"%'");
						
						for(Aluno alunoParaPercorrerLista : alunosPesquisados){
							for (Matricula matricula : alunoParaPercorrerLista.getMatriculas()) {
								if(matricula.isArquivoMorto() == false){
									this.resultados.add(matricula);
								}
							}
						}
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString() == "Descrição da Turma")
					{
						List<Turma> turmasPesquisadas = Turma.consultar("descricao like '%"+this.txtPesquisa.getText()+"%'");
						
						for(Turma turmaParaPercorrerLista : turmasPesquisadas){
							for (Matricula matricula : turmaParaPercorrerLista.getMatriculas()) {
								if(matricula.isArquivoMorto() == false){
									this.resultados.add(matricula);
								}
							}
						}
					}
					
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
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleção está vazia
				//Se estiver bloqueia os botões
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
			}
			else { //Se não estiver os botões serão desbloqueados, exceto o de Visualizar Matrículas de Dependentes, pois só pode ter um responsável selecionado por vez
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){ //Verifica se só existe um item selecionado
					// Se tiver, desbloqueia o botão
					this.btnModificar.setDisable(false);
				}
				else {
					//Se houver mais de um, bloqueia o botão
					this.btnModificar.setDisable(true);
				}
				this.btnExcluir.setDisable(false);
			}
		}
		else if(e.getSource() == this.btnGerarRelatorioGeralMatricula){
			
			final TelaCarregamentoRelatorio telaCarregamentoRelatorio = new TelaCarregamentoRelatorio();
			telaCarregamentoRelatorio.show();
			
			Task<Void> taskCarregadorRelatorio = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/OrionMusic?user=root&password=1234");
						JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("reports/MatriculasMensal.jasper");
						
						JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap<String,Object>(), con);
						JasperExportManager.exportReportToPdfFile(jp, "reports/matriculasMensais.pdf");
						
						new AbridorDeJasper(jp,"Balancete Mensal de Matrículas");
						
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
