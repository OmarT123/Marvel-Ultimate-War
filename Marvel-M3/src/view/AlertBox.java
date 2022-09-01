package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.*;

public class AlertBox extends BorderPane{
	
	public AlertBox(Pane parent, String message) {
		
		this.setMaxSize(500, 250);
		this.setStyle("-fx-background-color: white");
		
		Label alert = new Label("Alert");
		alert.setPrefHeight(50);
		this.setTop(alert);
		
		Label label = new Label(message);
		this.setCenter(label);
		
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setPrefHeight(100);
		Button closeButton = new Button();
		closeButton.setText("CLOSE");
		box.getChildren().add(closeButton);
		this.setBottom(box);
		closeButton.setOnAction(e -> parent.getChildren().remove(this));
		
		AlertBox.setAlignment(this.getTop(),Pos.CENTER);
		AlertBox.setAlignment(this.getCenter(),Pos.CENTER);
		
		parent.getChildren().add(this);
	}
}
