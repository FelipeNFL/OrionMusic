package principal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.sun.pdfview.PDFViewer;

public class PrincipalLeitorPDF {

	public static void main(String[] args) {
		
		List<String> parametros = new ArrayList<>();
		
		for (String parametro : args) {
			parametros.add(parametro);
		}
		
		PDFViewer leitorPDF = new PDFViewer(true);
		leitorPDF.doOpen(parametros.get(0));
		leitorPDF.setExtendedState(JFrame.MAXIMIZED_BOTH);
		leitorPDF.setVisible(true);
	}

}
