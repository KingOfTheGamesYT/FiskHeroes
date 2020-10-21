package com.fiskmods.heroes.common.interaction;

import com.fiskmods.heroes.common.data.Cooldowns.Cooldown;
import com.fiskmods.heroes.common.entity.EntityEarthquake;
import com.fiskmods.heroes.common.hero.modifier.Ability;
import com.fiskmods.heroes.util.SHHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.PlayerEntity;

public class InteractionEarthquake extends InteractionBase
{
    public static final String KEY_EARTHQUAKE = "EARTHQUAKE";

    public InteractionEarthquake()
    {
        super(InteractionType.RIGHT_CLICK_BLOCK);
        requireModifier(Ability.GEOKINESIS);
    }

    @Override
    public boolean serverRequirements(PlayerEntity player, InteractionType type, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean clientRequirements(PlayerEntity player, InteractionType type, int x, int y, int z)
    {
        return Cooldown.EARTHQUAKE.available(player) && SHHelper.getHero(player).isKeyPressed(player, KEY_EARTHQUAKE);
    }

    @Override
    public void receive(PlayerEntity sender, PlayerEntity clientPlayer, InteractionType type, Side side, int x, int y, int z)
    {
        if (side.isServer())
        {
            EntityEarthquake entity = new EntityEarthquake(sender.world, sender);
            entity.posX = x + 0.5F;
            entity.posY = y + 0.5F;
            entity.posZ = z + 0.5F;

            sender.world.spawnEntityInWorld(entity);
        }
        else if (sender == clientPlayer)
        {
            sender.swingItem();
            Cooldown.EARTHQUAKE.set(sender);
        }
    }

    @Override
    public TargetPoint getTargetPoint(PlayerEntity player, int x, int y, int z)
    {
        return TARGET_NONE;
    }
}
