package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.config.Rule;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExplosiveArrow extends EntityTrickArrow
{
    public EntityExplosiveArrow(World world)
    {
        super(world);
    }

    public EntityExplosiveArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityExplosiveArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityExplosiveArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        super.onImpact(mop);
        trigger();
    }

    @Override
    public boolean onCaught(LivingEntity entity)
    {
        trigger();
        return true;
    }

    public void trigger()
    {
        if (!world.isRemote)
        {
            world.createExplosion(getShooter(), posX, posY, posZ, Rule.RADIUS_EXPLOSIVEARROW.getHero(getShooter()), false);
            setDead();
        }
    }
}
