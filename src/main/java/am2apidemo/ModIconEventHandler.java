package am2apidemo;

import am2.api.events.RegisterSkillTreeIcons;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ModIconEventHandler {
	@SubscribeEvent
	public void onAM2RequestIcons(RegisterSkillTreeIcons event){
		ModCore.proxy.spellList.registerClientside();
	}
}
