package am2apidemo.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CustomCreativeTab extends CreativeTabs {

	private Item IconItem;	
	
	public CustomCreativeTab(String label) {
		super(label);
	}

	public void setIconItemIndex(Item iconItem){
		this.IconItem = iconItem;
	}
	
	@Override
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

	@Override
	public Item getTabIconItem() {
		return this.IconItem;
	}
}
