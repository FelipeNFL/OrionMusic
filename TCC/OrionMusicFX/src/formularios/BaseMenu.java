package formularios;

import ferramentas.ResolucaoTela;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class BaseMenu extends VBox{

	public BaseMenu() {
		//Estilo CSS
		this.getChildren().add(new VBox(30));
		this.setStyle("-fx-background-color: white; -fx-font-size: 40px;");
		this.setSpacing(30);
		this.alignmentProperty().set(Pos.TOP_CENTER);
		this.setMinHeight(ResolucaoTela.getAltura(95));
	}
	
}
