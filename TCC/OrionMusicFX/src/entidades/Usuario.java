package entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ferramentas.GerenciadorEntidades;

@Entity
public class Usuario {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int codUsuario;
	
	@Column
	private String nome;
	
	@Column
	private String login;
	
	@Column
	private String senha;
	
	@Column
	private String perguntaSecreta;
	
	@Column
	private String respostaSecreta;
	
	@Column
	private String perfil;
	
	@Column
	private boolean arquivoMorto;
	
	public int getCodUsuario() {
		return codUsuario;
	}
	
	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getPerguntaSecreta() {
		return perguntaSecreta;
	}
	
	public void setPerguntaSecreta(String perguntaSecreta) {
		this.perguntaSecreta = perguntaSecreta;
	}
	
	public String getRespostaSecreta() {
		return respostaSecreta;
	}
	
	public void setRespostaSecreta(String respostaSecreta) {
		this.respostaSecreta = respostaSecreta;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public boolean isArquivoMorto() {
		return arquivoMorto;
	}
	
	public void setArquivoMorto(boolean arquivoMorto) {
		this.arquivoMorto = arquivoMorto;
	}
	
	
	//Não apagar daqui para baixo


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
	public static List<Usuario> consultar() throws Exception{
		List<Usuario> resultados = GerenciadorEntidades.consultar("from Usuario where arquivoMorto = false");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Usuario> consultar(String sqlEspecifico) throws Exception{
		List<Usuario> resultados = GerenciadorEntidades.consultar("from Usuario where arquivoMorto = false AND "+sqlEspecifico);
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Usuario> consultarArquivoMorto() throws Exception{
		List<Usuario> resultados = GerenciadorEntidades.consultar("from Usuario where arquivoMorto = true");
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Usuario> consultarArquivoMorto(String sqlEspecifico) throws Exception{
		List<Usuario> resultados = GerenciadorEntidades.consultar("from Usuario where arquivoMorto = true AND "+sqlEspecifico);
		return resultados;
	}
}
