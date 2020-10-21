package com.fiskmods.heroes.common.move;

import com.fiskmods.heroes.common.entity.EntityIcicle;
import com.fiskmods.heroes.common.hero.Hero;
import com.google.common.collect.ImmutableMap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MovingObjectPosition;

public class MoveIcicles extends Move
{
    public MoveIcicles(int tier)
    {
        super(tier, MouseAction.RIGHT_CLICK);
        setModifierFormat(Number::intValue);
    }

    @Override
    public String localizeModifier(String key)
    {
        switch (key)
        {
        case KEY_QUANTITY:
            return "move.modifier.quantity";
        default:
            return null;
        }
    }

    @Override
    public boolean onActivated(LivingEntity entity, Hero hero, MovingObjectPosition mop, MoveActivation activation, ImmutableMap<String, Number> modifiers, float focus)
    {
        int num = modifiers.getOrDefault(KEY_QUANTITY, 0).intValue();

        if (num > 0)
        {
            if (!entity.world.isRemote)
            {
                float spread = 0.5F;

                for (int i = 0; i < num; ++i)
                {
                    EntityIcicle icicle = new EntityIcicle(entity.world, entity);

                    if (i > 0)
                    {
                        icicle.motionX += (Math.random() - 0.5D) * spread;
                        icicle.motionY += (Math.random() - 0.5D) * spread;
                        icicle.motionZ += (Math.random() - 0.5D) * spread;
                    }

                    entity.world.spawnEntityInWorld(icicle);
                }
            }
            else
            {
                entity.swingItem();
            }

            return true;
        }

        return false;
    }
}
