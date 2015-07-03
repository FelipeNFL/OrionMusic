package formularios;

import java.awt.GraphicsEnvironment;

import recursos.Icones;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JanelaMaximizada extends Stage {
	
	protected GridPane layout = new GridPane();
	
	public JanelaMaximizada() {
		this.setScene(new Scene(layout));
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE){
					close();
				}
			}
		});
	}
}
