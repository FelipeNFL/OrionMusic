package ferramentas;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.sql.Connection;


public class GerenciadorEntidades {
	private static EntityManagerFactory fabricaDeGerenciadoresDeEntidades = Persistence.createEntityManagerFactory("UnidadeDePersistenciaPrograma");
	private static EntityManager gerenciadorDeEntidade = fabricaDeGerenciadoresDeEntidades.createEntityManager();
	
	private static void abrirGerenciadorDeEntidade(){
		gerenciadorDeEntidade = fabricaDeGerenciadoresDeEntidades.createEntityManager();
	}
	
	public static void aquecerMotores(){
		abrirGerenciadorDeEntidade();
		GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().begin();			
		GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().commit();
		GerenciadorEntidades.gerenciadorDeEntidade.close();
	}

	public static boolean cadastrar(Object objeto){
		try {
			abrirGerenciadorDeEntidade();
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().begin();			
			GerenciadorEntidades.gerenciadorDeEntidade.persist(objeto);
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().commit();
			GerenciadorEntidades.gerenciadorDeEntidade.close();
			return true;
		}
		catch (Exception erro) {
			erro.printStackTrace();
			return false;
		}
	}
	
	public static boolean atualizar(Object objeto){
		try {
			abrirGerenciadorDeEntidade();
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().begin();			
			GerenciadorEntidades.gerenciadorDeEntidade.merge(objeto);
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().commit();
			GerenciadorEntidades.gerenciadorDeEntidade.close();
			return true;
		}
		catch (Exception erro) {
			erro.printStackTrace();
			return false;
		}
	}
	
	public static Connection cederConexaoJDBC(){
		abrirGerenciadorDeEntidade();
		return HibernateUtils.getJDBCConnectionObject(gerenciadorDeEntidade);
	}
	
	public static boolean executarSQL(String SQL){
		Query query = gerenciadorDeEntidade.createNativeQuery("BEGIN"+SQL+"END;");
		if(query.executeUpdate() != 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static List consultar(String sql) throws Exception{
			abrirGerenciadorDeEntidade();
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().begin();			
		
			Query query = GerenciadorEntidades.gerenciadorDeEntidade.createQuery(sql);
			
			List listaResultados = query.getResultList();
			
			GerenciadorEntidades.gerenciadorDeEntidade.getTransaction().commit();
			GerenciadorEntidades.gerenciadorDeEntidade.close();
			
			return listaResultados;
	}
}
