package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.config.Rule;
import com.fiskmods.heroes.common.data.effect.StatEffect;
import com.fiskmods.heroes.common.data.effect.StatusEffect;
import com.fiskmods.heroes.common.entity.IPiercingProjectile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityTutridiumArrow extends EntityTrickArrow implements IPiercingProjectile
{
    public EntityTutridiumArrow(World world)
    {
        super(world);
    }

    public EntityTutridiumArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityTutridiumArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityTutridiumArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected void handlePostDamageEffects(LivingEntity entityHit)
    {
        super.handlePostDamageEffects(entityHit);
        
        if (!world.isRemote)
        {
            StatusEffect.add(entityHit, StatEffect.TUTRIDIUM_POISON, Rule.TICKS_TUTRIDIUMARROW.getHero(getShooter()), 0);
        }
    }

    @Override
    public boolean canPierceDurability(LivingEntity entity)
    {
        return true;
    }
}
