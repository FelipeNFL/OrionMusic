package formularios.teoria.conteudo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import recursos.FolhasEstilo;

import ferramentas.Alerta;
import ferramentas.ResolucaoTela;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class PrincipalConteudo extends GridPane implements EventHandler<MouseEvent>{
	
	private Button btnCarregarConteudo = new Button("Carregar Novo Conteúdo");
	private Button btnExcluir = new Button("Excluir Conteúdo");
	private ListView<String> lstArquivos = new ListView<>();
	
	private Stage stageOwnerParaJanelaDeSelecaoDeArquivos = new Stage();
	
	public void setStageOwner(Stage stage){
		this.stageOwnerParaJanelaDeSelecaoDeArquivos = stage;
	}
	
	public PrincipalConteudo() {
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.setId("principalAcompanhamento");
		
		this.preencherTabelaComTodosResultados();
		
		this.lstArquivos.getItems().remove(".");
		this.lstArquivos.getItems().remove("..");
		
		this.lstArquivos.setMinHeight(ResolucaoTela.getAltura(85));
		this.lstArquivos.setMinWidth(ResolucaoTela.getLargura(77.5));
		
		this.setVgap(15);
		this.setHgap(15);
		
		this.add(this.lstArquivos,0,0);
		this.add(new HBox(15,this.btnCarregarConteudo,this.btnExcluir),0,1);
		
		this.btnCarregarConteudo.setOnMouseClicked(this);
		this.btnExcluir.setOnMouseClicked(this);
		this.lstArquivos.setOnMouseClicked(this);
		
		this.btnExcluir.setDisable(true);
		
		this.preencherTabelaComTodosResultados();
		
		this.lstArquivos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void preencherTabelaComTodosResultados(){
		this.lstArquivos.getItems().clear();
		
		for (String arquivo : new File("conteudos").list()) {
			this.lstArquivos.getItems().add(arquivo);
		}
	}

	@Override
	public void handle(MouseEvent e) {
		if(e.getSource() == this.btnCarregarConteudo){
			FileChooser janelaParaEscolherArquivo = new FileChooser();
			ExtensionFilter filtroDeExtensoes = new ExtensionFilter("*.pdf","*.PDF");
			
			janelaParaEscolherArquivo.getExtensionFilters().add(filtroDeExtensoes);
			
			janelaParaEscolherArquivo.titleProperty().set("Escolha um arquivo para enviar para o site");
			List<File> listaDeArquivosUpdados = janelaParaEscolherArquivo.showOpenMultipleDialog(this.stageOwnerParaJanelaDeSelecaoDeArquivos);
			
			if(listaDeArquivosUpdados.isEmpty() == false){
				for (File arquivoDeOrigem : listaDeArquivosUpdados) {					
					File destino = new File("conteudos/"+arquivoDeOrigem.getName());
					
					FileInputStream fis = null;
					FileOutputStream fos = null;
					
					try {
						fis = new FileInputStream(arquivoDeOrigem);
						fos = new FileOutputStream(destino);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						Alerta.mostrarMensagemSucesso("Erro ao carregar o arquivo \""+arquivoDeOrigem.getName()+"\"!", "Erro ao carregar arquivo");
					}
					
					FileChannel inChannel = fis.getChannel();
					FileChannel outChannel = fos.getChannel();
					
					try {
						outChannel.transferFrom(inChannel, 0, inChannel.size());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			Alerta.mostrarMensagemSucesso("Arquivos carregados com sucesso!", "Sucesso");
			
			this.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnExcluir){
			boolean sucesso = true;
			
			for (String arquivo : this.lstArquivos.getSelectionModel().getSelectedItems()) {
				sucesso = new File("conteudos/"+arquivo).delete();
				if(sucesso == false){
					Alerta.mostrarMensagemErro("Não foi possível excluir o arquivo \""+arquivo+"\"!","Erro ao efetuar exclusão");
				}
			}
			
			if(sucesso){
				Alerta.mostrarMensagemSucesso("Os arquivos foram excluídos com sucesso!", "Exclusão efetuada com sucesso");
			}
			
			this.preencherTabelaComTodosResultados();
			this.btnExcluir.setDisable(true);
		}
		else if(this.lstArquivos == e.getSource()){
			if(e.getClickCount() == 2){
				try {
					Runtime.getRuntime().exec("cmd.exe /c java -jar "+new File("leitorPDF.jar").getAbsolutePath()+" \""+new File("conteudos/"+this.lstArquivos.getSelectionModel().getSelectedItem()).getAbsolutePath()+"\"");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else {
				this.btnExcluir.setDisable(this.lstArquivos.getSelectionModel().isEmpty());
			}
		}
	}
}