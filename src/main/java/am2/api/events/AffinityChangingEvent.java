package am2.api.events;

import am2.api.spell.enums.Affinity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class AffinityChangingEvent extends Event{
	public final EntityPlayer player;
	public float amount;
	public final Affinity affinity;
	
	public AffinityChangingEvent(EntityPlayer player, Affinity affinity, float amt){
		this.player = player;
		this.amount = amt;
		this.affinity = affinity;
	}
}
