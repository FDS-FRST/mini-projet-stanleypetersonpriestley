import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    private String nom;
    private Personnage[] equipe;

    public Joueur(String nom) {
        this.nom = nom;
        this.equipe = new Personnage[3];
    }

    public void creerEquipe(Scanner scanner) {
        System.out.println(nom + ", crée ton équipe :");
        for (int i = 0; i < 3; i++) {
            System.out.print("Nom du personnage " + (i + 1) + " : ");
            String nomPersonnage = scanner.nextLine();
            equipe[i] = new Personnage(nomPersonnage);
        }
    }

    public boolean aEncoreDesPersonnagesVivants() {
        for (Personnage p : equipe) {
            if (p.estVivant()) {
                return true;
            }
        }
        return false;
    }

    public void afficherEquipe() {
        System.out.println(nom + " :");
        for (Personnage p : equipe) {
            System.out.println("  " + p.afficherEtat());
        }
    }

    // Getters
    public String getNom() { return nom; }
    public Personnage[] getEquipe() { return equipe; }
}