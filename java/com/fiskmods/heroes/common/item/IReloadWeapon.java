package com.fiskmods.heroes.common.item;

import com.fiskmods.heroes.common.hero.Hero;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IReloadWeapon
{
    int getReloadTime(ItemStack stack, EntityPlayer player, Hero hero);
}
