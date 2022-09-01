package view;

import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class DisplayChampionEffect extends BorderPane {
	private static int count;
	private PlayGamePane parent;

	public DisplayChampionEffect(PlayGamePane parent, ChampCard card,
			double width, double height) {
		this.parent = parent;
		this.setMaxWidth(width);
		this.setMaxHeight(height);

		Image image = new Image("capture.PNG");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		this.setBackground(background);

		ChampCard copy1 = new ChampCard(card.getChampion());

		VBox left = new VBox();
		Label currName = new Label(card.getChampion().getName());
		currName.setStyle("-fx-text-fill: white; -fx-font-size: 20px;-fx-font-weight: bold;");
		left.setAlignment(Pos.CENTER);
		left.setPrefWidth(400);
		left.setSpacing(30);
		left.setStyle("-fx-border-size: 2px;-fx-border-style: solid;-fx-border-color: white;");
		displayInfo(card);

		HBox Info = new HBox();
		Info.setAlignment(Pos.CENTER);
		Button info = new Button("Info");
		info.setOnAction(e -> displayInfo(card));

		Button effects = new Button("Effects");
		effects.setOnAction(e -> displayEffects(card));
		
		Info.setSpacing(20);
		Info.getChildren().add(info);
		if (card.getChampion().getAppliedEffects().size() != 0)
			Info.getChildren().add(effects);

		ArrayList<Ability> ability = card.getChampion().getAbilities();

		HBox abilities = new HBox();
		abilities.setAlignment(Pos.CENTER);
		abilities.setSpacing(20);
		Button ab1 = new Button(ability.get(0).getName());
		ab1.setOnAction(e -> displayAbilityOne(ability));
		Button ab2 = new Button(ability.get(1).getName());
		ab2.setOnAction(e -> displayAbilityTwo(ability));
		Button ab3 = new Button(ability.get(2).getName());
		ab3.setOnAction(e -> displayAbilityThree(ability));
		abilities.getChildren().addAll(ab1, ab2, ab3);

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(30);
//		Button select = new Button("Select");
//		select.setOnAction(e -> selectChampion(card));
		Button close = new Button("Close");
		close.setOnAction(e -> {
			((VBox) parent.getLeft()).setStyle("-fx-opacity: 1");
			((VBox) parent.getRight()).setStyle("-fx-opacity: 1");
			// HERE
			((GridPane) parent.getBoard()).setStyle("-fx-opacity: 1");
			((StackPane) parent.getCenter()).getChildren().remove(this);
			
		});
		//HERE
		box.getChildren().addAll(close);

		left.getChildren().addAll(currName, copy1.getCard(), Info, abilities,
				box);

		this.setLeft(left);
		this.setStyle("-fx-border-size: 2px;-fx-border-style: solid;-fx-border-color: white;");
		// HERE
		((StackPane) parent.getCenter()).getChildren().add(this);
	}

	public void displayInfo(ChampCard card) {
		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setSpacing(20);
		Label currName = new Label(card.getChampion().getName());
		Label details = new Label(card.getChampion().toString());
		currName.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		info.getChildren().addAll(currName, details);
		this.setCenter(info);
	}

	public void displayEffects(ChampCard card) {
		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setSpacing(20);
		for (Effect e : card.getChampion().getAppliedEffects()) {
			Label effName = new Label(e.getName() +" "+ e.getDuration());
			//Label details = new Label("" + e.getDuration());
			effName.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
			//details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
			info.getChildren().addAll(effName);
		}
		this.setCenter(info);
	}

	public void displayAbilityOne(ArrayList<Ability> abilities) {
		VBox ability1 = new VBox();
		ability1.setAlignment(Pos.CENTER);
		ability1.setSpacing(30);
		Label ab1Name = new Label(abilities.get(0).getName());
		ab1Name.setAlignment(Pos.CENTER);
		Label ab1Details = new Label(abilities.get(0).toString());
		ab1Details.setAlignment(Pos.CENTER);
		ab1Name.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ab1Details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ability1.getChildren().addAll(ab1Name, ab1Details);
		this.setCenter(ability1);
	}

	public void displayAbilityTwo(ArrayList<Ability> abilities) {
		VBox ability2 = new VBox();
		ability2.setAlignment(Pos.CENTER);
		ability2.setSpacing(30);
		Label ab2Name = new Label(abilities.get(1).getName());
		ab2Name.setAlignment(Pos.CENTER);
		Label ab2Details = new Label(abilities.get(1).toString());
		ab2Details.setAlignment(Pos.CENTER);
		ab2Name.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ab2Details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ability2.getChildren().addAll(ab2Name, ab2Details);
		this.setCenter(ability2);
	}

	public void displayAbilityThree(ArrayList<Ability> abilities) {
		VBox ability3 = new VBox();
		ability3.setAlignment(Pos.CENTER);
		ability3.setSpacing(30);
		Label ab3Name = new Label(abilities.get(2).getName());
		ab3Name.setAlignment(Pos.CENTER);
		Label ab3Details = new Label(abilities.get(2).toString());
		ab3Details.setAlignment(Pos.CENTER);
		ab3Name.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ab3Details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		ability3.getChildren().addAll(ab3Name, ab3Details);
		this.setCenter(ability3);
	}
	
}
