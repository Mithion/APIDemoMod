package am2apidemo.spell;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import am2.api.ArsMagicaApi;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.api.spell.enums.SpellModifiers;

public class SpellComponentExplosion implements ISpellComponent {

	@Override
	public Object[] getRecipeItems() {
		//crafting altar recipe.  Must be unique, and must contain at least one item.
		return new Object[]{
			new ItemStack(Items.blaze_powder),
			new ItemStack(Blocks.tnt),
			new ItemStack(Items.gunpowder)
		};
	}

	/**
	 * This should really be made configurable in your mod
	 */
	@Override
	public int getID() {
		return 100; //unique id of this component
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster) {
		//make the magic happen when targeting a block.  Return false if the effect does not apply.
		int radius = ArsMagicaApi.instance.getSpellUtils().getModifiedInt_Mul(2, stack, caster, null, world, SpellModifiers.RADIUS);
		goBoom(caster, world, impactX, impactY, impactZ, radius);
		return true;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target) {
		//make the magic happen when targeting an entity.  Return false if the effect does not apply.
		int radius = ArsMagicaApi.instance.getSpellUtils().getModifiedInt_Mul(2, stack, caster, target, world, SpellModifiers.RADIUS);
		goBoom(caster, world, target.posX, target.posY, target.posZ, radius);
		return true;
	}
	
	private void goBoom(EntityLivingBase caster, World world, double x, double y, double z, float size){
		if (!world.isRemote)
			world.newExplosion(caster, x, y, z, size, false, true);
	}

	@Override
	public float manaCost(EntityLivingBase caster) {
		return 125; //how much mana does the component add to the overall spell's cost
	}

	@Override
	public float burnout(EntityLivingBase caster) {
		return 10; //how much burnout should the component add
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster) {
		//if specified, these reagents will be required in inventory, and will be consumed upon the effect applying
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier) {
		//spawn particles (in addition to default ones) when the effect applies
	}

	@Override
	public EnumSet<Affinity> getAffinity() {
		return EnumSet.of(Affinity.FIRE); //you need to set at least one affinity or it will crash
	}

	@Override
	public float getAffinityShift(Affinity affinity) {
		return 0.05f; //this is the default value for affinity shifts.  You should probably keep it around here if you want to stay balanced.
	}

}
