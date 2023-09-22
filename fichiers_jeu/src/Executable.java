import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;



public class Executable extends Application {
    private Pane root;
    private Group caracteres;
    private GestionJeu gestionnaire;
    private int hauteurTexte;
    private int largeurCaractere;
    public static void main(String[] args) {
        launch(args);
    }

    private void afficherCaracteres(){
        caracteres.getChildren().clear();
        int hauteur = (int) root.getHeight();
        for( ChainePositionnee c : gestionnaire.getChaines().chaines)
        {
            Text t = new Text (c.x*largeurCaractere,hauteur - c.y*hauteurTexte, c.c);
            t.setFont(Font.font ("Monospaced", 10));
            t.setFill(Color.web(c.getCouleur()));

            Text score = new Text();
            score.setText("Score : " + String.valueOf(gestionnaire.getScore()));
            score.setFont(Font.font("Monospaced", 15));
            score.setY(20);
            score.setFill(Color.WHITE);
            
            Text lives = new Text();
            String vies = "";
            for (int i = 0; i < gestionnaire.getPVVaisseau(); i++){
                vies += "♥";
            }
            lives.setText("Vies : " + vies);
            lives.setFont(Font.font("Monospaced", 15));
            lives.setFill(Color.WHITE);
            lives.setY(20);
            lives.setX(100);

            caracteres.getChildren().addAll(t, score, lives);

            if (gestionnaire.partieGagnee()){
                this.afficherPartieGagnee();
            }

            else if (gestionnaire.partiePerdue()){
                this.afficherPartiePerdue();
            }
        }
    }

    private void afficherPartieGagnee(){
        caracteres.getChildren().clear();
        Text t = new Text();
        t.setFont(Font.font ("Monospaced", 30));
        t.setText("Vous avez gagné la partie !");
        t.setY(200);
        t.setX(43);
        t.setFill(Color.WHITE);

        caracteres.getChildren().addAll(t);
    }

    private void afficherPartiePerdue(){
        caracteres.getChildren().clear();
        Text t = new Text();
        t.setFont(Font.font ("Monospaced", 30));
        t.setText("Vous avez perdu la partie !");
        t.setFill(Color.WHITE);

        caracteres.getChildren().addAll(t);
        caracteres.setLayoutX(43);
        caracteres.setLayoutY(200);
    }

    private void lancerAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent actionEvent) {
                            gestionnaire.jouerUnTour();
                            afficherCaracteres();
                        }
                    }),
                new KeyFrame(Duration.seconds(0.05))
                );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    @Override
        public void start(Stage primaryStage) throws FileNotFoundException{
            primaryStage.setTitle("IUTO Space Invader");
            caracteres = new Group();
            // root = new AnchorPane(caracteres);
            gestionnaire = new GestionJeu();
            Text t=new Text("█");
            t.setFont(Font.font("Monospaced",10));
            hauteurTexte =(int) t.getLayoutBounds().getHeight();
            largeurCaractere = (int) t.getLayoutBounds().getWidth();

            String pathMusic = "./fichiers_jeu/sound/spaceinvaders1.mpeg";
            Media musiqueDeFond = new Media(new File(pathMusic).toURI().toString());
            final MediaPlayer playerMusiqueDeFond = new MediaPlayer(musiqueDeFond);
            playerMusiqueDeFond.setAutoPlay(true);

            Image spaceImage = new Image(new FileInputStream("./fichiers_jeu/img/space.gif"));
            BackgroundImage spaceBG = new BackgroundImage(spaceImage, null, null, null, null);
            root = new Pane();
            root.setBackground(new Background(spaceBG));
            root.getChildren().add(caracteres);

            Scene sceneGame = new Scene(root,gestionnaire.getLargeur()*largeurCaractere,gestionnaire.getHauteur()*hauteurTexte);

            sceneGame.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if(key.getCode()==KeyCode.LEFT)
                    gestionnaire.toucheGauche();
                if(key.getCode()==KeyCode.RIGHT)
                    gestionnaire.toucheDroite();
                if(key.getCode()==KeyCode.SPACE)
                    gestionnaire.toucheEspace();
            });

            primaryStage.setScene(sceneGame);
            primaryStage.setResizable(false);
            primaryStage.show();
            lancerAnimation();
        }
}
