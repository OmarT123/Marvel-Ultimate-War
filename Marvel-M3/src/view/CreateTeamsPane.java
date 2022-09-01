package view;

import java.util.ArrayList;

import model.world.Champion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CreateTeamsPane extends BorderPane {
	private GameView gameView;
	private VBox left, right;
	private StackPane center;
	private GridPane scene2Grid;
	private static int col, row;
	private boolean leader1Selected, leader2Selected;
	private GameButton play;
	private ArrayList<ChampCard> selectedCards;

	public CreateTeamsPane(GameView gameView) {
		this.gameView = gameView;
		left = new VBox();
		left.setAlignment(Pos.TOP_CENTER);
		left.setPrefWidth(400);
		left.setPadding(new Insets(80, 0, 0, 0));
		left.setSpacing(30);

		Label name1 = new Label(gameView.getPlayerOneName());
		name1.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");
		left.getChildren().addAll(name1);
		this.setLeft(left);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.TOP_CENTER);
		bottom.setPrefHeight(150);
		bottom.setPadding(new Insets(-100,0,0,0));
		play = new GameButton("Next");
		play.setSize(150, 100);
		play.setOnMouseClicked(e -> {
			if (gameView.getPlayer2().getTeam().size() != 3) {
				new AlertBox((Pane) gameView.getTeamsPane().getCenter(),
						"You must choose 3 champions for each team");
			}
			else
				gameView.setPane(new ChooseLeaderPane(gameView));
			});
		bottom.getChildren().add(play);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		center = new StackPane();
		center.setAlignment(Pos.CENTER);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		scene2Grid = new GridPane();
		center.getChildren().add(scene2Grid);
		scene2Grid.setVgap(20);
		scene2Grid.setHgap(20);
		scene2Grid.setAlignment(Pos.CENTER);
		selectedCards = new ArrayList<>();

		ArrayList<Champion> champions = gameView.getAvailableChampions();
		for (Champion c : champions) {
			ChampCard champion = new ChampCard(c);
			champion.getCard().setOnAction(e -> {
				left.setStyle("-fx-opacity: 0.3");
				right.setStyle("-fx-opacity: 0.3");
				scene2Grid.setStyle("-fx-opacity: 0.3");
				play.setStyle("-fx-opacity: 0.3");
				new DisplayChampion(this, champion, 800, 620);
			});
			addChampion(champion.getCard());
		}
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		right = new VBox();
		right.setAlignment(Pos.TOP_CENTER);
		right.setPrefWidth(400);
		right.setPadding(new Insets(80, 0, 0, 0));
		Label name2 = new Label(gameView.getPlayerTwoName());
		name2.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");
		right.setSpacing(30);
		right.getChildren().addAll(name2);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		this.setPadding(new Insets(10));
		// this.setTop(top);
		this.setLeft(left);
		this.setRight(right);
		this.setCenter(center);
		this.setBottom(bottom);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Image image = new Image("scene2Background.jpg");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		this.setBackground(background);

	}

	public GameView getGameView() {
		return gameView;
	}

	public GridPane getScene2Grid() {
		return scene2Grid;
	}

	public void addChampion(Button card) {
		scene2Grid.add(card, col++, row);
		update();
	}

	public void update() {
		if (col == 5) {
			col = 0;
			row++;
		}
	}

	public boolean isLeader1Selected() {
		return leader1Selected;
	}

	public void setLeader1Selected(boolean leader1Selected) {
		this.leader1Selected = leader1Selected;
	}

	public boolean isLeader2Selected() {
		return leader2Selected;
	}

	public void setLeader2Selected(boolean leader2Selected) {
		this.leader2Selected = leader2Selected;
	}

	public ArrayList<ChampCard> getSelectedCards() {
		return selectedCards;
	}

	public GameButton getPlay() {
		return play;
	}

}
