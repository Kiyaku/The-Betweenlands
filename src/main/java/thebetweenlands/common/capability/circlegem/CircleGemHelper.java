package thebetweenlands.common.capability.circlegem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.common.capability.circlegem.CircleGem.CombatType;
import thebetweenlands.common.registries.CapabilityRegistry;

public class CircleGemHelper {
	/**
	 * Returns true if gems are applicable to the object
	 * @param obj
	 * @return
	 */
	public static boolean isApplicable(Object obj) {
		if(obj instanceof ItemStack) {
			ItemStack stack = (ItemStack)obj;
			Item item = stack.getItem();
			return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemBow || item instanceof ItemTool;
		} else if(obj instanceof EntityLivingBase) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the gem of the specified item stack
	 * @param stack
	 * @param gem
	 */
	public static void setGem(ItemStack stack, CircleGemType gem) {
		if(stack.hasCapability(CapabilityRegistry.CAPABILITY_ITEM_CIRCLE_GEM, null)) {
			ICircleGemCapability capability = stack.getCapability(CapabilityRegistry.CAPABILITY_ITEM_CIRCLE_GEM, null);
			capability.removeAll();
			capability.addGem(new CircleGem(gem, CombatType.BOTH));
		}
	}

	/**
	 * Returns the gem on the specified item stack
	 * @param stack
	 * @return
	 */
	public static CircleGemType getGem(ItemStack stack) {
		if(stack.hasCapability(CapabilityRegistry.CAPABILITY_ITEM_CIRCLE_GEM, null)) {
			ICircleGemCapability capability = stack.getCapability(CapabilityRegistry.CAPABILITY_ITEM_CIRCLE_GEM, null);
			List<CircleGem> gems = capability.getGems();
			if(!gems.isEmpty()) {
				return gems.get(0).getGemType();
			}
		}
		return CircleGemType.NONE;
	}

	/**
	 * Adds a gem to the specified entity
	 * @param entity
	 * @param gem
	 * @param type
	 */
	public static void addGem(Entity entity, CircleGemType gemType, CircleGem.CombatType combatType) {
		if(entity.hasCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null)) {
			ICircleGemCapability capability = entity.getCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null);
			CircleGem gem = new CircleGem(gemType, combatType);
			if(capability.canAdd(gem)) {
				capability.addGem(gem);
			}
		}
	}

	/**
	 * Returns a list of gems on the specified entity
	 * @param entity
	 * @return
	 */
	public static List<CircleGem> getGems(Entity entity) {
		List<CircleGem> gems = new ArrayList<CircleGem>();
		if(entity.hasCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null)) {
			ICircleGemCapability capability = entity.getCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null);
			return capability.getGems();
		}
		return gems;
	}

	/**
	 * Returns the gem of the slot on the specified entity
	 * @param entity
	 * @param slot
	 * @return
	 */
	public static CircleGem getGem(Entity entity, int slot) {
		if(entity.hasCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null)) {
			ICircleGemCapability capability = entity.getCapability(CapabilityRegistry.CAPABILITY_ENTITY_CIRCLE_GEM, null);
			List<CircleGem> gems = capability.getGems();
			if(gems.size() > slot)
				return capability.getGems().get(slot);
		}
		return new CircleGem(CircleGemType.NONE, CombatType.BOTH);
	}

	/**
	 * Adds the gem property overrides to the specified item
	 * @param item
	 */
	public static void addGemPropertyOverrides(Item item) {
		item.addPropertyOverride(new ResourceLocation("gem"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return CircleGemHelper.getGem(stack).id;
			}
		});
	}

	public static final float MAX_GEM_DAMAGE_VARIATION = 8.0F;
	public static final float GEM_PROC_CHANCE = 0.1F;

	/**
	 * Handles an attack and returns the new damage
	 * @param damageSource
	 * @param attackedEntity
	 * @param damage
	 * @return
	 */
	public static float handleAttack(DamageSource damageSource, EntityLivingBase attackedEntity, float damage) {
		if(attackedEntity.hurtTime == 0 && attackedEntity.deathTime == 0 && damageSource instanceof EntityDamageSource && (attackedEntity instanceof EntityPlayer == false || !((EntityPlayer)attackedEntity).capabilities.disableDamage)) {
			Entity attacker = null;
			Entity source = null;
			if(damageSource instanceof EntityDamageSourceIndirect) {
				attacker = ((EntityDamageSourceIndirect)damageSource).getSourceOfDamage();
				source = ((EntityDamageSource)damageSource).getEntity();
			} else {
				attacker = ((EntityDamageSource)damageSource).getEntity();
				source = attacker;
			}
			if(attacker != null && source != null) {
				List<CircleGem> attackerGems = CircleGemHelper.getGems(attacker);
				List<CircleGem> sourceGems = new ArrayList<CircleGem>();
				if(source != attacker) {
					sourceGems.addAll(CircleGemHelper.getGems(source));
				}
				CircleGemType attackerItemGem = CircleGemType.NONE;
				if(attacker instanceof EntityLivingBase) {
					ItemStack heldItem = ((EntityLivingBase)attacker).getActiveItemStack();
					if(heldItem != null) attackerItemGem = CircleGemHelper.getGem(heldItem);
				}
				//At this point either userGem or attackerItemGem are set because either there's a user shooting a (non-living) projectile (user != attacker) or the user is attacking directly (user == attacker)
				List<CircleGem> attackedGems = CircleGemHelper.getGems(attackedEntity); 
				CircleGemType attackedBlockingItemGem = CircleGemType.NONE;
				if(attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					ItemStack heldItem = player.getActiveItemStack();
					if(heldItem != null && player.isActiveItemStackBlocking()) {
						attackedBlockingItemGem = CircleGemHelper.getGem(heldItem);
					}
				}
				int gemRelation = 0;
				for(CircleGem gem : attackerGems) {
					if(gem.matchCombatType(CircleGem.CombatType.OFFENSIVE)) {
						for(CircleGem gemAttacked : attackedGems) {
							if(gemAttacked.matchCombatType(CircleGem.CombatType.DEFENSIVE)) {
								gemRelation += gem.getGemType().getRelation(gemAttacked.getGemType());
							}
						}
						gemRelation += gem.getGemType().getRelation(attackedBlockingItemGem);
					}
				}
				for(CircleGem gemAttacked : attackedGems) {
					if(gemAttacked.matchCombatType(CircleGem.CombatType.DEFENSIVE)) {
						gemRelation += attackerItemGem.getRelation(gemAttacked.getGemType());
					}
				}
				gemRelation += attackerItemGem.getRelation(attackedBlockingItemGem);
				for(CircleGem gem : sourceGems) {
					if(gem.matchCombatType(CircleGem.CombatType.OFFENSIVE)) {
						for(CircleGem gemAttacked : attackedGems) {
							if(gemAttacked.matchCombatType(CircleGem.CombatType.DEFENSIVE)) {
								gemRelation += gem.getGemType().getRelation(gemAttacked.getGemType());
							}
						}
						gemRelation += gem.getGemType().getRelation(attackedBlockingItemGem);
					}
				}
				if(attackedEntity instanceof EntityLivingBase) {
					Iterable<ItemStack> equipment = ((EntityLivingBase)attackedEntity).getEquipmentAndArmor();
					for(ItemStack equipmentStack : equipment) {
						if(equipmentStack != null && !equipmentStack.equals(((EntityLivingBase)attackedEntity).getActiveHand()) && equipmentStack.getItem() instanceof ItemArmor) {
							CircleGemType armorGem = CircleGemHelper.getGem(equipmentStack);
							for(CircleGem gem : attackerGems) {
								if(gem.matchCombatType(CircleGem.CombatType.OFFENSIVE)) {
									gemRelation += gem.getGemType().getRelation(armorGem);
								}
							}
							gemRelation += attackerItemGem.getRelation(armorGem);
							for(CircleGem gem : sourceGems) {
								if(gem.matchCombatType(CircleGem.CombatType.OFFENSIVE)) {
									gemRelation += gem.getGemType().getRelation(armorGem);
								}
							}
						}
					}
				}
				float gemDamageVariation = Math.min(((gemRelation != 0 ? Math.signum(gemRelation) * 1 : 0) + gemRelation) / 6.0F * MAX_GEM_DAMAGE_VARIATION, MAX_GEM_DAMAGE_VARIATION);
				if(gemDamageVariation != 0.0F) {
					damage = Math.max(damage + gemDamageVariation, 1.0F);
				}

				boolean attackerProc = attacker.worldObj.rand.nextFloat() <= (source == attacker && !attacker.onGround && attacker.motionY < 0 ? GEM_PROC_CHANCE * 1.33F : GEM_PROC_CHANCE);
				boolean defenderProc = attacker.worldObj.rand.nextFloat() <= GEM_PROC_CHANCE;

				boolean attackerProcd = false;
				boolean defenderProcd = false;

				//Attacker gems
				TObjectIntHashMap<CircleGemType> attackerGemCounts = new TObjectIntHashMap<CircleGemType>();
				for(CircleGem gem : attackerGems) {
					if(gem.matchCombatType(CircleGem.CombatType.OFFENSIVE)) {
						attackerGemCounts.adjustOrPutValue(gem.getGemType(), 1, 1);
					}
				}
				attackerGemCounts.adjustOrPutValue(attackerItemGem, 1, 1);
				for(CircleGemType gem : attackerGemCounts.keySet()) {
					attackerProcd |= applyProc(gem, attacker, source, attacker, attackedEntity, attackerProc, defenderProc, getMultipleProcStrength(attackerGemCounts.get(gem), damage));
				}

				//Defender gems
				TObjectIntHashMap<CircleGemType> defenderGemCounts = new TObjectIntHashMap<CircleGemType>();
				if(attackedEntity instanceof EntityLivingBase) {
					Iterable<ItemStack> equipment = ((EntityLivingBase)attackedEntity).getEquipmentAndArmor();
					for(ItemStack equipmentStack : equipment) {
						if(equipmentStack != null && !equipmentStack.equals(((EntityLivingBase)attackedEntity).getActiveHand()) && equipmentStack.getItem() instanceof ItemArmor) {
							CircleGemType armorGem = CircleGemHelper.getGem(equipmentStack);
							if(armorGem != CircleGemType.NONE) {
								defenderGemCounts.adjustOrPutValue(armorGem, 1, 1);
							}
						}
					}
				}
				for(CircleGem gem : attackedGems) {
					if(gem.matchCombatType(CircleGem.CombatType.DEFENSIVE)) {
						defenderGemCounts.adjustOrPutValue(gem.getGemType(), 1, 1);
					}
				}
				defenderGemCounts.adjustOrPutValue(attackedBlockingItemGem, 1, 1);
				for(CircleGemType gem : defenderGemCounts.keySet()) {
					defenderProcd |= applyProc(gem, attackedEntity, source, attacker, attackedEntity, attackerProc, defenderProc, getMultipleProcStrength(defenderGemCounts.get(gem), damage));
				}

				if(attackerProcd || defenderProcd) {
					/*Random rnd = source.worldObj.rand;
					World world = attackedEntity.worldObj;
					int dim = 0;
					if (world instanceof WorldServer) {
						dim = ((WorldServer)world).provider.dimensionId;
					}
					if(attackerProcd) {
						TheBetweenlands.networkWrapper.sendToAllAround(TheBetweenlands.sidedPacketHandler.wrapPacket(new PacketGemProc((byte)0, attackedEntity.getEntityId())), new TargetPoint(dim, attackedEntity.posX, attackedEntity.posY, attackedEntity.posZ, 64D));
					}
					if(defenderProcd) {
						TheBetweenlands.networkWrapper.sendToAllAround(TheBetweenlands.sidedPacketHandler.wrapPacket(new PacketGemProc((byte)1, attackedEntity.getEntityId())), new TargetPoint(dim, attackedEntity.posX, attackedEntity.posY, attackedEntity.posZ, 64D));
					}
					source.worldObj.playSoundAtEntity(source, "random.successful_hit", 1, 1);
					attackedEntity.worldObj.playSoundAtEntity(attackedEntity, "random.successful_hit", 1, 1);*/
					//TODO: Proc packet
				}
			}
		}
		return damage;
	}

	private static float getMultipleProcStrength(int procs, float strength) {
		float ret = 0;
		for(int i = 0; i < procs; i++) {
			ret += strength / Math.pow(1.4F, i);
		}
		return ret;
	}

	private static boolean applyProc(CircleGemType gem, Entity owner, Entity source, Entity attacker, Entity defender, boolean attackerProc, boolean defenderProc, float strength) {
		boolean isAttacker = owner == attacker;
		if((isAttacker && attackerProc) || (!isAttacker && defenderProc)) {
			return gem.applyProc(isAttacker, owner, source, attacker, defender, strength);
		}
		return false;
	}
}