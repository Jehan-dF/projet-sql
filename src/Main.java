
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		DataBase db = new DataBase();

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
				System.out.println("Insertion d'un local ");
				System.out.println("Nom du local : ");
				String nomL = scan.next();
				System.out.println("Nombre de places du local : ");
				int place = scan.nextInt();
				System.out.println("Le local possède t'il des machines ? (true/false) : ");
				boolean machineL = scan.nextBoolean();
				db.creerLocal(nomL, place, machineL);
				break;
			case 2:
				System.out.println("Insertion d'un examen ");
				System.out.println("Code de l'examen : ");
				String code = scan.next();
				System.out.println("Bloc de l'examen : ");
				String bloc = scan.next();
				System.out.println("Nom de l'examen : ");
				String nomE = scan.next();
				System.out.println("Durée de l'examen : ");
				int duree = scan.nextInt();
				System.out.println("L'examen est il sur machine ? (true/false) : ");
				boolean machineE = scan.nextBoolean();
				db.creerExamen(code, bloc, nomE, duree, machineE);
				
				break;
			case 3:
				System.out.println("Insertion d'une heure de début ");
				break;
			case 4:
				System.out.println("Insertion d'un examen dans un local ");
				System.out.println("Code de l'examen : ");
				String codeEL = scan.next();
				System.out.println("Nom du local : ");
				String nomEL = scan.next();
				db.creerExamenLocal(nomEL, codeEL);
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
				scan.close();
				break;
			}
		}
		
	}
}
