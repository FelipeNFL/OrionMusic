package formularios;

import java.io.File;

import recursos.FolhasEstilo;
import recursos.Icones;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class TelaCarregamento extends Stage {
	
	private String caminhoVideo = "file:///"+new File("resources/images/telaCarregamento.mp4").getAbsolutePath().replace("\\","/");
	private Media video = new Media(caminhoVideo);
	protected MediaPlayer videoCarregamento = new MediaPlayer(video);
	protected MediaView visualizador = new MediaView(videoCarregamento);
	protected VBox layout = new VBox();
	protected Label lblStatus = new Label("");
	
	public TelaCarregamento() {
		
		this.setScene(new Scene(layout));
		this.layout.getChildren().add(visualizador);
		this.layout.getChildren().add(lblStatus);
		this.layout.setAlignment(Pos.CENTER);
		this.initStyle(StageStyle.UNDECORATED);
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleTelaCarregamento());
		
		this.visualizador.setFitWidth(600);
		this.visualizador.setFitHeight(340);
		
		this.videoCarregamento.play();
		
		this.videoCarregamento.setCycleCount(MediaPlayer.INDEFINITE);
		
		this.getScene().getStylesheets().add(FolhasEstilo.getCaminhoStyleTelaCarregamento());
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				videoCarregamento.stop();
			}
		});
	}
}
