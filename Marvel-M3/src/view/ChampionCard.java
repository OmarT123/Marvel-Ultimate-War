package view;

import model.world.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;

public class ChampionCard extends VBox {
	private Champion champ;
	private ProgressBar bar;
	private DamagableIcon icon;

	public DamagableIcon getIcon() {
		return icon;
	}

	public ChampionCard(Champion c) {
		this.champ = c;
		icon = new DamagableIcon(champ.getName());
		StackPane pane = new StackPane();
		double currentHP = c.getCurrentHP();
		double maxHP = c.getMaxHP();
		bar = new ProgressBar(currentHP / maxHP);
		bar.setMaxWidth(80);
		bar.setStyle("-fx-accent: green;"
				+ "-fx-text-box-border: rgba(0,0,0,0.2);");
		Label currHP = new Label("" + c.getCurrentHP());
		String color = (currentHP / maxHP) > 0.5 ? "white" : "black";
		currHP.setStyle("-fx-font-size: 12px;" + "-fx-text-fill: " + color
				+ ";");
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(bar, currHP);
		this.getChildren().addAll(icon, pane);
		this.setAlignment(Pos.CENTER);
		this.setPrefSize(150, 150);
	}
	
	public void setSize(double width,double height){
		icon.setSize(width, height);
		bar.setMaxWidth(width);
	}

	public ProgressBar getBar() {
		return bar;
	}

	public void setColor(String color) {
		bar.setStyle("-fx-accent:" + color + ";");
	}

	public void setBar(ProgressBar bar) {
		this.bar = bar;
	}

	public Champion getChamp() {
		return champ;
	}
}
