package view;

import model.abilities.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AbilityImage extends StackPane {
	private Ability ability;
	private ImageView imageView;
	private VBox info;

	public AbilityImage(Ability a) {
		this.ability = a;
		Image image = new Image("file:abilities/" + a.getName() + ".png");
		imageView = new ImageView(image);
		imageView.setFitHeight(90);
		imageView.setFitWidth(90);
		this.setPrefWidth(150);
		this.getChildren().add(imageView);
		this.setOnMouseEntered(e -> {
			addInfo(a);
		});
		this.setOnMouseExited(e -> {
			this.getChildren().remove(info);
		});

	}

	public void setSize(double width, double height) {
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
	}

	public void addInfo(Ability a) {
		info = new VBox();
		String type;
		if (a instanceof CrowdControlAbility)
			type = "CC";
		else if (a instanceof HealingAbility)
			type = "HEL";
		else
			type = "DMG";

		Label label1 = new Label(a.getName());
		label1.setAlignment(Pos.CENTER);
		Label label2 = new Label(type + ", " + a.getCastArea());
		label2.setAlignment(Pos.CENTER);
		label1.setTextFill(Color.WHITE);
		info.getChildren().add(label1);
		label2.setTextFill(Color.WHITE);
		info.getChildren().add(label2);
		if (type.equals("CC")) {
			Label label3 = new Label(((CrowdControlAbility) a).getEffect()
					.getName()
					+ " "
					+ ((CrowdControlAbility) a).getEffect().getDuration());
			label3.setTextFill(Color.WHITE);
			label3.setAlignment(Pos.CENTER);
			info.getChildren().add(label3);
		}
		this.getChildren().add(info);
	}
}
