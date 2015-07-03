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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ferramentas.GerenciadorEntidades;

@Entity
public class Turma {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int codTurma;
	
	@ManyToOne
	@JoinColumn(name="codEscola")
	private Escola escola;
	
	@ManyToOne
	@JoinColumn(name="codDisciplina")
	private Disciplina disciplina;
	
	@Column
	private String diaSemana;
	
	@Column
	private String horaInicial;
	
	@Column
	private String minutoInicial;
	
	@Column
	private String horaFinal;
	
	@Column
	private String minutoFinal;
	
	@Column
	private String descricaoTurma;
	
	@Column
	private boolean arquivoMorto = false;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codTurma")
	private List<Matricula> matriculas = new ArrayList<Matricula>();

	//Não apagar daqui para baixo!!!
	
	public int getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(int codTurma) {
		this.codTurma = codTurma;
	}

	public Escola getEscola() {
		return escola;
	}

	public void setEscola(Escola escola) {
		this.escola = escola;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = this.formatarNumero(horaInicial);
	}

	public String getMinutoInicial() {
		return minutoInicial;
	}

	public void setMinutoInicial(String minutoInicial) {
		this.minutoInicial = this.formatarNumero(minutoInicial);
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = this.formatarNumero(horaFinal);
	}

	public String getMinutoFinal() {
		return minutoFinal;
	}

	public void setMinutoFinal(String minutoFinal) {
		this.minutoFinal = this.formatarNumero(minutoFinal);
	}

	public String getDescricaoTurma() {
		return descricaoTurma;
	}

	public void setDescricaoTurma(String descricaoTurma) {
		this.descricaoTurma = descricaoTurma;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public String getHorarioInicial(){
		return this.horaInicial+":"+this.minutoInicial;
	}
	
	public String getHorarioFinal(){
		return this.horaFinal+":"+this.minutoFinal;
	}
	
	// Não apagar daqui para baixo
	
	public String getHoraInicialFormatada(){
		return this.horaInicial+":"+this.minutoInicial;
	}
	
	public String getHoraFinalFormatada(){
		return this.horaFinal+":"+this.minutoFinal;
	}
	
	private String formatarNumero(String numero){
		if(Integer.parseInt(numero) < 10)
			return 0+numero;
		else
			return numero;
	}
	
	public String toString(){
		return this.descricaoTurma;
	}

	public boolean criar(){
		return GerenciadorEntidades.cadastrar(this);
	}
	
	public boolean modificar(){
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean excluir(){
		this.arquivoMorto = true;
		for (Matricula matricula : matriculas) {
			matricula.setTurma(null);
		}
		return GerenciadorEntidades.atualizar(this);			
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Turma> consultar() throws Exception{
		List<Turma> resultados = GerenciadorEntidades.consultar("from Turma where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Turma> consultar(String sqlEspecifico) throws Exception{
		List<Turma> resultados = GerenciadorEntidades.consultar("from Turma where arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Turma> consultarArquivoMorto() throws Exception{
		List<Turma> resultados = GerenciadorEntidades.consultar("from Turma where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Turma> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Turma> resultados = GerenciadorEntidades.consultar("from Turma where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}

}
