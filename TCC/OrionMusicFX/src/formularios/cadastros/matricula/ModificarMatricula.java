package formularios.cadastros.matricula;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Matricula;
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

public class ModificarMatricula extends Stage implements EventHandler<Event> {
	
	private GridPane layout = new GridPane();
	private Scene cenaPrincipal = new Scene(layout);
	
	//labels
	private Label lblNome = new Label("Nome do Aluno: *");
	private Label lblTurma = new Label("Turma: *");
	private Label lblValor = new Label("Valor: * R$");
	
	//buttons
	private Button btnModificar = new Button("Modificar");
	private Button btnPesquisarAluno = new Button("Pesquisar Aluno");
	private Button btnPesquisarTurma = new Button("Pesquisar Turma");
	
	//TextField
	private TextField txtNome = new TextField();
	private TextField txtTurma = new TextField();
	private MaskTextField txtValor = new MaskTextField();
	private MaskTextField txtCentavos = new MaskTextField();
	
	private Matricula matricula;
	
	public ModificarMatricula(Matricula matricula) {	
		//Componentes do layout
		
		this.matricula = matricula;
		
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
	
		String valorFormatado = this.matricula.getValorFormatado().replace("R$ ", "").replace(",", "").replace(".", "");
		
		this.txtValor.setText(valorFormatado.substring(0, valorFormatado.length() - 2));
		this.txtCentavos.setText(valorFormatado.substring(valorFormatado.length() - 2));
		
		this.layout.add(new Label("* Campos Obrigatórios"), 1, 3);
		
		//5 linha
		this.layout.add(this.btnModificar, 0, 3);
		
		this.layout.setHgap(5);
		this.layout.setVgap(5);
		this.layout.setStyle("-fx-padding:5px");
		
		this.txtNome.setEditable(false);
		this.txtTurma.setEditable(false);
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal()));
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.btnModificar.setOnMouseClicked(this);
		this.btnPesquisarAluno.setOnMouseClicked(this);
		this.btnPesquisarTurma.setOnMouseClicked(this);
		
		this.setScene(cenaPrincipal);
		
		this.setResizable(false);
		
		this.setTitle("Modificação de Matrícula");
		
		this.txtNome.setText(this.matricula.getAluno().getNome());
		try {
			this.txtTurma.setText(this.matricula.getTurma().getDescricaoTurma());			
		}
		catch(Exception e){
			e.printStackTrace();
			//Provavelmente a turma foi excluída e a matrícula ficou sem turma, dando erro ao carregar a turma
			this.txtTurma.setText("");
		}
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
		if(e.getSource() == this.btnModificar){
			if(this.verificarMatricula()){
				try { // Talvez a aula seja de graça #Money #MG 
					if(Validacao.valorVazio(this.txtCentavos.getText())){
						this.matricula.setValorAula(Double.parseDouble(this.txtValor.getText()+".00"));										
					}
					else {
						this.matricula.setValorAula(Double.parseDouble(this.txtValor.getText()+"."+this.txtCentavos.getText()));
					}				
				}
				catch(Exception erro) {
					erro.printStackTrace();
				}
				
				if(matricula.modificar()){
					Alerta.mostrarMensagemSucesso("A matrícula do aluno "+this.matricula.getAluno().getNome()+" foi modificada com sucesso!", "Matrícula efetuada com sucesso");
				}
				else {
					Alerta.mostrarMensagemSucesso("Ocorreu um erro ao modificar a matrícula do aluno "+this.matricula.getAluno().getNome()+".", "Erro ao efetuar matrícula");
				}				
				this.close();
			}
		}
		else if(e.getSource() == this.btnPesquisarAluno){
			PesquisarAluno telaParaPesquisarAluno = new PesquisarAluno();
			telaParaPesquisarAluno.showAndWait();
			this.matricula.setAluno(telaParaPesquisarAluno.getAluno());
			this.txtNome.setText(this.matricula.getAluno().getNome());
		}
		else if(e.getSource() == this.btnPesquisarTurma){
			PesquisarTurma telaParaPesquisarTurma = new PesquisarTurma();
			telaParaPesquisarTurma.showAndWait();
			this.matricula.setTurma(telaParaPesquisarTurma.getTurma());
			this.txtTurma.setText(this.matricula.getAluno().getNome());
		}
	}
}