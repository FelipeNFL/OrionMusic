package formularios.cronograma.disciplina;

import java.util.List;

import javax.swing.JOptionPane;

import recursos.FolhasEstilo;
import entidades.Disciplina;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;

public class PrincipalDisciplina extends GridPane implements EventHandler<Event> {
	
	private TableColumn<Disciplina, String> colunaDisciplina = new TableColumn<Disciplina,String>("Disciplina");
	private TableView<Disciplina> tabela = new TableView<Disciplina>();
	
	private List<Disciplina> resultados;
	
	private Button btnCriarNova = new Button("Criar Nova");
	private Button btnModificar = new Button("Modificar"); 
	private Button btnExcluir = new Button("Excluir");
	
	public PrincipalDisciplina() {
		
		this.colunaDisciplina.setCellValueFactory(new PropertyValueFactory<Disciplina,String>("nome")); //Define o atributo que a coluna ir� usar
		this.colunaDisciplina.setMinWidth(215); // Define a largura m�nima que a coluna ir� ocupar na tabela 

		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);
		
		RowConstraints linha0 = new RowConstraints();
		linha0.setPercentHeight(95);
		this.getRowConstraints().add(linha0);
		
		RowConstraints linha1 = new RowConstraints();
		linha1.setPercentHeight(5);
		this.getRowConstraints().add(linha1);
		
		this.construirTabela(); //Chama o m�todo que consulta TODOS os dados e preenche a tabela com eles
		
		//Adiciona os bot�es no Layout
		this.add(new HBox(5, this.btnCriarNova,this.btnModificar,this.btnExcluir),0,1);
		
		this.colunaDisciplina.setPrefWidth(ResolucaoTela.getLargura(77.5));
		
		//Adiciona os eventos nos bot�es
		this.btnModificar.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.btnCriarNova.setOnMouseClicked(this);
		
		//No momento em que a janela est� sendo constru�da (Voc� est� no construtor!), n�o h� nada selecionado na tabela
		//Portanto os bot�es de Excluir e Modificar ficam desativados e s� ser�o ativados quando algo ficar selecionado
		this.btnExcluir.setDisable(true);
		this.btnModificar.setDisable(true);
		
		this.setVgap(15); //Define uma dist�ncia vertical de 15 pixels entre cada componente
		this.setHgap(15); //Define uma dist�ncia horizontal de 15 pixels entre cada componente
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.setId("principalCronograma");
	}
	
	@SuppressWarnings("unchecked")
	public void construirTabela(){
		try {
			this.resultados = Disciplina.consultar(); //Consulta todos os dados da Tabela Disciplina e salva na lista de resultados
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela = new TableView<Disciplina>();
		
		this.tabela.getColumns().addAll(this.colunaDisciplina); 
		//Adiciona as colunas criadas na tabela
		
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
		//Inidica que a tabela poder� ter v�rios itens selecionados
		
		this.tabela.setOnMouseClicked(this); 
		//Coloca um evento na tabela, para ficar vigiando as sele��es
		// Pois enquanto n�o haver nada selecionado, os bot�es de excluir e modificar ficar�o desativados

		this.tabela.getItems().setAll(FXCollections.observableArrayList(this.resultados)); // Adiciona a lista de resultados achados na tabela
		
		this.getChildren().remove(tabela);
		
		this.add(this.tabela,0,0,3,1); //Adiciona a tabela no Layout

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

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnCriarNova){
			CadastrarDisciplina criarNova = new CadastrarDisciplina(); //Cria uma nova janela de cria��o de Nivel de Desempenho
			criarNova.initModality(Modality.WINDOW_MODAL); //Define que ela ser� uma janela modal
			criarNova.showAndWait(); //Mostra a janela de cria��o
			this.construirTabela(); // Preenche novamente a tabela para atualizar os dados criados
		}
		else if(e.getSource() == this.btnExcluir){
			//Percorre os itens selecionados
			
			if(Alerta.mostrarMensagemSimNao("Voc� deseja realmente excluir os registros selecionados?", "Confirma��o de Exclus�o") == false){
				return;
			}
			
			for(Disciplina itemParaExcluir : this.tabela.getSelectionModel().getSelectedItems()){
				if(itemParaExcluir.excluir() == false){
					//Se ocorrer um erro ao excluir, mostra uma mensagem de erro
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir os itens selecionados!","Erro",JOptionPane.ERROR_MESSAGE);
					return; //Se der erro, encerra o m�todo para n�o continuar excluindo e mostrando erro
				}
			}
			this.construirTabela();
			this.btnExcluir.setDisable(true);
			this.btnModificar.setDisable(true);
		}
		else if(e.getSource() == this.btnModificar){
			ModificarDisciplina janelaParaModifcar = new ModificarDisciplina(this.tabela.getSelectionModel().getSelectedItem());
			janelaParaModifcar.initModality(Modality.WINDOW_MODAL);
			janelaParaModifcar.showAndWait();
			this.construirTabela();
			this.btnExcluir.setDisable(true);
			this.btnModificar.setDisable(true);
		}
		else if(e.getSource() == this.tabela){
			//Verifica se existe mais de 0 itens selecionados
			if(this.tabela.getSelectionModel().getSelectedItems().size() > 0){
				this.btnExcluir.setDisable(false); //Ativa o bot�o
				
				if(this.tabela.getSelectionModel().getSelectedItems().size() == 1){
					this.btnModificar.setDisable(false); //Se houver apenas um, ativa o bot�o 
				}
				else {
					this.btnModificar.setDisable(true);
				}
			}
			else {
				// Se n�o existir nada selecionado, desativa os bot�es
				this.btnExcluir.setDisable(true); 
				this.btnModificar.setDisable(true); 
			}
		}
	}
}
