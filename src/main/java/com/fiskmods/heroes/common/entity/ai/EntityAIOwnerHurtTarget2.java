package com.fiskmods.heroes.common.entity.ai;

import com.fiskmods.heroes.common.entity.EntityIronMan;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIOwnerHurtTarget2 extends EntityAITarget
{
    EntityIronMan theIronMan;
    LivingEntity theTarget;
    private int attackTime;

    public EntityAIOwnerHurtTarget2(EntityIronMan entity)
    {
        super(entity, false);
        theIronMan = entity;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        LivingEntity owner = theIronMan.getOwner();

        if (owner == null)
        {
            return false;
        }
        else
        {
            theTarget = owner.getLastAttacker();
            int i = owner.getLastAttackerTime();
            return i != attackTime && isSuitableTarget(theTarget, false);
        }
    }

    @Override
    public void startExecuting()
    {
        taskOwner.setAttackTarget(theTarget);
        LivingEntity owner = theIronMan.getOwner();

        if (owner != null)
        {
            attackTime = owner.getLastAttackerTime();
        }

        super.startExecuting();
    }
}
