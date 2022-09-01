package view;

import controller.GameController;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayGamePane extends BorderPane {

	private GameView gameView;
	private HBox bottom, turn, buttons;
	private StackPane center, left;
	private GridPane board;
	private static int col, row;
	private boolean onAttack, onCastDirectional, onCastSingle, onCast;
	private Ability currAbility;

	public PlayGamePane(GameView gameView) {
		gameView.setPlayGame(this);
		this.gameView = gameView;
		left = new StackPane();
		left.setAlignment(Pos.TOP_CENTER);
		left.setPadding(new Insets(20,0,20,60));
		left.setPrefWidth(600);
		gameView.getListener().onCreateLeft(gameView.getListener().getCurrChamp());

		center = new StackPane();
		center.setAlignment(Pos.CENTER);
		center.setPadding(new Insets(30, 0, 30, 0));

		board = new GridPane();
		board.setAlignment(Pos.TOP_CENTER);
		board.setVgap(5);
		board.setHgap(5);
		gameView.getListener().onCreateBoard();
		center.getChildren().add(board);

		bottom = new HBox();
		bottom.setPadding(new Insets(50, 0, 50, 0));
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		bottom.setSpacing(50);
		bottom.setStyle("-fx-background-color:rgba(0,0,0,0.5)");

		turn = new HBox();
		turn.setPadding(new Insets(0,0,0,-150));
		turn.setPrefWidth(700);
		turn.setAlignment(Pos.CENTER_LEFT);
		turn.setSpacing(20);
		gameView.getListener().onUpdateTurn();

		buttons = new HBox();
		buttons.setPadding(new Insets(0,-150,0,0));
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.setSpacing(20);
		addControls();

		bottom.getChildren().addAll(turn, buttons);

		this.setLeft(left);
		this.setCenter(center);
		this.setBottom(bottom);

		Image image = new Image("Capture.PNG");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		this.setBackground(background);
	}

	public StackPane getLeftPane() {
		return left;
	}

	public boolean isOnAttack() {
		return onAttack;
	}

	public void setOnAttack(boolean onAttack) {
		this.onAttack = onAttack;
	}

	public boolean isOnCastDirectional() {
		return onCastDirectional;
	}

	public static void setCol(int col) {
		PlayGamePane.col = col;
	}

	public static void setRow(int row) {
		PlayGamePane.row = row;
	}

	public boolean isOnCast() {
		return onCast;
	}

	public boolean isOnCastSingle() {
		return onCastSingle;
	}

	public void addToBoard(Node card) {
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(card);
		board.add(pane, col++, row);
		update();
	}

	public void update() {
		if (col == 5) {
			col = 0;
			row++;
		}
	}

	public void addToTurn(ChampCard cc) {
		turn.getChildren().add(cc.getCard());
	}

	public void setOnCastDirectional(boolean onCastDirectional) {
		this.onCastDirectional = onCastDirectional;
	}

	public void setOnCastSingle(boolean onCastSingle) {
		this.onCastSingle = onCastSingle;
	}

	public void setOnCast(boolean onCast) {
		this.onCast = onCast;
	}

	public HBox getTurn() {
		return turn;
	}

	public void addControls() {
		gameView.getGameScene().setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case UP:
				gameView.getListener().onHandleActions(Direction.UP);
				break;
			case DOWN:
				gameView.getListener().onHandleActions(Direction.DOWN);
				break;
			case LEFT:
				gameView.getListener().onHandleActions(Direction.LEFT);
				break;

			case RIGHT:
				gameView.getListener().onHandleActions(Direction.RIGHT);
				break;
			}
		});

		StackPane attack = new StackPane();
		Ability a1 = gameView.getListener().abilityButton(0);
		Ability a2 = gameView.getListener().abilityButton(1);
		Ability a3 = gameView.getListener().abilityButton(2);
		AbilityImage ability1 = new AbilityImage(a1);
		AbilityImage ability2 = new AbilityImage(a2);
		AbilityImage ability3 = new AbilityImage(a3);
		GameButton endTurn = new GameButton("End Turn");

		attack.setPrefSize(110, 110);
		attack.setAlignment(Pos.CENTER);
		Image atk = new Image("attack.jpg");
		ImageView atkImg = new ImageView(atk);
		atkImg.setFitWidth(100);
		atkImg.setFitHeight(100);
		attack.getChildren().add(atkImg);
		attack.setOnMouseEntered(e -> {

		});
		attack.setOnMouseExited(e -> {
			atkImg.setFitWidth(100);
			atkImg.setFitHeight(100);
		});
		attack.setOnMouseClicked(e -> {
			atkImg.setFitWidth(110);
			atkImg.setFitHeight(110);
			onAttack = true;
			onCastDirectional = false;
			onCastSingle = false;
			onCast = false;
		});
		ability1.setOnMouseClicked(e -> {
			identifyAbility(a1);
			currAbility = a1;
			if (onCast)
				gameView.getListener().handleCast();
		});
		ability2.setOnMouseClicked(e -> {
			identifyAbility(a2);
			currAbility = a2;
			if (onCast)
				gameView.getListener().handleCast();
		});
		ability3.setOnMouseClicked(e -> {
			identifyAbility(a3);
			currAbility = a3;
			if (onCast)
				gameView.getListener().handleCast();
		});

		HBox abilities = new HBox();
		abilities.setAlignment(Pos.CENTER);
		

		endTurn.setOnMouseClicked(e -> gameView.getListener().onEndTurn());
		buttons.getChildren().removeAll(buttons.getChildren());
		if (((GameController) (gameView.getListener())).getGame()
				.getCurrentChampion().getAbilities().size() == 4) {
			Ability punch = ((GameController) (gameView.getListener())).getGame()
			.getCurrentChampion().getAbilities().get(3);
			AbilityImage punchImage = new AbilityImage(punch);
			punchImage.setOnMouseClicked(e -> {
				identifyAbility(punch);
				currAbility = punch;
				if (onCast)
					gameView.getListener().handleCast();
			});
			abilities.getChildren().add(punchImage);
		}
		abilities.getChildren().addAll(ability1, ability2, ability3);
		buttons.getChildren().addAll(attack, abilities, endTurn);
	}

	public void identifyAbility(Ability a) {
		if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
			onAttack = false;
			onCastDirectional = true;
			onCastSingle = false;
			onCast = false;
		} else if (a.getCastArea() == AreaOfEffect.SINGLETARGET) {
			onAttack = false;
			onCastDirectional = false;
			onCastSingle = true;
			onCast = false;
		} else {
			onAttack = false;
			onCastDirectional = false;
			onCastSingle = false;
			onCast = true;
		}

	}

	public HBox getButtons() {
		return buttons;
	}

	public GridPane getBoard() {
		return board;
	}

	public void setBoard(GridPane board) {
		this.board = board;
	}

	public Ability getCurrAbility() {
		return currAbility;
	}

	public void setCurrAbility(Ability currAbility) {
		this.currAbility = currAbility;
	}

}
