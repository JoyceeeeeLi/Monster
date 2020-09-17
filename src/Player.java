import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Player{
    int myScores = 0;
    Text scores;

    int lives = 3;
    int myLevel;
    Text hearts;

    int numberOfEnemy = 20;
    Text enemies;

    Player(int level){
        myLevel = level;
        hearts = new Text();
        scores = new Text();
        enemies = new Text();
    }

    public void decreaseLives(Pane root, Scene scene){
        --lives;
        if(lives == 2){
            String heart = Character.toString(0x2764);
            hearts.setText(heart+heart);
        } else if(lives == 1){
            String heart = Character.toString(0x2764);
            hearts.setText(heart);
        } else {
            hearts.setText("");
        }
    }

    public void setUpScores(Pane root, Scene scene){
        // setting scores
        scores.setText(Integer.toString(myScores));
        scores.setFont(Font.font("Showcard Gothic", 38));
        scores.setFill(Color.WHITE);
        scores.setTextAlignment(TextAlignment.CENTER);
        scores.setY(600);
        scores.setX(50);
        root.getChildren().add(scores);
    }

    public int getLives(){
        return lives;
    }

    public int getMyScores(){
        return myScores;
    }

    public void setUpHearts(Pane root, Scene scene){
        // setting hearts
        String heart = Character.toString(0x2764);
        hearts.setText(heart+heart+heart);
        hearts.setFont(Font.font("Showcard Gothic", 32));
        hearts.setFill(Color.WHITE);
        hearts.setTextAlignment(TextAlignment.CENTER);
        hearts.setY(600);
        hearts.layoutXProperty().bind(scene.widthProperty().subtract(hearts.prefWidth(-1)).divide(2));
        root.getChildren().add(hearts);
    }

    public void setUpEnemy(Pane root, Scene scene){
        // setting enemies
        enemies.setText("Enemies: "+Integer.toString(numberOfEnemy));
        enemies.setFont(Font.font("Showcard Gothic", 32));
        enemies.setFill(Color.WHITE);
        enemies.setTextAlignment(TextAlignment.CENTER);
        enemies.setY(50);
        enemies.setX(30);
        root.getChildren().add(enemies);
    }

    public void decreaseEnemies(){
        enemies.setText("Enemies: "+Integer.toString(numberOfEnemy-1));
        --numberOfEnemy;
    }

    public void increaseScores(){
        scores.setText(Integer.toString(myScores+1));
        ++myScores;
    }

    public void decreaseScores(){
        scores.setText(Integer.toString(myScores-1));
        --myScores;
    }
}