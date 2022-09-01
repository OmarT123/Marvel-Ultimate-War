package view;

import java.util.*;

import model.abilities.Ability;
import model.world.*;
import engine.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameView extends Stage {

	private Scene gameScene;
	private String playerOneName, playerTwoName;
	private Image logo;
	private StartGamePane startGame;
	private CreateTeamsPane teamsPane;
	private ChooseLeaderPane leaderPane;
	public ChooseLeaderPane getLeaderPane() {
		return leaderPane;
	}

	public void setLeaderPane(ChooseLeaderPane leaderPane) {
		this.leaderPane = leaderPane;
	}
	
	private PlayGamePane playGame;
	private EndGamePane endGamePane;
	// private WinnerPane winner;
	private Player player1, player2;
	private ArrayList<Champion> availableChampions;
	private ArrayList<Ability> availableAbilities;
	private final double width, height;
	private GameViewListener listener;

	public GameView(double width, double height) {
		this.width = width;
		this.height = height;
		this.startGame = new StartGamePane(this);

		gameScene = new Scene(startGame,this.width,this.height);
		this.setScene(gameScene);
		this.setFullScreen(true);
		this.setFullScreenExitHint("");
		this.setTitle("Marvel Ultimate War");
		this.getIcons().add(new Image("Icon1.jpg"));

		this.show();
	}

	public void setPlayerOneName(String playerOneName) {
		this.playerOneName = playerOneName;
	}

	public void setPlayerTwoName(String playerTwoName) {
		this.playerTwoName = playerTwoName;
	}

	public void setPane(Pane p) {
		gameScene.setRoot(p);
	}

	public Scene getGameScene() {
		return gameScene;
	}

	public String getPlayerOneName() {
		return playerOneName;
	}

	public String getPlayerTwoName() {
		return playerTwoName;
	}

	public StartGamePane getStartGame() {
		return startGame;
	}

	public CreateTeamsPane getTeamsPane() {
		return teamsPane;
	}

	public PlayGamePane getPlayGame() {
		return playGame;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void setStartGame(StartGamePane startGame) {
		this.startGame = startGame;
	}

	public void setTeamsPane(CreateTeamsPane teamsPane) {
		this.teamsPane = teamsPane;
	}
	
	public void setPlayGame(PlayGamePane playGame) {
		this.playGame = playGame;
	}
	
	public ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public void setAvailableChampions(ArrayList<Champion> availableChampions) {
		this.availableChampions = availableChampions;
	}

	public ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public void setAvailableAbilities(ArrayList<Ability> availableAbilities) {
		this.availableAbilities = availableAbilities;
	}
	
	public GameViewListener getListener() {
		return listener;
	}

	public EndGamePane getEndGamePane(){
		return endGamePane;
	}
	public void setEndGamePane(EndGamePane e){
		endGamePane = e;
	}
	public void setListener(GameViewListener listener) {
		this.listener = listener;
	}
	

}