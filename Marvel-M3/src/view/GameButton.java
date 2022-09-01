package view;

import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class GameButton extends StackPane {

	private ImageView imageView;

	public GameButton(String word) {
		Image img = new Image("Button.png");
		imageView = new ImageView(img);
		imageView.setFitHeight(50);
		imageView.setFitWidth(100);
		Label label = new Label(word);
		label.setStyle("-fx-text-fill: white");
		this.getChildren().addAll(imageView, label);
	}

	public void setSize(double width, double height) {
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
	}
}
