package com.fiskmods.heroes.common.entity.ai;

import com.fiskmods.heroes.common.entity.EntityIronMan;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIOwnerHurtByTarget2 extends EntityAITarget
{
    EntityIronMan theIronMan;
    LivingEntity theOwnerAttacker;
    private int revenge;

    public EntityAIOwnerHurtByTarget2(EntityIronMan entity)
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
            theOwnerAttacker = owner.getAITarget();
            int i = owner.func_142015_aE();
            return i != revenge && isSuitableTarget(theOwnerAttacker, false);
        }
    }

    @Override
    public void startExecuting()
    {
        taskOwner.setAttackTarget(theOwnerAttacker);
        LivingEntity owner = theIronMan.getOwner();

        if (owner != null)
        {
            revenge = owner.func_142015_aE();
        }

        super.startExecuting();
    }
}
