package formularios.cadastros.matricula;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Aluno;
import entidades.Matricula;
import entidades.Turma;
import ferramentas.Alerta;
import ferramentas.MaskTextField;
import ferramentas.ResolucaoTela;
import ferramentas.Validacao;
import formularios.cadastros.aluno.PesquisarAluno;
import formularios.cronograma.turma.PesquisarTurma;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CadastrarMatricula extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//labels
	private Label lblNome = new Label("Nome do Aluno: *");
	private Label lblTurma = new Label("Turma: *");
	private Label lblValor = new Label("Valor: * R$ ");
	
	//buttons
	private Button btnMatricular = new Button("Matrícular");
	private Button btnLimparCampos = new Button("Limpar Campos");
	private Button btnPesquisarAluno = new Button("Pesquisar Aluno");
	private Button btnPesquisarTurma = new Button("Pesquisar Turma");
	
	//TextField
	private TextField txtNome = new TextField();
	private TextField txtTurma = new TextField();
	private MaskTextField txtValor = new MaskTextField();
	private MaskTextField txtCentavos = new MaskTextField();
	
	private Aluno aluno = new Aluno();
	private Turma turma = new Turma();
	
	public CadastrarMatricula() {	
		//Componentes do layout
		
		//1 linha
		this.layout.add(this.lblNome,0,0);
		this.layout.add(this.txtNome, 1,0); 
		this.layout.add(this.btnPesquisarAluno, 2,0);
		
		//3 linha
		this.layout.add(this.lblTurma, 0, 1);
		this.layout.add(this.txtTurma, 1, 1);	
		this.layout.add(this.btnPesquisarTurma, 2,1);
		
		//4 linha
		this.layout.add(this.lblValor, 0, 2);
		this.layout.add(new HBox(5,this.txtValor,new Label(","),this.txtCentavos),1,2);
		this.txtValor.setMask("NNNNNN");
		this.txtValor.setMaxWidth(ResolucaoTela.getLargura(5));
		this.txtCentavos.setMask("NN");
		this.txtCentavos.setMaxWidth(ResolucaoTela.getLargura(3));
		
		this.layout.add(new Label("* Campos Obrigatórios"), 0, 3);
		
		//5 linha
		this.layout.add(this.btnMatricular, 0, 4);
		this.layout.add(this.btnLimparCampos, 1, 4);
		
		this.layout.setHgap(5);
		this.layout.setVgap(5);
		this.layout.setStyle("-fx-padding:5px");
		
		this.txtNome.setEditable(false);
		this.txtTurma.setEditable(false);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.btnMatricular.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		this.btnPesquisarAluno.setOnMouseClicked(this);
		this.btnPesquisarTurma.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal);
		
		this.setResizable(false);
		
		this.setTitle("Matricular Aluno");
		
		this.cenaPrincipal.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		this.layout.setStyle("-fx-padding: 15px");
	}
	
	public CadastrarMatricula(Aluno aluno) {	
		this.aluno = aluno;
		
		//Componentes do layout
		
		//1 linha
		this.layout.add(this.lblNome,0,0);
		this.layout.add(this.txtNome, 1,0); 
		this.layout.add(this.btnPesquisarAluno, 2,0);
		this.txtNome.setText(this.aluno.getNome());
		
		//3 linha
		this.layout.add(this.lblTurma, 0, 1);
		this.layout.add(this.txtTurma, 1, 1);	
		this.layout.add(this.btnPesquisarTurma, 2,1);
		
		//4 linha
		this.layout.add(this.lblValor, 0, 2);
		this.layout.add(this.txtValor, 1, 2);
		
		//5 linha
		this.layout.add(this.btnMatricular, 0, 3);
		this.layout.add(this.btnLimparCampos, 1, 3);
		
		this.layout.setHgap(5);
		this.layout.setVgap(5);
		this.layout.setStyle("-fx-padding:5px");
		
		this.txtNome.setEditable(false);
		this.txtTurma.setEditable(false);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.btnMatricular.setOnMouseClicked(this);
		this.btnLimparCampos.setOnMouseClicked(this);
		this.btnPesquisarAluno.setOnMouseClicked(this);
		this.btnPesquisarTurma.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal);
		
		this.setResizable(false);
		
		this.setTitle("Matricular Aluno");
		
		this.cenaPrincipal.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		this.layout.setStyle("-fx-padding: 15px");
	}
	
	private boolean verificarMatricula(){
		if(txtNome.getText().equals("")){
			this.txtNome.requestFocus();
			this.txtNome.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			return false;
		}else if(txtTurma.getText().equals("")){
			this.txtTurma.requestFocus();
			this.txtTurma.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			return false;
		}else if(txtValor.getText().equals("")){
			this.txtValor.requestFocus();
			this.txtValor.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			Alerta.mostrarMensagemDeEsquecimentoDePreenchimentoDeCampo();
			return false;
		}else{return true;}
	}
	
	@Override
	public void handle(Event e) {
		if(e.getSource()  == this.btnLimparCampos){	
			this.txtNome.setText("");
			this.txtTurma.setText("");
			this.txtValor.setText("");
		}
		else if(e.getSource() == this.btnMatricular){
			if(this.verificarMatricula()){
				Matricula matricula = new Matricula();
				matricula.setAluno(this.aluno);
				matricula.setTurma(this.turma);
				
				try { // Talvez a aula seja de graça #Money #MG 
					if(Validacao.valorVazio(this.txtCentavos.getText())){
						matricula.setValorAula(Double.parseDouble(this.txtValor.getText()+".00"));										
					}
					else {
						matricula.setValorAula(Double.parseDouble(this.txtValor.getText()+"."+this.txtCentavos.getText()));
					}
				}
				catch(Exception erro) {
					erro.printStackTrace();
				}
				
				if(matricula.matricular()){
					Alerta.mostrarMensagemSucesso("A matrícula do aluno "+aluno.getNome()+" foi efetuada com sucesso!", "Matrícula efetuada com sucesso");
				}
				else {
					Alerta.mostrarMensagemSucesso("Ocorreu um erro ao efetuar a matrícula do aluno "+aluno.getNome()+".", "Erro ao efetuar matrícula");
				}
				this.close();				
			}
		}
		else if(e.getSource() == this.btnPesquisarAluno){
			PesquisarAluno telaParaPesquisarAluno = new PesquisarAluno();
			telaParaPesquisarAluno.showAndWait();
			this.aluno = telaParaPesquisarAluno.getAluno();
			this.txtNome.setText(this.aluno.getNome());
		}
		else if(e.getSource() == this.btnPesquisarTurma){
			PesquisarTurma telaParaPesquisaTurma = new PesquisarTurma();
			telaParaPesquisaTurma.showAndWait();
			this.turma = telaParaPesquisaTurma.getTurma();
			this.txtTurma.setText(this.turma.getDescricaoTurma());
		}
	}
}