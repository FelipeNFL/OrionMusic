package entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import ferramentas.GerenciadorEntidades;

@Entity
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod;

	@Column
	private String nome;
	
	@Column
	private boolean arquivoMorto = false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "codDisciplina")
	private List<Turma> turmas = new ArrayList<Turma>();

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	
	// --------------------------------------------------- Não apagar daqui para baixo -----------------------------------------------------------------
	public String toString(){
		return this.nome;
	}

	public boolean cadastrar() {
		return GerenciadorEntidades.cadastrar(this);
	}

	public boolean modificar() {
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
	public static List<Disciplina> consultar() throws Exception {
		List<Disciplina> resultados = GerenciadorEntidades
				.consultar("from Disciplina where arquivoMorto = false");
		return resultados;
	}

	@SuppressWarnings("unchecked")
	public static List<Disciplina> consultar(String sqlEspecifico)
			throws Exception {
		List<Disciplina> resultados = GerenciadorEntidades
				.consultar("from Disciplina where arquivoMorto = false AND " + sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Disciplina> consultarArquivoMorto() throws Exception{
		List<Disciplina> resultados = GerenciadorEntidades.consultar("from Disciplina where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Disciplina> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Disciplina> resultados = GerenciadorEntidades.consultar("from Disciplina where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
