package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.entity.IPiercingProjectile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityVibraniumArrow extends EntityTrickArrow implements IPiercingProjectile
{
    public EntityVibraniumArrow(World world)
    {
        super(world);
    }

    public EntityVibraniumArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityVibraniumArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityVibraniumArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    public boolean canPierceDurability(LivingEntity entity)
    {
        return true;
    }
}
