package am2apidemo;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import am2.api.ArsMagicaApi;
import am2apidemo.items.CustomCreativeTab;
import am2apidemo.items.ItemForceRing;
import am2apidemo.items.ItemIconStore;

public class ModItems {
	private CustomCreativeTab creativeTab;
	
	public static ItemForceRing forceRing;
	public static ItemIconStore iconStore;
	
	public ModItems(){
		creativeTab = new CustomCreativeTab("AM2 API Demo");		
		
		forceRing = (ItemForceRing) new ItemForceRing().setUnlocalizedName("am2apidemo:forceRing").setCreativeTab(creativeTab);
		GameRegistry.registerItem(forceRing, "forceRing");
		GameRegistry.addRecipe(new ShapedOreRecipe(
			new ItemStack(forceRing),
			new Object[]
			{
				"IVI", 
				"IPI", 
				"IVI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('V'), "dustVinteum",
				Character.valueOf('P'), new ItemStack(GameRegistry.findItem("arsmagica2", "itemOre"), 1, 3) //purified vinteum
			}));
		
		iconStore = (ItemIconStore) new ItemIconStore().setUnlocalizedName("am2apidemo:iconStore").setCreativeTab(null);
		GameRegistry.registerItem(iconStore, "iconStore");
		
		creativeTab.setIconItemIndex(forceRing);
	}
}
