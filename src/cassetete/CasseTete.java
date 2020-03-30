package cassetete;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javafx.scene.Node;

public class CasseTete extends Application
{
    Grille g;
    GridPane gPane;
    Image image;
    ImageView[][] tabImgView;
    boolean partieGagnee;
    float winTimer = 0;
    @Override
    public void start(Stage primaryStage) 
    {
        // Gestion de la dimension de la fenêtre
        int sceneWidth = 0;
        int sceneHeight = 0;
        tailleFenetre(sceneWidth,sceneHeight);
        
        //Initialisation des variables nécéssaires
        int nbLevels = 10;
       
        //Initialisation des musiques
        Media levelMusic = new Media(new File("./src/music/Push_it_to_the_Limit.mp3").toURI().toString());
        MediaPlayer levelPlayer = new MediaPlayer(levelMusic);
        
        Media menuMusic = new Media(new File("./src/music/Musique_Menu.mp3").toURI().toString());
        MediaPlayer menuPlayer = new MediaPlayer(menuMusic);
        menuPlayer.play();
        
        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        //Affichage titre (Ajout logo possible grâce a HBox)
        HBox topHB = new HBox();
        topHB.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        Text affichage = new Text("Grille Drag&Drop");
        affichage.setFont(Font.font("Verdana", 72));
        affichage.setFill(Color.BLACK);
        topHB.getChildren().add(affichage);
        border.setTop(topHB);
        
        //Ajout du message de bienvenue
        Text bienvenue = new Text("Bienvenue a toi utilisateur !");
        bienvenue.setFont(Font.font("Actor", 50));
        bienvenue.setFill(Color.DARKBLUE);
        
        Text explication1 = new Text("Pour reussir ces casse-tetes il te faudra :");
        explication1.setFont(Font.font("Actor", 30));
        explication1.setFill(Color.DARKBLUE);
        
        Text explication2 = new Text("  -Lier chaque symbole a son homonyme");
        explication2.setFont(Font.font("Actor", 25));
        explication2.setFill(Color.DARKBLUE);
        
        Text explication3 = new Text("  -Ne laisser aucun vide dans la grille");
        explication3.setFont(Font.font("Actor", 25));
        explication3.setFill(Color.DARKBLUE);
        GridPane choixLevel = new GridPane();
        
        //Range les textes dans une VBox pour le placer comme il faut
        VBox menu = new VBox();
        menu.setSpacing(15);
        menu.getChildren().add(bienvenue);
        menu.getChildren().add(explication1);
        menu.getChildren().add(explication2);
        menu.getChildren().add(explication3);
        menu.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        border.setBottom(menu);
        
        //Gènere le bouton pour quitter la partie un fois en jeu
        Button quitterNiveau = new Button();
        quitterNiveau.setText("Quitter le niveau en cours");
        quitterNiveau.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                border.setCenter(choixLevel);
                partieGagnee = true;
                border.setBottom(menu);
                menuPlayer.play();
                levelPlayer.stop();
            }
        });
        
        //Range le bouton dans une VBox pour le placer comme il faut
        VBox VBoxQuitterNiveau = new VBox(quitterNiveau);
        VBoxQuitterNiveau.setPadding(new Insets(10,0,80,0));
        VBoxQuitterNiveau.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        
        //Genere les boutons de choix de niveau
        for(int i=0; i<nbLevels ; i++)
        {
            Button bouton = new Button();
            bouton.setText(String.valueOf(i));
            bouton.setMinSize(80,60);
            bouton.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        initPartie(border,choixLevel,Integer.parseInt(bouton.getText()), menuPlayer, levelPlayer);
                        partieGagnee = false;
                        border.setBottom(VBoxQuitterNiveau);
                        menuPlayer.stop();
                        levelPlayer.play();
                    }
                });
            choixLevel.add(bouton, i%5, i/5);
        }
        choixLevel.setAlignment(javafx.geometry.Pos.CENTER);
        choixLevel.setHgap(20);
        choixLevel.setVgap(20);
        border.setCenter(choixLevel);
        
        
        //Affiche la scene a l'écran
        Scene scene = new Scene(border, sceneWidth, sceneHeight, Color.web("#d8d9d3"));
        primaryStage.setTitle("Projet Ligne");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    // Fonction qui met a jour la grille dans la fenêtre
    public void majGrille(Grille g, ImageView tabImgView[][],int tx, int ty)
    {
        for (int row = 0; row < tx; row++) 
            for (int column = 0; column < ty; column++)
               tabImgView[column][row].setImage(typeImage(g,column, row));
    }

    // Retourne le type de l'image a la position donnée
    public Image typeImage(Grille g,int column, int row)
    {
        Image image;
        if(g.getGrilleCT()[column][row].getType() == "CASE_CHEMIN")
        {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream("image/case_vide.png"));
        }
        else
        {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream("image/case_" + g.getGrilleCT()[column][row].getType() +".png"));
        }
        
        return image;
    }
    
    //Défini la dimension de la fenetre en fonction de la taille de l'écran
    public void tailleFenetre(int sceneWidth, int sceneHeight)
    {       
        int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
        int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

        if (screenWidth <= 1280 && screenHeight <= 768) 
        {
            sceneWidth = 800;
            sceneHeight = 450;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) 
        {
            sceneWidth = 1280;
            sceneHeight = 720;
        }
    }
    
    //Initialise la partie
    public void initPartie(BorderPane border,GridPane choixLevel, int niveau, MediaPlayer menu, MediaPlayer level)
    {
        //Initialise les variables
        gPane = new GridPane();
        g = new Grille();
        
        //Choisi le niveau
        g = g.niveauGrille(niveau);
        int lonX = g.getLonX();
        int lonY = g.getLonY();
        tabImgView = new ImageView[lonX][lonY];

                // Ajoute un observer de mise a jour sur g
                g.addObserver(new Observer() {

                    @Override
                    public void update(Observable o, Object arg) 
                    {
                        majGrille(g,tabImgView,lonX,lonY);
                        partieGagnee = g.testVictoire();
                        if(partieGagnee)
                        {
                            winTimer=g.tempsVictoire();
                            border.setCenter(choixLevel);
                            Text winText = new Text("Vous avez gagner en : " + winTimer + " secondes");
                            winText.setFont(Font.font("Actor", 40));
                            winText.setFill(Color.DARKGREEN);
                            VBox cadreWinText = new VBox(winText);
                            cadreWinText.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
                            border.setBottom(cadreWinText);
                            level.stop();
                            menu.play();
                        }
                    }
                });
                
                //Genere chaque case en leurs affectant les evenements correspondants
                for (int row = 0; row < lonX; row++) {
                    for (int column = 0; column < lonY; column++) {

                        final int fColumn = column;
                        final int fRow = row;
                        
                        //Recupere l'image correspondant a la case
                        image = typeImage(g,column, row);
                        ImageView img = new ImageView(image);
                        tabImgView[column][row] = img;
                        
                        //Ajoute l'evenement de début de drag
                        img.setOnDragDetected(new EventHandler<MouseEvent>() 
                        {
                            @Override
                            public void handle(MouseEvent event) 
                            {

                                Dragboard db = img.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();       
                                content.putString(""); // non utilisé actuellement
                                db.setContent(content);
                                event.consume();

                                g.startDD(fColumn, fRow);

                            }
                        });
                        
                        //Ajoute l'evenement de drag
                        img.setOnDragEntered(new EventHandler<DragEvent>() 
                        {
                            @Override
                            public void handle(DragEvent event) 
                            {

                                g.parcoursDD(fColumn, fRow);
                                event.consume();
                            }
                        });

                        //Ajoute l'evenement de drop
                        img.setOnDragDone(new EventHandler<DragEvent>() 
                        {
                            public void handle(DragEvent event) 
                            {
                                g.stopDD(fColumn, fRow);
                            }
                        });
                        
                        //Ajout la case a la GridPane
                        gPane.add( tabImgView[column][row], row, column);
                    }
                }

                //Affiche la GridPane
                gPane.setGridLinesVisible(true);
                gPane.setAlignment(javafx.geometry.Pos.CENTER);
                border.setCenter(gPane);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}
