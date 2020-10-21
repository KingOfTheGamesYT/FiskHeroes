package com.fiskmods.heroes.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySHFreezeRayFX extends EntityFX
{
    private float flameScale;

    public EntitySHFreezeRayFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582D + motionX;
        this.motionY = this.motionY * 0.009999999776482582D + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582D + motionZ;
        flameScale = particleScale;
        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;
        particleMaxAge = 3;
        noClip = false;
        setParticleTextureIndex(7);

        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;

        if (motionY == 0)
        {
            int divider = 10;
            EntitySHFreezeRayFX entity = new EntitySHFreezeRayFX(world, x + (rand.nextDouble() - 0.5D) / divider, y + (rand.nextDouble() - 0.5D) / divider, z + (rand.nextDouble() - 0.5D) / divider, motionX, 1, motionZ);
            entity.particleRed = 0.4F;
            entity.particleGreen = 0.4F + 0.3F;
            entity.particleBlue = 1.0F;
            entity.motionY = 0;

            Minecraft.getInstance().effectRenderer.addEffect(entity);
        }
    }

    @Override
    public void renderParticle(Tessellator tesselator, float partialTicks, float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (particleAge + partialTicks) / particleMaxAge;
        particleScale = flameScale * (1.0F - f5 * f5 * 0.5F) * 0.75F;
        super.renderParticle(tesselator, partialTicks, f, f1, f2, f3, f4);
    }

    @Override
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    @Override
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9599999785423279D;
        motionY *= 0.9599999785423279D;
        motionZ *= 0.9599999785423279D;

        if (onGround)
        {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }
    }
}
