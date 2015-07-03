package formularios.teoria.conteudo;

import javax.swing.JFrame;

import com.sun.pdfview.PDFViewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class LeitorPDF extends Application {

	private String caminho;
	
	public LeitorPDF(String caminho) {
		this.caminho = caminho;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		PDFViewer visualizadorPDF = new PDFViewer(true);
		//"conteudos/"+this.lstArquivos.getSelectionModel().getSelectedItem()
		visualizadorPDF.doOpen(this.caminho);
		visualizadorPDF.setVisible(true);
		visualizadorPDF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//visualizadorPDF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
