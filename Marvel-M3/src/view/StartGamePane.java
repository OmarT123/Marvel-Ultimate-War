package view;

import engine.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class StartGamePane extends BorderPane {

	private TextField player1Name, player2Name;
	private GameButton playButton, quitButton;
	private GameView gameView;

	public StartGamePane(GameView gameView) {
		this.gameView = gameView;
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10));
		root.setSpacing(20);

		Image image = new Image("introbackground.jpeg");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		root.setBackground(background);

		Label player1Label = new Label("Player 1");
		player1Label.setStyle("-fx-font-size: 20");
		player1Name = new TextField();
		player1Name.setPromptText("Enter a name");
		player1Name.setMaxSize(300, 50);

		Label player2Label = new Label("Player 2");
		player2Label.setStyle("-fx-font-size: 20");
		player2Name = new TextField();
		player2Name.setPromptText("Enter a name");
		player2Name.setMaxSize(300, 50);

		playButton = new GameButton("Play");
		playButton.setStyle("-fx-font-size: 15");
		playButton.setSize(100, 70);
		playButton.setOnMouseClicked(e -> {
			player1Name.setStyle("-fx-border-color: gray");
			player2Name.setStyle("-fx-border-color: gray");
			if (player1Name.getText().isEmpty())
				player1Name.setStyle("-fx-border-color: red");
			if (player2Name.getText().isEmpty())
				player2Name.setStyle("-fx-border-color: red");
			if (!player1Name.getText().isEmpty()
					&& !player2Name.getText().isEmpty()) {
				this.gameView.setPlayerOneName(player1Name.getText());
				this.gameView.setPlayerTwoName(player2Name.getText());
				this.gameView.setPlayer1(new Player(player1Name.getText()));
				this.gameView.setPlayer2(new Player(player2Name.getText()));
				Game temp = new Game(gameView.getPlayer1(),gameView.getPlayer2());
				try {
					Game.loadAbilities("Abilities.csv");
					Game.loadChampions("Champions.csv");
					this.gameView.setAvailableAbilities(Game.getAvailableAbilities());
					this.gameView.setAvailableChampions(Game.getAvailableChampions());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.gameView.setTeamsPane(new CreateTeamsPane(gameView));
				this.gameView.setPane(this.gameView.getTeamsPane());
			}
		});

		quitButton = new GameButton("Quit");
		quitButton.setStyle("-fx-font-size: 15");
		quitButton.setSize(100, 70);
		quitButton.setOnMouseClicked(e -> gameView.close());

		root.getChildren().addAll(player1Label, player1Name, player2Label,
				player2Name, playButton, quitButton);

		this.setCenter(root);
	}
}
