package com.fiskmods.heroes.common.entity.arrow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityExcessiveArrow extends EntityTrickArrow
{
    public EntityExcessiveArrow(World world)
    {
        super(world);
    }

    public EntityExcessiveArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityExcessiveArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityExcessiveArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    public boolean onCaught(LivingEntity entity)
    {
        return false;
    }
}
