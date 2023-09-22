import java.util.ArrayList;
import java.util.List;

public class Vaisseau {
    private double posX;
    private double posY;
    private int pv;

    /**
     * Constructeur de la classe Vaisseau
     * @param x sa position sur l'axe des abscisses
     * @param y sa position sur l'axe des ordonnées
     * @param pv son nombre de points de vie
     */
    public Vaisseau(double x, double y, int pv){
        this.posX = x;
        this.posY = y;
        this.pv = pv;
    }

    /**
     * Permet de déplacer le vaisseau selon une valeur sur l'axe des abscisses
     * @param dx
     */
    public void deplace(double dx){
        this.posX += dx;
    }

    /**
     * 
     * @return sa position sur l'axe des abscisses
     */
    public double getX(){
        return this.posX;
    }

    /**
     * 
     * @return sa position sur l'axe des ordonnées
     */
    public double getY(){
        return this.posY;
    }

    /**
     * 
     * @return les chaines permettant de dessiner le vaisseau
     */
    public EnsembleChaines getChaines(){
        EnsembleChaines res = new EnsembleChaines();
        res.ajouteChaine((int) posX, (int) posY+5, "        ▄        ", "0xFFFFFF");
        res.ajouteChaine((int) posX, (int) posY+4, "       ███       ", "0xFFFFFF");
        res.ajouteChaine((int) posX, (int) posY+3, "  ▄███████████▄  ", "0xFFFFFF");
        res.ajouteChaine((int) posX, (int) posY+2, "  █████████████  ", "0xFFFFFF");
        res.ajouteChaine((int) posX, (int) posY+1, "  █████████████  ", "0xFFFFFF");
        res.ajouteChaine((int) posX, (int) posY, "                 ", "0xFFFFFF");
        return res;
    }

    /**
     * 
     * @return la position sur l'axe des abscisses du canon du vaisseau
     */
    public double positionCanon(){
        return posX + 8;
    }

    /**
     * 
     * @return le nombre de points de vie du vaisseau
     */
    public int getPV(){
        return this.pv;
    }

    /**Si le vaisseau est touché, il perd un point de vie */
    public void vaisseauTouche(){
        this.pv -= 1;
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
}