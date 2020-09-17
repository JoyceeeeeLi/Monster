import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy{
    int myLevel;
    String myImage = "resource/img/enemy.png";
    Image enemyImage;
    ImageView enemyImageView;
    double width;
    double height;
    AnimationTimer timer;

    float dx;
    double enemy_x = -120;
    double enemy_y = 330;

    Enemy(int level){
        myLevel = level;
        enemyImage = new Image(getClass().getResource(myImage).toExternalForm());
        enemyImageView = new ImageView(enemyImage);
        enemyImageView.setPreserveRatio(true);
        enemyImageView.setFitWidth(220);
        width = enemyImageView.getFitWidth();
        height = enemyImageView.getFitHeight();
        if (level == 1){
            dx = 1.0f;
        } else if(level ==2){
            dx = 1.5f;
        } else {
            dx = 2.0f;
        }
    }

    public void setX(double x){
       enemyImageView.setX(x);
    }

    public void setY(double y){
        enemyImageView.setY(y);
    }

    public void addToRoot(Pane root){
        root.getChildren().add(enemyImageView);
    }

    public void flipX(){
        enemyImageView.setScaleX(-1);
    }

    public double getEnemyX(){
        return enemy_x;
    }

    public double getEnemyY(){
        return enemy_y;
    }

    public void modifyEnemyX(double x){
        enemy_x = x;
    }

    public void enemyxPlusDX(){
        enemy_x += dx;
    }

    public void enemyxMinusDX(){
        enemy_x -= dx;
    }

    public ImageView getImageView(){
        return enemyImageView;
    }

    public boolean contains(double localX, double localY){
        return enemyImageView.contains(localX, localY);
    }

    public void addTimer(AnimationTimer t){
        this.timer = t;
        timer.start();
    }

    public void stop(){
        timer.stop();
    }

}