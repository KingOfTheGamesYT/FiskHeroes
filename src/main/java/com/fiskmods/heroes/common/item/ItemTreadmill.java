package com.fiskmods.heroes.common.item;

import com.fiskmods.heroes.common.block.BlockTreadmill;
import com.fiskmods.heroes.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemTreadmill extends ItemBlock
{
    public ItemTreadmill(Block block)
    {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, PlayerEntity player, World world, int x, int y, int z, int side, float f, float f1, float f2)
    {
        if (world.isRemote)
        {
            return true;
        }
        else if (side != 1)
        {
            return false;
        }
        else
        {
            ++y;
            BlockTreadmill blocktreadmill = (BlockTreadmill) ModBlocks.treadmill;
            int i1 = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
            byte b0 = 0;
            byte b1 = 0;

            if (i1 == 0)
            {
                b1 = 1;
            }

            if (i1 == 1)
            {
                b0 = -1;
            }

            if (i1 == 2)
            {
                b1 = -1;
            }

            if (i1 == 3)
            {
                b0 = 1;
            }

            if (player.canPlayerEdit(x, y, z, side, itemstack) && player.canPlayerEdit(x + b0, y, z + b1, side, itemstack))
            {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + b0, y, z + b1) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + b0, y - 1, z + b1))
                {
                    world.setBlock(x, y, z, blocktreadmill, i1, 3);

                    if (world.getBlock(x, y, z) == blocktreadmill)
                    {
                        world.setBlock(x + b0, y, z + b1, blocktreadmill, i1 + 8, 3);
                    }

                    --itemstack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
