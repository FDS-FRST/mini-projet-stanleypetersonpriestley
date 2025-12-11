import java.util.Scanner;
import java.util.ArrayList;

public class Jeu {
    private static Scanner scanner = new Scanner(System.in);
    private static Joueur joueur1, joueur2;
    private static int tour = 1;

    public static void main(String[] args) {
        System.out.println("**************************************");
        System.out.println(" Bienvenue dans le Jeu de Combat ");
        System.out.println("**************************************");
        System.out.println();

        creerJoueurs();

        combat();

        scanner.close();
    }

    private static void creerJoueurs() {
        System.out.print("Joueur 1, entre ton nom : ");
        String nom1 = scanner.nextLine();
        joueur1 = new Joueur(nom1);

        System.out.print("Joueur 2, entre ton nom : ");
        String nom2 = scanner.nextLine();
        joueur2 = new Joueur(nom2);

        System.out.println();
        joueur1.creerEquipe(scanner);
        System.out.println();
        joueur2.creerEquipe(scanner);
    }

    private static void combat() {
        System.out.println();
        System.out.println("⚔ Que le combat commence !");

        while (joueur1.aEncoreDesPersonnagesVivants() &&
                joueur2.aEncoreDesPersonnagesVivants()) {

            if (joueur1.aEncoreDesPersonnagesVivants()) {
                executerTour(joueur1, joueur2);
                if (!joueur2.aEncoreDesPersonnagesVivants()) break;
            }

            if (joueur2.aEncoreDesPersonnagesVivants()) {
                executerTour(joueur2, joueur1);
            }

            tour++;
        }

        annoncerVainqueur();
    }

    private static void executerTour(Joueur attaquant, Joueur defenseur) {
        System.out.println();
        System.out.println("--- TOUR " + tour + " ---");
        System.out.println("C'est au tour de " + attaquant.getNom() + " !");

        System.out.println("********************************");
        System.out.println("Choisissez une action :");
        System.out.println("1. Attaquer l'adversaire");
        System.out.println("2. Soigner un de vos personnages");
        System.out.print("Votre choix (1 ou 2) : ");

        String choixAction = scanner.nextLine();

        System.out.println("État des équipes avant l'action :");
        joueur1.afficherEquipe();
        joueur2.afficherEquipe();

        switch (choixAction) {
            case "1":
                executerAttaque(attaquant, defenseur);
                break;
            case "2":
                executerSoin(attaquant);
                break;
            default:
                System.out.println(" Choix invalide. Vous perdez votre tour.");
                break;
        }

        System.out.println();
        System.out.println("État des équipes après l'action :");
        joueur1.afficherEquipe();
        joueur2.afficherEquipe();
    }

    private static void executerSoin(Joueur soigneur) {
        System.out.println("Qui voulez-vous soigner ? (Personnage qui n'est pas mort)");

        Personnage personnageSoigne = choisirPersonnageVivant(soigneur);

        if (personnageSoigne == null) {
            System.out.println("Aucun personnage vivant à soigner.");
            return;
        }

        final int MONTANT_SOIN = 30;
        int vieAvant = personnageSoigne.getVie();

        personnageSoigne.soigner(MONTANT_SOIN);

        System.out.println(" " + personnageSoigne.getNom() + " est soigné !");
        System.out.println(personnageSoigne.getNom() + " gagne " + MONTANT_SOIN + " points de vie (" + vieAvant + " → " + personnageSoigne.getVie() + ")");
    }

    private static void executerAttaque(Joueur attaquant, Joueur defenseur) {

        Personnage personnageAttaquant = choisirPersonnageVivant(attaquant);
        if (personnageAttaquant == null) return;

        Personnage cible = choisirPersonnageVivant(defenseur);
        if (cible == null) return;

        int vieAvant = cible.getVie();
        personnageAttaquant.attaquer(cible);

        System.out.println(" " + personnageAttaquant.getNom() + " attaque " + cible.getNom() + " !");
        System.out.println(cible.getNom() + " perd 20 points de vie (" + vieAvant + " → " + cible.getVie() + ")");

        if (!cible.estVivant()) {
            System.out.println(" " + cible.getNom() + " est éliminé !");
        }
    }

    private static Personnage choisirPersonnageVivant(Joueur joueur) {
        ArrayList<Personnage> vivants = new ArrayList<>();

        for (Personnage p : joueur.getEquipe()) {
            if (p.estVivant()) {
                vivants.add(p);
            }
        }

        if (vivants.isEmpty()) return null;

        System.out.println("Choisissez un personnage pour " + joueur.getNom() + " :");
        for (int i = 0; i < vivants.size(); i++) {
            System.out.println((i + 1) + ". " + vivants.get(i).afficherEtat());
        }

        int choix = -1;
        while (choix < 1 || choix > vivants.size()) {
            System.out.print("Votre choix (1-" + vivants.size() + ") : ");
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Veuillez entrer un nombre valide.");
            }
        }

        return vivants.get(choix - 1);
    }

    private static void annoncerVainqueur() {
        System.out.println();
        if (joueur1.aEncoreDesPersonnagesVivants()) {
            System.out.println(" Victoire de " + joueur1.getNom() + " !");
            System.out.println("Tous les personnages de " + joueur2.getNom() + " sont éliminés.");
        } else {
            System.out.println(" Victoire de " + joueur2.getNom() + " !");
            System.out.println("Tous les personnages de " + joueur1.getNom() + " sont éliminés.");
        }
    }
}