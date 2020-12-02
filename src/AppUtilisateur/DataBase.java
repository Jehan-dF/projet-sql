package AppUtilisateur;
import java.sql.*;

import Cryptage.BCrypt;

public class DataBase {
	
	static PreparedStatement psInsererEtudiant;
	static PreparedStatement psConnexion;
	static PreparedStatement psConnexionMdpCrypt;
	static PreparedStatement psListeExamens;
	static PreparedStatement psInsererEtudiantExamen;
	static PreparedStatement psInscriptionTousExamensUnBloc;
	static PreparedStatement psVisualiserHoraireExamens;
	
	static PreparedStatement psRecupererBlocId;
	
	public DataBase (){
		Connection conn = connectToDb("postgres", "azerty");
		try {
			psInsererEtudiant = conn.prepareStatement("SELECT projetSQL.insererEtudiant(?,?,?,?,?)");
			psConnexion = conn.prepareStatement("SELECT projetSQL.connexion(?)");
			psConnexionMdpCrypt = conn.prepareStatement("SELECT projetSQL.connexionMdpCrypt(?)");
			psListeExamens = conn.prepareStatement("SELECT * FROM projetSQL.ListeExamens");
			psInsererEtudiantExamen = conn.prepareStatement("SELECT projetSQL.insererEtudiantExamen(?,?)");
			psInscriptionTousExamensUnBloc = conn.prepareStatement("SELECT projetSQL.inscriptionTousExamensUnBloc(?)");
			psVisualiserHoraireExamens = conn.prepareStatement("SELECT * FROM projetSQL.HoraireExamens WHERE \"Etudiant id\" = ?");
			
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
			
			Boolean mdpCorrect = false;
			String sessionId = null;
			
			ResultSet rs=null;
			
			//Comparison passwords
			try {			
				psConnexionMdpCrypt.setString(1, nom);
				rs = psConnexionMdpCrypt.executeQuery(); {
					while (rs.next()) {
						mdpCorrect = BCrypt.checkpw(mdp,rs.getString(1));						
					}
				}			
				
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			//If correct password : get etudiant_id
			if(mdpCorrect) {
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
			}
			
			//If connection succeed return etudiant_id , else return 0
			if(sessionId == null) {
				return 0;
			} else {
				return Integer.parseInt(sessionId);
			}
		}
		
		public void visualiserExamens() {
			
			ResultSet rs = null;

			try {
				rs = psListeExamens.executeQuery();
				{
					while (rs.next()) {
						System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "\n");
					}
				}
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
						
		}
		
		public void inscriptionExamen(int etudiantId, String codeExamen) {
			
			try {
				psInsererEtudiantExamen.setInt(1, etudiantId);
				psInsererEtudiantExamen.setString(2, codeExamen);
				psInsererEtudiantExamen.execute();
				System.out.println("Inscription à l'examen validée \n");
			} catch (SQLException se) {
				System.out.println("Erreur lors de l’insertion !");
				System.out.println(se.getMessage());
				System.exit(1);
			}
			
		}
		
		public void inscriptionTousExamenBloc(int etudiantId) {
			
			try {
				psInscriptionTousExamensUnBloc.setInt(1, etudiantId);
				psInscriptionTousExamensUnBloc.execute();
				System.out.println("Inscription à tous les examens du bloc validée \n");
			} catch (SQLException se) {
				System.out.println("Erreur lors de l’insertion !");
				System.out.println(se.getMessage());
				System.exit(1);
			}
			
		}
		
		public void visualiserHoraireExamen(int etudiantId) {
						
			ResultSet rs = null;

			try {
				psVisualiserHoraireExamens.setInt(1, etudiantId);
				rs = psVisualiserHoraireExamens.executeQuery();
				{
					while (rs.next()) {
						System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + "\n");
					}
				}
			} catch (SQLException se) {
				System.out.println(se.getMessage());
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
