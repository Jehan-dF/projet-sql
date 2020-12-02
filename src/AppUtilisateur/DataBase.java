package AppUtilisateur;
import java.sql.*;

public class DataBase {
	
	static PreparedStatement psInsererEtudiant;
	static PreparedStatement psConnexion;
	
	static PreparedStatement psRecupererBlocId;
	
	public DataBase (){
		Connection conn = connectToDb("postgres", "azerty");
		try {
			psInsererEtudiant = conn.prepareStatement("SELECT projetSQL.insererEtudiant(?,?,?,?,?)");
			psConnexion = conn.prepareStatement("SELECT projetSQL.connexion(?)");
			
			psRecupererBlocId = conn.prepareStatement("SELECT b.bloc_id FROM projetSQL.Blocs b WHERE b.code = ?");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	//Connection to dataBase
		private Connection connectToDb(String username, String password) {
			
			Connection conn = null;
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Driver PostgreSQL manquant !");
				System.exit(1);
			}

			String url = "jdbc:postgresql://127.0.0.1:5432/dbu2binDEFOY";

			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				System.out.println("Impossible de joindre le server !");
				System.exit(1);
			}
			
			return conn;
			
		}
		
		public void inscription(String nom, String prenom, String mail, String mdp, String codeBloc) {
			
			String blocId = getIdFromDb(codeBloc, psRecupererBlocId);
			
			try {
				psInsererEtudiant.setString(1, nom);
				psInsererEtudiant.setString(2, prenom);
				psInsererEtudiant.setString(3, mail);
				psInsererEtudiant.setString(4, mdp);
				psInsererEtudiant.setInt(5, Integer.parseInt(blocId));
				psInsererEtudiant.execute();
				System.out.println("Inscription validée, vous pouvez maintenant vous connecter \n");
			} catch (SQLException se) {
				System.out.println("Erreur lors de l’insertion !");
				System.out.println(se.getMessage());
				System.exit(1);
			}
			
		}
		
		public int connexion(String nom, String mdp) {
			
			String sessionId = null;
			
			ResultSet rs=null;
			try {			
				psConnexion.setString(1, nom);
				rs = psConnexion.executeQuery(); {
					while (rs.next()) {
						sessionId = rs.getString(1);
						
					}
				}			
				
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			if(sessionId == null) {
				return 0;
			} else {
				return Integer.parseInt(sessionId);
			}
		}
		
		//Function to omit duplicate code
		//Gets various id's from DB
		private String getIdFromDb(String nom, PreparedStatement statement) {
			
			String id = "";
			
			ResultSet rs=null;
			try {			
				statement.setString(1, nom);
				rs = statement.executeQuery(); {
					while (rs.next()) {
						id += rs.getString(1);
					}
				}			
				
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			return id;
		}

}
