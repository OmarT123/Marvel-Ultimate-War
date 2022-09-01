package view;

import javafx.scene.image.*;

public class DamagableIcon extends ImageView {
	
	private String icon;
	
	public DamagableIcon(String icon) {
		this.icon = icon;
		Image img = new Image(icon+".jpg");
		this.setFitWidth(80);
		this.setFitHeight(100);
		this.setImage(img);
	}
	
	public String getIcon() {
		return icon;
	}

	public void setSize(double width,double height){
		this.setFitWidth(width);
		this.setFitHeight(height);
	}
}
