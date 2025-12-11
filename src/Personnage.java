public class Personnage {
    private String nom;
    private int vie;
    private int attaque;
    private final int VIE_MAX = 100;

    public Personnage(String nom) {
        this.nom = nom;
        this.vie = 100;
        this.attaque = 20;
    }

    public void soigner(int montantSoin) {
        this.vie = Math.min(VIE_MAX, this.vie + montantSoin);
    }

    public boolean estVivant() {
        return this.vie > 0;
    }

    public void attaquer(Personnage cible) {
        if (this.estVivant()) {
            cible.vie = Math.max(0, cible.vie - this.attaque);
        }
    }

    public String afficherEtat() {
        return nom + " - " + vie + " HP" + (estVivant() ? "" : " (MORT)");
    }

    public String getNom() { return nom; }
    public int getVie() { return vie; }
    public int getAttaque() { return attaque; }
}