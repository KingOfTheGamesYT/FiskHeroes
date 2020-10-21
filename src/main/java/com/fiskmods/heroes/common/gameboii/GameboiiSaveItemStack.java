package com.fiskmods.heroes.common.gameboii;

import com.fiskmods.heroes.gameboii.GameboiiCartridge;
import com.fiskmods.heroes.gameboii.GameboiiSave;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class GameboiiSaveItemStack extends GameboiiSave
{
    public final PlayerEntity player;

    public GameboiiSaveItemStack(PlayerEntity player)
    {
        this.player = player;
    }

    @Override
    public void saveData(byte[] data, GameboiiCartridge cartridge) throws Exception
    {
        ItemStack stack = player.getHeldItem();

        if (stack != null)
        {
            if (!stack.hasTag())
            {
                stack.setTag(new CompoundNBT());
            }

            stack.getTag().setByteArray(cartridge.id, data);
        }
    }

    @Override
    public byte[] loadData(GameboiiCartridge cartridge) throws Exception
    {
        ItemStack stack = player.getHeldItem();

        if (stack != null && stack.hasTag())
        {
            CompoundNBT compound = stack.getTag();
            return compound.getByteArray(cartridge.id);
        }

        return null;
    }
}
