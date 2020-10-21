package com.fiskmods.heroes.common.entity.arrow;

import java.util.Iterator;
import java.util.List;

import com.fiskmods.heroes.common.item.ItemTrickArrow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityVialArrow extends EntityTrickArrow
{
    public EntityVialArrow(World world)
    {
        super(world);
    }

    public EntityVialArrow(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityVialArrow(World world, LivingEntity shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    public EntityVialArrow(World world, LivingEntity shooter, float velocity, boolean horizontal)
    {
        super(world, shooter, velocity, horizontal);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (getArrowId() > 0)
        {
            LivingEntity shooter = null;

            if (getShooter() instanceof LivingEntity)
            {
                shooter = (LivingEntity) getShooter();
            }

            if (!world.isRemote)
            {
                ItemStack potionDamage = ItemTrickArrow.getItem(getArrowItem());

                if (potionDamage != null)
                {
                    List list = Items.potionitem.getEffects(potionDamage);

                    if (list != null && !list.isEmpty())
                    {
                        AxisAlignedBB aabb = boundingBox.expand(4.0D, 2.0D, 4.0D);
                        List list1 = world.getEntitiesWithinAABB(LivingEntity.class, aabb);

                        if (list1 != null && !list1.isEmpty())
                        {
                            Iterator iterator = list1.iterator();

                            while (iterator.hasNext())
                            {
                                LivingEntity entity = (LivingEntity) iterator.next();
                                double dist = getDistanceSqToEntity(entity);

                                if (dist < 16.0D)
                                {
                                    double amount = 1.0D - Math.sqrt(dist) / 4.0D;

                                    if (entity == mop.entityHit)
                                    {
                                        amount = 1.0D;
                                    }

                                    Iterator iterator1 = list.iterator();

                                    while (iterator1.hasNext())
                                    {
                                        PotionEffect effect = (PotionEffect) iterator1.next();
                                        int id = effect.getPotionID();

                                        if (Potion.potionTypes[id].isInstant())
                                        {
                                            Potion.potionTypes[id].affectEntity(shooter, entity, effect.getAmplifier(), amount);
                                        }
                                        else
                                        {
                                            int duration = (int) (amount * effect.getDuration() + 0.5D);

                                            if (duration > 20)
                                            {
                                                entity.addPotionEffect(new PotionEffect(id, duration, effect.getAmplifier()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    world.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), potionDamage.getDamage());
                }
            }
        }

        ItemTrickArrow.setItem(getArrowItem(), null);
        setArrowId(0);

        if (getArrowItem().hasTag() && getArrowItem().getTag().hasNoTags())
        {
            getArrowItem().setTag(null);
        }

        super.onImpact(mop);
    }
}
