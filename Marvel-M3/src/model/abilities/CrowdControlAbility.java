package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;

	}

	public Effect getEffect() {
		return effect;
	}
	
	public String toString(){
		String s = "Type: Crowd Control Ability"+"\n"+
					"Area of Effect: "+ getCastArea()+"\n"+
					"Cast Range: "+ getCastRange()+"\n"+
					"Mana Cost: "+ getManaCost()+"\n"+
					"Action Points Cost: "+getRequiredActionPoints()+"\n"+
					"Base Cooldown: "+ getBaseCooldown()+ "\n"+
					"Current Cooldown: "+ getCurrentCooldown()+ "\n"+
					 getEffect().toString();
		return s;
	}
	@Override
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
		for(Damageable d: targets)
		{
			Champion c =(Champion) d;
			c.getAppliedEffects().add((Effect) effect.clone());
			effect.apply(c);
		}
		
	}

}
