package am2apidemo.spell;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import am2.api.ArsMagicaApi;
import am2.api.spell.enums.SkillPointTypes;
import am2.api.spell.enums.SkillTrees;
import am2apidemo.ModCore;
import am2apidemo.ModItems;

public class SpellList {
	public static SpellComponentExplosion explosion;
	
	public void init(){
		explosion = new SpellComponentExplosion();
	}
	
	public void register(){
		ArsMagicaApi.instance.registerSkillTreeEntry(
				explosion, 
				"Explosion", 
				SkillTrees.Offense, 
				25, //x-coord in occulus UI
				175,  //y-coord in occulus UI
				SkillPointTypes.GREEN,
				ArsMagicaApi.instance.getSpellPartManager().getSkill("Forge") //need to unlock Forge (and all it's prereqs) before you can take this one
		);		
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClientside(){
		if (ModItems.iconStore.getIcon("Explosion") != null)
			ArsMagicaApi.instance.registerSkillIcon("Explosion", ModItems.iconStore.getIcon("Explosion"));
	}
}
