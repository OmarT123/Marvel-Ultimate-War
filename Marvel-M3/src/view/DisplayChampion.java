package view;

import java.io.File;
import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;
import model.world.Champion;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DisplayChampion extends BorderPane {
	private static int count;
	private CreateTeamsPane parent;

	public DisplayChampion(CreateTeamsPane parent, ChampCard card,
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

		// this.setStyle("-fx-background-color: rgba(0,0,0,0.9);-fx-border-size: 2px;-fx-border-style: solid;-fx-border-color: white;");

		ChampCard copy1 = new ChampCard(card.getChampion());

		File f = new File("gameAudio/"
				+ filterSpaces(copy1.getChampion().getName()) + ".mp3");
		Media media = new Media(f.toURI().toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		mp.play();

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
		Image img = new Image("info.png");
		ImageView infoImg = new ImageView(img);
		infoImg.setFitHeight(50);
		infoImg.setFitWidth(50);

		infoImg.setOnMouseClicked(e -> displayInfo(card));

		Button effects = new Button("Effects");
		effects.setOnAction(e -> displayEffects(card));

		Info.getChildren().add(infoImg);
		if (card.getChampion().getAppliedEffects().size() != 0)
			Info.getChildren().add(effects);

		ArrayList<Ability> ability = card.getChampion().getAbilities();

		HBox abilities = new HBox();
		abilities.setAlignment(Pos.CENTER);
		abilities.setSpacing(30);
		Image ab1 = new Image("file:abilities/"
				+ card.getChampion().getAbilities().get(0).getName() + ".png");
		ImageView ability1 = new ImageView(ab1);
		ability1.setFitHeight(60);
		ability1.setFitWidth(60);
		ability1.setOnMouseClicked(e -> displayAbilityOne(ability));

		Image ab2 = new Image("file:abilities/"
				+ card.getChampion().getAbilities().get(1).getName() + ".png");
		ImageView ability2 = new ImageView(ab2);
		ability2.setFitHeight(60);
		ability2.setFitWidth(60);

		ability2.setOnMouseClicked(e -> displayAbilityTwo(ability));

		Image ab3 = new Image("file:abilities/"
				+ card.getChampion().getAbilities().get(2).getName() + ".png");
		ImageView ability3 = new ImageView(ab3);
		ability3.setFitHeight(60);
		ability3.setFitWidth(60);

		ability3.setOnMouseClicked(e -> displayAbilityThree(ability));
		abilities.getChildren().addAll(ability1, ability2, ability3);

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(30);
		GameButton select = new GameButton("Select");
		select.setOnMouseClicked(e -> selectChampion(card));
		select.setSize(100, 50);
		GameButton close = new GameButton("Close");
		close.setSize(100, 50);
		close.setOnMouseClicked(e -> {
			// HERE
			((VBox) parent.getLeft()).setStyle("-fx-opacity: 1");
			((VBox) parent.getRight()).setStyle("-fx-opacity: 1");
			((GridPane) parent.getScene2Grid()).setStyle("-fx-opacity: 1");
			((StackPane) parent.getCenter()).getChildren().remove(this);
			parent.getPlay().setStyle("-fx-opacity: 1");
		});
		box.getChildren().addAll(select, close);

		left.getChildren().addAll(currName, copy1.getCard(), infoImg,
				abilities, box);

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
			Label effName = new Label(e.getName());
			Label details = new Label("" + e.getDuration());
			effName.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
			details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
			info.getChildren().addAll(effName, details);
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

	public ChampCard addToFirstPlayer(Champion c) {
		ChampCard card = new ChampCard(c);
		((VBox) parent.getLeft()).getChildren().addAll(card.getCard());
		return card;
	}

	public ChampCard addToSecondPlayer(Champion c) {
		ChampCard card = new ChampCard(c);
		Button leader = new Button("Leader");
		((VBox) parent.getRight()).getChildren().addAll(card.getCard());
		return card;
	}

	public void selectChampion(ChampCard champ) {
		if (count == 6) {
			// alert *********************************
		} else {
			ChampCard card;
			if ((count & 1) == 0) {
				card = addToFirstPlayer(champ.getChampion());
				parent.getGameView().getPlayer1().getTeam()
						.add(champ.getChampion());
			} else {
				card = addToSecondPlayer(champ.getChampion());
				parent.getGameView().getPlayer2().getTeam()
						.add(champ.getChampion());
			}
			parent.getSelectedCards().add(card);
			champ.getCard().setDisable(true);
			count++;
			((VBox) parent.getLeft()).setStyle("-fx-opacity: 1");
			((VBox) parent.getRight()).setStyle("-fx-opacity: 1");
			((GridPane) parent.getScene2Grid()).setStyle("-fx-opacity: 1");
			((StackPane) parent.getCenter()).getChildren().remove(this);
			parent.getPlay().setStyle("-fx-opacity: 1");
		}
	}

	public String filterSpaces(String s) {
		return s.replaceAll("\\s+", "");
	}
}
