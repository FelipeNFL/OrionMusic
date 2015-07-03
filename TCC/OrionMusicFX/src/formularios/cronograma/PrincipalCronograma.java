package formularios.cronograma;

import java.awt.GraphicsEnvironment;

import recursos.FolhasEstilo;
import recursos.Icones;
import ferramentas.ResolucaoTela;
import formularios.cronograma.cronograma.Cronograma;
import formularios.cronograma.disciplina.PrincipalDisciplina;
import formularios.cronograma.turma.PrincipalTurma;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalCronograma extends Stage implements EventHandler<Event>{
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	private Button btnDisciplina = new Button("Disciplina");
	private Button btnTurma = new Button("Turma");
	private Button btnVisualizarCronograma = new Button("Cronograma");
	private Button btnVoltar = new Button("Voltar");
	
	private PrincipalDisciplina formularioDisciplina = new PrincipalDisciplina();
	private PrincipalTurma formularioTurma = new PrincipalTurma();
	private Cronograma formularioCronograma = new Cronograma();
	
	public void atualizarDados(){
		this.formularioCronograma.construirCronograma();
		this.formularioTurma.preencherTabelaComTodosResultados();
		this.formularioDisciplina.construirTabela();
	}
	
	public void prepararCronogramaParaExibir(){
		this.handle(new Event(this.btnVisualizarCronograma, null, null));
	}
	
	public void prepararDisciplinaParaExibir(){
		this.handle(new Event(this.btnDisciplina, null, null));
	}
	
	public void prepararTurmaParaExibir(){
		this.handle(new Event(this.btnTurma, null, null));
	}
	
	public PrincipalCronograma() {
		this.setScene(cenaPrincipal);
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(20);
		this.layout.getColumnConstraints().add(coluna0);
		
		ColumnConstraints coluna1 = new ColumnConstraints();
		coluna1.setPercentWidth(80);
		this.layout.getColumnConstraints().add(coluna1);
		
		this.layout.add(btnDisciplina, 0, 0);
		this.layout.add(btnTurma, 0, 1);
		this.layout.add(btnVisualizarCronograma, 0, 2);
		this.layout.add(btnVoltar,0,3);
		
		this.layout.add(this.formularioDisciplina, 1, 0,1,5);
		this.layout.add(this.formularioTurma, 1, 0,1,5);
		this.layout.add(this.formularioCronograma, 1, 0,1,5);
		
		this.formularioDisciplina.setVisible(false);
		
		this.btnDisciplina.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnTurma.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnVisualizarCronograma.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnVoltar.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		
		this.btnDisciplina.setOnMouseClicked(this);
		this.btnTurma.setOnMouseClicked(this);
		this.btnVisualizarCronograma.setOnMouseClicked(this);
		this.btnVoltar.setOnMouseClicked(this);
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		
		this.setTitle("Acompanhamento");
		
		this.cenaPrincipal.getStylesheets().add(FolhasEstilo.getCaminhoStyleJanelas());
		
		this.btnDisciplina.setId("botaoOpcoes");
		this.btnTurma.setId("botaoOpcoes");
		this.btnVisualizarCronograma.setId("botaoOpcoes");
		this.btnVoltar.setId("botaoOpcoes");
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));

		this.handle(new Event(btnVisualizarCronograma, null, null));
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnDisciplina){
			this.formularioCronograma.setVisible(false);
			this.formularioTurma.setVisible(false);
			this.formularioDisciplina.setVisible(true);
			
			this.formularioDisciplina.construirTabela();
			
			this.setTitle("Disciplina");
		}
		else if(e.getSource() == this.btnTurma){
			this.formularioCronograma.setVisible(false);
			this.formularioTurma.setVisible(true);
			this.formularioDisciplina.setVisible(false);
			
			this.formularioTurma.preencherTabelaComTodosResultados();
			
			this.setTitle("Turma");
		}
		else if(e.getSource() == this.btnVisualizarCronograma){
			this.formularioCronograma.setVisible(true);
			this.formularioTurma.setVisible(false);
			this.formularioDisciplina.setVisible(false);
			
			this.formularioCronograma.construirCronograma();
			
			this.setTitle("Cronograma");
		}
		else if(e.getSource() == this.btnVoltar){
			this.close();
		}
	}
}
