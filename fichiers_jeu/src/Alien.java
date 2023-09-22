import java.util.ArrayList;
import java.util.List;

public class Alien {
    private double posX;
    private double posY;

    /**
     * Constructeur de la classe Alien
     * @param x sa position sur l'axe des abscisses
     * @param y sa position sur l'axe des ordonnées
     */
    public Alien(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    /**
     * 
     * @return les chaines permettant de dessiner l'alien
     */
    public EnsembleChaines getChaines(){
        EnsembleChaines res = new EnsembleChaines();
        res.ajouteChaine((int) posX, (int) posY+4, "  ▀▄   ▄▀  ", "0x00FF0C");
        res.ajouteChaine((int) posX, (int) posY+3, " ▄█▀███▀█▄ ", "0x00FF0C");
        res.ajouteChaine((int) posX, (int) posY+2, "█▀███████▀█", "0x00FF0C");
        res.ajouteChaine((int) posX, (int) posY+1, "█ █▀▀▀▀▀█ █", "0x00FF0C");
        res.ajouteChaine((int) posX, (int) posY,   "   ▀▀ ▀▀   ", "0x00FF0C");
        return res;
    }

    /**
     * 
     * @return sa position sur l'axe des abscisses
     */
    public double positionX(){
        return this.posX;
    }

    /**
     * 
     * @return sa position sur l'axe des ordonnées
     */
    public double positionY(){
        return this.posY;
    }

    /**
     * L'alien évolue en fonction des tours passés
     * @param cpt le nombre du tour actuel
     */
    public void evolue(int cpt){
        if (cpt < 100){
            this.posX += 0.1;
        }
        else if (cpt == 100){
            this.posY -= 1;
        }
        else if (cpt > 100 && cpt < 200){
            this.posX -= 0.1;
        }
        else if (cpt > 100 && cpt == 200){
            this.posY -= 1;
        }
    }

    /**
     * 
     * @param tourPair
     * @return les chaines permettant de dessiner l'alien en fonction du nombre de tour
     */
    public EnsembleChaines animationAlien(boolean tourPair){
        EnsembleChaines res = this.getChaines();
        if (!tourPair){
            res.clear();
            res.ajouteChaine((int) posX, (int) posY+4, "  ▀▄   ▄▀  ", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+3, " ▄█▀███▀█▄ ", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+2, "█▀███████▀█", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+1, "█ █▀▀▀▀▀█ █", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY,   "   ▀▀ ▀▀   ", "0x00FF0C");
        }
        else{
            res.clear();
            res.ajouteChaine((int) posX, (int) posY+4, "▄ ▀▄   ▄▀ ▄", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+3, "█▄███████▄█", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+2, "███▄███▄███", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY+1, "▀█████████▀", "0x00FF0C");
            res.ajouteChaine((int) posX, (int) posY,   " ▄▀     ▀▄ ", "0x00FF0C");
        }
        return res;
    }

    /**
     * 
     * @param x position sur l'axe des abscisses
     * @param y position sur l'axe des ordonnées
     * @return true si une chaine se trouve bien aux positions x et y, false sinon
     */
    public boolean contient(int x, int y){
        List<EnsembleChaines> lesChaines = new ArrayList<>();
        lesChaines.add(this.getChaines());
        for (EnsembleChaines chaines : lesChaines){
            return chaines.contient(x, y);
        }
        return false;
    }

    /**
     * 
     * @return point de départ du tir des aliens
     */
    public double departDuTir(){
        return this.posX + 6;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if (o instanceof Alien){
            Alien alien = (Alien) o;
            return this.posX == alien.posX && this.posY == alien.posY;
        }
        return false;
    }
}