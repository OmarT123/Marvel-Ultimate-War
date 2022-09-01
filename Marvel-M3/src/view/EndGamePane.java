package view;

import engine.Game;
import engine.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EndGamePane extends VBox{
	private Label congrats ;
	private GameButton quitButton;
	private GameView gameView;
	
	
	public EndGamePane(GameView gameView, Player winner,Game game){
		this.gameView = gameView;
		congrats = new Label("CONGRATULATIONS  "+winner.getName());
		congrats.setPrefHeight(100);
		quitButton = new GameButton("QUIT");
		quitButton.setSize(100,70);
		quitButton.setOnMouseClicked(e -> gameView.close());
		//winnerName.setTextFill(Color.WHITE);
		congrats.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");	
		
		Image image = new Image("last scene background.gif");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		this.setBackground(background);
//		if(game.getFirstPlayer().getTeam().size()==0)
//			winnerName.setText(game.getSecondPlayer().getName());
//		else if (game.getSecondPlayer().getTeam().size()==0){
//			winnerName.setText(game.getFirstPlayer().getName());
//				
//		}
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(80);
		this.getChildren().addAll(congrats, quitButton);
		this.gameView.setPane(this);
	}

}
