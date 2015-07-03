package formularios.cronograma.disciplina;

import recursos.FolhasEstilo;
import recursos.Icones;
import entidades.Disciplina;
import ferramentas.Alerta;
import ferramentas.Validacao;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class CadastrarDisciplina extends Stage implements EventHandler<Event>{

	private Label lblNomeDisciplina = new Label("Nome da Disciplina: *");
	private TextField txtNomeDisciplina = new TextField();
	private Button btnCadastrar = new Button("Cadastrar");
	
	private GridPane layout = new GridPane();
	private Scene cena = new Scene(layout);
	
	private Disciplina disciplinaCadastrada = new Disciplina();
	
	public Disciplina getDisciplina(){
		return this.disciplinaCadastrada;
	}
	
	public CadastrarDisciplina() {
		
		this.layout.add(lblNomeDisciplina, 0, 0);
		this.layout.add(txtNomeDisciplina, 0, 1);
		this.layout.add(new Label("* Campos Obrigatórios"),0,2);
		this.layout.add(btnCadastrar, 0, 3);
		
		this.setScene(cena);
		
		this.btnCadastrar.setOnMouseClicked(this);
		
		this.layout.setHgap(15); //Define uma distância horizontal de 15 pixels entre cada componente
		this.layout.setVgap(15); //Define uma distância vertical de 15 pixels entre cada componente
		
		this.getIcons().add(new Image(Icones.getCaminhoIconePrincipal())); // Adiciona um ícone ao palco (Estrutura de janela)
		
		this.layout.getStylesheets().add(FolhasEstilo.getCaminhoStyleDialogo());
		
		this.setResizable(false);
		
		this.setTitle("Cadastrar");
		
		this.layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					acionarBotaoCadastro();
				}
				else if(event.getCode() == KeyCode.ESCAPE){
					close();
				}
			}
		});
	}
	
	private boolean validar(){
		
		boolean existeDisciplinaComOMesmoNome = false;
		
		try {
			existeDisciplinaComOMesmoNome = Disciplina.consultar("nome = '"+this.txtNomeDisciplina.getText()+"'").isEmpty() == false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(Validacao.valorVazio(this.txtNomeDisciplina.getText())){
			Alerta.mostrarMensagemErro("O campo Nome da Disciplina não pode ficar em branco!", "Campos Obrigatórios Não Preenchidos");
			txtNomeDisciplina.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else if(existeDisciplinaComOMesmoNome) {
			Alerta.mostrarMensagemErro("Já existe uma Disciplina com o nome digitado!", "Disciplina Já Existente");
			txtNomeDisciplina.setStyle("-fx-border-color:red; -fx-border-width: 2px;");
			return false;
		}
		else {
			return true;
		}
	}
	
	private final void acionarBotaoCadastro(){
		this.handle(new Event(this.btnCadastrar,null,null));
	}

	@Override
	public void handle(Event e) {
		if(e.getSource() == btnCadastrar){
			if(this.validar()){
				Disciplina disciplina = new Disciplina();
				disciplina.setNome(txtNomeDisciplina.getText());
				disciplina.cadastrar();
				this.disciplinaCadastrada = disciplina;
				this.close();
			}
		}
	}
}
