import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.*;

public class Level{
    int myLevel;
    Sprite mySprite;
    Player myPlayer;
    String background = "resource/img/background2.png";
    String idleImage = "resource/img/idle.png";
    BackgroundImage myBI;

    // sound files
    String failSound = "src/resource/sound/fail.wav";
    String firecast = "src/sound_wav/firecast.wav";
    String killSound = "src/resource/resources/sound/kill_sound.wav";
    String wonSound = "src/resource/sound/won.mp3";

    Scene menuScene;

    Level(int level, Scene scene){
        myLevel = level;
        mySprite = new Sprite(level);
        myPlayer = new Player(level);
        Image image = new Image(getClass().getResource(background).toExternalForm());
        myBI = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        menuScene = scene;
    }

    public void setUp(Pane root, Scene scene){
        myPlayer.setUpScores(root, scene);
        myPlayer.setUpHearts(root, scene);
        myPlayer.setUpEnemy(root, scene);
    }

    public void makeSprite(Pane root, Scene scene){
        mySprite.makeSprite(root, scene);
    }

    public void startTheGame(Stage stage, Pane root, Scene scene){

        // add 20 enemies
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(addEnemy(stage, root, scene, 0, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 100, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 300, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 600, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 800, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 1200, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 1300, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 1400, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 1700, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 1800, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2000, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2100, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2500, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2600, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2750, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 2800, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 3200, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 3350, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 3450, myPlayer, mySprite));
        enemies.add(addEnemy(stage, root, scene, 3550, myPlayer, mySprite));

        // key event handler
        // attack
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.LEFT)){
                    ImageView imageView = mySprite.attack(true, root, scene, enemies, myPlayer);

                    // add firecase sound
                    Media fireSound = new Media(new File(firecast).toURI().toString());
                    MediaPlayer fireMediaPlayer = new MediaPlayer(fireSound);

                    AnimationTimer t = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            // checking hits
                            for(int i=0; i<enemies.size(); ++i){
                                Enemy e = enemies.get(i);
                                if (e.contains(imageView.getX()-25, imageView.getY())){
                                    // increase scores
                                    myPlayer.increaseScores();

                                    // remove fireball
                                    this.stop();
                                    Duration fadeduration = Duration.millis(100);
                                    FadeTransition fadeTransitionfb = new FadeTransition(fadeduration, imageView);
                                    fadeTransitionfb.setFromValue(1.0);
                                    fadeTransitionfb.setToValue(0.0);
                                    fadeTransitionfb.play();

                                    // remove enemy
                                    Media kill = new Media(new File(killSound).toURI().toString());
                                    MediaPlayer killMediaPlayer = new MediaPlayer(kill);
                                    FadeTransition fadeTransitionen = new FadeTransition(fadeduration, e.getImageView());
                                    fadeTransitionen.setFromValue(1.0);
                                    fadeTransitionen.setToValue(0.0);
                                    fadeTransitionen.play();
                                    e.stop();
                                    killMediaPlayer.play();
                                    enemies.remove(i);
                                    myPlayer.decreaseEnemies();

                                    if (enemies.size()==0) {
                                        Media won = new Media(new File(wonSound).toURI().toString());
                                        MediaPlayer wonMediaPlayer = new MediaPlayer(won);

                                        // Win scene
                                        Pane rootWin = new Pane();
                                        rootWin.setBackground(new Background(myBI));
                                        Scene sceneWin = new Scene(rootWin, 1280, 640);

                                        // win the game
                                        Rectangle r1 = new Rectangle();
                                        r1.setWidth(400);
                                        r1.setHeight(300);
                                        r1.setFill(Color.ORANGE);
                                        r1.setX(440);
                                        r1.setY(120);

                                        Rectangle r2 = new Rectangle();
                                        r2.setWidth(500);
                                        r2.setHeight(375);
                                        r2.setFill(Color.YELLOW);
                                        r2.setX(390);
                                        r2.setY(82.5);

                                        Image idle = new Image(getClass().getResource(idleImage).toExternalForm());
                                        ImageView idleImageView = new ImageView(idle);
                                        idleImageView.setPreserveRatio(true);
                                        idleImageView.setFitWidth(220);
                                        idleImageView.setFitHeight(250);
                                        idleImageView.setX(730);
                                        idleImageView.setY(295);

                                        Text win = new Text();
                                        win.setText("YOU WON!!");
                                        win.setFont(Font.font("Showcard Gothic", 50));
                                        win.setFill(Color.YELLOW);
                                        win.setTextAlignment(TextAlignment.CENTER);
                                        win.layoutXProperty().bind(sceneWin.widthProperty().subtract(win.prefWidth(-1)).divide(2));
                                        win.setY(200);

                                        Text yourScore = new Text();
                                        yourScore.setText("YOUR SCORE\n" + Integer.toString(myPlayer.getMyScores()));
                                        yourScore.setFont(Font.font("Showcard Gothic", 36));
                                        yourScore.setFill(Color.WHITE);
                                        yourScore.setTextAlignment(TextAlignment.CENTER);
                                        yourScore.layoutXProperty().bind(sceneWin.widthProperty().subtract(yourScore.prefWidth(-1)).divide(2));
                                        yourScore.setY(300);

                                        Text quit = new Text();
                                        quit.setText(myLevel<3 ? "PRESS ENTER TO START NEXT LEVEL!\nQUIT - Q      GO BACK TO MENU - M" : "YOU'VE FINISHED ALL LEVELS!\nQUIT - Q      GO BACK TO MENU - M");
                                        quit.setFont(Font.font("Showcard Gothic", 36));
                                        quit.setFill(Color.WHITE);
                                        quit.setTextAlignment(TextAlignment.CENTER);
                                        quit.layoutXProperty().bind(sceneWin.widthProperty().subtract(quit.prefWidth(-1)).divide(2));
                                        quit.setY(580);

                                        // key event handler
                                        sceneWin.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                            @Override
                                            public void handle(KeyEvent keyEvent) {
                                                if (keyEvent.getCode().equals(KeyCode.Q)) {
                                                    stage.close();
                                                } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                                                    if(myLevel<3) {
                                                        // go to next level
                                                        ++myLevel;

                                                        // new root new scene
                                                        Pane newRoot = new Pane();
                                                        newRoot.setBackground(new Background(myBI));
                                                        Scene newScene = new Scene(newRoot, 1280, 640);

                                                        // set up new player and new sprite
                                                        mySprite = new Sprite(myLevel);
                                                        myPlayer = new Player(myLevel);
                                                        setUp(newRoot, newScene);
                                                        makeSprite(newRoot, newScene);
                                                        startTheGame(stage, newRoot, newScene);
                                                        stage.setScene(newScene);
                                                    }
                                                } else if (keyEvent.getCode().equals(KeyCode.M)){
                                                    stage.setScene(menuScene);
                                                }
                                            }
                                        });

                                        rootWin.getChildren().add(r2);
                                        rootWin.getChildren().add(r1);
                                        rootWin.getChildren().add(idleImageView);
                                        rootWin.getChildren().add(yourScore);
                                        rootWin.getChildren().add(win);
                                        rootWin.getChildren().add(quit);
                                        stage.setScene(sceneWin);
                                        wonMediaPlayer.play();
                                    }
                                }
                            }

                            // checking miss
                            if(imageView.getX()<= mySprite.getFireBallDistance(myLevel, true)){
                                this.stop();
                                Duration fadeduration = Duration.millis(100);
                                FadeTransition fadeTransition = new FadeTransition(fadeduration, imageView);
                                fadeTransition.setFromValue(1.0);
                                fadeTransition.setToValue(0.0);
                                fadeTransition.play();

                                // decrease scores
                                myPlayer.decreaseScores();
                            }

                            // animate fireballs
                            imageView.setX(imageView.getX()-mySprite.getFireBallLifeTime(myLevel));
                        }
                    };
                    t.start();
                    fireMediaPlayer.play();
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)){
                    ImageView imageView = mySprite.attack(false, root, scene, enemies, myPlayer);
                    // add firecase sound
                    Media fireSound = new Media(new File(firecast).toURI().toString());
                    MediaPlayer fireMediaPlayer = new MediaPlayer(fireSound);

                    AnimationTimer t = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            // checking hits
                            for(int i=0; i<enemies.size(); ++i){
                                Enemy e = enemies.get(i);
                                if (e.contains(imageView.getX()+25, imageView.getY())){

                                    // increase scores
                                    myPlayer.increaseScores();

                                    // remove fireball
                                    this.stop();
                                    Duration fadeduration = Duration.millis(100);
                                    FadeTransition fadeTransitionfb = new FadeTransition(fadeduration, imageView);
                                    fadeTransitionfb.setFromValue(1.0);
                                    fadeTransitionfb.setToValue(0.0);
                                    fadeTransitionfb.play();

                                    // remove enemy
                                    Media kill = new Media(new File(killSound).toURI().toString());
                                    MediaPlayer killMediaPlayer = new MediaPlayer(kill);
                                    FadeTransition fadeTransitionen = new FadeTransition(fadeduration, e.getImageView());
                                    fadeTransitionen.setFromValue(1.0);
                                    fadeTransitionen.setToValue(0.0);
                                    fadeTransitionen.play();
                                    e.stop();
                                    killMediaPlayer.play();
                                    enemies.remove(i);
                                    myPlayer.decreaseEnemies();

                                    if (enemies.size()==0) {
                                        Media won = new Media(new File(wonSound).toURI().toString());
                                        MediaPlayer wonMediaPlayer = new MediaPlayer(won);

                                        // Win scene
                                        Pane rootWin = new Pane();
                                        rootWin.setBackground(new Background(myBI));
                                        Scene sceneWin = new Scene(rootWin, 1280, 640);

                                        // win the game
                                        Rectangle r1 = new Rectangle();
                                        r1.setWidth(400);
                                        r1.setHeight(300);
                                        r1.setFill(Color.ORANGE);
                                        r1.setX(440);
                                        r1.setY(120);

                                        Rectangle r2 = new Rectangle();
                                        r2.setWidth(500);
                                        r2.setHeight(375);
                                        r2.setFill(Color.YELLOW);
                                        r2.setX(390);
                                        r2.setY(82.5);

                                        Image idle = new Image(getClass().getResource(idleImage).toExternalForm());
                                        ImageView idleImageView = new ImageView(idle);
                                        idleImageView.setPreserveRatio(true);
                                        idleImageView.setFitWidth(220);
                                        idleImageView.setFitHeight(250);
                                        idleImageView.setX(730);
                                        idleImageView.setY(295);

                                        Text win = new Text();
                                        win.setText("YOU WON!!");
                                        win.setFont(Font.font("Showcard Gothic", 50));
                                        win.setFill(Color.YELLOW);
                                        win.setTextAlignment(TextAlignment.CENTER);
                                        win.layoutXProperty().bind(sceneWin.widthProperty().subtract(win.prefWidth(-1)).divide(2));
                                        win.setY(200);

                                        Text yourScore = new Text();
                                        yourScore.setText("YOUR SCORE\n" + Integer.toString(myPlayer.getMyScores()));
                                        yourScore.setFont(Font.font("Showcard Gothic", 36));
                                        yourScore.setFill(Color.WHITE);
                                        yourScore.setTextAlignment(TextAlignment.CENTER);
                                        yourScore.layoutXProperty().bind(sceneWin.widthProperty().subtract(yourScore.prefWidth(-1)).divide(2));
                                        yourScore.setY(300);

                                        Text quit = new Text();
                                        quit.setText(myLevel<3 ? "PRESS ENTER TO START NEXT LEVEL!\nQUIT - Q      GO BACK TO MENU - M" : "YOU'VE FINISHED ALL LEVELS!\nQUIT - Q      GO BACK TO MENU - M");
                                        quit.setFont(Font.font("Showcard Gothic", 36));
                                        quit.setFill(Color.WHITE);
                                        quit.setTextAlignment(TextAlignment.CENTER);
                                        quit.layoutXProperty().bind(sceneWin.widthProperty().subtract(quit.prefWidth(-1)).divide(2));
                                        quit.setY(580);

                                        // key event handler
                                        sceneWin.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                            @Override
                                            public void handle(KeyEvent keyEvent) {
                                                if (keyEvent.getCode().equals(KeyCode.Q)) {
                                                    stage.close();
                                                } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                                                    if(myLevel<3) {
                                                        // go to next level
                                                        ++myLevel;

                                                        // new root new scene
                                                        Pane newRoot = new Pane();
                                                        newRoot.setBackground(new Background(myBI));
                                                        Scene newScene = new Scene(newRoot, 1280, 640);

                                                        // set up new player and new sprite
                                                        mySprite = new Sprite(myLevel);
                                                        myPlayer = new Player(myLevel);
                                                        setUp(newRoot, newScene);
                                                        makeSprite(newRoot, newScene);
                                                        startTheGame(stage, newRoot, newScene);
                                                        stage.setScene(newScene);
                                                    }
                                                } else if(keyEvent.getCode().equals(KeyCode.M)){
                                                    stage.setScene(menuScene);
                                                }
                                            }
                                        });

                                        rootWin.getChildren().add(r2);
                                        rootWin.getChildren().add(r1);
                                        rootWin.getChildren().add(idleImageView);
                                        rootWin.getChildren().add(yourScore);
                                        rootWin.getChildren().add(win);
                                        rootWin.getChildren().add(quit);
                                        stage.setScene(sceneWin);
                                        wonMediaPlayer.play();
                                    }
                                }
                            }

                            // checking miss
                            if(imageView.getX()>=mySprite.getFireBallDistance(myLevel, false)){
                                this.stop();
                                Duration fadeduration = Duration.millis(100);
                                FadeTransition fadeTransition = new FadeTransition(fadeduration, imageView);
                                fadeTransition.setFromValue(1.0);
                                fadeTransition.setToValue(0.0);
                                fadeTransition.play();

                                // decrease scores
                                myPlayer.decreaseScores();
                            }

                            // animate fireballs
                            imageView.setX(imageView.getX()+mySprite.getFireBallLifeTime(myLevel));
                        }
                    };
                    t.start();
                    fireMediaPlayer.play();
                }
            }
        });


    }

    public Enemy addEnemy(Stage stage, Pane root, Scene scene, int constant, Player player, Sprite sprite){
        // 20 times
        boolean left = true;
        Random rand = new Random();
        Enemy enemy = new Enemy(myLevel);

        if (rand.nextInt() % 2 == 0) {
            // left
            enemy.flipX();
            enemy.modifyEnemyX(-120-constant);
        } else {
            left = false;
            enemy.modifyEnemyX(1200+constant);
        }

        enemy.setX(enemy.getEnemyX());
        enemy.setY(enemy.getEnemyY());
        enemy.addToRoot(root);

        boolean finalLeft = left;

        // add timer to each enemy
        AnimationTimer t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handle_animation(stage, enemy, finalLeft, player, sprite, root, scene);
            }
        };
        enemy.addTimer(t);
        return enemy;
    }

    public void handle_animation(Stage stage, Enemy e, boolean left, Player player, Sprite sprite, Pane root, Scene scene){
        double x = e.getEnemyX();
        double y = e.getEnemyY();
        if (left){
            // Hit test
            if(sprite.contains(x+330, y)){
                e.modifyEnemyX(x-45);
                player.decreaseLives(root, scene);

                if(player.getLives()==0){
                    Media fail = new Media(new File(failSound).toURI().toString());
                    MediaPlayer failMediaPlayer = new MediaPlayer(fail);

                    // game over scene
                    Pane rootGameOver = new Pane();
                    rootGameOver.setBackground(new Background(myBI));
                    Scene sceneGameOver = new Scene(rootGameOver, 1280, 640);

                    // lose the game
                    Rectangle r1 = new Rectangle();
                    r1.setWidth(400);
                    r1.setHeight(300);
                    r1.setFill(Color.ORANGE);
                    r1.setX(440);
                    r1.setY(120);

                    Rectangle r2 = new Rectangle();
                    r2.setWidth(500);
                    r2.setHeight(375);
                    r2.setFill(Color.YELLOW);
                    r2.setX(390);
                    r2.setY(82.5);

                    Image idle = new Image(getClass().getResource(idleImage).toExternalForm());
                    ImageView idleImageView = new ImageView(idle);
                    idleImageView.setPreserveRatio(true);
                    idleImageView.setFitWidth(220);
                    idleImageView.setFitHeight(250);
                    idleImageView.setX(730);
                    idleImageView.setY(295);

                    Text gameOver = new Text();
                    String sadFace = Character.toString(0x2639);
                    gameOver.setText("YOU LOST"+sadFace);
                    gameOver.setFont(Font.font("Showcard Gothic", 50));
                    gameOver.setFill(Color.YELLOW);
                    gameOver.setTextAlignment(TextAlignment.CENTER);
                    gameOver.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(gameOver.prefWidth(-1)).divide(2));
                    gameOver.setY(200);

                    Text yourScore = new Text();
                    yourScore.setText("YOUR SCORE\n"+Integer.toString(player.getMyScores()));
                    yourScore.setFont(Font.font("Showcard Gothic", 36));
                    yourScore.setFill(Color.WHITE);
                    yourScore.setTextAlignment(TextAlignment.CENTER);
                    yourScore.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(yourScore.prefWidth(-1)).divide(2));
                    yourScore.setY(300);

                    Text quit = new Text();
                    quit.setText("PRESS ENTER TO START AGAIN!\nQUIT - Q      GO BACK TO MENU - M");
                    quit.setFont(Font.font("Showcard Gothic", 36));
                    quit.setFill(Color.WHITE);
                    quit.setTextAlignment(TextAlignment.CENTER);
                    quit.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(quit.prefWidth(-1)).divide(2));
                    quit.setY(580);

                    // key event handler
                    sceneGameOver.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if(keyEvent.getCode().equals(KeyCode.Q)){
                                stage.close();
                            } else if (keyEvent.getCode().equals(KeyCode.ENTER)){
                                // new root new scene
                                Pane newRoot = new Pane();
                                newRoot.setBackground(new Background(myBI));
                                Scene newScene = new Scene(newRoot, 1280, 640);

                                // set up new player and new sprite
                                mySprite = new Sprite(myLevel);
                                myPlayer = new Player(myLevel);
                                setUp(newRoot, newScene);
                                makeSprite(newRoot, newScene);
                                startTheGame(stage, newRoot, newScene);
                                stage.setScene(newScene);
                            } else if (keyEvent.getCode().equals(KeyCode.M)){
                                stage.setScene(menuScene);
                            }
                        }
                    });

                    rootGameOver.getChildren().add(r2);
                    rootGameOver.getChildren().add(r1);
                    rootGameOver.getChildren().add(idleImageView);
                    rootGameOver.getChildren().add(yourScore);
                    rootGameOver.getChildren().add(gameOver);
                    rootGameOver.getChildren().add(quit);
                    stage.setScene(sceneGameOver);
                    failMediaPlayer.play();
                }
            }
            e.enemyxPlusDX();
        } else {
            // Hit test
            if(sprite.contains(x, y)){
                e.modifyEnemyX(x+45);
                player.decreaseLives(root, scene);

                if(player.getLives()==0){
                    Media fail = new Media(new File(failSound).toURI().toString());
                    MediaPlayer failMediaPlayer = new MediaPlayer(fail);

                    // game over scene
                    Pane rootGameOver = new Pane();
                    rootGameOver.setBackground(new Background(myBI));
                    Scene sceneGameOver = new Scene(rootGameOver, 1280, 640);

                    // lose the game
                    Rectangle r1 = new Rectangle();
                    r1.setWidth(400);
                    r1.setHeight(300);
                    r1.setFill(Color.ORANGE);
                    r1.setX(440);
                    r1.setY(120);

                    Rectangle r2 = new Rectangle();
                    r2.setWidth(500);
                    r2.setHeight(375);
                    r2.setFill(Color.YELLOW);
                    r2.setX(390);
                    r2.setY(82.5);

                    Image idle = new Image(getClass().getResource(idleImage).toExternalForm());
                    ImageView idleImageView = new ImageView(idle);
                    idleImageView.setPreserveRatio(true);
                    idleImageView.setFitWidth(220);
                    idleImageView.setFitHeight(250);
                    idleImageView.setX(730);
                    idleImageView.setY(295);

                    Text gameOver = new Text();
                    String sadFace = Character.toString(0x2639);
                    gameOver.setText("YOU LOST"+sadFace);
                    gameOver.setFont(Font.font("Showcard Gothic", 50));
                    gameOver.setFill(Color.YELLOW);
                    gameOver.setTextAlignment(TextAlignment.CENTER);
                    gameOver.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(gameOver.prefWidth(-1)).divide(2));
                    gameOver.setY(200);

                    Text yourScore = new Text();
                    yourScore.setText("YOUR SCORE\n"+Integer.toString(player.getMyScores()));
                    yourScore.setFont(Font.font("Showcard Gothic", 36));
                    yourScore.setFill(Color.WHITE);
                    yourScore.setTextAlignment(TextAlignment.CENTER);
                    yourScore.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(yourScore.prefWidth(-1)).divide(2));
                    yourScore.setY(300);

                    Text quit = new Text();
                    quit.setText("PRESS ENTER TO START AGAIN!\nQUIT - Q      GO BACK TO MENU - M");
                    quit.setFont(Font.font("Showcard Gothic", 36));
                    quit.setFill(Color.WHITE);
                    quit.setTextAlignment(TextAlignment.CENTER);
                    quit.layoutXProperty().bind(sceneGameOver.widthProperty().subtract(quit.prefWidth(-1)).divide(2));
                    quit.setY(580);

                    // key event handler
                    sceneGameOver.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if(keyEvent.getCode().equals(KeyCode.Q)){
                                stage.close();
                            } else if (keyEvent.getCode().equals(KeyCode.ENTER)){
                                // new root new scene
                                Pane newRoot = new Pane();
                                newRoot.setBackground(new Background(myBI));
                                Scene newScene = new Scene(newRoot, 1280, 640);

                                // set up new player and new sprite
                                mySprite = new Sprite(myLevel);
                                myPlayer = new Player(myLevel);
                                setUp(newRoot, newScene);
                                makeSprite(newRoot, newScene);
                                startTheGame(stage, newRoot, newScene);
                                stage.setScene(newScene);
                            } else if (keyEvent.getCode().equals(KeyCode.M)){
                                stage.setScene(menuScene);
                            }
                        }
                    });

                    rootGameOver.getChildren().add(r2);
                    rootGameOver.getChildren().add(r1);
                    rootGameOver.getChildren().add(idleImageView);
                    rootGameOver.getChildren().add(yourScore);
                    rootGameOver.getChildren().add(gameOver);
                    rootGameOver.getChildren().add(quit);
                    stage.setScene(sceneGameOver);
                    failMediaPlayer.play();
                }
            }

            e.enemyxMinusDX();
        }

        e.setX(e.getEnemyX());

    }

}