package view;

import model.world.Direction;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class moveButtons extends BorderPane{
	private StackPane up = new StackPane(), down = new StackPane(), left = new StackPane(), right = new StackPane(), center = new StackPane();
	private GameView gameView;
	
	public moveButtons(GameView gv, double width, double height){
		gameView = gv;
		up.setOnMouseClicked(e -> gameView.getListener().onHandleActions(Direction.UP));
		down.setOnMouseClicked(e -> gameView.getListener().onHandleActions(Direction.DOWN));
		left.setOnMouseClicked(e -> gameView.getListener().onHandleActions(Direction.LEFT));
		right.setOnMouseClicked(e -> gameView.getListener().onHandleActions(Direction.RIGHT));
		
		Image image = new Image("moveRight.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		right.getChildren().add(imageView);
		
		Image image1 = new Image("moveUp.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitWidth(50);
		imageView1.setFitHeight(50);
		up.getChildren().add(imageView1);
		
		Image image2 = new Image("moveLeft.png");
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(50);
		imageView2.setFitHeight(50);
		left.getChildren().add(imageView2);
		
		Image image3 = new Image("moveDown.png");
		ImageView imageView3 = new ImageView(image3);
		imageView3.setFitWidth(50);
		imageView3.setFitHeight(50);
		down.getChildren().add(imageView3);
		
		center.setPrefSize(50, 50);
		up.setAlignment(Pos.CENTER);
		down.setAlignment(Pos.CENTER);
		
		
		this.setTop(up);
		this.setBottom(down);
		this.setCenter(center);
		this.setLeft(left);
		this.setRight(right);
	}
	
	
	
	
	
}
