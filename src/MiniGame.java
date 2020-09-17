import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class MiniGame extends Application{

    @Override
    public void start(Stage stage) {

        Image image = new Image(getClass().getResource("resource/img/background2.png").toExternalForm());
        BackgroundImage myBI = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Pane root1 = new Pane();
        root1.setBackground(new Background(myBI));
        Scene sceneIntroduction = new Scene(root1, 1280, 640);

        // add logo
        Image myLogo = new Image(getClass().getResource("resource/img/logo.png").toExternalForm());
        ImageView imageView = new ImageView(myLogo);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setX(440);
        imageView.setY(30);
        root1.getChildren().add(imageView);

        // add texts
        Text t1 = new Text();
        String leftArrow = Character.toString(0x2190);
        String rightArrow = Character.toString(0x2192);
        t1.setText("CONTROLS\nPRESS"+leftArrow+" OR "+rightArrow+" TO FIRE\n\nSTART GAME - ENTER\nQUIT - Q\nGO BACK TO MENU - M");
        t1.setFont(Font.font("Showcard Gothic", 36));
        t1.setFill(Color.WHITE);
        t1.setTextAlignment(TextAlignment.CENTER);

        t1.layoutXProperty().bind(sceneIntroduction.widthProperty().subtract(t1.prefWidth(-1)).divide(2));
        t1.setY(280);
        root1.getChildren().add(t1);

        // add my name & student id
        Text studentID = new Text();
        studentID.setText("Joyce Li\n20798712");
        studentID.setFont(Font.font("Showcard Gothic", 28));
        studentID.setFill(Color.WHITE);
        studentID.setTextAlignment(TextAlignment.CENTER);
        studentID.setY(40);
        studentID.setX(20);
        root1.getChildren().add(studentID);

        Pane root2 = new Pane();
        root2.setBackground(new Background(myBI));
        Scene sceneLevelOne = new Scene(root2, 1280, 640);

        // key event handler
        sceneIntroduction.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)){
                    startTheGame(stage, root2, sceneLevelOne, 1, sceneIntroduction);
                    stage.setScene(sceneLevelOne);
                } else if (keyEvent.getCode().equals(KeyCode.Q)){
                    stage.close();
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT1)){
                    Pane r1 = new Pane();
                    r1.setBackground(new Background(myBI));
                    Scene s1 = new Scene(r1, 1280, 640);
                    startTheGame(stage, r1, s1, 1, sceneIntroduction);
                    stage.setScene(s1);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT2)){
                    Pane r2 = new Pane();
                    r2.setBackground(new Background(myBI));
                    Scene s2 = new Scene(r2, 1280, 640);
                    startTheGame(stage, r2, s2, 2, sceneIntroduction);
                    stage.setScene(s2);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT3)){
                    Pane r3 = new Pane();
                    r3.setBackground(new Background(myBI));
                    Scene s3 = new Scene(r3, 1280, 640);
                    startTheGame(stage, r3, s3, 3, sceneIntroduction);
                    stage.setScene(s3);
                }
            }
        });

        stage.setTitle("Monster vs Sprite");
        stage.setScene(sceneIntroduction);
        stage.setResizable(false);
        stage.show();
    }

    public void startTheGame(Stage stage, Pane root, Scene scene, int level, Scene menuScene){
        Level myLevel = new Level(level, menuScene);

        myLevel.setUp(root, scene);
        myLevel.makeSprite(root, scene);
        myLevel.startTheGame(stage, root, scene);

    }
}