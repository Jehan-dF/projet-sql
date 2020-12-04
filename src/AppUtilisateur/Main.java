package AppUtilisateur;

import java.util.Scanner;

import Cryptage.BCrypt;

public class Main {

	public static void main(String[] args) {

		DataBase db = new DataBase();

		Scanner scan = new Scanner(System.in);
		int option = 0;

		boolean connected = false;
		int sessionId = 0;

		while (!connected) {
			
			System.out.println(" 1 : S'inscrire \n 2 : Se connecter");
			System.out.print("Choissisez une option : ");

			option = scan.nextInt();

			scan.nextLine();

			switch (option) {
			case 1:
				System.out.println("Incrivez-vous ! ");
				System.out.print("Nom : ");
				String nom = scan.nextLine();
				System.out.print("Prenom : ");
				String prenom = scan.nextLine();
				System.out.print("Mail : ");
				String mail = scan.nextLine();
				System.out.print("Mot de passe : ");
				String mdp = scan.nextLine();
				String sel=BCrypt.gensalt();
				mdp = BCrypt.hashpw(mdp, sel);
				System.out.print("Code bloc : ");
				String codeBloc = scan.nextLine();
				db.inscription(nom,prenom,mail,mdp,codeBloc);
				break;
			case 2:
				System.out.println("Connectez-vous ! ");
				System.out.print("Nom : ");
				nom = scan.nextLine();
				System.out.print("Mot de passe : ");
				mdp = scan.nextLine();
				sessionId = db.connexion(nom,mdp);
				if(sessionId != 0) {
					connected = true;
					System.out.println("Bienvenue " + nom + ", vous êtes maintenant connecté!");
				} else {
					System.out.println("Nom ou mot de passe éroné \n");
				};
				break;
			}
		}
		
		//Switch 2, accessible when connected
		while(option != 5) {
			System.out.println(" 1 : Visualiser les examens \n 2 : S'inscrire à un examen \n 3 : S'inscrire à tous les examens de son bloc \n 4 : Voir son horaire d'examen \n 5 : Quitter l'application \n");
			System.out.print("Choissisez une option : ");
			option = scan.nextInt();
	
			scan.nextLine();
			
			switch (option) {
			case 1:
				System.out.println("Visualiser les examens ");
				System.out.println("code examen || nom examen || bloc id || durée");
				db.visualiserExamens();
				System.out.println("\n");
				break;
			case 2:
				System.out.println("S'inscrire à un examen");
				System.out.print("Code de l'examen : ");
				String codeExamen = scan.nextLine();
				db.inscriptionExamen(sessionId, codeExamen);
				System.out.println("\n");
				break;
			case 3:
				System.out.println("S'inscrire à tous les examens de son bloc");
				db.inscriptionTousExamenBloc(sessionId);
				System.out.println("\n");
				break;
			case 4:
				System.out.println("Voir son horaire d'examen");
				System.out.println("code examen || nom examen || bloc id || heure de début      || heure de fin        || liste des locaux");
				db.visualiserHoraireExamen(sessionId);
				System.out.println("\n");
				break;
			case 5:
				scan.close();
				break;
			}
		}
		
	}

}
