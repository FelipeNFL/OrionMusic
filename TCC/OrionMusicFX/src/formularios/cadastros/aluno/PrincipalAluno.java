package formularios.cadastros.aluno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import recursos.FolhasEstilo;
import entidades.Aluno;
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

/* Essa classe herda GridPane pois ela funcionarï¿½ como um painel que serï¿½ exibido dentro da Janela PrincipalCadastros */
public class PrincipalAluno extends GridPane implements EventHandler<Event>{
	
	private TableView<Aluno> tabela = new TableView<>(); //Cria uma tabela para exibir dados
	private TableColumn<Aluno, String> colunaCodigo = new TableColumn<>("Código"); //Cria a coluna cï¿½digo
	private TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaEndereco = new TableColumn<>("Endereço"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaNumero = new TableColumn<>("Número"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaComplemento  = new TableColumn<>("Complemento"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaBairro = new TableColumn<>("Bairro"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaCidade = new TableColumn<>("Cidade"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaCEP = new TableColumn<>("CEP"); //Cria a coluna nome
	private TableColumn<Aluno, String> colunaTelefone = new TableColumn<>("Telefone"); //Cria a coluna telefone
	private TableColumn<Aluno, String> colunaCelular = new TableColumn<>("Celular"); //Cria a coluna celular
	private TableColumn<Aluno, String> colunaEmail = new TableColumn<>("E-mail"); //Cria a coluna telefone
	private TableColumn<Aluno, String> colunaDataNascimento = new TableColumn<>("Data de Nascimento"); //Cria a coluna data de nascimento
	private TableColumn<Aluno, String> colunaResponsavel = new TableColumn<>("Responsável"); //Cria a coluna data de nascimento
	private List<Aluno> resultados; //Cria uma lista para guardar os alunos a serem exibidos
	private ComboBox<String> cmbFiltrosPesquisa = new ComboBox<>(FXCollections.observableArrayList("Código","Nome","Endereço","Bairro","Cidade","Data de Nascimento","Responsável"));
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnModificar = new  Button("Modificar");
	private Button btnGerarRelatorioMatriculas = new Button("Gerar Relatório de Matrículas");
	
	@SuppressWarnings("unchecked")
	public PrincipalAluno() {

		this.setHgap(15); // Define uma distï¿½ncia horizontal de 15px entre os componentes do painel
		this.setVgap(15); // Define uma distï¿½ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);
		
		HBox linhaPesquisa = new HBox(this.cmbFiltrosPesquisa,this.txtPesquisa);
		linhaPesquisa.setSpacing(5);
		
		HBox linhaBotoes = new HBox(this.btnCadastrar,this.btnModificar,this.btnExcluir,this.btnGerarRelatorioMatriculas);
		linhaBotoes.setSpacing(5);
		
		//Adiciona os componentes ao Layout
		this.add(linhaPesquisa, 0, 0);
		this.add(this.tabela, 0, 1);
		this.add(linhaBotoes,0,2);
		
		//Construï¿½ï¿½o da Tabela
		this.tabela.getColumns().addAll(this.colunaCodigo,this.colunaNome,this.colunaEndereco, this.colunaNumero, this.colunaComplemento, this.colunaBairro, this.colunaCidade, this.colunaCEP, this.colunaTelefone,this.colunaCelular,this.colunaEmail,this.colunaDataNascimento,this.colunaResponsavel); //Adiciona as colunas virtuais criadas a tabela
		
		//Fica dentro do Catch pois a consulta de Responsï¿½veis pode gerar Erro
		try {
			this.resultados = Aluno.consultar(); //Preenche a lista de resultados com todos os responsï¿½veis do DB
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Preenche a tabela com a lista de resultados.
		// ï¿½ necessï¿½rio o FXCollections para transformar os resultados do tipo List para ObservableList, que ï¿½ o tipo requisitado pelo setItems()
		
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //Indica que a tabela poderï¿½ ter multi-seleï¿½ï¿½es
		
		//Indica para as colunas com qual atributo do Responsï¿½vel elas devem ser preenchidas
		this.colunaCodigo.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cod"));
		this.colunaNome.setCellValueFactory(new PropertyValueFactory<Aluno,String>("nome"));
		this.colunaEndereco.setCellValueFactory(new PropertyValueFactory<Aluno,String>("nomeLogradouro"));
		this.colunaNumero.setCellValueFactory(new PropertyValueFactory<Aluno,String>("numLogradouro"));
		this.colunaComplemento.setCellValueFactory(new PropertyValueFactory<Aluno,String>("complemento"));
		this.colunaBairro.setCellValueFactory(new PropertyValueFactory<Aluno,String>("bairro"));
		this.colunaCidade.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cidade"));
		this.colunaCEP.setCellValueFactory(new PropertyValueFactory<Aluno,String>("cepFormatado"));
		this.colunaTelefone.setCellValueFactory(new PropertyValueFactory<Aluno,String>("telefoneFormatado"));
		this.colunaCelular.setCellValueFactory(new PropertyValueFactory<Aluno,String>("celularFormatado"));
		this.colunaEmail.setCellValueFactory(new PropertyValueFactory<Aluno,String>("email"));
		this.colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<Aluno,String>("dataNascimento"));
		this.colunaResponsavel.setCellValueFactory(new PropertyValueFactory<Aluno,String>("responsavel"));
		
		//Adiciona eventos aos botï¿½es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnModificar.setOnMouseClicked(this);
		this.btnGerarRelatorioMatriculas.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento ï¿½ tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro ï¿½ selecionado, portanto os botï¿½es podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnGerarRelatorioMatriculas.setDisable(true);
		
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(65));
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90)); 
		/*Define a altura que a tabela terá. Nesse caso, deverá chamar a classe que trata a resolução da tela, para pegar 90% da altura da tela e atribuir
		 * a altura da tabela.
		 */
		
		//Pega 7% do valor da largura da tela e atribui como largura das colunas da tabela
		
		this.colunaBairro.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCelular.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCEP.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCidade.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaCodigo.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaComplemento.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaDataNascimento.setMinWidth(ResolucaoTela.getLargura(7)); //Como "Data de Nascimento" é um nome enoooorme, vamos dar mais 0.3 % para não cortar
		this.colunaEmail.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaEndereco.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaNome.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaNumero.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaTelefone.setMinWidth(ResolucaoTela.getLargura(7));
		this.colunaResponsavel.setMinWidth(ResolucaoTela.getLargura(7));
		
		//Define o nome inicial do ComboBox
		this.cmbFiltrosPesquisa.setPromptText("Critério de pesquisa");
		
		this.cmbFiltrosPesquisa.setMinWidth(ResolucaoTela.getLargura(20));
		
		this.cmbFiltrosPesquisa.setOnMouseClicked(this);
		
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
			this.resultados = Aluno.consultar(); //Consulta todos os registros
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.tabela.getItems().clear();
		this.tabela.setItems(FXCollections.observableArrayList(resultados)); //Adiciona novos resultados na tabela
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCadastrar){
			CadastrarAluno janelaCadastro = new CadastrarAluno();
			janelaCadastro.showAndWait();
			this.preencherTabelaComTodosResultados();
			this.tabela.getSelectionModel().selectLast();
		}
		else if(e.getSource() == this.cmbFiltrosPesquisa){
			this.handle(new Event(this.txtPesquisa, null, null));
		}
		else if(e.getSource() == this.btnExcluir){
			
			if(Alerta.mostrarMensagemSimNao("Você deseja realmente excluir os registros selecionados?", "Confirmação de Exclusão") == false){
				return;
			}
			
			List<Aluno> selecionados = this.tabela.getSelectionModel().getSelectedItems(); //Salva uma lista dos objetos selecionados
				for(Aluno exclusao : selecionados){ //Faz uma pesquisa nessa lista
					if(exclusao.excluir() == false) //Manda cada objeto da lista ser excluso e verifica se deu erro
						Alerta.mostrarMensagemErro("Ocorreu um erro ao excluir o aluno "+exclusao.getNome()+"!","Erro");
				}
				this.tabela.getSelectionModel().clearSelection(); //Limpa as seleï¿½ï¿½es				
				//Apï¿½s a aï¿½ï¿½o, a tabela perde a seleï¿½ï¿½o, portanto os botï¿½es devem ser desativados
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
				this.btnGerarRelatorioMatriculas.setDisable(true);
				this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnModificar){
			ModificarAluno janelaModificacao = new ModificarAluno(this.tabela.getSelectionModel().getSelectedItem());
			janelaModificacao.showAndWait();
			this.preencherTabelaComTodosResultados(); //Atualiza os dados da tabela
			
			this.btnExcluir.setDisable(true);
			this.btnModificar.setDisable(true);
			this.btnGerarRelatorioMatriculas.setDisable(true);
			this.tabela.getSelectionModel().clearSelection(); //Limpa as seleï¿½ï¿½es				
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome estï¿½ vazio
				//Se o txtNome nï¿½o estiver tenta pesquisar Responsï¿½veis com o conteï¿½do digitado
				try {
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Código")){
						this.resultados = Aluno.consultar("cod = "+Integer.parseInt(this.txtPesquisa.getText()));						
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Nome")){
						this.resultados = Aluno.consultar("nome like '%"+this.txtPesquisa.getText()+"%'");					
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Endereço")){
						this.resultados = Aluno.consultar("nomeLogradouro like '%"+this.txtPesquisa.getText()+"%'");						
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Bairro")){
						this.resultados = Aluno.consultar("bairro like '%"+this.txtPesquisa.getText()+"%'");						
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Cidade")){
						this.resultados = Aluno.consultar("cidade like '%"+this.txtPesquisa.getText()+"%'");					
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Data de Nascimento")){
						this.resultados = Aluno.consultar("dataNascimento like '%"+this.txtPesquisa.getText()+"%'");						
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Responsável")){
						List<Aluno> alunosRetornados = new ArrayList<>();
						for(Responsavel responsavel : Responsavel.consultar("nome like '%"+this.txtPesquisa.getText()+"%'")){
							for(Aluno aluno : responsavel.getDependentes()){
								if(aluno.isArquivoMorto() == false){
									alunosRetornados.add(aluno);
								}
							}
						}
						this.resultados = alunosRetornados;						
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
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleï¿½ï¿½o estï¿½ vazia
				//Se estiver bloqueia os botï¿½es
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true);
				this.btnGerarRelatorioMatriculas.setDisable(true);
			}
			else { //Se nï¿½o estiver os botï¿½es serï¿½o desbloqueados, exceto o de Visualizar Matrï¿½culas de Dependentes, pois sï¿½ pode ter um responsï¿½vel selecionado por vez				
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){
					this.btnModificar.setDisable(false);
					this.btnGerarRelatorioMatriculas.setDisable(false);
				}
				else{
					this.btnModificar.setDisable(true);
					this.btnGerarRelatorioMatriculas.setDisable(true);
				}
				this.btnExcluir.setDisable(false);
			}
		}
		else if(e.getSource() == this.btnGerarRelatorioMatriculas){
			
			final TelaCarregamentoRelatorio telaCarregamentoRelatorio = new TelaCarregamentoRelatorio();
			telaCarregamentoRelatorio.show();
			
			Task<Void> taskCarregadorRelatorio = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/OrionMusic?user=root&password=1234");
						JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("reports/MatriculasAluno.jasper");
						
						HashMap<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codAluno", tabela.getSelectionModel().getSelectedItem().getCod());
						
						JasperPrint jp = JasperFillManager.fillReport(jr, parametros, con);
						JasperExportManager.exportReportToPdfFile(jp, "reports/matriculasAluno.pdf");
						
						new AbridorDeJasper(jp,"Matrículas Por Aluno");
						
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