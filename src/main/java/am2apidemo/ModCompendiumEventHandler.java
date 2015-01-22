package am2apidemo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import am2.api.events.RegisterCompendiumEntries;
import am2.api.events.RegisterSkillTreeIcons;

public class ModCompendiumEventHandler {
	@SubscribeEvent
	public void onAM2RequestCompendium(RegisterCompendiumEntries event){
		event.loreHelper.AddCompenidumEntry(ModItems.forceRing, "am2apidemo:forceRing", "Force Ring", "The force ring stores a little bit of kinetic energy mixed with a little magic every time you move more than a twitch.  Punching an enemy with this ring charged will release the stored energy, doing a great deal of damage and send them flying.", null, false, "inscriptionTable");
		event.loreHelper.AddCompenidumEntry(ModCore.proxy.spellList.explosion, "Explosion", "Explosion", "You want to make things go boom?  This is how you make things go boom.  Radius gets bigger with radius modifiers.", null, false);
	}
}
