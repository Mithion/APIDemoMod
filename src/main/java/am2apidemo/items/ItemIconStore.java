package am2apidemo.items;

import java.util.HashMap;

import am2apidemo.ModCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemIconStore extends Item {
	
	@SideOnly(Side.CLIENT)
	private HashMap<String, IIcon> icons;
	@SideOnly(Side.CLIENT)
	private String[] textureNames;
	
	public ItemIconStore(){
	}
	
	public void registerIcons(IIconRegister register) {
		textureNames = new String[] { "Explosion" };
		icons = new HashMap<String, IIcon>();
		for (int i = 0; i < textureNames.length; ++i){
			icons.put(textureNames[i], register.registerIcon("am2apidemo:" + textureNames[i]));
		}
		
		if (icons.size() > 0)
			this.itemIcon = icons.values().iterator().next();		
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(String name){
		return icons.get(name);
	}
}
