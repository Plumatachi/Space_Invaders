public class ChainePositionnee{
    int x,y;
    String c;
    String couleur;
    public ChainePositionnee(int a,int b, String d, String color){
        x=a;
        y=b;
        c=d;
        couleur = color;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String getC(){
        return this.c;
    }

    public String getCouleur(){
        return this.couleur;
    }

    public void setCouleur(String newColor){
        this.couleur = newColor;
    }
}
