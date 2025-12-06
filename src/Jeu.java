import java.util.Scanner;
import java.util.ArrayList;

public class Jeu {
    private static Scanner scanner = new Scanner(System.in);
    private static Joueur joueur1, joueur2;
    private static int tour = 1;

    public static void main(String[] args) {
        System.out.println("🎮 Bienvenue dans le Jeu de Combat !");
        System.out.println();

        // Phase 1: Création des joueurs
        creerJoueurs();

        // Phase 2: Combat
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
        System.out.println("⚔️ Que le combat commence !");

        while (joueur1.aEncoreDesPersonnagesVivants() &&
                joueur2.aEncoreDesPersonnagesVivants()) {

            // Tour du joueur 1
            if (joueur1.aEncoreDesPersonnagesVivants()) {
                executerTour(joueur1, joueur2);
                if (!joueur2.aEncoreDesPersonnagesVivants()) break;
            }

            // Tour du joueur 2
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

        // Choisir l'attaquant
        Personnage personnageAttaquant = choisirPersonnageVivant(attaquant);
        if (personnageAttaquant == null) return;

        // Choisir la cible
        Personnage cible = choisirPersonnageVivant(defenseur);
        if (cible == null) return;

        // Exécuter l'attaque
        int vieAvant = cible.getVie();
        personnageAttaquant.attaquer(cible);

        System.out.println("💥 " + personnageAttaquant.getNom() + " attaque " + cible.getNom() + " !");
        System.out.println(cible.getNom() + " perd 20 points de vie (" + vieAvant + " → " + cible.getVie() + ")");

        if (!cible.estVivant()) {
            System.out.println("💀 " + cible.getNom() + " est éliminé !");
        }

        // Afficher l'état des équipes
        System.out.println();
        System.out.println("État des équipes :");
        joueur1.afficherEquipe();
        joueur2.afficherEquipe();
    }

    private static Personnage choisirPersonnageVivant(Joueur joueur) {
        ArrayList<Personnage> vivants = new ArrayList<>();

        // Collecter les personnages vivants
        for (Personnage p : joueur.getEquipe()) {
            if (p.estVivant()) {
                vivants.add(p);
            }
        }

        if (vivants.isEmpty()) return null;

        // Afficher les options
        System.out.println("Choisissez un personnage :");
        for (int i = 0; i < vivants.size(); i++) {
            System.out.println((i + 1) + ". " + vivants.get(i).afficherEtat());
        }

        // Lire le choix
        int choix = -1;
        while (choix < 1 || choix > vivants.size()) {
            System.out.print("Votre choix (1-" + vivants.size() + ") : ");
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide.");
            }
        }

        return vivants.get(choix - 1);
    }

    private static void annoncerVainqueur() {
        System.out.println();
        if (joueur1.aEncoreDesPersonnagesVivants()) {
            System.out.println("🏆 Victoire de " + joueur1.getNom() + " !");
            System.out.println("Tous les personnages de " + joueur2.getNom() + " sont éliminés.");
        } else {
            System.out.println("🏆 Victoire de " + joueur2.getNom() + " !");
            System.out.println("Tous les personnages de " + joueur1.getNom() + " sont éliminés.");
        }
    }
}