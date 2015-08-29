package formularios.teoria.figurasMusicais.aClasse;

import java.io.File;

import ferramentas.ResolucaoTela;
import recursos.FolhasEstilo;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Videos extends GridPane implements EventHandler<Event> {

	private String separadorDePastas = File.separator;
	
	private File arquivoVideoSemibreve = new File("resources"+separadorDePastas+"videos"+separadorDePastas+"semibreve.mp4");
	private File arquivoVideoSeminima = new File("resources"+separadorDePastas+"videos"+separadorDePastas+"seminima.mp4");
	private File arquivoVideoMinima = new File("resources"+separadorDePastas+"videos"+separadorDePastas+"minima.mp4");
	
	private File arquivoImagemPlay = new File("resources"+separadorDePastas+"images"+separadorDePastas+"teoria"+separadorDePastas+"btnPlay.png");
	private File arquivoImagemPause = new File("resources"+separadorDePastas+"images"+separadorDePastas+"teoria"+separadorDePastas+"btnPause.png");
	private File arquivoImagemStop = new File("resources"+separadorDePastas+"images"+separadorDePastas+"teoria"+separadorDePastas+"btnStop.png");
	private File arquivoImagemRestart = new File("resources"+separadorDePastas+"images"+separadorDePastas+"teoria"+separadorDePastas+"btnRestar.png");
	
	private Media videoSemibreve = new Media(this.gerarURL(arquivoVideoSemibreve));
	private Media videoMinima = new Media(this.gerarURL(arquivoVideoMinima));
	private Media videoSeminima = new Media(this.gerarURL(arquivoVideoSeminima));

	private ImageView imagemPlay = new ImageView(new Image(this.gerarURL(arquivoImagemPlay)));
	private ImageView imagemPause = new ImageView(new Image(this.gerarURL(arquivoImagemPause)));
	private ImageView imagemStop = new ImageView(new Image(this.gerarURL(arquivoImagemStop)));
	private ImageView imagemRestart = new ImageView(new Image(this.gerarURL(arquivoImagemRestart)));
	
	private MediaPlayer playerDeVideo = new MediaPlayer(videoMinima);
	private MediaView visualizadorDeMidia = new MediaView(playerDeVideo);
	private HBox caixa = new HBox();
	private HBox caixa2 = new HBox();
	
	private Button btnMinima = new Button("Minima");
	private Button btnSemibreve = new Button("Semibreve");
	private Button btnSeminima = new Button("Seminima");
	
	Duration inicio = Duration.seconds(0);
	Duration fim = Duration.INDEFINITE;
	
	Button btnplay = new Button("Play",imagemPlay);
	Button btnpause = new Button("Pause",imagemPause);
	Button btnstop = new Button("Stop",imagemStop);
	Button btnRestart = new Button("Restart",imagemRestart);

	StackPane raiz = new StackPane();
	
	public Videos() {
		this.imagemPlay.setFitHeight(20);
		this.imagemPlay.setFitWidth(20);
		
		this.imagemPause.setFitHeight(20);
		this.imagemPause.setFitWidth(20);
		
		this.imagemRestart.setFitHeight(20);
		this.imagemRestart.setFitWidth(20);
		
		this.imagemStop.setFitHeight(20);
		this.imagemStop.setFitWidth(20);
		
		
		visualizadorDeMidia.setFitHeight(ResolucaoTela.getAltura(80));
		visualizadorDeMidia.setFitWidth(ResolucaoTela.getLargura(80));
		
		caixa.getChildren().add(btnplay);
		caixa.getChildren().add(btnpause);
		caixa.getChildren().add(btnstop);
		caixa.getChildren().add(btnRestart);
		
		caixa.setSpacing(15);
		
		HBox duracoes = new HBox(15, btnSemibreve,btnMinima,btnSeminima);
		
		this.add(duracoes, 0, 0);
		this.btnSemibreve.setOnMouseClicked(this);
		this.btnMinima.setOnMouseClicked(this);
		this.btnSeminima.setOnMouseClicked(this);
		
		this.add(caixa2, 2, 1,5,1);
		caixa2.setAlignment(Pos.CENTER_LEFT);
		
		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.setId("principalCadastro");
		
		//Adiciona o video ao layout
		raiz.getChildren().add(visualizadorDeMidia);
		this.add(raiz, 0, 2,5,1);
		
		this.add(caixa,0,4,5,1);
		caixa.setAlignment(Pos.CENTER);

		btnplay.setOnMouseClicked(this);
		btnstop.setOnMouseClicked(this);
		btnpause.setOnMouseClicked(this);
		btnRestart.setOnMouseClicked(this);
		
		//Aquele embelezamento
		this.setAlignment(Pos.CENTER);
		
		playerDeVideo.setCycleCount(Integer.MAX_VALUE);
		playerDeVideo.setStartTime(inicio);
		playerDeVideo.setStopTime(fim);
		
		btnMinima.setMinWidth(100);
		btnSemibreve.setMinWidth(100);
		btnSeminima.setMinWidth(100);
		
		btnpause.setMinWidth(100);
		btnplay.setMinWidth(100);
		btnRestart.setMinWidth(100);
		btnstop.setMinWidth(100);
		
		setVgap(5);
		setHgap(5);
	}
	
	private String gerarURL(File arquivo){
		return "file:///" + arquivo.getAbsolutePath().replace(separadorDePastas, "/");
	}
	
	private void mudancaDeVideo(Object video){
		if(video== btnSemibreve){
			playerDeVideo = new MediaPlayer(videoSemibreve);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			this.handle(new Event(this.btnplay, null, null));
		}
		if(video == btnMinima){
			playerDeVideo = new MediaPlayer(videoMinima);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			this.handle(new Event(this.btnplay, null, null));
		}
		if(video == btnSeminima){
			playerDeVideo = new MediaPlayer(videoSeminima);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			this.handle(new Event(this.btnplay, null, null));
		}
	}
	
	@Override
	public void handle(Event event) {
		mudancaDeVideo(event.getSource());

		if(event.getSource()==btnplay){
			playerDeVideo.play();
		}
		if(event.getSource()==btnpause){
			playerDeVideo.pause();
		}
		if(event.getSource()==btnstop){
			playerDeVideo.stop();
		}
		if(event.getSource()==btnRestart){
			playerDeVideo.stop();
			playerDeVideo.play();
		}	
	}
}
