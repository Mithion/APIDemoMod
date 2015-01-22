package am2apidemo.items;

import java.util.List;

import am2.api.ArsMagicaApi;
import am2.api.IExtendedProperties;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemForceRing extends Item {

	//Used to identify the charge in the item's NBT
	private static final String KEY_ChargeIdentifier = "FRCharge";
	//Used to identify the item owner's last world position in the item's NBT
	private static final String KEY_PosIdentifier = "FRPos";
	//maximum charge the item can hold
	private static final float MAX_CHARGE = 1000;
	//factor that charge is multiplied by to determine force
	private static final float FORCE_FACTOR = 0.01f;
	//factor that charge is multiplied by to determine damage
	private static final float DAMAGE_FACTOR = 0.006f;
	//at what charge should the ring start to glow?
	private static final float GLOW_THRESHOLD = 500;
	//how much mana should be taken per charge?
	private static final float MANA_CONSUMPTION_RATE = 4.2f;
	
	/**
	 * Basic constructor
	 */
	public ItemForceRing(){
		super();
		//can't break
		setMaxDamage(0);
		//can't stack
		setMaxStackSize(1);
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		//register the icon from the mod's texture folder under resources
		this.itemIcon = iconRegister.registerIcon("am2apidemo:forceRing");
	}

	/**
	 * Gets the charge stored in the itemstack
	 */
	private float getCharge(ItemStack stack){
		if (!stack.hasTagCompound())
			return 0;
		return stack.stackTagCompound.getFloat(KEY_ChargeIdentifier);
	}

	/**
	 * Adds one charge to the item stack
	 */
	private void addCharge(ItemStack stack){
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		float charge = Math.min(getCharge(stack) + 1, MAX_CHARGE);
		stack.stackTagCompound.setFloat(KEY_ChargeIdentifier, charge);
	}

	/**
	 * Sets the item's charge to zero
	 */
	private void zeroCharge(ItemStack stack){
		if (!stack.hasTagCompound())
			return;

		stack.stackTagCompound.setFloat(KEY_ChargeIdentifier, 0);
	}
	
	/**
	 * Deducts [MANA_CONSUMPTION_RATE] mana from the entity or returns false if there isn't enough.
	 */
	private boolean deductMana(EntityLivingBase entity){
		IExtendedProperties props = ArsMagicaApi.instance.getExtendedProperties(entity);
		float curMana = props.getCurrentMana();
		if (curMana < MANA_CONSUMPTION_RATE)
			return false;
		props.setCurrentMana(curMana - MANA_CONSUMPTION_RATE);
		return true;
	}

	/**
	 * Stores the specified position in the item's NBT
	 */
	private void storePosition(ItemStack stack, double x, double y, double z){
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.stackTagCompound.setDouble(KEY_PosIdentifier + "_x", x);
		stack.stackTagCompound.setDouble(KEY_PosIdentifier + "_y", y);
		stack.stackTagCompound.setDouble(KEY_PosIdentifier + "_z", z);
	}

	/**
	 * Gets the position stored in the item's NBT
	 */
	private Vec3 getLastPosition(ItemStack stack){
		if (!stack.hasTagCompound())
			return Vec3.createVectorHelper(0, 0, 0);

		return Vec3.createVectorHelper(
				stack.stackTagCompound.getDouble(KEY_PosIdentifier+ "_x"), 
				stack.stackTagCompound.getDouble(KEY_PosIdentifier+ "_y"), 
				stack.stackTagCompound.getDouble(KEY_PosIdentifier+ "_z"));
	}

	/**
	 * This is where you can add lines to the item's description text (when moused over)
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List descLines, boolean iHaveNoIdeaAndDontCare) {
		super.addInformation(stack, player, descLines, iHaveNoIdeaAndDontCare);
		descLines.add(
				String.format(
						StatCollector.translateToLocal("am2apidemo.tooltip.forceRingCharge"), 
						String.format("%.2f", getCharge(stack) //needed because for some reason adding the %.2f in the lang file doesn't work but %s does
								)));
	}

	/**
	 * Ensure data auto-syncs between client and server
	 */
	@Override
	public boolean getShareTag() {
		return true;
	}

	/**
	 * Called when equipped and an entity is left-clicked
	 */
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		float charge = getCharge(stack);
		float force = charge * FORCE_FACTOR;
		float damage = charge * DAMAGE_FACTOR;

		Vec3 velocityVector = Vec3.createVectorHelper(player.posX, player.posY, player.posZ).subtract(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ)).normalize();

		entity.addVelocity(velocityVector.xCoord * force, 0.25 + velocityVector.yCoord * force, velocityVector.zCoord * force);
		entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);

		if (!player.worldObj.isRemote)
			zeroCharge(stack);

		return true;
	}

	/**
	 * Called every tick when the item is in a player's inventory
	 */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int time, boolean isEquipped) {
		super.onUpdate(stack, world, entity, time, isEquipped);
		
		if (!(entity instanceof EntityLivingBase))
			return;
		
		Vec3 lastPos = getLastPosition(stack);
		Vec3 curPos = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
		if ((
				!world.isRemote && (													//server world?
						(int)Math.floor(lastPos.xCoord) != (int)Math.floor(curPos.xCoord) ||	//has position changed in any way by more than a full block?
						(int)Math.floor(lastPos.yCoord) != (int)Math.floor(curPos.yCoord) ||
						(int)Math.floor(lastPos.zCoord) != (int)Math.floor(curPos.zCoord)
						))){
			if (deductMana((EntityLivingBase) entity)){
				addCharge(stack);				
			}
			storePosition(stack, curPos.xCoord, curPos.yCoord, curPos.zCoord);
		}
	}
	
	/**
	 * Is the item all enchantment-glowy
	 */
	@Override
	public boolean hasEffect(ItemStack stack) {
		return getCharge(stack) > GLOW_THRESHOLD;
	}
}
