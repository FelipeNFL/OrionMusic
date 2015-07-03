package formularios.teoria.figurasMusicais.aClasse;

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

	Media videoSemibreve = new Media(getClass().getResource("../videos/semibreve.mp4").toString());
	Media videoMinima = new Media(getClass().getResource("../videos/minima.mp4").toString());
	Image play = new Image(getClass().getResource("../imagens/btnPlay.png").toString());
	ImageView imagemPlay = new ImageView(play);
	Image pause = new Image(getClass().getResource("../imagens/btnPause.png").toString());
	ImageView imagemPause = new ImageView(pause);
	Image stop = new Image(getClass().getResource("../imagens/btnStop.png").toString());
	ImageView imagemStop = new ImageView(stop);
	Image restart = new Image(getClass().getResource("../imagens/btnRestar.png").toString());
	ImageView imagemRestart = new ImageView(restart);
	Media videoSeminima = new Media(getClass().getResource("../videos/seminima.mp4").toString());
//	Media videoColcheia = new Media(getClass().getResource("/videos/colcheia.mp4").toString());
//	Media videoSemicolcheia = new Media(getClass().getResource("/videos/semicolcheia.mp4").toString());
	MediaPlayer playerDeVideo = new MediaPlayer(videoMinima);
	MediaView visualizadorDeMidia = new MediaView(playerDeVideo);
	HBox caixa = new HBox();
	HBox caixa2 = new HBox();
	
	Button btnMinima = new Button("Minima");
	Button btnSemibreve = new Button("Semibreve");
	Button btnSeminima = new Button("Seminima");
//	Button btnColcheia = new Button("Colcheia");
//	Button btnSemiColcheia = new Button("Semicolcheia");
	
	Duration inicio = Duration.seconds(0);
	Duration fim = Duration.INDEFINITE;
	
	Button btnplay = new Button("Play",imagemPlay);
	Button btnpause = new Button("Pause",imagemPause);
	Button btnstop = new Button("Stop",imagemStop);
	Button btnRestart = new Button("Restart",imagemRestart);
	
//	Button btnVoltar = new Button("Voltar");
//	Button btnNome = new Button("Nome da Figura");
//	Button btnEscritaPositiva = new Button("Escrita Positiva da Figura");
//	Button btnNumero = new Button("Numero da Figura");
//	Button btnEscritaNegativa = new Button("Escrita Negativa da Figura");
	StackPane raiz = new StackPane();
	
	private void mudancaDeVideo(Object video){
		if(video== btnSemibreve){
			playerDeVideo = new MediaPlayer(videoSemibreve);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			//lblNomeDoVideo.setText("SEMIBREVE");
			this.handle(new Event(this.btnplay, null, null));
		}
		if(video == btnMinima){
			playerDeVideo = new MediaPlayer(videoMinima);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			//lblNomeDoVideo.setText("MÍNIMA");
			this.handle(new Event(this.btnplay, null, null));
		}
		if(video == btnSeminima){
			playerDeVideo = new MediaPlayer(videoSeminima);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			//lblNomeDoVideo.setText("SEMÍNIMA");
			this.handle(new Event(this.btnplay, null, null));
		}
	/*	if(video == btnColcheia){
			playerDeVideo = new MediaPlayer(videoColcheia);
			visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			lblNomeDoVideo.setText("COLCHEIA");
		}
		if(video == btnSemicolcheia){
			playerDeVideo = new MediaPlayer(videoSemicolcheia);
				visualizadorDeMidia.setMediaPlayer(playerDeVideo);
			lblNomeDoVideo.setText("SEMICOLCHEIA");
		}
*/	
	}
	
//	private void essesBotoes(boolean podePa){
//		if(!podePa){
//			btnEscritaNegativa.setDisable(true);
//			btnEscritaPositiva.setDisable(true);
//			btnNome.setDisable(true);
//			btnNumero.setDisable(true);
//		}else{
//			btnEscritaNegativa.setDisable(false);
//			btnEscritaPositiva.setDisable(false);
//			btnNome.setDisable(false);
//			btnNumero.setDisable(false);
//		}
//		
//		
//	}
	
	
	public Videos() {
		// TODO Auto-generated constructor stub
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
		
		HBox duracoes = new HBox(15, btnSemibreve,btnMinima,btnSeminima/*,btnColcheia,btnSemiColcheia*/);
		
		this.add(duracoes, 0, 0);
		this.btnSemibreve.setOnMouseClicked(this);
		this.btnMinima.setOnMouseClicked(this);
		this.btnSeminima.setOnMouseClicked(this);
//		this.btnColcheia.setOnMouseClicked(this);
//		this.btnSemiColcheia.setOnMouseClicked(this);
		
		//caixa2.getChildren().add(lblNomeDoVideo);
		
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
		
//		this.add(btnNome, 0,3);
//		btnNome.setOnMouseClicked(this);
//		
//		this.add(btnEscritaPositiva, 1, 3);
//		btnEscritaPositiva.setOnMouseClicked(this);
//		
//		this.add(btnNumero, 2, 3);
//		btnNumero.setOnMouseClicked(this);
//		
//		this.add(btnEscritaNegativa, 3, 3);
//	btnEscritaNegativa.setOnMouseClicked(this);
//		
//		this.add(btnVoltar,4,3);
//	btnVoltar.setOnMouseClicked(this);
		
		//Aquele embelezamento
		this.setAlignment(Pos.CENTER);
		
		playerDeVideo.setCycleCount(Integer.MAX_VALUE);
		playerDeVideo.setStartTime(inicio);
		playerDeVideo.setStopTime(fim);
		
//		btnColcheia.setMinWidth(100);
		btnMinima.setMinWidth(100);
		btnSemibreve.setMinWidth(100);
//		btnSemiColcheia.setMinWidth(100);
		btnSeminima.setMinWidth(100);
		
		btnpause.setMinWidth(100);
		btnplay.setMinWidth(100);
		btnRestart.setMinWidth(100);
		btnstop.setMinWidth(100);
		
		setVgap(5);
		setHgap(5);
	}
	
	@Override
	public void handle(Event event) {
//		if(event.getSource()==btnNome){
//			playerDeVideo.stop();
//			fim = Duration.seconds(3.5);
//			playerDeVideo.setStopTime(fim);
//		 	playerDeVideo.play();
//		 	this.essesBotoes(false);
//		}
//		
//		if(event.getSource()==btnEscritaPositiva){
//			playerDeVideo.stop();
//			fim = Duration.seconds(7);
//			playerDeVideo.setStopTime(fim);
//			playerDeVideo.play();
//			this.essesBotoes(false);
//		}
//		
//		if(event.getSource()==btnEscritaNegativa){
//			playerDeVideo.stop();
//			fim = Duration.seconds(14);
//			playerDeVideo.setStopTime(fim);
//			playerDeVideo.play();
//			this.essesBotoes(false);
//		}
//		if(event.getSource()==btnNumero){
//			playerDeVideo.stop();
//			fim = Duration.seconds(10.5);
//			playerDeVideo.setStopTime(fim);
//			playerDeVideo.play();
//			this.essesBotoes(false);
//		}
//		if(event.getSource()==btnVoltar){
//
//			fim = Duration.INDEFINITE;
//			playerDeVideo.setStopTime(fim);
//			playerDeVideo.stop();
//			playerDeVideo.pause();
//			essesBotoes(true);
//		}
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
