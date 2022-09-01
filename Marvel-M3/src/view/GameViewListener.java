package view;

import model.abilities.Ability;
import model.world.Champion;
import model.world.Direction;

public interface GameViewListener {
	public void onPlay();

	public void onCreateBoard();

	public void onUpdateTurn();

	public void onHandleActions(Direction d);

	public Ability abilityButton(int index);

	public void handleCast();

	public void onEndTurn();

	public void castLeaderAbility();

	public void onCreateLeft(Champion curr);

	public Champion getCurrChamp();
}
