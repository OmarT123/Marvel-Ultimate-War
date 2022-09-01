package view;

import java.io.File;

import model.world.Champion;
import engine.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ChooseLeaderPane extends StackPane {
	private HBox top, down;
	private VBox up, bottom;
	private GameView gameView;
	private GameButton next;
	private Label title, team1, team2;
	private VBox center;

	public void updateOpacity1(Button bt) {
		for (int i = 0; i < 3; i++) {
			Button curr = (Button) (top.getChildren().get(i));
			curr.setOpacity(0.2);
		}
		bt.setOpacity(1);
	}

	public void updateOpacity2(Button bt) {
		for (int i = 0; i < 3; i++) {
			Button curr = (Button) (down.getChildren().get(i));
			curr.setOpacity(0.2);
		}
		bt.setOpacity(1);
	}
	public String filterSpaces(String s){
		return s.replaceAll("\\s+","");
	}

	public ChooseLeaderPane(GameView gv) {
		gameView = gv;
		gameView.setLeaderPane(this);

		Player player1 = gameView.getPlayer1();
		Player player2 = gameView.getPlayer2();
		team1 = new Label(player1.getName() + "'team :     ");
		team1.setStyle("-fx-font-size: 25; -fx-text-fill: white");

		team2 = new Label(player2.getName() + "'team :     ");
		team2.setStyle("-fx-font-size: 25; -fx-text-fill: white");

		top = new HBox();
		down = new HBox();
		center = new VBox();
		bottom = new VBox();

		for (Champion c : player1.getTeam()) {
			ChampCard card = new ChampCard(c);
			card.setWidth(150, 200);
			card.getCard().setOnAction(e -> {
				gameView.getPlayer1().setLeader(c);
				updateOpacity1(card.getCard());
				gameView.getTeamsPane().setLeader1Selected(true);
				File f = new File("gameAudio/"+filterSpaces(c.getName())+".mp3");
				Media media = new Media(f.toURI().toString());
				MediaPlayer mp = new MediaPlayer(media);
				mp.setAutoPlay(true);
				mp.play();
			});
			top.getChildren().add(card.getCard());
		}

		for (Champion c : player2.getTeam()) {
			ChampCard card = new ChampCard(c);
			card.setWidth(150, 200);
			card.getCard().setOnAction(e -> {
				gameView.getPlayer2().setLeader(c);
				updateOpacity2(card.getCard());
				gameView.getTeamsPane().setLeader2Selected(true);
				File f = new File("gameAudio/"+filterSpaces(c.getName())+".mp3");
				Media media = new Media(f.toURI().toString());
				MediaPlayer mp = new MediaPlayer(media);
				mp.setAutoPlay(true);
				mp.play();
			});
			down.getChildren().add(card.getCard());
		}
		top.setPadding(new Insets(20, 0, 100, 0));
		bottom.setPadding(new Insets(20, 0, 200, 0));
		down.setPadding(new Insets(20, 0, 50, 0));
		top.setAlignment(Pos.CENTER);
		down.setAlignment(Pos.CENTER);
		top.setSpacing(30);
		down.setSpacing(30);

		next = new GameButton("Play");
		next.setOnMouseClicked(e -> gameView.getListener().onPlay());

		title = new Label("Choose Your Leaders");
		title.setPadding(new Insets(100, 0, 100, 0));
		title.setStyle("-fx-font-size: 30; -fx-text-fill: white");
		bottom.setAlignment(Pos.TOP_CENTER);
		bottom.getChildren().addAll(team2, down, next);

		up = new VBox();
		up.setAlignment(Pos.CENTER);
		up.getChildren().addAll(title, team1, top);

		Image image = new Image("scene2Background.jpg");
		BackgroundImage backImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false,
						false, false, true));
		Background background = new Background(backImage);
		this.setBackground(background);

		center.setAlignment(Pos.CENTER);
		center.getChildren().addAll(up, bottom);
		this.getChildren().add(center);

	}

	public StackPane getCenter() {
		return this;
	}

}