public class Projectile {
    private double posX;
    private double posY;

    /**
     * Constructeur de la classe Projectile
     * @param x sa position sur l'axe des abscisses
     * @param y sa position sur l'axe des ordonnées
     */
    public Projectile(double x, double y){
        this.posX = x;
        this.posY = y;
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
     * 
     * @return les chaines permettant de dessiner le projectile du vaisseau
     */
    public EnsembleChaines getEnsembleChaines(){
        EnsembleChaines res = new EnsembleChaines();
        res.ajouteChaine((int) posX, (int) posY, "|", "0xFFFFFF");
        return res;
    }

    /**
     * 
     * @return les chaines permettant de dessiner le projectile des aliens
     */
    public EnsembleChaines getEnsembleChainesAliens(){
        EnsembleChaines res = new EnsembleChaines();
        res.ajouteChaine((int) posX, (int) posY, "●", "0xFFFFFF");
        return res;
    }

    /**Le projectile se déplace sur l'axe des ordonnées jusqu'à atteindre la fin de l'écran de jeu ou un alien*/
    public void evolue(){
        this.posY += 2;
    }

    /**Le projectile se déplace sur l'axe des ordonnées jusqu'à atteindre la fin de l'écran de jeu ou le vaisseau*/
    public void evolutionTirEnnemi(){
        this.posY -= 1;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if (o instanceof Projectile){
            Projectile proj = (Projectile) o;
            return this.posX == proj.posX && this.posY == proj.posY;
        }
        return false;
    }
}
