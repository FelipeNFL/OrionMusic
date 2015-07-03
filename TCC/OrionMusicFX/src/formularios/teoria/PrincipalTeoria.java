package formularios.teoria;

import java.awt.GraphicsEnvironment;

import recursos.FolhasEstilo;
import recursos.Icones;
import ferramentas.ResolucaoTela;
import formularios.teoria.conteudo.PrincipalConteudo;
import formularios.teoria.figurasMusicais.aClasse.Videos;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalTeoria extends Stage implements EventHandler<Event>{

	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	private Button btnConteudos = new Button("Conte�dos");
	private Button btnVideosDidaticos = new Button("Figuras Musicais");
	private Button btnVoltar = new Button("Voltar");
	
	private PrincipalConteudo telaConteudo = new PrincipalConteudo();
	private Videos figurasMusicais = new Videos();
	
	public void prepararFormConteudo(){
		this.handle(new Event(this.btnConteudos, null, null));
	}
	
	public PrincipalTeoria() {
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(20);
		this.layout.getColumnConstraints().add(coluna0);
		
		ColumnConstraints coluna1 = new ColumnConstraints();
		coluna1.setPercentWidth(80);
		this.layout.getColumnConstraints().add(coluna1);
		
		//Adiciona os componentes ao Layout
		this.layout.add(btnConteudos, 0, 0);
		this.layout.add(btnVideosDidaticos,0,1);
		this.layout.add(btnVoltar, 0, 2);
		
		this.layout.add(telaConteudo, 1, 0, 1, 4);
		this.layout.add(figurasMusicais, 1, 0, 1, 4);
		
		/* O N�MERO DE LINHAS OCUPADAS PELOS FORMUL�RIOS DEVEM SER UM N�MERO MAIOR QUE A QUANTIDADE DE LINHAS OCUPADAS PELOS BOT�ES, PARA QUE
		 * OS FORMS TENHAM UMA LINHA SOBRANDO PARA SE ESTICAR E N�O MODIFICAR A ORGANIZA��O DOS BOT�ES
		 * 
		 * EXEMPLO: SE TIVERMOS 5 BOT�ES, OS FORMS DEVER�O OCUPAR 6 LINHAS
		 */
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		
		//Define os eventos que os bot�es ter�o
		this.btnConteudos.setOnMouseClicked(this);
		this.btnVideosDidaticos.setOnMouseClicked(this);
		this.btnVoltar.setOnMouseClicked(this);
		
		//Define os tamanhos dos bot�es
		this.btnConteudos.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnVideosDidaticos.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnVoltar.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		
		//Define os estilos dos bot�es
		this.btnConteudos.setId("botaoOpcoes");
		this.btnVideosDidaticos.setId("botaoOpcoes");
		this.btnVoltar.setId("botaoOpcoes");
		
		this.setScene(cenaPrincipal); //Define a cena que a janela deve exibir
		
		this.setTitle("Teoria Musical");
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		this.cenaPrincipal.getStylesheets().add(FolhasEstilo.getCaminhoStyleJanelas());
		
		this.handle(new Event(btnConteudos, null, null));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ESCAPE)
							fecharJanela();
					}
		});
	}
	
	private final void fecharJanela(){
		this.close();
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnConteudos){
			this.setTitle("Conte�dos");
			this.figurasMusicais.setVisible(false);
			this.telaConteudo.setVisible(true);
		}
		else if(e.getSource() == this.btnVideosDidaticos){
			this.setTitle("V�deo Did�tico - Figuras Musicais");
			this.figurasMusicais.setVisible(true);
			this.telaConteudo.setVisible(false);
		}
		else if(e.getSource() == this.btnVoltar){
			this.close();
		}
	}
}