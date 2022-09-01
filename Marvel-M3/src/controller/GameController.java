package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import model.abilities.Ability;
import model.effects.Effect;
import model.world.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import engine.*;
import exceptions.*;
import view.*;

public class GameController extends Application implements GameViewListener {
	private GameView view;
	private Game game;

	@Override
	public void onPlay() {
		if (!view.getTeamsPane().isLeader1Selected()
				|| !view.getTeamsPane().isLeader2Selected()) {
			new AlertBox((Pane) view.getLeaderPane().getCenter(),
					"You must choose team leader for each team");
		} else {
			game = new Game(view.getPlayer1(), view.getPlayer2());
			view.setPane(new PlayGamePane(view));
		}
	}

	public Game getGame() {
		return this.game;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		view = new GameView(1920, 1000);
		view.setListener(this);
		File f = new File(
				"gameAudio/game.mp3");
		Media media = new Media(f.toURI().toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		mp.setCycleCount(MediaPlayer.INDEFINITE);
		
		mp.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onCreateBoard() {
		Object[][] gameBoard = game.getBoard();
		GridPane grid = view.getPlayGame().getBoard();
		grid.getChildren().removeAll(grid.getChildren());
		view.getPlayGame().getBoard().getChildren().removeAll();

		PlayGamePane.setCol(0);
		PlayGamePane.setRow(0);

		for (int i = 4; i >= 0; i--) {
			for (int j = 0; j < 5; j++) {
				if (gameBoard[i][j] instanceof Champion) {
					ChampionCard card = new ChampionCard(
							(Champion) gameBoard[i][j]);
					if (game.getSecondPlayer().getTeam()
							.contains((Champion) gameBoard[i][j])) {
						card.setColor("red");
					}
					final int x = i;
					final int y = j;
					card.setOnMouseClicked(e -> handleCastSingle(x, y));
					card.setOnMouseEntered(e -> card.setSize(90, 115));
					card.setOnMouseExited(e -> card.setSize(80, 100));
					view.getPlayGame().addToBoard(card);
				} else if (gameBoard[i][j] instanceof Cover) {
					CoverCard card = new CoverCard((Cover) gameBoard[i][j]);
					ProgressBar bar = new ProgressBar();
					card.setBar(bar);
					final int x = i;
					final int y = j;
					card.setOnMouseClicked(e -> handleCastSingle(x, y));
					card.setOnMouseEntered(e -> card.setSize(115, 115));
					card.setOnMouseExited(e -> card.setSize(100, 100));
					view.getPlayGame().addToBoard(card);
				} else {
					Button card = new Button();
					card.setPrefSize(150, 150);
					card.setStyle("-fx-opacity: 0.1");
					final int x = i;
					final int y = j;
					card.setOnAction(e -> handleCastSingle(x, y));
					view.getPlayGame().addToBoard(card);
				}

			}
		}

		// ((Pane)(view.getPlayGame().getCenter())).getChildren().add(grid);
	}

	@Override
	public void onUpdateTurn() {
		HBox turn = view.getPlayGame().getTurn();
		turn.getChildren().removeAll(turn.getChildren());
		Stack<Champion> temp = new Stack<>();
		while (!game.getTurnOrder().isEmpty()) {
			Champion curr = (Champion) (game.getTurnOrder().remove());
			temp.push(curr);
		}
		while (!temp.isEmpty()) {
			Champion curr = temp.pop();
			game.getTurnOrder().insert(curr);
			ChampCard cc = new ChampCard(curr);
			cc.setWidth(70, 100);
			view.getPlayGame().addToTurn(cc);
		}

	}

	private void handleAttack(Direction d) {
		try {
			game.attack(d);
			onCreateBoard();
			onUpdateTurn();
			File f = new File("gameAudio/attack.mp3");
			Media media = new Media(f.toURI().toString());
			MediaPlayer mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			mp.play();
		} catch (NotEnoughResourcesException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		} catch (ChampionDisarmedException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		} catch (InvalidTargetException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		}

		updateBalabizo();
	}

	public void handleCastSingle(int i, int j) {
		if (view.getPlayGame().isOnCastSingle()
				&& view.getPlayGame().getCurrAbility() != null) {
			try {
				game.castAbility(view.getPlayGame().getCurrAbility(), i, j);
				if(!view.getPlayGame().getCurrAbility().getName().equals("Punch")){
					File f = new File("gameAudio/"
							+ filterSpaces(view.getPlayGame().getCurrAbility()
									.getName()) + ".mp3");
					Media media = new Media(f.toURI().toString());
					MediaPlayer mp = new MediaPlayer(media);
					mp.setAutoPlay(true);
					mp.play();
				}
			} catch (NotEnoughResourcesException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			} catch (AbilityUseException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			} catch (InvalidTargetException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			} catch (CloneNotSupportedException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			}
			updateBalabizo();
		} else {
			if (game.getBoard()[i][j] instanceof Champion)
				onCreateLeft((Champion) game.getBoard()[i][j]);
		}
	}

	private void handleCastDirectional(Direction d) {
		try {
			game.castAbility(view.getPlayGame().getCurrAbility(), d);
			File f = new File("gameAudio/"
					+ filterSpaces(view.getPlayGame().getCurrAbility()
							.getName()) + ".mp3");
			Media media = new Media(f.toURI().toString());
			MediaPlayer mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			mp.play();
		} catch (NotEnoughResourcesException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		} catch (AbilityUseException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		} catch (CloneNotSupportedException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		}
		updateBalabizo();

	}

	@Override
	public void onHandleActions(Direction direction) {
		if (view.getPlayGame().isOnAttack()) {
			handleAttack(direction);
			return;
		} else if (view.getPlayGame().isOnCastDirectional()) {

			handleCastDirectional(direction);
			return;
		}
		// move
		else if (!view.getPlayGame().isOnCast()
				&& !view.getPlayGame().isOnCastSingle()) {
			try {
				game.move(direction);
				onCreateBoard();
				onCreateLeft(game.getCurrentChampion());
			} catch (UnallowedMovementException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			} catch (NotEnoughResourcesException e) {
				new AlertBox((Pane) view.getPlayGame().getCenter(),
						e.getMessage());
			}
		}

	}

	@Override
	public Ability abilityButton(int index) {
		Champion c = game.getCurrentChampion();
		return (c.getAbilities().get(index));
	}

	@Override
	public void handleCast() {
		try {
			game.castAbility(view.getPlayGame().getCurrAbility());
			File f = new File("gameAudio/"
					+ filterSpaces(view.getPlayGame().getCurrAbility()
							.getName()) + ".mp3");
			Media media = new Media(f.toURI().toString());
			MediaPlayer mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			mp.play();
		} catch (AbilityUseException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());

		} catch (NotEnoughResourcesException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		} catch (CloneNotSupportedException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		}
		updateBalabizo();
	}

	@Override
	public void onEndTurn() {
		game.endTurn();
		updateBalabizo();
		File f = new File(
				"gameAudio/"
						+ filterSpaces(((Champion) (game.getTurnOrder()
								.peekMin())).getName()) + ".mp3");
		Media media = new Media(f.toURI().toString());
		MediaPlayer mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		mp.play();
	}

	@Override
	public void castLeaderAbility() {
		try {
			game.useLeaderAbility();
			onCreateLeft(getCurrChamp());
			isGameOver();
			File f = new File(
					"gameAudio/"
							+ filterSpaces(((Champion) (game.getTurnOrder()
									.peekMin())).getName()) + ".mp3");
			Media media = new Media(f.toURI().toString());
			MediaPlayer mp = new MediaPlayer(media);
			mp.setAutoPlay(true);
			mp.play();
		} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e) {
			new AlertBox((Pane) view.getPlayGame().getCenter(), e.getMessage());
		}

	}

	public void isGameOver() {
		Player winner = game.checkGameOver();
		if (winner != null) {
			new EndGamePane(view, winner,game);
		}
	}

	public void updateBalabizo() {
		isGameOver();
		onCreateBoard();
		onUpdateTurn();
		onCreateLeft(game.getCurrentChampion());
		view.getPlayGame().setOnAttack(false);
		view.getPlayGame().setOnCastSingle(false);
		view.getPlayGame().setOnCastDirectional(false);
		view.getPlayGame().setOnCast(false);
		view.getPlayGame().setCurrAbility(null);
		view.getPlayGame().addControls();
	}

	@Override
	public void onCreateLeft(Champion curr) {
		StackPane pane = view.getPlayGame().getLeftPane();
		pane.getChildren().removeAll(pane.getChildren());
		VBox left = new VBox();
		left.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(left);

		left.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
		HBox top = new HBox();
		top.setPrefSize(600, 70);
		top.setPadding(new Insets(10, 0, 10, 0));
		top.setAlignment(Pos.CENTER);
		top.setSpacing(100);
		top.setStyle("-fx-background-color: rgba(0,0,255,0.5); -fx-font-size: 15; -fx-text-fill: white");
		if (curr == game.getCurrentChampion()) {
			String name;
			if (view.getPlayer1().getTeam().contains(game.getCurrentChampion()))
				name = view.getPlayerOneName();
			else
				name = view.getPlayerTwoName();

			Label label1 = new Label(name + "'turn");

			label1.setStyle("-fx-font-size: 20; -fx-text-fill: white");

			GameButton leaderAb = new GameButton("Use Leader Ability");
			leaderAb.setOnMouseClicked(e -> castLeaderAbility());
			leaderAb.setSize(200, 70);

			if (game.getFirstPlayer().getTeam().contains(curr)
					&& game.isFirstLeaderAbilityUsed()
					|| game.getSecondPlayer().getTeam().contains(curr)
					&& game.isSecondLeaderAbilityUsed()) {
				leaderAb.setStyle("-fx-opacity: 0.5");
			}

			top.getChildren().addAll(label1, leaderAb);
		} else {
			top.setPrefSize(600, 90);
			top.setPadding(new Insets(0));
			GameButton exit = new GameButton("cancel");
			exit.setSize(150, 70);
			exit.setStyle("-fx-font-size: 15;-fx-text-fill: white;");
			exit.setAlignment(Pos.CENTER);
			exit.setOnMouseClicked(e -> {
				left.getChildren().removeAll(left.getChildren());
				onCreateLeft(game.getCurrentChampion());
			});
			top.getChildren().add(exit);
		}

		HBox champ = new HBox();
		champ.setPadding(new Insets(20, 0, 20, 0));
		champ.setMaxHeight(250);
		ChampionCard card = new ChampionCard(curr);
		card.setSize(150, 200);

		champ.setAlignment(Pos.CENTER);
		champ.setSpacing(100);

		VBox info = new VBox(), box = new VBox();

		Image img = new Image("info.png");
		ImageView infoImg = new ImageView(img);
		infoImg.setFitHeight(50);
		infoImg.setFitWidth(50);

		infoImg.setOnMouseClicked(e -> displayInfo(box, card));
		displayInfo(box, card);

		Image ab1 = new Image("file:abilities/"
				+ curr.getAbilities().get(0).getName() + ".png");
		ImageView ability1 = new ImageView(ab1);
		ability1.setFitHeight(60);
		ability1.setFitWidth(60);
		ability1.setOnMouseClicked(e -> displayAbility(box, curr.getAbilities()
				.get(0)));

		Image ab2 = new Image("file:abilities/"
				+ curr.getAbilities().get(1).getName() + ".png");
		ImageView ability2 = new ImageView(ab2);
		ability2.setFitHeight(60);
		ability2.setFitWidth(60);

		ability2.setOnMouseClicked(e -> displayAbility(box, curr.getAbilities()
				.get(1)));

		Image ab3 = new Image("file:abilities/"
				+ curr.getAbilities().get(2).getName() + ".png");
		ImageView ability3 = new ImageView(ab3);
		ability3.setFitHeight(60);
		ability3.setFitWidth(60);

		ability3.setOnMouseClicked(e -> displayAbility(box, curr.getAbilities()
				.get(2)));

		info.getChildren().addAll(ability1, ability2, ability3);
		info.setSpacing(10);

		champ.getChildren().addAll(card.getIcon(), infoImg, info);

		left.getChildren().addAll(top, champ, box);

	}

	public void displayInfo(VBox parent, ChampionCard card) {
		parent.getChildren().removeAll(parent.getChildren());
		Label label1 = new Label(card.getChamp().getName());
		label1.setPrefSize(600, 30);
		label1.setAlignment(Pos.CENTER);
		label1.setPadding(new Insets(5, 0, 5, 0));
		label1.setStyle("-fx-background-color: rgba(0,0,255,0.5); -fx-font-size: 25; -fx-text-fill: white");

		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setSpacing(20);
		String s = "";
		if (view.getPlayer1().getLeader().equals(card.getChamp())
				|| view.getPlayer2().getLeader().equals(card.getChamp())) {
			s = "Leader \n";
		}
		Label details = new Label(s + card.getChamp().toString());
		details.setPadding(new Insets(0, 0, 20, 0));
		details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		info.getChildren().addAll(label1, details);
		parent.getChildren().add(info);

		StackPane cont = new StackPane();
		cont.setPadding(new Insets(10, 0, 0, 0));

		HBox effects = new HBox();
		effects.setAlignment(Pos.BASELINE_LEFT);
		effects.setPadding(new Insets(5, 0, 5, 50));
		effects.setSpacing(10);

		Image img = new Image("file:effects/effects.png");
		ImageView effectImg = new ImageView(img);
		effectImg.setFitWidth(500);
		effectImg.setFitHeight(120);

		Label label2 = new Label("Effects");
		label2.setPrefSize(600, 30);
		label2.setAlignment(Pos.CENTER);
		label2.setPadding(new Insets(10, 0, 5, 0));
		label2.setStyle("-fx-background-color: rgba(0,0,255,0.5); -fx-font-size: 25; -fx-text-fill: white");

		for (Effect e : card.getChamp().getAppliedEffects()) {
			EffectView ev = new EffectView(e);
			effects.getChildren().add(ev);
		}

		cont.getChildren().addAll(effectImg, effects);

		parent.getChildren().addAll(label2, cont);
	}

	public void displayAbility(VBox parent, Ability a) {
		parent.getChildren().removeAll(parent.getChildren());
		Label label1 = new Label(a.getName());
		label1.setPrefSize(600, 30);
		label1.setAlignment(Pos.CENTER);
		label1.setPadding(new Insets(5, 0, 5, 0));
		label1.setStyle("-fx-background-color: rgba(0,0,255,0.5); -fx-font-size: 25; -fx-text-fill: white");

		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setSpacing(20);
		Label details = new Label(a.toString());
		details.setPadding(new Insets(0, 0, 20, 0));
		details.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		info.getChildren().addAll(label1, details);
		parent.getChildren().add(info);
	}

	public String filterSpaces(String s) {
		return s.replaceAll("\\s+", "");
	}

	@Override
	public Champion getCurrChamp() {
		return game.getCurrentChampion();
	}

}
