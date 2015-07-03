package formularios.site;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import recursos.FolhasEstilo;
import ferramentas.Alerta;
import ferramentas.ConexaoFTP;
import ferramentas.ResolucaoTela;
import formularios.JanelaMaximizada;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/* Essa classe herda GridPane pois ela funcionará como um painel que será exibido dentro da Janela PrincipalCadastros */
public class PrincipalSite extends JanelaMaximizada implements EventHandler<MouseEvent>{
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(this.layout);
	private ListView<String> tabela = new ListView<String>();
	private Button btnEnviarNovoConteudo = new Button("Enviar Novo Conteúdo");
	private Button btnExcluir = new Button("Excluir");
	
	public PrincipalSite() {

		this.setScene(this.cenaPrincipal);
		
		this.layout.setHgap(15); // Define uma distância horizontal de 15px entre os componentes do painel
		this.layout.setVgap(15); // Define uma distância vertical de 15 pixels entre os componentes do painel
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.layout.getColumnConstraints().add(coluna0);
		
		//Adiciona os componentes ao Layout
		this.layout.add(this.tabela, 0, 0);
		this.layout.add(new HBox(5, this.btnEnviarNovoConteudo,this.btnExcluir), 0, 1);
		
		this.preencherTabelaComTodosResultados();
		
		//Indica que poderão ser selecionados múltiplos itens
		this.tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Adiciona eventos aos botões
		this.btnEnviarNovoConteudo.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		
		//Adiciona evento à tabela
		this.tabela.setOnMouseClicked(this);
		
		// Inicialmente, nenhum registro é selecionado, portanto os botões podem ficar bloqueados
		this.btnExcluir.setDisable(true);
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());

		this.layout.setId("principalCadastro");
		
		this.tabela.setPrefHeight(ResolucaoTela.getAltura(90));
		this.tabela.setPrefWidth(Double.MAX_VALUE);

		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE){
					acionarBotaoExcluir();
				}
			}
		});
		
		this.setTitle("Site");
	}
	
	private final void acionarBotaoExcluir(){
		this.funcaoExcluir();
	}
	
	private void preencherTabelaComTodosResultados(){
		this.tabela.getItems().clear();
		this.tabela.getItems().addAll(FXCollections.observableArrayList(ConexaoFTP.listarArquivos("/public_html/GALERIA")));
		this.tabela.getItems().remove(".");
		this.tabela.getItems().remove("..");
	}
	
	private void funcaoExcluir(){
		if(Alerta.mostrarMensagemSimNao("Você deseja realmente excluir os arquivos selecionados?", "Confirmação de Exclusão") == false){
			return;
		}
		
		TelaCarregamentoConteudoSite carregamentoExclusao = new TelaCarregamentoConteudoSite(this.tabela.getSelectionModel().getSelectedItems());
		carregamentoExclusao.showAndWait();
		
		this.preencherTabelaComTodosResultados();
		
		this.tabela.getSelectionModel().clearSelection(); //Limpa as seleções				
		//Após a ação, a tabela perde a seleção, portanto os botões devem ser desativados
		this.btnExcluir.setDisable(true);
	}
	
	@Override
	public void handle(MouseEvent e) {
		if(e.getSource() == this.btnEnviarNovoConteudo){
			FileChooser janelaParaEscolherArquivo = new FileChooser();
			ExtensionFilter filtroDeExtensoes = new ExtensionFilter("*.jpg","*.jpeg","*.jpeg","*.JPG","*.JPEG");
			
			janelaParaEscolherArquivo.getExtensionFilters().add(filtroDeExtensoes);
			
			janelaParaEscolherArquivo.titleProperty().set("Escolha um arquivo para enviar para o site");
			List<File> listaDeArquivosUpdados = janelaParaEscolherArquivo.showOpenMultipleDialog(getOwner());
			
			
			if(listaDeArquivosUpdados.isEmpty() == false){
				TelaCarregamentoConteudoSite telaCarregamento = new TelaCarregamentoConteudoSite(listaDeArquivosUpdados);
				telaCarregamento.showAndWait();
				//if()
			}
			
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			this.funcaoExcluir();
		}
		else if(e.getSource() == this.tabela){
			if(e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY){
				try {
					Desktop.getDesktop().browse(new URI("http://orionmusic.esy.es/GALERIA/"+this.tabela.getSelectionModel().getSelectedItem()));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
			if(this.tabela.getSelectionModel().getSelectedItems().isEmpty()){ //Verifica se a seleção está vazia
				//Se estiver bloqueia os botões
				this.btnExcluir.setDisable(true);
			}
			else { //Se não estiver os botões serão desbloqueados, exceto o de Visualizar Matrículas de Dependentes, pois só pode ter um responsável selecionado por vez
				this.btnExcluir.setDisable(false);
			}
		}
	}
}