package view;

import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EffectView extends StackPane {

	private Effect effect;
	private ImageView imageView;
	private VBox info;

	public EffectView(Effect effect) {
		this.effect = effect;
		
		Image image = new Image("file:effects/" + effect.getName() + ".png");
		imageView = new ImageView(image);
		imageView.setFitHeight(60);
		imageView.setFitWidth(60);
		this.setPrefWidth(80);
		this.getChildren().add(imageView);
		this.setAlignment(Pos.CENTER);
		this.setOnMouseEntered(e -> {
			addInfo();
		});
		this.setOnMouseExited(e -> {
			this.getChildren().remove(info);
		});
	}
	
	public void addInfo(){
		info = new VBox();
		Label label1 = new Label(effect.getName()+", "+effect.getDuration());
		label1.setAlignment(Pos.CENTER);
		label1.setTextFill(Color.WHITE);
		info.getChildren().add(label1);
		this.getChildren().add(info);
	}
}
