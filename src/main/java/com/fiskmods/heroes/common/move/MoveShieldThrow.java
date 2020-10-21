package com.fiskmods.heroes.common.move;

import com.fiskmods.heroes.client.sound.SHSounds;
import com.fiskmods.heroes.common.entity.EntityThrownShield;
import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.common.item.ModItems;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class MoveShieldThrow extends Move
{
    public static final String KEY_BOUNCE = "BOUNCE";

    public MoveShieldThrow(int tier)
    {
        super(tier, MouseAction.LEFT_CLICK);
        setModifierFormat(Number::intValue);
    }

    @Override
    public String localizeModifier(String key)
    {
        switch (key)
        {
        case KEY_BOUNCE:
            return "move.modifier.bounce";
        default:
            return null;
        }
    }

    @Override
    public boolean onActivated(LivingEntity entity, Hero hero, MovingObjectPosition mop, MoveActivation activation, ImmutableMap<String, Number> modifiers, float focus)
    {
        ItemStack heldItem = entity.getHeldItem();

        if (heldItem != null && heldItem.getItem() == ModItems.captainAmericasShield)
        {
            if (!entity.world.isRemote)
            {
                entity.world.playSoundAtEntity(entity, SHSounds.ITEM_SHIELD_THROW.toString(), 1, 1 + (entity.getRNG().nextFloat() - 0.5F) * 0.2F);
                entity.world.spawnEntityInWorld(new EntityThrownShield(entity.world, entity, heldItem.copy()));
                entity.setCurrentItemOrArmor(0, null);
            }
            else
            {
                entity.swingItem();
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean canPickupItem(PlayerInventory inventory, ItemStack stack, MoveSet set)
    {
        return stack.getItem() == ModItems.captainAmericasShield;
    }
}
