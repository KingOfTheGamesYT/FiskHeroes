package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.damagesource.ModDamageSources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCarrotArrow extends EntityTrickArrow
{
    public EntityCarrotArrow(World world)
    {
        super(world);
    }

    public EntityCarrotArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityCarrotArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityCarrotArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected DamageSource getDamageSource(Entity entity)
    {
        return ModDamageSources.causeCarrowDamage(this, getShooter());
    }

    @Override
    protected void handlePostDamageEffects(LivingEntity entityHit)
    {
        super.handlePostDamageEffects(entityHit);
        entityHit.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1200, 0));
    }
}
