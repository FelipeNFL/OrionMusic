package entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ferramentas.GerenciadorEntidades;

@Entity
public class Lembrete {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cod;
	
	@ManyToOne
	@JoinColumn(name = "codMatricula")
	private Matricula matricula;
	
	@Column
	private String anotacoesGerais;
	
	@Column 
	private boolean arquivoMorto = false;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public String getAnotacoesGerais() {
		return anotacoesGerais;
	}

	public void setAnotacoesGerais(String anotacoesGerais) {
		this.anotacoesGerais = anotacoesGerais;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}
	
	// Não apagar daqui para baixo
	
	public String getNomeAluno(){
		return this.matricula.getNomeAluno();
	}
	
	public String getDescricaoTurma(){
		return this.matricula.getTurma().getDescricaoTurma();
	}

	public boolean cadastrar(){
		return GerenciadorEntidades.cadastrar(this);
	}
	
	public boolean modificar(){
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean excluir(){
		this.arquivoMorto = true;
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Lembrete> consultar() throws Exception{
		List<Lembrete> resultados = GerenciadorEntidades.consultar("from Lembrete where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Lembrete> consultar(String sqlEspecifico) throws Exception{
		List<Lembrete> resultados = GerenciadorEntidades.consultar("from Lembrete WHERE arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Lembrete> consultarArquivoMorto() throws Exception{
		List<Lembrete> resultados = GerenciadorEntidades.consultar("from Lembrete where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Lembrete> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Lembrete> resultados = GerenciadorEntidades.consultar("from Lembrete where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}