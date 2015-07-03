package formularios.backup.restauracaoDeDados;

import java.util.List;

import recursos.FolhasEstilo;
import entidades.Disciplina;
import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;
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
import javafx.scene.layout.RowConstraints;

public class PrincipalDisciplinaRestauracao extends JanelaMaximizada implements EventHandler<Event> {
	
	private TableColumn<Disciplina, String> colunaDisciplina = new TableColumn<Disciplina,String>("Disciplina");
	private TableView<Disciplina> tabela = new TableView<Disciplina>();
	
	private List<Disciplina> resultados;
	
	private Button btnRestaurar = new Button("Restaurar");
	
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	public PrincipalDisciplinaRestauracao() {
		this.setScene(cena);

		this.colunaDisciplina.setCellValueFactory(new PropertyValueFactory<Disciplina,String>("nome")); //Define o atributo que a coluna irá usar
		this.colunaDisciplina.setMinWidth(215); // Define a largura mínima que a coluna irá ocupar na tabela 

		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		RowConstraints linha0 = new RowConstraints();
		linha0.setPercentHeight(95);
		this.layout.getRowConstraints().add(linha0);
		
		RowConstraints linha1 = new RowConstraints();
		linha1.setPercentHeight(5);
		this.layout.getRowConstraints().add(linha1);
		
		this.construirTabela(); //Chama o método que consulta TODOS os dados e preenche a tabela com eles
		
		//Adiciona os botões no Layout
		this.layout.add(new HBox(5, this.btnRestaurar),0,1);
		
		this.colunaDisciplina.setPrefWidth(ResolucaoTela.getLargura(77.5));
		
		//Adiciona os eventos nos botões
		this.btnRestaurar.setOnMouseClicked(this);
		
		//No momento em que a janela está sendo construída (Você está no construtor!), não há nada selecionado na tabela
		//Portanto os botões de Excluir e Modificar ficam desativados e só serão ativados quando algo ficar selecionado
		this.btnRestaurar.setDisable(true);
		
		this.layout.setVgap(15); //Define uma distância vertical de 15 pixels entre cada componente
		this.layout.setHgap(15); //Define uma distância horizontal de 15 pixels entre cada componente
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.layout.setId("principalCronograma");
		
		this.setTitle("Restauração de Registros - Disciplinas");
	}
	
	@SuppressWarnings("unchecked")
	public void construirTabela(){
		try {
			this.resultados = Disciplina.consultarArquivoMorto(); //Consulta todos os dados da Tabela Disciplina e salva na lista de resultados
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tabela = new TableView<Disciplina>();
		
		this.tabela.getColumns().addAll(this.colunaDisciplina); 
		//Adiciona as colunas criadas na tabela
		
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
		//Inidica que a tabela poderá ter vários itens selecionados
		
		this.tabela.setOnMouseClicked(this); 
		//Coloca um evento na tabela, para ficar vigiando as seleções
		// Pois enquanto não haver nada selecionado, os botões de excluir e modificar ficarão desativados

		this.tabela.getItems().setAll(FXCollections.observableArrayList(this.resultados)); // Adiciona a lista de resultados achados na tabela
		
		this.layout.getChildren().remove(tabela);
		
		this.layout.add(this.tabela,0,0,3,1); //Adiciona a tabela no Layout

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Restauração de Registros");
	}
	
	private final void acionarBotaoExcluir(){
		this.handle(new Event(this.btnRestaurar, null, null));
	}

	@Override
	public void handle(Event e) {
		
		 if(e.getSource() == this.btnRestaurar){
			//Percorre os itens selecionados
			
			if(Alerta.mostrarMensagemSimNao("Você deseja realmente excluir os registros selecionados?", "Confirmação de Exclusão") == false){
				return;
			}
			
			for(Disciplina itemParaExcluir : this.tabela.getSelectionModel().getSelectedItems()){
				if(itemParaExcluir.restaurar() == false){
					//Se ocorrer um erro ao excluir, mostra uma mensagem de erro
					Alerta.mostrarMensagemErro("Ocorreu um erro ao excluir os itens selecionados!","Erro");
					return; //Se der erro, encerra o método para não continuar excluindo e mostrando erro
				}
			}
			this.construirTabela();
			this.btnRestaurar.setDisable(true);
		}
		
		else if(e.getSource() == this.tabela){
			//Verifica se existe mais de 0 itens selecionados
			if(this.tabela.getSelectionModel().getSelectedItems().size() > 0){
				this.btnRestaurar.setDisable(false); //Ativa o botão
				
				
			}
			else {
				// Se não existir nada selecionado, desativa os botões
				this.btnRestaurar.setDisable(true); 
			}
		}
	}
}
