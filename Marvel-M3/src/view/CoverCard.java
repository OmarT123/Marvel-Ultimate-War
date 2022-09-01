package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import model.world.*;

public class CoverCard extends VBox {
	private Cover cover;
	private ProgressBar bar;
	private DamagableIcon icon;
	private StackPane pane;

	public CoverCard(Cover cover) {
		this.cover = cover;
		icon = new DamagableIcon(cover.getName());
		bar = new ProgressBar(1);
		bar.setMaxWidth(75);
		bar.setStyle("-fx-accent: green;"
				+ "-fx-text-box-border: rgba(0,0,0,0.2);");
		pane = new StackPane();
		Label currHP = new Label("" + cover.getCurrentHP());
		String color = (cover.getCurrentHP() / cover.getMaxHP()) > 0.5 ? "white" : "black";
		currHP.setStyle("-fx-font-size: 12px;" + "-fx-text-fill: " + color
				+ ";");
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(bar, currHP);
		this.getChildren().addAll(icon, pane);
		this.setAlignment(Pos.CENTER);
		this.setMaxSize(100, 140);
		this.setSize(100, 100);

	}
	
	public void setSize(double width,double height){
		icon.setSize(width, height);
		bar.setMaxWidth(width);
	}

	public ProgressBar getBar() {
		return bar;
	}

	public void setBar(ProgressBar bar) {
		this.getChildren().remove(pane);
		this.bar = bar;
		this.bar.setProgress(cover.getCurrentHP()*1.0/cover.getMaxHP());
		pane = new StackPane();
		Label currHP = new Label("" + cover.getCurrentHP());
		String color = (cover.getCurrentHP()*1.0 / cover.getMaxHP()) > 0.5 ? "white" : "black";
		currHP.setStyle("-fx-font-size: 12px;" + "-fx-text-fill: " + color
				+ ";");
		pane.getChildren().addAll(bar, currHP);
		this.getChildren().add(pane);
	}

	public Cover getCover() {
		return cover;
	}

}
