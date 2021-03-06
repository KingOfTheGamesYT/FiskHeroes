package com.fiskmods.heroes.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySHFlameFX extends EntityFX
{
    private float flameScale;

    public EntitySHFlameFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582D + motionX;
        this.motionY = this.motionY * 0.009999999776482582D + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582D + motionZ;
        double d6 = x + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        d6 = y + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        d6 = z + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
        flameScale = particleScale;
        particleRed = particleGreen = particleBlue = 1.0F;
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        noClip = false;
        setParticleTextureIndex(48);

        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
    }

    @Override
    public void renderParticle(Tessellator tesselator, float partialTicks, float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (particleAge + partialTicks) / particleMaxAge;
        particleScale = flameScale * (1.0F - f5 * f5 * 0.5F);
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
