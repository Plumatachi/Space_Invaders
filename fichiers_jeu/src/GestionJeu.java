import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GestionJeu {
    private int posX;
    private int posY;
    private int score;
    private int nbTours;
    private Vaisseau vaisseau;
    private List<Projectile> lesProjectiles;
    private List<Projectile> tirEnnemi;
    private List<Alien> lesAliens;

    /**Constructeur de la classe GestionJeu */
    public GestionJeu(){
        this.posX = 0;
        this.posY = 30;
        this.score = 0;
        this.nbTours = 0;
        this.lesProjectiles = new ArrayList<>();
        this.lesAliens = new ArrayList<>();
        this.tirEnnemi = new ArrayList<>();

        this.vaisseau = new Vaisseau(40, 0, 3);
        this.lesAliens.add(new Alien(0, 40));
        this.lesAliens.add(new Alien(15, 40));
        this.lesAliens.add(new Alien(30, 40));
        this.lesAliens.add(new Alien(45, 40));
        this.lesAliens.add(new Alien(60, 40));
        this.lesAliens.add(new Alien(75, 40));

        this.lesAliens.add(new Alien(0, 50));
        this.lesAliens.add(new Alien(15, 50));
        this.lesAliens.add(new Alien(30, 50));
        this.lesAliens.add(new Alien(45, 50));
        this.lesAliens.add(new Alien(60, 50));
        this.lesAliens.add(new Alien(75, 50));
    }

    /**
     * 
     * @return la hauteur de la fenêtre de jeu
     */
    public int getHauteur(){
        return 60;
    }

    /**
     * 
     * @return la largeur de la fenêtre de jeu
     */
    public int getLargeur(){
        return 100;
    }
    
    /**Permet au vaisseau de se déplacer à gauche sans sortir de la fenêtre de jeu */
    public void toucheGauche(){
        if (this.vaisseau.getX() > 0){
            this.vaisseau.deplace(-1);
        }
        // System.out.println("Appuie sur la touche gauche");
    }

    /**Permet au vaisseau de se déplacer à droite sans sortir de la fenêtre de jeu */
    public void toucheDroite(){
        if (this.vaisseau.getX()+16 < this.getLargeur()){
            this.vaisseau.deplace(1);
        }
        // System.out.println("Appuie sur la touche droite");
    }

    /**Permet au vaisseau de tirer un projectile */
    public void toucheEspace(){
        // System.out.println("Appuie sur la touche Espace");
        this.lesProjectiles.add(new Projectile(this.vaisseau.positionCanon(), 6));
    }

    /**
     * 
     * @return le score actuel du joueur
     */
    public int getScore(){
        return this.score;
    }

    /**Permet aux aliens de tirer des projectiles */
    public void tirAliens(){
        Random random = new Random();
        for (Alien a : this.lesAliens){
            double posRandom = (double) random.nextInt(100);
            if (posRandom > 98){
                tirEnnemi.add(new Projectile(a.positionX(), a.positionY()));
            }
        }
    }

    /**
     * 
     * @return l'ensemble des chaines du jeu (vaisseau, aliens, projectiles)
     */
    public EnsembleChaines getChaines(){
        EnsembleChaines res = new EnsembleChaines();
        EnsembleChaines aliens = new EnsembleChaines();
        // res.ajouteChaine(posX, posY, "@@");
        
        res.union(this.vaisseau.getChaines());

        for (Projectile p : lesProjectiles){
            res.union(p.getEnsembleChaines());
            p.evolue();
        }
        for (Alien a : lesAliens){
            aliens.union(a.animationAlien(getTourPair()));
            a.evolue(this.nbTours);
            res.union(aliens);
        }
        for (Projectile tirA : this.tirEnnemi){
            res.union(tirA.getEnsembleChainesAliens());
            tirA.evolutionTirEnnemi();
        }
        this.nbTours();
        return res;
    }

    /**
     * Ajoute le paramètre au score
     * @param valeur
     */
    public void ajoute(int valeur){
        this.score += valeur;
    }

    /**
     * 
     * @return le nombre de tours du jeu
     */
    public int nbTours(){
        this.nbTours += 1;
        if (this.nbTours > 200){
            this.nbTours = 0;
        }
        return this.nbTours;
    }

    /**
     * 
     * @return true si le nombre du tour actuel est pair, false sinon
     */
    public boolean getTourPair(){
        if (this.nbTours%4 == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 
     * @return les points de vie du vaisseau
     */
    public int getPVVaisseau(){
        return this.vaisseau.getPV();
    }

    /**Pendant le tour : 
     * - on vérifie si les projectiles touchent le camp adverse
     * - on vérifie que la partie n'est pas encore perdue pour le joueur
     * - les aliens tirent des projectiles
     */
    public void jouerUnTour(){
        // System.out.println(score);
        if (!this.lesProjectiles.isEmpty()){
            this.touche();
        }
        if (!this.tirEnnemi.isEmpty()){
            this.vaisseauTouche();
        }
        if (this.vaisseau.getPV() == 0){
            this.partiePerdue();
        }
        this.tirAliens();
    }

    /**Gère si les projectiles du vaisseau touchent les aliens */
    public void touche(){
        List<Alien> aliensTouches = new ArrayList<>();
        List<Alien> aliens = new ArrayList<>(this.lesAliens);
        List<Projectile> projectilesQuiOntFaitMouche = new ArrayList<>();
        List<Projectile> projectiles = new ArrayList<>(this.lesProjectiles);
        for (Alien a : aliens){
            for (Projectile p : projectiles){
                if (a.contient((int) p.positionX(), (int) p.positionY())){
                    aliensTouches.add(a);
                    projectilesQuiOntFaitMouche.add(p);
                    this.lesAliens.removeAll(aliensTouches);
                    this.lesProjectiles.removeAll(projectilesQuiOntFaitMouche);
                    this.ajoute(1);
                }
            }
        }
    }

    /**Gère si les tirs des aliens touchent le vaisseau */
    public void vaisseauTouche(){
        List<Projectile> projectilesEnnemisQuiOntFaitMouche = new ArrayList<>();
        List<Projectile> tirsEnnemis = new ArrayList<>(this.tirEnnemi);
        for (Projectile tirA : tirsEnnemis){
            if (vaisseau.contient((int) tirA.positionX(), (int) tirA.positionY())){
                this.vaisseau.vaisseauTouche();
                projectilesEnnemisQuiOntFaitMouche.add(tirA);
                this.tirEnnemi.removeAll(projectilesEnnemisQuiOntFaitMouche);
            }
        }
    }

    /**
     * 
     * @return true si tous les aliens sont éliminés, false sinon
     */
    public boolean partieGagnee(){
        if (this.lesAliens.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 
     * @return true si le vaisseau n'a plus de points de vie, false sinon
     */
    public boolean partiePerdue(){
        if (this.vaisseau.getPV() <= 0){
            return true;
        }
        return false;
    }
}
