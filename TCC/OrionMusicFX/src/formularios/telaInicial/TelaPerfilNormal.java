package formularios.telaInicial;

import java.awt.GraphicsEnvironment;

import recursos.FolhasEstilo;
import recursos.Icones;
import ferramentas.ResolucaoTela;
import ferramentas.TarefaLembrete;
import formularios.cadastros.PrincipalCadastros;
import formularios.cronograma.PrincipalCronograma;
import formularios.lembrete.PrincipalLembretes;
import formularios.pesquisa.PrincipalPesquisa;
import formularios.teoria.PrincipalTeoria;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TelaPerfilNormal extends Stage implements EventHandler<MouseEvent> {

	private ImageView iconeAcompanhamento = new ImageView(new Image(Icones.getIconeAcompanhamento()));
	private ImageView iconeCadastro = new ImageView(new Image(Icones.getIconeCadastro()));
	private ImageView iconeCronograma = new ImageView(new Image(Icones.getIconeCronogramaAdministrador()));
	private ImageView iconeTeoria = new ImageView(new Image(Icones.getIconeTeoria()));
	private ImageView iconePesquisar = new ImageView(new Image(Icones.getIconePesquisarAdministrador()));
	private ImageView iconeLogoff = new ImageView(new Image(Icones.getIconeLogoff()));
	
	private ImageView logotipo = new ImageView(new Image(Icones.getCaminhoLogoTelaInicial()));
	
	private Pane layout = new Pane(); // Cria um Gerenciador de Layout
	private Button btnCadastros = new Button(" Cadastros",this.iconeCadastro);
	private Button btnCronograma = new Button(" Cronograma",this.iconeCronograma);
	private Button btnAcompanhamento = new Button(" Lembretes",this.iconeAcompanhamento);
	private Button btnTeoria = new Button(" Teoria",this.iconeTeoria);
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRestaurarDados = new Button("Restauração de Dados");
	private Button btnLogoff = new Button("",this.iconeLogoff);
	
	private PrincipalLembretes telaLembretes = new PrincipalLembretes();
	private PrincipalCadastros telaCadastro = new PrincipalCadastros();
	private PrincipalCronograma telaCronograma = new PrincipalCronograma();
	private PrincipalTeoria telaTeoria = new PrincipalTeoria();
	private PrincipalPesquisa telaPesquisa = new PrincipalPesquisa(false);
	
	private VBox layoutBotaoPesquisa = new VBox();
	
	private boolean logoff;
	
	public boolean fazerLogoff(){
		return this.logoff;
	}
	
	public void resetarLogoff(){
		this.logoff = false;
	}
	
	public TelaPerfilNormal() {
		
		//Define eventos para os botões
		this.btnAcompanhamento.setOnMouseClicked(this);
		this.btnCadastros.setOnMouseClicked(this);
		this.btnCronograma.setOnMouseClicked(this);
		this.btnTeoria.setOnMouseClicked(this);
		this.btnPesquisar.setOnMouseClicked(this);
		this.iconePesquisar.setOnMouseClicked(this);
		this.btnRestaurarDados.setOnMouseClicked(this);
		this.btnLogoff.setOnMouseClicked(this);
		
		this.logotipo.setFitHeight(ResolucaoTela.getAltura(10));
		this.logotipo.setFitWidth(ResolucaoTela.getLargura(40));
		
		this.iconeLogoff.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeLogoff.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeAcompanhamento.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeAcompanhamento.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeCadastro.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeCadastro.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeCronograma.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeCronograma.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeTeoria.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeTeoria.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconePesquisar.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconePesquisar.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.btnLogoff.relocate(ResolucaoTela.getLargura(88), ResolucaoTela.getAltura(0));
		this.btnAcompanhamento.relocate(ResolucaoTela.getLargura(19), ResolucaoTela.getAltura(2));
		this.btnCadastros.relocate(ResolucaoTela.getLargura(72),ResolucaoTela.getAltura(45));
		this.btnCronograma.relocate(ResolucaoTela.getLargura(42),ResolucaoTela.getAltura(38));
		this.btnTeoria.relocate(ResolucaoTela.getLargura(55),ResolucaoTela.getAltura(78));
		this.layoutBotaoPesquisa.relocate(ResolucaoTela.getLargura(8),ResolucaoTela.getAltura(48));
		this.logotipo.relocate(ResolucaoTela.getLargura(4),ResolucaoTela.getAltura(80));
		
		this.layoutBotaoPesquisa.getChildren().addAll(this.iconePesquisar,this.btnPesquisar);
		this.layoutBotaoPesquisa.setAlignment(Pos.CENTER);
		
		this.layout.getChildren().addAll(this.btnLogoff,this.btnAcompanhamento,this.btnCadastros,this.btnCronograma,this.btnTeoria,this.logotipo,this.layoutBotaoPesquisa);
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE)
					fecharForm();
			}
			
		});
		
		this.btnAcompanhamento.setOnMouseEntered(this);
		this.btnCadastros.setOnMouseEntered(this);
		this.btnCronograma.setOnMouseEntered(this);
		this.btnLogoff.setOnMouseEntered(this);
		this.btnPesquisar.setOnMouseEntered(this);
		this.btnTeoria.setOnMouseEntered(this);
		this.iconePesquisar.setOnMouseEntered(this);
		
		this.btnAcompanhamento.setOnMouseExited(this);
		this.btnCadastros.setOnMouseExited(this);
		this.btnCronograma.setOnMouseExited(this);
		this.btnLogoff.setOnMouseExited(this);
		this.btnPesquisar.setOnMouseExited(this);
		this.btnTeoria.setOnMouseExited(this);
		this.iconePesquisar.setOnMouseExited(this);
		
		try {
			Scene scene = new Scene(this.layout); // Cria uma nova cena (Conteúdo de janela), adiciona o gerenciador de layout 
			scene.getStylesheets().add(FolhasEstilo.getCaminhoStyleMainComum()); //Carrega o arquivo CSS que controla o visual da cena
			this.setScene(scene); // primaryStage é o palco (definido lá nos parâmetros), ele é a estrutura de janela que recebe a cena (o conteúdo da janela)

			this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
			this.setTitle("OrionMusic"); //Define um título para a janela
			
			this.setMinHeight(ResolucaoTela.getAltura(60)); // Define um tamanho mínimo para a largura da janela
			this.setMinWidth(ResolucaoTela.getLargura(85)); // Define um tamanho mínimo para a largura da janela
			
		} catch(Exception e) {
			e.printStackTrace(); //Mostra na linha de comando os erros que podem ocorrer na construção da Janela
		}
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		
		this.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				new TarefaLembrete();
			}
		});
	}
	
	//Função constante que serve para que a função de fechar seja acionada no evento em que o ESC é apertado
	private final void fecharForm(){
		this.close();
	}
	
	private void adicionarEfeito(ImageView icone, Button botao){
		icone.setFitHeight(icone.getFitHeight() + ResolucaoTela.getAltura(3));
		icone.setFitWidth(icone.getFitWidth() + ResolucaoTela.getLargura(2));
		Bloom efeitoLuminoso = new Bloom();
		efeitoLuminoso.setThreshold(0.2);
		botao.setEffect(efeitoLuminoso);
	}
	
	private void retirarEfeito(ImageView icone, Button botao){
		icone.setFitHeight(icone.getFitHeight() - ResolucaoTela.getAltura(3));
		icone.setFitWidth(icone.getFitWidth() - ResolucaoTela.getLargura(2));
		botao.setEffect(null);
	}

	@Override
	public void handle(MouseEvent e) {
		if(e.getSource() == this.btnAcompanhamento){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaLembretes.preencherTabelaComTodosResultados();
				this.telaLembretes.showAndWait();
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeAcompanhamento,this.btnAcompanhamento);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeAcompanhamento,this.btnAcompanhamento);
			}
		}
		else if(e.getSource() == this.btnCadastros){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaCadastro.atualizarDados();
				this.telaCadastro.showAndWait();				
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeCadastro,this.btnCadastros);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeCadastro,this.btnCadastros);
			}
		}
		else if(e.getSource() == this.btnCronograma){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaCronograma.atualizarDados();
				this.telaCronograma.showAndWait();				
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeCronograma,this.btnCronograma);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeCronograma,this.btnCronograma);
			}
		}
		else if(e.getSource() == this.btnTeoria){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaTeoria.showAndWait();				
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeTeoria,this.btnTeoria);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeTeoria,this.btnTeoria);
			}
		}
		else if(e.getSource() == this.btnPesquisar || e.getSource() == this.iconePesquisar){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaPesquisa.showAndWait();
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconePesquisar,this.btnPesquisar);
				Bloom efeitoLuminoso = new Bloom();
				efeitoLuminoso.setThreshold(0.2);
				this.iconePesquisar.setEffect(efeitoLuminoso);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconePesquisar,this.btnPesquisar);
				this.iconePesquisar.setEffect(null);
			}
		}
		else if(e.getSource() == this.btnLogoff){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.logoff = true;
				this.close();								
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeLogoff,this.btnLogoff);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeLogoff,this.btnLogoff);
			}
			
		}
	}
}
