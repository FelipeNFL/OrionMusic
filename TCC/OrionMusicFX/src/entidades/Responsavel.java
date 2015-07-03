package entidades; // Coloca o Responsável no pacote de Entidades

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column; // Importa o @Column para que os atributos sejam reconhecidos pelo como coluna pelo Hibernate
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue; //Importa o @GeneratedValue para que o Hibernate gere os valores da coluna (Gerador de Id)
import javax.persistence.GenerationType; // Importa os tipos de geradores de valores, como aleatórios, ordenados (1,2,3,4...), etc.
import javax.persistence.Id; // Importa o @Id para que os atributos sejam reconhecidos pelo como coluna pelo Hibernate
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import ferramentas.GerenciadorEntidades;

@Entity
public class Responsavel {
	
	/** Classe com informações do Responsável do Aluno **/
	
	@Id // Indica que o atributo será o Código da tabela
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Configura o Id para gerar valores automaticamente (Auto-increment)
	private int cod;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String nome;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String telefone;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String celular;
	
	@Column
	private boolean arquivoMorto = false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Não está achando os alunos, portando o EAGER está carregando-os primeiro
	@JoinColumn(name = "codResponsavel")
	private List<Aluno> dependentes = new ArrayList<Aluno>();
	
	// Cria Getters e Setters para todos os atributos, exceto o código
	
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
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public boolean isArquivoMorto() {
		return arquivoMorto;
	}
	
	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}

	public List<Aluno> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Aluno> dependentes) {
		this.dependentes = dependentes;
	}
	
	//Não apagar daqui para baixo
	
	public String getTelefoneFormatado(){
		if(this.telefone.isEmpty() == false){
			String telefone = "";
			telefone += "(";
			telefone += this.getTelefone().charAt(0);
			telefone += this.getTelefone().charAt(1);
			telefone += ") ";
			telefone += this.getTelefone().charAt(2);
			telefone += this.getTelefone().charAt(3);
			telefone += this.getTelefone().charAt(4);
			telefone += this.getTelefone().charAt(5);
			telefone += "-";
			telefone += this.getTelefone().charAt(5);
			telefone += this.getTelefone().charAt(6);
			telefone += this.getTelefone().charAt(7);
			telefone += this.getTelefone().charAt(8);
			
			return telefone;			
		}
		else {
			return "";
		}
	}
	
	public String getCelularFormatado(){
		if(this.telefone.isEmpty() == false){
			String celular = "";
			celular += "(";
			celular += this.getCelular().charAt(0);
			celular += this.getCelular().charAt(1);
			celular += ") ";
			celular += this.getCelular().charAt(2);
			celular += this.getCelular().charAt(3);
			celular += this.getCelular().charAt(4);
			celular += this.getCelular().charAt(5);
			celular += this.getCelular().charAt(6);
			celular += "-";
			celular += this.getCelular().charAt(7);
			celular += this.getCelular().charAt(8);
			celular += this.getCelular().charAt(9);
			celular += this.getCelular().charAt(10);
			
			return celular;			
		}
		else {
			return "";
		}
	}
	
	public String toString(){
		return this.nome;
	}

	public boolean cadastrar(){
		return GerenciadorEntidades.cadastrar(this);
	}
	

	public boolean modificar(){
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean excluir(){
		this.arquivoMorto = true;
		for(Aluno aluno : this.dependentes){
			aluno.setResponsavel(null);
		}
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Responsavel> consultar() throws Exception{
		List<Responsavel> resultados = GerenciadorEntidades.consultar("from Responsavel where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Responsavel> consultar(String sqlEspecifico) throws Exception{
		List<Responsavel> resultados = GerenciadorEntidades.consultar("from Responsavel where arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Responsavel> consultarArquivoMorto() throws Exception{
		List<Responsavel> resultados = GerenciadorEntidades.consultar("from Responsavel where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Responsavel> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Responsavel> resultados = GerenciadorEntidades.consultar("from Responsavel where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
