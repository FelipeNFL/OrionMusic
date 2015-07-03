package entidades; // Coloca o Aluno no pacote de Entidades

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column; // Importa o @Column para que os atributos sejam reconhecidos pelo como coluna pelo Hibernate
import javax.persistence.Entity; // Importa o @Entity para que a classe seja definida como Entidade/Tabela
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue; //Importa o @GeneratedValue para que o Hibernate gere os valores da coluna (Gerador de Id)
import javax.persistence.GenerationType; // Importa os tipos de geradores de valores, como aleatórios, ordenados (1,2,3,4...), etc.
import javax.persistence.Id; // Importa o @Id para que os atributos sejam reconhecidos pelo como coluna pelo Hibernate
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ferramentas.GerenciadorEntidades;

@Entity
public class Aluno {
	
	/** Classe que guardará informações sobre o Aluno **/
	
	@Id // Indica que o atributo será o Código da tabela
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Configura o Id para gerar valores automaticamente (Auto-increment)
	private int cod;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String nome;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String nomeLogradouro;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String numLogradouro;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String complemento;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String bairro;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String cidade;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String cep;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String telefone;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String celular;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String email;
	
	@Column // Indica que o atributo será uma coluna da tabela
	private String dataNascimento;
	
	@Column
	private boolean arquivoMorto = false;
	
	@ManyToOne
	@JoinColumn(name = "codResponsavel")
	private Responsavel responsavel;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codAluno")
	private List<Matricula> matriculas = new ArrayList<Matricula>();

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

	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	public String getNumLogradouro() {
		return numLogradouro;
	}

	public void setNumLogradouro(String numLogradouro) {
		this.numLogradouro = numLogradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public boolean cadastrar(){
		return GerenciadorEntidades.cadastrar(this);
	}
	
	public boolean modificar(){
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean excluir(){
		this.arquivoMorto = true;
		for (Matricula matricula : matriculas) {
			matricula.excluir();
		}
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	//Não apagar daqui para baixo
	
	public String getCepFormatado(){
		if(this.cep.isEmpty() == false){
			String cep = "";
			
			cep += this.cep.charAt(0);
			cep += this.cep.charAt(1);
			cep += this.cep.charAt(2);
			cep += this.cep.charAt(3);
			cep += this.cep.charAt(4);
			cep += "-";
			cep += this.cep.charAt(5);
			cep += this.cep.charAt(6);
			cep += this.cep.charAt(7);
			
			return cep;
		}
		else {
			return "";
		}
	}
	
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
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> consultar() throws Exception{
		List<Aluno> resultados = GerenciadorEntidades.consultar("from Aluno where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> consultar(String sqlEspecifico) throws Exception{
		List<Aluno> resultados = GerenciadorEntidades.consultar("from Aluno where arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> consultarArquivoMorto() throws Exception{
		List<Aluno> resultados = GerenciadorEntidades.consultar("from Aluno where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Aluno> resultados = GerenciadorEntidades.consultar("from Aluno where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
