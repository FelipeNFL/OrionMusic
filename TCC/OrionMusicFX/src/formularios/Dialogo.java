package formularios;

import recursos.FolhasEstilo;
import recursos.Icones;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Dialogo extends Stage {
	
	protected GridPane layout = new GridPane();
	
	public Dialogo() {
		this.setScene(new Scene(layout));

		this.setResizable(false);
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE){
					close();
				}
			}
		});
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
	}
}
