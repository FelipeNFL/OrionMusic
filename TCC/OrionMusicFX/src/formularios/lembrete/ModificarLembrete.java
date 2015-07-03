package formularios.lembrete;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entidades.Lembrete;
import entidades.Matricula;
import ferramentas.Alerta;
import ferramentas.Validacao;
import formularios.Dialogo;
import formularios.cadastros.matricula.PesquisarMatricula;

public class ModificarLembrete extends Dialogo implements EventHandler<Event> {

	private Label lblMatricula = new Label("Matrícula: *");
	private Label lblAnotacao = new Label("Anotação: *");
	
	private TextField txtMatricula = new TextField();
	private TextArea txtAnotacao = new TextArea();
	
	private Button btnModificar = new Button("Modificar");
	private Button btnProcurarMatricula = new Button("Procurar Matrícula");
	
	private Matricula matricula = new Matricula();
	
	private Lembrete lembrete;
	
	public ModificarLembrete(Lembrete lembrete) {
		this.lembrete = lembrete;
		this.matricula = lembrete.getMatricula();
		this.txtMatricula.setText(this.matricula.getAluno().getNome());
		this.txtAnotacao.setText(this.lembrete.getAnotacoesGerais());
		
		this.layout.add(lblMatricula, 0, 0);
		this.layout.add(txtMatricula, 1, 0);
		this.layout.add(btnProcurarMatricula, 2, 0);
		
		this.layout.add(lblAnotacao, 0, 1);
		this.layout.add(txtAnotacao, 1, 1,2,1);		
		
		this.layout.add(btnModificar, 0, 2);
		this.layout.add(new Label("* Campos Obrigatórios"), 1, 2);
		
		this.txtMatricula.setEditable(false);
		
		this.btnModificar.setOnMouseClicked(this);
		this.btnProcurarMatricula.setOnMouseClicked(this);
	}
	
	private boolean validarCampos(){
		if(Validacao.valorVazio(this.txtMatricula.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			Validacao.realcarCampo(txtMatricula);
			return false;
		}
		else if(Validacao.valorVazio(this.txtAnotacao.getText())){
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			Validacao.realcarCampo(txtAnotacao);
			return false;
		}
		else {
			Validacao.voltarCampoAoNormal(txtAnotacao);
			Validacao.voltarCampoAoNormal(txtMatricula);
			return true;
		}
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnModificar){
			if(validarCampos()){
				this.lembrete.setMatricula(this.matricula);
				this.lembrete.setAnotacoesGerais(this.txtAnotacao.getText());
				this.lembrete.modificar();
				this.close();
			}
		}
		else if(e.getSource() == this.btnProcurarMatricula){
			PesquisarMatricula pesquisarMatricula = new PesquisarMatricula();
			pesquisarMatricula.showAndWait();
			this.matricula = pesquisarMatricula.getMatricula();
			this.txtMatricula.setText(this.matricula.getNomeAluno());
		}
	}
}
