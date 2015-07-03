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
public class Escola {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod;
	
	@Column
	private String nome;

	@Column
	private String endereco;

	@Column
	private String numEndereco;

	@Column
	private String complemento;

	@Column
	private String bairro;

	@Column
	private String cidade;

	@Column
	private String cep;

	@Column
	private String telefone;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codEscola")
	private List<Turma> turmas = new ArrayList<Turma>();
	
	@Column
	private boolean arquivoMorto = false;
	
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumEndereco() {
		return numEndereco;
	}

	public void setNumEndereco(String numEndereco) {
		this.numEndereco = numEndereco;
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

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public boolean isArquivoMorto() {
		return arquivoMorto;
	}

	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
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
		for (Turma turma : turmas) {
			turma.excluir();
		}
		return GerenciadorEntidades.atualizar(this);
	}
	
	public boolean restaurar(){
		this.arquivoMorto = false;
		return GerenciadorEntidades.atualizar(this);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Escola> consultar() throws Exception{
		List<Escola> resultados = GerenciadorEntidades.consultar("from Escola where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Escola> consultar(String sqlEspecifico) throws Exception{
		List<Escola> resultados = GerenciadorEntidades.consultar("from Escola WHERE arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Escola> consultarArquivoMorto() throws Exception{
		List<Escola> resultados = GerenciadorEntidades.consultar("from Escola where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Escola> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Escola> resultados = GerenciadorEntidades.consultar("from Escola where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
