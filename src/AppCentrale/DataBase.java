package AppCentrale;
import java.sql.*;

public class DataBase {
	
	static PreparedStatement psInsererLocal;
	static PreparedStatement psInsererExamen;
	static PreparedStatement psInsererHeureDebut;
	static PreparedStatement psInsererExamenLocal;
	static PreparedStatement psVisualiserHoraireExamensBloc;
	static PreparedStatement psVisualiserExamensDansLocal;
	static PreparedStatement psExamensNonCompletementReserves;
	static PreparedStatement psNombreExamensNonReservesParBloc;
	static PreparedStatement psRecupererBlocId;
	static PreparedStatement psRecupererLocalId;
	
	//Constructor
	public DataBase () {
		Connection conn = connectToDb("postgres", "azerty");
		try {
			psInsererLocal = conn.prepareStatement("SELECT projetSQL.insererLocal(?,?,?)");
			psInsererExamen = conn.prepareStatement("SELECT projetSQL.insererExamen(?,?,?,?,?)");
			psInsererHeureDebut = conn.prepareStatement("SELECT projetSQL.insererHeureDebut(?,?)");
			psInsererExamenLocal = conn.prepareStatement("SELECT projetSQL.insererExamenLocal(?,?)");
			psVisualiserHoraireExamensBloc = conn.prepareStatement("SELECT * FROM projetSQL.HoraireExamensBloc WHERE \"Code bloc\" = ?");
			psVisualiserExamensDansLocal = conn.prepareStatement("SELECT * FROM projetSQL.ExamensDansLocal WHERE \"Nom local\" = ?");
			psExamensNonCompletementReserves = conn.prepareStatement("SELECT * FROM projetSQL.ExamensNonCompletementReserves");
			psNombreExamensNonReservesParBloc = conn.prepareStatement("SELECT * FROM projetSQL.NombreExamensNonReservesParBloc WHERE \"Code du bloc\" = ?");
			psRecupererBlocId = conn.prepareStatement("SELECT b.bloc_id FROM projetSQL.Blocs b WHERE b.code = ?");
			psRecupererLocalId = conn.prepareStatement("SELECT l.local_id FROM projetSQL.locaux l WHERE l.nom = ?");
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

	public void creerLocal(String nom, int nbrPlaces, boolean possedeMachine) {
		try {
			psInsererLocal.setString(1, nom);
			psInsererLocal.setInt(2, nbrPlaces);
			psInsererLocal.setBoolean(3, possedeMachine);
			psInsererLocal.execute();
			System.out.println("Local bien ajouté \n");
		} catch (SQLException se) {
			System.out.println("Erreur lors de l’insertion !");
			System.out.println(se.getMessage());
			System.exit(1);
		}

	}
	
	public void creerExamen(String codeExamen, String codeBloc, String nom, int duree, boolean estSurMachine) {
		
		String blocId = getIdFromDb(codeBloc, psRecupererBlocId);

		try {
			psInsererExamen.setString(1, codeExamen);
			psInsererExamen.setInt(2, Integer.parseInt(blocId));
			psInsererExamen.setString(3, nom);
			psInsererExamen.setInt(4, duree);
			psInsererExamen.setBoolean(5, estSurMachine);
			psInsererExamen.execute();
			System.out.println("Examen bien ajouté \n");
		} catch (SQLException se) {
			System.out.println("Erreur lors de l’insertion !");
			System.out.println(se.getMessage());
			System.exit(1);
		}
			
		
	}
	
	public void insererHeureDebut(String codeExamen, String heureDebut) {
		
		Timestamp timestamp = Timestamp.valueOf(heureDebut);
		
		System.out.println(timestamp);
		
		try {
			psInsererHeureDebut.setString(1, codeExamen);
			psInsererHeureDebut.setTimestamp(2, timestamp);
			psInsererHeureDebut.execute();
			System.out.println("Heure de l'examen bien ajoutée \n");
		} catch (SQLException se) {
			System.out.println("Erreur lors de l’insertion !");
			System.out.println(se.getMessage());
			System.exit(1);
		}
		
	}
	
	public void creerExamenLocal(String nomLocal,String codeExamen) {
		
		String localId = getIdFromDb(nomLocal, psRecupererLocalId);
		
		try {
			psInsererExamenLocal.setString(1, codeExamen);
			psInsererExamenLocal.setInt(2, Integer.parseInt(localId));
			psInsererExamenLocal.execute();
			System.out.println("Local bien réservé \n");
		} catch (SQLException se) {
			System.out.println("Erreur lors de l’insertion !");
			System.out.println(se.getMessage());
			System.exit(1);
		}		

	}
	
	public void visualiserHoraireBloc(String codeBloc) {
		
		ResultSet rs = null;

		try {
			psVisualiserHoraireExamensBloc.setString(1, codeBloc);
			rs = psVisualiserHoraireExamensBloc.executeQuery();
			{
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + "\n \n");
				}
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		
	}
	
	public void visualiserExamenDansLocal(String nomLocal) {
		
		ResultSet rs = null;

		try {
			psVisualiserExamensDansLocal.setString(1, nomLocal);
			rs = psVisualiserExamensDansLocal.executeQuery();
			{
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "\n \n");
				}
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		
	}
	
	public void visualiserExamensNonCompletementReserves() {
		
		ResultSet rs = null;

		try {
			rs = psExamensNonCompletementReserves.executeQuery();
			{
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + "\n \n");
				}
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		
	}
	
	public void nombreExamensNonReservesParBloc(String codeBloc) {
		
		ResultSet rs = null;

		try {
			psNombreExamensNonReservesParBloc.setString(1, codeBloc);
			rs = psNombreExamensNonReservesParBloc.executeQuery();
			{
				while (rs.next()) {
					System.out.println(rs.getString(1) + " " + rs.getString(2) + "\n \n");
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
