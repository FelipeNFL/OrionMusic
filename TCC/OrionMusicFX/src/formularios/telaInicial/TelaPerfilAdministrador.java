package formularios.telaInicial;

import recursos.FolhasEstilo;
import recursos.Icones;
import ferramentas.ResolucaoTela;
import ferramentas.TarefaLembrete;
import formularios.JanelaMaximizada;
import formularios.backup.PrincipalBackup;
import formularios.cadastros.PrincipalCadastros;
import formularios.cronograma.PrincipalCronograma;
import formularios.lembrete.PrincipalLembretes;
import formularios.pesquisa.PrincipalPesquisa;
import formularios.site.PrincipalSite;
import formularios.teoria.PrincipalTeoria;
import formularios.usuarios.PrincipalUsuario;
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
import javafx.stage.WindowEvent;

public class TelaPerfilAdministrador extends JanelaMaximizada implements EventHandler<MouseEvent> {

	private ImageView iconeAcompanhamento = new ImageView(new Image(Icones.getIconeAcompanhamento()));
	private ImageView iconeCadastro = new ImageView(new Image(Icones.getIconeCadastro()));
	private ImageView iconeBackup = new ImageView(new Image(Icones.getIconeBackup()));
	private ImageView iconeCronograma = new ImageView(new Image(Icones.getIconeCronogramaAdministrador()));
	private ImageView iconeTeoria = new ImageView(new Image(Icones.getIconeTeoria()));
	private ImageView iconeUsuarios = new ImageView(new Image(Icones.getIconeUsuarios()));
	private ImageView iconePesquisar = new ImageView(new Image(Icones.getIconePesquisarAdministrador()));
	private ImageView iconeSite = new ImageView(new Image(Icones.getIconeSite()));
	private ImageView iconeLogoff = new ImageView(new Image(Icones.getIconeLogoff()));
	
	private ImageView logotipo = new ImageView(new Image(Icones.getCaminhoLogoTelaInicial()));
	
	private Pane layout = new Pane(); // Cria um Gerenciador de Layout
	private Button btnCadastros = new Button(" Cadastros",this.iconeCadastro);
	private Button btnCronograma = new Button(" Cronograma",this.iconeCronograma);
	private Button btnAcompanhamento = new Button(" Lembretes",this.iconeAcompanhamento);
	private Button btnTeoria = new Button(" Teoria",this.iconeTeoria);
	private Button btnSite = new Button(" Site",this.iconeSite);
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnRestaurarDados = new Button("Restauração de Dados");
	private Button btnBackup = new Button(" Backup",this.iconeBackup);
	private Button btnUsuario = new Button(" Usuários",this.iconeUsuarios);
	private Button btnLogoff = new Button("",this.iconeLogoff);
	
	private PrincipalLembretes telaLembretes = new PrincipalLembretes();
	private PrincipalCadastros telaCadastro = new PrincipalCadastros();
	private PrincipalCronograma telaCronograma = new PrincipalCronograma();
	private PrincipalUsuario telaUsuarios = new PrincipalUsuario();
	private PrincipalTeoria telaTeoria = new PrincipalTeoria();
	private PrincipalBackup telaBackup = new PrincipalBackup();
	private PrincipalSite telaSite = new PrincipalSite();
	private PrincipalPesquisa telaPesquisa = new PrincipalPesquisa(true);
	
	private VBox layoutBotaoPesquisa = new VBox();
	
	private boolean logoff;
	
	public boolean fazerLogoff(){
		return this.logoff;
	}
	
	public void resetarLogoff(){
		this.logoff = false;
	}
	
	public TelaPerfilAdministrador() {
		
		//Define eventos para os botões
		this.btnAcompanhamento.setOnMouseClicked(this);
		this.btnCadastros.setOnMouseClicked(this);
		this.btnCronograma.setOnMouseClicked(this);
		this.btnTeoria.setOnMouseClicked(this);
		this.btnPesquisar.setOnMouseClicked(this);
		this.iconePesquisar.setOnMouseClicked(this);
		this.btnSite.setOnMouseClicked(this);
		this.btnRestaurarDados.setOnMouseClicked(this);
		this.btnBackup.setOnMouseClicked(this);
		this.btnUsuario.setOnMouseClicked(this);
		this.btnLogoff.setOnMouseClicked(this);
		
		this.logotipo.setFitHeight(ResolucaoTela.getAltura(10));
		this.logotipo.setFitWidth(ResolucaoTela.getLargura(40));
		
		this.iconeLogoff.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeLogoff.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeSite.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeSite.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeAcompanhamento.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeAcompanhamento.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeCadastro.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeCadastro.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeBackup.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeBackup.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeCronograma.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeCronograma.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeTeoria.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeTeoria.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconeUsuarios.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconeUsuarios.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.iconePesquisar.setFitHeight(ResolucaoTela.getAltura(10));
		this.iconePesquisar.setFitWidth(ResolucaoTela.getLargura(6));
		
		this.btnLogoff.relocate(ResolucaoTela.getLargura(88), ResolucaoTela.getAltura(0));
		this.btnAcompanhamento.relocate(ResolucaoTela.getLargura(20), ResolucaoTela.getAltura(2));
		this.btnCadastros.relocate(ResolucaoTela.getLargura(47),ResolucaoTela.getAltura(25));
		this.btnCronograma.relocate(ResolucaoTela.getLargura(42),ResolucaoTela.getAltura(37));
		this.btnBackup.relocate(ResolucaoTela.getLargura(73),ResolucaoTela.getAltura(45));
		this.btnTeoria.relocate(ResolucaoTela.getLargura(40),ResolucaoTela.getAltura(51));
		this.layoutBotaoPesquisa.relocate(ResolucaoTela.getLargura(8),ResolucaoTela.getAltura(46));
		this.btnUsuario.relocate(ResolucaoTela.getLargura(57),ResolucaoTela.getAltura(79));
		this.btnSite.relocate(ResolucaoTela.getLargura(72),ResolucaoTela.getAltura(69));
		this.logotipo.relocate(ResolucaoTela.getLargura(4),ResolucaoTela.getAltura(80));
		
		this.layoutBotaoPesquisa.getChildren().addAll(this.iconePesquisar,this.btnPesquisar);
		this.layoutBotaoPesquisa.setAlignment(Pos.CENTER);
		
		this.layout.getChildren().addAll(this.btnLogoff,this.btnAcompanhamento,this.btnCadastros,this.btnBackup,this.btnCronograma,this.btnTeoria,this.btnUsuario,this.btnSite,this.logotipo,this.layoutBotaoPesquisa);
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE)
					fecharForm();
			}
			
		});
		
		this.btnAcompanhamento.setOnMouseEntered(this);
		this.btnBackup.setOnMouseEntered(this);
		this.btnCadastros.setOnMouseEntered(this);
		this.btnCronograma.setOnMouseEntered(this);
		this.btnLogoff.setOnMouseEntered(this);
		this.btnPesquisar.setOnMouseEntered(this);
		this.btnSite.setOnMouseEntered(this);
		this.btnTeoria.setOnMouseEntered(this);
		this.btnUsuario.setOnMouseEntered(this);
		this.iconePesquisar.setOnMouseEntered(this);
		
		this.btnAcompanhamento.setOnMouseExited(this);
		this.btnBackup.setOnMouseExited(this);
		this.btnCadastros.setOnMouseExited(this);
		this.btnCronograma.setOnMouseExited(this);
		this.btnLogoff.setOnMouseExited(this);
		this.btnPesquisar.setOnMouseExited(this);
		this.btnSite.setOnMouseExited(this);
		this.btnTeoria.setOnMouseExited(this);
		this.btnUsuario.setOnMouseExited(this);
		this.iconePesquisar.setOnMouseExited(this);
		
		Scene scene = new Scene(this.layout); // Cria uma nova cena (Conteúdo de janela), adiciona o gerenciador de layout 
		scene.getStylesheets().add(FolhasEstilo.getCaminhoStyleMainAdministrador()); //Carrega o arquivo CSS que controla o visual da cena
		this.setScene(scene); // primaryStage é o palco (definido lá nos parâmetros), ele é a estrutura de janela que recebe a cena (o conteúdo da janela)

		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		this.setTitle("OrionMusic"); //Define um título para a janela
			
		this.setMinHeight(ResolucaoTela.getAltura(60)); // Define um tamanho mínimo para a largura da janela
		this.setMinWidth(ResolucaoTela.getLargura(85)); // Define um tamanho mínimo para a largura da janela
		
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
		else if(e.getSource() == this.btnSite){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaSite.showAndWait();
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeSite,this.btnSite);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeSite,this.btnSite);
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
		else if(e.getSource() == this.btnBackup){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaBackup.showAndWait();
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeBackup,this.btnBackup);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeBackup,this.btnBackup);
			}
		}
		else if(e.getSource() == this.btnUsuario){
			if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
				this.telaUsuarios.preencherTabelaComTodosResultados();
				this.telaUsuarios.showAndWait();
			}
			else if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
				this.adicionarEfeito(this.iconeUsuarios,this.btnUsuario);
			}
			else if(e.getEventType() == MouseEvent.MOUSE_EXITED){
				this.retirarEfeito(this.iconeUsuarios,this.btnUsuario);
			}
		}
	}
}
