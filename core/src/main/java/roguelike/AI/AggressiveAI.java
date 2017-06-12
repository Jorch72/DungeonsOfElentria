package roguelike.AI;

import roguelike.Mob.EnemyEntity;
import roguelike.Mob.Player;

public class AggressiveAI extends BaseAI{
	
	public Player player;
	
	public AggressiveAI(EnemyEntity mob, Player player){
		super(mob);
	}
	
	public void onUpdate(){
		this.player = mob.level().player;
		if(this.canSee(player.x, player.y)){
			hunt(player);
		}
		else if(mob.level().hasItemAlready(mob.x, mob.y)){ mob.pickupItem(); }
		else{
			wander();
		}
	}
}
