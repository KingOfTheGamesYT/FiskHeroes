package com.fiskmods.heroes.common.interaction;

import com.fiskmods.heroes.client.sound.SHSounds;
import com.fiskmods.heroes.common.data.Cooldowns.Cooldown;
import com.fiskmods.heroes.common.data.SHData;
import com.fiskmods.heroes.common.entity.EntityFireBlast;
import com.fiskmods.heroes.common.hero.modifier.Ability;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.PlayerEntity;

public class InteractionFireball extends InteractionBase
{
    public InteractionFireball()
    {
        requireModifier(Ability.PYROKINESIS);
    }

    @Override
    public boolean serverRequirements(PlayerEntity player, InteractionType type, int x, int y, int z)
    {
        return !SHData.AIMING.get(player) && player.getHeldItem() == null && !player.isSneaking();
    }

    @Override
    public boolean clientRequirements(PlayerEntity player, InteractionType type, int x, int y, int z)
    {
        return Cooldown.FIREBALL.available(player);
    }

    @Override
    public void receive(PlayerEntity sender, PlayerEntity clientPlayer, InteractionType type, Side side, int x, int y, int z)
    {
        if (side.isServer())
        {
            sender.world.spawnEntityInWorld(new EntityFireBlast(sender.world, sender));
            sender.world.playSoundAtEntity(sender, SHSounds.ABILITY_PYROKINESIS_FIREBALL.toString(), 1, 1);
        }
        else if (sender == clientPlayer)
        {
            sender.swingItem();
            Cooldown.FIREBALL.set(sender);
        }
    }

    @Override
    public TargetPoint getTargetPoint(PlayerEntity player, int x, int y, int z)
    {
        return TARGET_NONE;
    }
}
