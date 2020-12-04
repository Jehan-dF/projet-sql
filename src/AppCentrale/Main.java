package AppCentrale;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		DataBase db = new DataBase();

		Scanner scan = new Scanner(System.in);
		int option = 0;

		while (option != 9) {

			System.out.println(" 1 : Cr�er un local \n 2 : Cr�er un examen \n 3 : Encoder l'heure d'un examen \n"
					+ " 4 : R�server un local pour un examen \n 5 : Visualiser l'horaire d'examens pour un bloc \n 6 : Visualiser toutes les r�servations d'un local \n"
					+ " 7 : Visualiser tous les examens qui ne sont pas compl�tement r�serv�s \n 8 : Visualiser le nombre d'examens non compl�tement r�serv�s pour chaque bloc \n"
					+ " 9 : Quitter l'application");
			System.out.print("Choissisez une option : ");

			option = scan.nextInt();
			
			scan.nextLine();

			switch (option) {
			case 1:
				System.out.println("Insertion d'un local ");
				System.out.println("Nom du local : ");
				String nomL = scan.nextLine();
				System.out.println("Nombre de places du local : ");
				int place = scan.nextInt();
				System.out.println("Le local poss�de t'il des machines ? (true/false) : ");
				boolean machineL = scan.nextBoolean();
				db.creerLocal(nomL, place, machineL);
				System.out.println("\n");
				break;
			case 2:
				System.out.println("Insertion d'un examen ");
				System.out.println("Code de l'examen : ");
				String code = scan.nextLine();
				System.out.println("Bloc de l'examen : ");
				String bloc = scan.nextLine();
				System.out.println("Nom de l'examen : ");
				String nomE = scan.nextLine();
				System.out.println("Dur�e de l'examen : ");
				int duree = scan.nextInt();
				System.out.println("L'examen est il sur machine ? (true/false) : ");
				boolean machineE = scan.nextBoolean();
				db.creerExamen(code, bloc, nomE, duree, machineE);
				System.out.println("\n");
				break;
			case 3:
				System.out.println("Insertion d'une heure de d�but ");
				System.out.println("Code de l'examen : ");
				code = scan.nextLine();
				System.out.print("Heure debut \n(format : aaaa-MM-jj hh:mm:ss ) : ");
				String heureDebut = scan.nextLine();
				db.insererHeureDebut(code,heureDebut);
				System.out.println("\n");
				break;
			case 4:
				System.out.println("Reservation d'un local pour un examen : ");
				System.out.println("Code de l'examen : ");
				String codeEL = scan.nextLine();
				System.out.println("Nom du local : ");
				String nomEL = scan.nextLine();
				db.creerExamenLocal(nomEL, codeEL);
				System.out.println("\n");
				break;
			case 5:
				System.out.println("Visualiser l'horaire d'examens pour un bloc : ");
				System.out.println("Code du bloc : ");
				String codeVH = scan.nextLine();
				System.out.println("code bloc || heure d�but         || code examen || nom examen || nombre locaux r�serv�");
				db.visualiserHoraireBloc(codeVH);
				System.out.println("\n");
				break;
			case 6:
				System.out.println("Visualiser toutes les r�servations d'un local : ");
				System.out.println("Nom du local : ");
				String nomVR = scan.nextLine();
				System.out.println("nom local || heure d�but         || code examen || nom examen ");
				db.visualiserExamenDansLocal(nomVR);
				System.out.println("\n");
				break;
			case 7:
				System.out.println("Visualiser tous les examens non compl�tement r�serv�s : ");
				System.out.println("code examen || nom examen || bloc id || heure de d�but      || dur�e  || sur machine ");
				db.visualiserExamensNonCompletementReserves();
				System.out.println("\n");
				break;
			case 8:
				System.out.println("Visualiser le nombre d'examens non compl�tement r�serv�s pour chaque bloc : ");
				System.out.println("Bloc   || nombre d'examens non r�serv�s " );
				db.visualiserNombreExamensNonReservesParBloc();
				System.out.println("\n");
				break;
			case 9:
				scan.close();
				break;
			}
		}
		
	}
}
