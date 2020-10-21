package com.fiskmods.heroes.common.gameboii;

import java.util.function.Function;

import com.fiskmods.heroes.gameboii.GameboiiSave;

import net.minecraft.entity.player.PlayerEntity;

public enum GameboiiSaveType
{
    ITEMSTACK(GameboiiSaveItemStack::new),
    FILE(GameboiiSaveFile::new);

    private final Function<EntityPlayer, GameboiiSave> func;

    GameboiiSaveType(Function<EntityPlayer, GameboiiSave> func)
    {
        this.func = func;
    }

    public GameboiiSave load(PlayerEntity player)
    {
        return func.apply(player);
    }
}
