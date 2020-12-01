import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {



		Scanner scan = new Scanner(System.in);
		int option = 0;

		while (option != 9) {

			System.out.println(" 1 : Créer un local \n 2 : Créer un examen \n 3 : Encoder l'heure d'un examen \n"
					+ " 4 : Réserver un local pour un examen \n 5 : Visualiser l'horaire d'examens pour un bloc \n 6 : Visualiser toutes les réservations d'un local \n"
					+ " 7 : Visualiser tous les examens qui ne sont pas complètement réservés \n 8 : Visualiser le nombre d'examens non complètement réservés pour chaque bloc \n"
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
				db.creerLocal(nom, place, machine);
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
