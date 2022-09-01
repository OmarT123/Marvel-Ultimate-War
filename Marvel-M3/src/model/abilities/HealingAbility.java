package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public  class HealingAbility extends Ability {
	private int healAmount;

	public HealingAbility(String name,int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required, int healingAmount) {
		super(name,cost, baseCoolDown, castRadius, area,required);
		this.healAmount = healingAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	public String toString(){
		String s = "Type: Healing Ability"+"\n"+
					"Area of Effect: "+ getCastArea()+"\n"+
					"Cast Range: "+ getCastRange()+"\n"+
					"Mana Cost: "+ getManaCost()+"\n"+
					"Action Points Cost: "+getRequiredActionPoints()+"\n"+
					"Base Cooldown: "+ getBaseCooldown()+ "\n"+
					"Current Cooldown: "+ getCurrentCooldown()+ "\n"+
					"Heal Amount: "+ getHealAmount();
		return s;
	}
	
	@Override
	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets)

			d.setCurrentHP(d.getCurrentHP() + healAmount);

	}
	

}
