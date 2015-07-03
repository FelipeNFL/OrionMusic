package formularios.cadastros.aluno;

import java.util.ArrayList;
import java.util.List;

import recursos.FolhasEstilo;
import entidades.Aluno;
import entidades.Responsavel;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;

/* Essa classe herda GridPane pois ela funcionar� como um painel que ser� exibido dentro da Janela PrincipalCadastros */
public class PesquisarAluno extends JanelaMaximizada implements EventHandler<Event>{
	
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
	private TableColumn<Aluno, String> colunaResponsavel = new TableColumn<>("Respons�vel"); //Cria a coluna data de nascimento
	private List<Aluno> resultados; //Cria uma lista para guardar os alunos a serem exibidos
	private ComboBox<String> cmbFiltrosPesquisa = new ComboBox<>(FXCollections.observableArrayList("C�digo","Nome","Endere�o","Bairro","Cidade","Data de Nascimento","Respons�vel"));
	private TextField txtPesquisa = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnSelecionar = new Button("Selecionar");
	private Aluno alunoSelecionado;
	
	public Aluno getAluno(){
		return this.alunoSelecionado;
	}
	
	@SuppressWarnings("unchecked")
	public PesquisarAluno() {
		
		this.layout.setHgap(15); // Define uma dist�ncia horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma dist�ncia vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		HBox linhaPesquisa = new HBox(this.cmbFiltrosPesquisa,this.txtPesquisa);
		linhaPesquisa.setSpacing(5);
		
		HBox linhaBotoes = new HBox(this.btnSelecionar,this.btnCadastrar);
		linhaBotoes.setSpacing(5);
		
		//Adiciona os componentes ao Layout
		this.layout.add(linhaPesquisa, 0, 0);
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
		
		//Indica para as colunas com qual atributo do Respons�vel elas devem ser preenchidas
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
		
		//Adiciona eventos aos bot�es
		this.btnCadastrar.setOnMouseClicked(this);
		this.btnSelecionar.setOnMouseClicked(this);
		
		//Adiciona eventos as caixas de texto
		this.txtPesquisa.setOnKeyReleased(this);
		
		//Adiciona evento � tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro � selecionado, portanto os bot�es podem ficar bloqueados
		this.btnSelecionar.setDisable(true);
		
		this.txtPesquisa.setPrefWidth(ResolucaoTela.getLargura(65));
		
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
		
		//Define o nome inicial do ComboBox
		this.cmbFiltrosPesquisa.setPromptText("Crit�rio de pesquisa");
		
		this.cmbFiltrosPesquisa.setMinWidth(ResolucaoTela.getLargura(20));
		
		this.cmbFiltrosPesquisa.setOnMouseClicked(this);
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Pesquisar Aluno");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnSelecionar, null, null));
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
		else if(e.getSource() == this.btnSelecionar){
			this.alunoSelecionado = this.tabela.getSelectionModel().getSelectedItem();
			this.close();
		}
		else if(e.getSource() == this.txtPesquisa){ // Se o txtNome for acionado
			if(this.txtPesquisa.getText().isEmpty() == false){ //Verifica se o txtNome est� vazio
				//Se o txtNome n�o estiver tenta pesquisar Respons�veis com o conte�do digitado
				try {
					if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("C�digo")){
						this.resultados = Aluno.consultar("cod = "+Integer.parseInt(this.txtPesquisa.getText()));						
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Nome")){
						this.resultados = Aluno.consultar("nome like '%"+this.txtPesquisa.getText()+"%'");					
					}
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Endere�o")){
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
					else if(this.cmbFiltrosPesquisa.getSelectionModel().getSelectedItem().toString().equals("Respons�vel")){
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
			this.btnSelecionar.setDisable(this.tabela.getSelectionModel().isEmpty());
		}
	}
}