package entidades;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ferramentas.GerenciadorEntidades;

@Entity
public class Matricula {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int cod;
	
	@Column
	private double valorAula;
	
	@Column
	private boolean arquivoMorto = false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codMatricula")
	private List<Lembrete> lembretes;
	
	@ManyToOne
	@JoinColumn(name = "codAluno")
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "codTurma")
	private Turma turma;
	
	@Column
	private String valorFormatado;
	
	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public double getValorAula() {
		return valorAula;
	}

	public void setValorAula(double valorAula) {
		this.valorAula = valorAula;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}

	public List<Lembrete> getLembretes() {
		return lembretes;
	}

	public void setLembretes(List<Lembrete> lembretes) {
		this.lembretes = lembretes;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public void setValorFormatado(String valorFormatado) {
		this.valorFormatado = valorFormatado;
	}
	
	public String getValorFormatado(){
		return this.valorFormatado;
	}

	public String getNomeAluno(){
		return this.aluno.getNome();
	}
	
	public String formatarValor(){
		
		String mascaraDeFormatacao = "";
		int numCaracteres = Double.toString(this.valorAula).length();
		
		for(int i = 0; i < numCaracteres - 3; i++){
			mascaraDeFormatacao += "0";
		}
		
		mascaraDeFormatacao += ".00";
		
		DecimalFormat formatadorDouble = new DecimalFormat(mascaraDeFormatacao);
		return "R$ "+formatadorDouble.format(this.valorAula).replace('.', ',');
	}
	
	//Não apagar daqui para baixo

	public boolean matricular(){
		this.valorFormatado = formatarValor();
		return GerenciadorEntidades.cadastrar(this);
	}
	
	public boolean modificar(){
		this.valorFormatado = formatarValor();
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean excluir(){
		this.arquivoMorto = true;
		for (Lembrete lembrete : lembretes) {
			lembrete.excluir();
		}
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Matricula> consultar() throws Exception{
		List<Matricula> resultados = GerenciadorEntidades.consultar("from Matricula where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Matricula> consultar(String sqlEspecifico) throws Exception{
		List<Matricula> resultados = GerenciadorEntidades.consultar("from Matricula where arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Matricula> consultarArquivoMorto() throws Exception{
		List<Matricula> resultados = GerenciadorEntidades.consultar("from Matricula where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Matricula> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Matricula> resultados = GerenciadorEntidades.consultar("from Matricula where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
