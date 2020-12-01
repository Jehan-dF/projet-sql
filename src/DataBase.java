import java.sql.*;
import java.util.Scanner;

public class DataBase {
	private static Connection conn = null;

	public static void creerLocal(String nom, int nbrPlaces, boolean possedeMachine) {
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO" + " projetSQL.locaux VALUES (DEFAULT, ?, ?, ?);");
			ps.setString(1, nom);
			ps.setInt(2, nbrPlaces);
			ps.setBoolean(3, possedeMachine);
			ps.executeUpdate();
		} catch (SQLException se) {
			System.out.println("Erreur lors de l’insertion !");
			System.out.println(se.getMessage());
			System.exit(1);
		}

	}
	
	public static void creerExamen(String codeExamen, String codeBloc, String nom, int duree, boolean estSurMachine) {
		
		ResultSet rs = null;
		try {
			//statement
			try (ResultSet rs = )
			
		} catch (SQLException se) {
			 se.printStackTrace();
		}

		
	}

	public static void main(String[] args) {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver PostgreSQL manquant !");
			System.exit(1);
		}

		String url = "jdbc:postgresql://127.0.0.1:5432/dbu2binDEFOY";

		try {
			conn = DriverManager.getConnection(url, "postgres", "@Ballon#2");
		} catch (SQLException e) {
			System.out.println("Impossible de joindre le server !");
			System.exit(1);
		}

		Scanner scan = new Scanner(System.in);
		int option = 0;

		while (option != 9) {

			System.out.println(" 1 : Créer un local \n 2 : Créer un examen \n 3 : Encoder l'heure d'un examen \n"
					+ " 4 : Réserver un local pour un examen \n 5 : Visualiser l'horaire d'examens pour un bloc \n 6 : Visualiser toutes les réservations d'un local \n"
					+ " 7 : Visualiser tous les examens qui ne sont pas complètement réservés \n 8 : Visualiser le nombre d'examens non complètement réservés pour chaque bloc"
					+ " 9 : Quitter l'application");
			System.out.print("Choissisez une option : ");

			option = scan.nextInt();

			switch (option) {
			case 1:
				System.out.println("création d'un local ");
				System.out.println("Nom du local : ");
				String nom = scan.next();
				System.out.println("Nombre de places : ");
				int place = scan.nextInt();
				System.out.println("Possède des machines (true/false) : ");
				boolean machine = scan.nextBoolean();
				creerLocal(nom, place, machine);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			}
		}
	}
}
