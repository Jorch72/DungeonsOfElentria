package roguelike.AI;

import roguelike.Mob.EnemyEntity;
import roguelike.Mob.Player;

public class GoblinAi extends BaseAI{
	
	public Player player;
	
	public GoblinAi(EnemyEntity mob, Player player){
		super(mob);
	}
	
	public void onUpdate(){
		this.player = mob.level().player;
		if(this.canSee(player.x, player.y)){
			hunt(player);
		}
		else{
			wander();
		}
	}
}
