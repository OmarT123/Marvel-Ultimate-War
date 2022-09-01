package view;

import model.world.Champion;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ChampCard extends Application{
	private Button card;
	private Image image;
	private Champion champion;
	private ImageView iv;

	public ChampCard(Champion champ) {
		card = new Button();
		this.image = new Image(champ.getName() + ".jpg");
		iv = new ImageView(image);
		iv.setFitWidth(120);
		iv.setFitHeight(180);
		card.setGraphic(iv);
		champion = champ;
	}
	
	public void setWidth(double width,double height){
		iv.setFitHeight(height);
		iv.setFitWidth(width);
	}

	public Button getCard() {
		return card;
	}

	public Image getImage() {
		return image;
	}

	public Champion getChampion() {
		return champion;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
