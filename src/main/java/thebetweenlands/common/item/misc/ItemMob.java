package thebetweenlands.common.item.misc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import thebetweenlands.client.tab.BLCreativeTabs;
import thebetweenlands.common.entity.mobs.EntityFirefly;
import thebetweenlands.common.entity.mobs.EntityGecko;

public class ItemMob extends Item {
	private final String name;

	public ItemMob(String name) {
		this.name = name;
		this.maxStackSize = 1;
		this.setCreativeTab(BLCreativeTabs.ITEMS);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return EnumActionResult.FAIL;
		EntityLiving entity = null;
		switch (name) {
			case "fireFly":
				entity = new EntityFirefly(world);
				break;
			case "gecko":
				entity = new EntityGecko(world);
				break;
		}
		if (entity != null) {
			BlockPos offset = pos.offset(facing);
			entity.setLocationAndAngles(offset.getX() + 0.5F, offset.getY(), offset.getZ() + 0.5F, 0.0F, 0.0F);
			if (!(stack.getDisplayName().equals(I18n.translateToLocal(stack.getUnlocalizedName()))) && !(stack.getDisplayName().equals(stack.getUnlocalizedName())))
				entity.setCustomNameTag(stack.getDisplayName());
			world.spawnEntityInWorld(entity);
		}
		player.setHeldItem(hand, null);
		return EnumActionResult.SUCCESS;
	}
}
