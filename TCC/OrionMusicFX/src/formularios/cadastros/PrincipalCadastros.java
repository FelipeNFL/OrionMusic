package formularios.cadastros;

import java.awt.GraphicsEnvironment;

import recursos.FolhasEstilo;
import recursos.Icones;
import ferramentas.ResolucaoTela;
import formularios.cadastros.aluno.PrincipalAluno;
import formularios.cadastros.escola.PrincipalEscola;
import formularios.cadastros.matricula.PrincipalMatricula;
import formularios.cadastros.responsavel.PrincipalResponsavel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalCadastros extends Stage implements EventHandler<Event>{

	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	private Button btnAluno = new Button("Aluno");
	private Button btnResponsavel = new Button("Responsável");
	private Button btnEscola = new Button("Escola");
	private Button btnMatricula = new Button("Matrícula");
	private Button btnVoltar = new Button("Voltar");
	private PrincipalResponsavel formularioResponsavel = new PrincipalResponsavel();
	private PrincipalEscola formularioEscola = new PrincipalEscola();
	private PrincipalAluno formularioAluno = new PrincipalAluno();
	private PrincipalMatricula formularioMatricula = new PrincipalMatricula();
	
	public void atualizarDados(){
		this.formularioAluno.preencherTabelaComTodosResultados();
		this.formularioEscola.preencherTabelaComTodosResultados();
		this.formularioMatricula.preencherTabelaComTodosResultados();
		this.formularioResponsavel.preencherTabelaComTodosResultados();
	}
	
	public void prepararParaExibirFormEscola(){
		this.handle(new Event(btnEscola, null, null));
	}
	
	public void prepararParaExibirFormAluno(){
		this.handle(new Event(btnAluno, null, null));
	}
	
	public void prepararParaExibirFormResponsavel(){
		this.handle(new Event(btnResponsavel, null, null));
	}
	
	public void prepararParaExibirFormMatricula(){
		this.handle(new Event(btnMatricula, null, null));
	}
	
	public PrincipalCadastros() {
		//Desativa os formulários inicialmente
		this.formularioAluno.setVisible(false);
		this.formularioEscola.setVisible(false);
		this.formularioResponsavel.setVisible(false);
		
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(20);
		this.layout.getColumnConstraints().add(coluna0);
		
		ColumnConstraints coluna1 = new ColumnConstraints();
		coluna1.setPercentWidth(80);
		this.layout.getColumnConstraints().add(coluna1);
		
		//Adiciona os componentes ao Layout
		this.layout.add(btnAluno, 0, 0);
		this.layout.add(btnResponsavel,0,1);
		this.layout.add(btnEscola,0,2);
		this.layout.add(btnMatricula,0,3);
		this.layout.add(btnVoltar, 0, 4);
		this.layout.add(this.formularioResponsavel, 1,0,1,6);
		this.layout.add(this.formularioEscola, 1,0,1,6);
		this.layout.add(this.formularioAluno, 1,0,1,6);
		this.layout.add(this.formularioMatricula, 1, 0,1,6);
		
		/* O NÚMERO DE LINHAS OCUPADAS PELOS FORMULÁRIOS DEVEM SER UM NÚMERO MAIOR QUE A QUANTIDADE DE LINHAS OCUPADAS PELOS BOTÕES, PARA QUE
		 * OS FORMS TENHAM UMA LINHA SOBRANDO PARA SE ESTICAR E NÃO MODIFICAR A ORGANIZAÇÃO DOS BOTÕES
		 * 
		 * EXEMPLO: SE TIVERMOS 5 BOTÕES, OS FORMS DEVERÃO OCUPAR 6 LINHAS
		 */
		
		this.setMaximized(true);
		this.layout.setPrefSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth(),GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight());
		
		//Define os eventos que os botões terão
		this.btnAluno.setOnMouseClicked(this);
		this.btnEscola.setOnMouseClicked(this);
		this.btnResponsavel.setOnMouseClicked(this);
		this.btnMatricula.setOnMouseClicked(this);
		this.btnVoltar.setOnMouseClicked(this);
		
		//Define os tamanhos dos botões
		this.btnAluno.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnEscola.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnResponsavel.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnMatricula.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		this.btnVoltar.setPrefSize(Double.MAX_VALUE, ResolucaoTela.getAltura(8));
		
		//Define os estilos dos botões
		this.btnAluno.setId("botaoOpcoes");
		this.btnEscola.setId("botaoOpcoes");
		this.btnResponsavel.setId("botaoOpcoes");
		this.btnMatricula.setId("botaoOpcoes");
		this.btnVoltar.setId("botaoOpcoes");
		
		this.setScene(cenaPrincipal); //Define a cena que a janela deve exibir
		
		this.setTitle("Cadastros");
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		this.cenaPrincipal.getStylesheets().add(FolhasEstilo.getCaminhoStyleJanelas());
		
		this.handle(new Event(btnAluno, null, null));
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ESCAPE)
							fecharJanela();
					}
		});
	}
	
	private final void fecharJanela(){
		this.close();
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource() == this.btnAluno){
			this.formularioResponsavel.setVisible(false); //Desativa o responsável
			this.formularioEscola.setVisible(false); //Desativa a escola
			this.formularioAluno.setVisible(true); //Ativa o aluno
			this.formularioMatricula.setVisible(false);
			
			this.setTitle("Aluno");
			this.formularioAluno.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnResponsavel){
			this.formularioResponsavel.setVisible(true); //Ativa apenas o formulário de Responsável
			this.formularioEscola.setVisible(false); //Desativa a escola
			this.formularioAluno.setVisible(false); //Desativa o aluno
			this.formularioMatricula.setVisible(false);
			
			this.setTitle("Responsável");
			this.formularioResponsavel.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnEscola){
			this.formularioResponsavel.setVisible(false); //Desativa o responsavel
			this.formularioEscola.setVisible(true); //Ativa a escola
			this.formularioAluno.setVisible(false); // Desativa o aluno
			this.formularioMatricula.setVisible(false);
			
			this.setTitle("Escola"); //Muda o título do formulário
			this.formularioEscola.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnMatricula){
			this.formularioAluno.setVisible(false);
			this.formularioEscola.setVisible(false);
			this.formularioResponsavel.setVisible(false);
			this.formularioMatricula.setVisible(true);
			
			this.setTitle("Matrícula");
			this.formularioMatricula.preencherTabelaComTodosResultados();
		}
		else if(e.getSource() == this.btnVoltar){
			this.close();
		}
	}

}