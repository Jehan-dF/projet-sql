import java.sql.*;


public class DataBase {
	
	static PreparedStatement psInsererLocal;
	static PreparedStatement psInsererExamen;
	static PreparedStatement psInsererHeureDebut;
	static PreparedStatement psInsererExamenLocal;
	static PreparedStatement psVisualiserHoraireExamenBloc;
	static PreparedStatement psVisualiserExamenDansLocal;
	static PreparedStatement psExamensNonCompletementReserves;
	static PreparedStatement psExamensNonReservesParBloc;
	
	//Constructor
	public DataBase () {
		Connection conn = connectToDb("postgres", "azerty");
		try {
			psInsererLocal = conn.prepareStatement("SELECT projetSQL.insererLocal(?,?,?)");
			psInsererExamen = conn.prepareStatement("SELECT projetSQL.insererExamen(?,?,?,?,?)");
			psInsererHeureDebut = conn.prepareStatement("SELECT projetSQL.insererHeureDebut(?,?)");
			psInsererExamenLocal = conn.prepareStatement("SELECT projetSQL.insererExamenLocal(?,?)");
			psVisualiserHoraireExamenBloc = conn.prepareStatement("SELECT * FROM projetSQL.HoraireExamenBloc WHERE \"Code bloc\" = ?");
			psVisualiserExamenDansLocal = conn.prepareStatement("SELECT * FROM projetSQL.ExamenDansLocal WHERE \"Nom local\" = ?");
			psExamensNonCompletementReserves = conn.prepareStatement("SELECT * FROM projetSQL.ExamensNonCompletementReserves");
			psExamensNonReservesParBloc = conn.prepareStatement("SELECT * FROM projetSQL.ExamensNonReservesParBloc WHERE \"Code bloc\" = ?");
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

		String url = "jdbc:postgresql://127.0.0.1:5432/dbu2binDORREKENS";

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Impossible de joindre le server !");
			System.exit(1);
		}
		
		return conn;
		
	}

	public static void creerLocal(String nom, int nbrPlaces, boolean possedeMachine) {
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
	public static void creerExamenLocal(String nomLocal,String codeExamen) {

	}
	public static void creerExamen(String codeExamen, String codeBloc, String nom, int duree, boolean estSurMachine) {
	}
	/*
		ResultSet rs = null;
		try {
			//statement
			try (ResultSet rs = )
			
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}

		
	}*/
}
