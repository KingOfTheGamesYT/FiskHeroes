package com.fiskmods.heroes.common.hero.modifier;

import com.fiskmods.heroes.common.data.SHData;

import net.minecraft.entity.player.PlayerEntity;

public class AbilityHover extends Ability
{
    public static final String KEY_HOVER = "HOVER";

    public AbilityHover(int tier)
    {
        super(tier);
    }

    @Override
    public boolean renderIcon(PlayerEntity player)
    {
        return SHData.HOVERING.get(player);
    }

    @Override
    public int getX()
    {
        return 90;
    }

    @Override
    public int getY()
    {
        return 0;
    }
}
