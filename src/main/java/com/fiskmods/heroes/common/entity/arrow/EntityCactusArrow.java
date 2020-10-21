package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.entity.EntityCactusSpike;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCactusArrow extends EntityTrickArrow
{
    public EntityCactusArrow(World world)
    {
        super(world);
    }

    public EntityCactusArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityCactusArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityCactusArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        super.onImpact(mop);
        createExplosion();
    }

    public void createExplosion()
    {
        if (!world.isRemote)
        {
            int j = 20 + rand.nextInt(2);

            for (int i = 0; i < j; ++i)
            {
                EntityCactusSpike entity = new EntityCactusSpike(world);

                if (getShooter() instanceof LivingEntity)
                {
                    entity = new EntityCactusSpike(world, (LivingEntity) getShooter());
                }

                float multiplier = 0.2F;
                float divider = 0.05F;
                entity.motionX = (posX - prevPosX) / divider + (rand.nextDouble() * 2 - 1) * multiplier;
                entity.motionY = (posY - prevPosY) / divider + (rand.nextDouble() * 2 - 1) * multiplier;
                entity.motionZ = (posZ - prevPosZ) / divider + (rand.nextDouble() * 2 - 1) * multiplier;

                entity.setPosition(posX, posY, posZ);
                world.spawnEntityInWorld(entity);
            }

            world.createExplosion(getShooter(), posX, posY, posZ, 1.999F, false);
            setDead();
        }
    }
}
