package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.config.Rule;
import com.fiskmods.heroes.common.data.effect.StatEffect;
import com.fiskmods.heroes.common.data.effect.StatusEffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityPhantomArrow extends EntityTrickArrow
{
    public EntityPhantomArrow(World world)
    {
        super(world);
    }

    public EntityPhantomArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityPhantomArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityPhantomArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected void handlePostDamageEffects(LivingEntity entityHit)
    {
        super.handlePostDamageEffects(entityHit);
        
        if (!world.isRemote)
        {
            StatusEffect.add(entityHit, StatEffect.PHASE_SUPPRESSANT, Rule.TICKS_PHANTOMARROW.getHero(getShooter()), 0);
        }
    }
}
