public class Personnage {
    private String nom;
    private int vie;
    private int attaque;

    // Constructeur
    public Personnage(String nom) {
        this.nom = nom;
        this.vie = 100;
        this.attaque = 20;
    }

    // Méthodes
    public boolean estVivant() {
        return this.vie > 0;
    }

    public void attaquer(Personnage cible) {
        if (this.estVivant() && cible.estVivant()) {
            cible.vie = Math.max(0, cible.vie - this.attaque);
        }
    }

    public String afficherEtat() {
        return nom + " - " + vie + " HP" + (estVivant() ? "" : " (MORT)");
    }

    // Getters
    public String getNom() { return nom; }
    public int getVie() { return vie; }
    public int getAttaque() { return attaque; }
}