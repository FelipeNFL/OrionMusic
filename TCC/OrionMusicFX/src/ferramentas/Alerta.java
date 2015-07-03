package ferramentas;

import java.util.Optional;

import recursos.Icones;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// #################### Tem coisa comentada nos imports ######################

public class Alerta {

	private static Alert alerta = null;

	public static void mostrarMensagemSucesso(String mensagem, String titulo) {
		alerta = new Alert(AlertType.INFORMATION, mensagem, ButtonType.OK);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		Stage teste = (Stage) alerta.getDialogPane().getScene().getWindow();
		teste.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		teste.showAndWait();
	}

	public static void mostrarMensagemAtencao(String mensagem, String titulo) {
		alerta = new Alert(AlertType.WARNING,mensagem, ButtonType.OK);
		alerta.setTitle(titulo); alerta.setHeaderText(null); Stage teste =
		(Stage) alerta.getDialogPane().getScene().getWindow();
		teste.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		teste.showAndWait();
	}

	public static void mostrarMensagemErro(String mensagem, String titulo) {
		alerta = new Alert(AlertType.ERROR, mensagem, ButtonType.OK);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		Stage teste = (Stage) alerta.getDialogPane().getScene().getWindow();
		teste.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		teste.showAndWait();
	}
	
	public static void mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo(){
		mostrarMensagemErro("Voc� esqueceu de preencher o campo indicado!", "Campos Obrigat�rios N�o Preenchidos");
	}

	public static boolean mostrarMensagemSimNao(String mensagem, String titulo) {
		alerta = new Alert(AlertType.CONFIRMATION);
		alerta.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
		alerta.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);

		Optional<ButtonType> resultado = alerta.showAndWait();

		if (resultado.get() == ButtonType.YES) {
			return true;
		} else {
			return false;
		}
	}
}
