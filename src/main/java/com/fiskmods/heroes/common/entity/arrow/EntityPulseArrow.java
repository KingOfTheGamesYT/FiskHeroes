package com.fiskmods.heroes.common.entity.arrow;

import com.fiskmods.heroes.common.data.world.SHMapData;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPulseArrow extends EntityTrickArrow
{
    public EntityPulseArrow(World world)
    {
        super(world);
    }

    public EntityPulseArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityPulseArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityPulseArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected String getParticleName()
    {
        return "reddust";
    }

    @Override
    protected void spawnTrailingParticles()
    {
        if (rand.nextFloat() < 0.5)
        {
            float f = 0.05F;
            world.spawnParticle(getParticleName(), posX + (rand.nextDouble() * 2 - 1) * f, posY + (rand.nextDouble() * 2 - 1) * f, posZ + (rand.nextDouble() * 2 - 1) * f, 0, 0, 0);
        }
    }

    @Override
    protected void onImpactEntity(MovingObjectPosition mop)
    {
    }

    @Override
    protected void onImpactBlock(MovingObjectPosition mop)
    {
        super.onImpactBlock(mop);

        if (!world.isRemote)
        {
            Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);

//            if (block.shouldCheckWeakPower(world, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit))
            {
                SHMapData.get(world).power(world, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);

                world.notifyBlockOfNeighborChange(mop.blockX, mop.blockY, mop.blockZ, block);
                world.markAndNotifyBlock(mop.blockX, mop.blockY, mop.blockZ, null, block, block, 3);
            }
        }
    }
}
