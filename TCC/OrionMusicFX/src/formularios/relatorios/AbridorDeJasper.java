package formularios.relatorios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class AbridorDeJasper extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5377652176709440999L;
	private JasperPrint jasperPrint = new JasperPrint();
	private JButton botaoQueAbreRelatorio = new JButton();
	private String titulo = "";

	public AbridorDeJasper(JasperPrint jasperPrint, String titulo) {
		this.getContentPane().add(botaoQueAbreRelatorio);
		this.jasperPrint = jasperPrint;
		this.titulo = titulo;
		
		this.botaoQueAbreRelatorio.addActionListener(this);
		this.actionPerformed(new ActionEvent(botaoQueAbreRelatorio, 0, null));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.botaoQueAbreRelatorio){
			JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
			jasperViewer.setVisible(true);
			jasperViewer.toFront();
			jasperViewer.setTitle(this.titulo);
			jasperViewer.setExtendedState(MAXIMIZED_BOTH);   
		}
	}
}
