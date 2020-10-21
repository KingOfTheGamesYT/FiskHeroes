package com.fiskmods.heroes.common.entity;

import com.fiskmods.heroes.SHConstants;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLightningCast extends Entity implements IEntityAdditionalSpawnData
{
    public LivingEntity casterEntity;
    public Entity anchorEntity;

    private int doExplosion;

    public EntityLightningCast(World world)
    {
        super(world);
        noClip = true;
        renderDistanceWeight = 64;
        ignoreFrustumCheck = true;
        preventEntitySpawning = false;
        setSize(0.1F, 0.1F);
    }

    public EntityLightningCast(World world, LivingEntity caster, Entity entity, Vec3 vec3, int explode)
    {
        this(world);
        casterEntity = caster;
        anchorEntity = entity;
        doExplosion = explode;
        setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, 0, 0);
    }

    @Override
    public void onUpdate()
    {
        if (doExplosion > 0)
        {
            if (world.isRemote)
            {
                if ((doExplosion & 1) == 1)
                {
                    world.spawnParticle("largeexplode", posX, posY, posZ, 0, 0, 0);
                }
            }
            else if ((doExplosion & 2) == 2)
            {
                playSound("random.explode", 6, 0.6F + rand.nextFloat() * 0.2F);
            }

            doExplosion = 0;
        }

        if (++ticksExisted > SHConstants.TICKS_LIGHTNING_CAST)
        {
            setDead();
        }
    }

    @Override
    public boolean shouldRenderInPass(int pass)
    {
        return pass == 1;
    }

    @Override
    public void readEntityFromNBT(CompoundNBT nbt)
    {
    }

    @Override
    public void writeEntityToNBT(CompoundNBT nbt)
    {
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    public void writeSpawnData(ByteBuf buf)
    {
        buf.writeInt(casterEntity != null ? casterEntity.getEntityId() : -1);
        buf.writeInt(anchorEntity != null ? anchorEntity.getEntityId() : -1);
        buf.writeByte(doExplosion);
    }

    @Override
    public void readSpawnData(ByteBuf buf)
    {
        Entity entity = world.getEntityByID(buf.readInt());

        if (entity instanceof LivingEntity)
        {
            casterEntity = (LivingEntity) entity;
        }

        anchorEntity = world.getEntityByID(buf.readInt());
        doExplosion = buf.readByte();
    }
}
