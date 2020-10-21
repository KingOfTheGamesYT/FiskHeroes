package com.fiskmods.heroes.common.tileentity;

import java.util.Random;

import com.fiskmods.heroes.common.block.ModBlocks;
import com.fiskmods.heroes.util.TemperatureHelper;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityIceLayer extends TileEntitySH
{
    public int ticks;
    public int thickness = 1;
    public int thawTicks = 100;

    @Override
    public void updateEntity()
    {
        float temp = TemperatureHelper.getCurrentBiomeTemperature(world, xCoord, yCoord, zCoord);
        ++ticks;

        if (temp > 0 || world.getSavedLightValue(EnumSkyBlock.Block, xCoord, yCoord, zCoord) > 11)
        {
            int metadata = getBlockMetadata();
            ForgeDirection dir = ForgeDirection.getOrientation(metadata);

            if (!(thickness >= 60 && world.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == ModBlocks.iceLayer && world.getBlockMetadata(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == metadata))
            {
                if (--thawTicks <= 0)
                {
                    if (thickness > 1)
                    {
                        setThickness(--thickness);
                    }
                    else
                    {
                        world.setBlockToAir(xCoord, yCoord, zCoord);
                    }

                    thawTicks = Math.max(20 + new Random(ticks * 100).nextInt(60) - Math.round(temp), 10);
                }
            }
        }
    }

    public void setThickness(int thickness)
    {
        this.thickness = Math.min(thickness, 64);
        markBlockForUpdate();
    }

    @Override
    public void readCustomNBT(CompoundNBT nbt)
    {
        thickness = nbt.getInteger("Thickness");
        thawTicks = nbt.getInteger("ThawTicks");
    }

    @Override
    public void writeCustomNBT(CompoundNBT nbt)
    {
        nbt.setInteger("Thickness", thickness);
        nbt.setInteger("ThawTicks", thawTicks);
    }
}
