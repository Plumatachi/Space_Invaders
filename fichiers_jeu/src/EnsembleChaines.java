import java.util.ArrayList;

public class EnsembleChaines {
    ArrayList<ChainePositionnee> chaines;

    public EnsembleChaines(){
        chaines= new ArrayList<ChainePositionnee>();
    }

    public void ajouteChaine(int x, int y, String c, String color){
        chaines.add(new ChainePositionnee(x,y,c, color));
    }

    public void union(EnsembleChaines e){
        for(ChainePositionnee c : e.chaines)
            chaines.add(c);
    }

    public void clear(){
        chaines = new ArrayList<ChainePositionnee>();
    }

    public boolean contient(int x, int y){
        boolean res = false;
        for (ChainePositionnee chaine : this.chaines){
            if (x >= chaine.getX() && x < chaine.getX()+11 && y == chaine.getY()){
                res = true;
            }
        }
        return res;
    }
}
