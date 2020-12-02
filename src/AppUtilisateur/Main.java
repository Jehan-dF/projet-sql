package AppUtilisateur;

import java.util.Scanner;

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
				System.out.println("Incrivez-vous : ");
				System.out.print("Nom : ");
				String nom = scan.nextLine();
				System.out.print("Prenom : ");
				String prenom = scan.nextLine();
				System.out.print("Mail : ");
				String mail = scan.nextLine();
				System.out.print("Mot de passe : ");
				String mdp = scan.nextLine();
				System.out.print("Code bloc : ");
				String codeBloc = scan.nextLine();
				db.inscription(nom,prenom,mail,mdp,codeBloc);
				break;
			case 2:
				System.out.println("Connectez-vous : ");
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
		
		scan.close();
	}

}
