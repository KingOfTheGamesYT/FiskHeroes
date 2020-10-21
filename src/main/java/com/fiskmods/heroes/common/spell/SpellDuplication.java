package com.fiskmods.heroes.common.spell;

import com.fiskmods.heroes.SHConstants;
import com.fiskmods.heroes.common.data.Cooldowns.Cooldown;
import com.fiskmods.heroes.common.entity.EntitySpellDuplicate;
import com.fiskmods.heroes.util.SHHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.util.MovingObjectPosition;

public class SpellDuplication extends Spell
{
    private LivingEntity target;

    public SpellDuplication()
    {
        super(Cooldown.SPELL_DUPLICATION, "adadaswad", false);
    }

    @Override
    public boolean canTrigger(LivingEntity caster)
    {
        collectTarget(caster);
        return target != null;
    }

    @Override
    public void onTrigger(LivingEntity caster)
    {
        if (!caster.world.isRemote)
        {
            if (target == null)
            {
                collectTarget(caster);
            }

            if (target != null)
            {
                caster.world.loadedEntityList.stream().filter(t -> t instanceof EntitySpellDuplicate && ((EntitySpellDuplicate) t).isOwner(caster)).forEach(t -> ((LivingEntity) t).setHealth(0));
                int duplicates = 6;

                for (int i = 1; i < duplicates; ++i)
                {
                    caster.world.spawnEntityInWorld(new EntitySpellDuplicate(caster, target, 360F / duplicates * i));
                }

                target = null;
            }
        }
    }

    private void collectTarget(LivingEntity caster)
    {
        MovingObjectPosition mop = null;

        for (float f = 0; f <= 1 && (mop == null || mop.entityHit == null); ++f)
        {
            mop = SHHelper.rayTrace(caster, SHConstants.RANGE_DUPLICATION, f, 4, 1);
        }

        Entity entity = mop != null ? mop.entityHit : null;

        if (entity instanceof EntityDragonPart && ((EntityDragonPart) entity).entityDragonObj instanceof LivingEntity)
        {
            entity = (Entity) ((EntityDragonPart) entity).entityDragonObj;
        }

        target = entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }
}
