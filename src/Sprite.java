
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class Sprite{
    int myLevel;
    String myImage = "resource/img/fire.png";
    String myFireBallImage = "resource/img/fireball2.gif";
    Image spriteImage;
    ImageView spriteImageView;
    double width;
    double height;
    double x;
    double y;

    Image fireBallImage;
    ImageView fireBallImageView;

    Sprite(int level){
        myLevel = level;
        spriteImage = new Image(getClass().getResource(myImage).toExternalForm());
        spriteImageView = new ImageView(spriteImage);
        spriteImageView.setFitWidth(220);
        spriteImageView.setFitHeight(250);
        spriteImageView.setPreserveRatio(true);
        width = spriteImageView.getFitWidth();
        height = spriteImageView.getFitHeight();
        x = spriteImageView.getX();
        y = spriteImageView.getY();
    }

    private class FireBall{
        FireBall(int level){
            fireBallImage = new Image(getClass().getResource(myFireBallImage).toExternalForm());
            fireBallImageView = new ImageView(fireBallImage);
            fireBallImageView.setPreserveRatio(true);
        }
    }

    public void makeSprite(Pane root, Scene scene){
        spriteImageView.setPreserveRatio(true);
        spriteImageView.setX(530);
        spriteImageView.setY(300);
        root.getChildren().add(spriteImageView);
    }

    public boolean contains(double localX, double localY){
        return spriteImageView.contains(localX, localY);
    }

    public ImageView getFireBallImageView(){
        return fireBallImageView;
    }

    public ImageView attack(boolean left, Pane root, Scene scene, List<Enemy> enemy, Player player){
        FireBall fb = new FireBall(myLevel);
        ImageView imageView = new ImageView(fireBallImage);
        imageView.setY(450);
        root.getChildren().add(imageView);

        if(left){
            // flip
            spriteImageView.setScaleX(1);
            imageView.setScaleX(-1);
            imageView.setX(590);
        }else{
            // flip
            spriteImageView.setScaleX(-1);
            imageView.setScaleX(1);
            imageView.setX(660);
        }
        return imageView;

    }

    public float getFireBallLifeTime(int level){
        if (level==1){
            return 5.0f;
        } else if (level==2){
            return 7.0f;
        } else {
            return 9.0f;
        }
    }

    public int getFireBallDistance(int level, boolean left){
        if (left) {
            if (level == 1) {
                return 440;
            } else if (level == 2) {
                return 470;
            } else {
                return 500;
            }
        } else {
            if (level == 1) {
                return 810;
            } else if (level == 2) {
                return 780;
            } else {
                return 750;
            }
        }
    }

}


