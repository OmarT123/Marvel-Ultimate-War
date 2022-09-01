package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public class DamagingAbility extends Ability {

	private int damageAmount;

	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.damageAmount = damageAmount;
	}

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	public String toString(){
		String s =  "Type: Damaging Ability"+"\n"+
					"Area of Effect: "+ getCastArea()+"\n"+
					"Cast Range: "+ getCastRange()+"\n"+
					"Mana Cost: "+ getManaCost()+"\n"+
					"Action Points Cost: "+getRequiredActionPoints()+"\n"+
					"Base Cooldown: "+ getBaseCooldown()+ "\n"+
					"Current Cooldown: "+ getCurrentCooldown()+ "\n"+
					"Damage Amount: "+ getDamageAmount();
		return s;
	}

	@Override
	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets)

			d.setCurrentHP(d.getCurrentHP() - damageAmount);

	}
}
