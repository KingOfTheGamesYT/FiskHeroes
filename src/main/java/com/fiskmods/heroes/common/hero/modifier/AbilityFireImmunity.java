package com.fiskmods.heroes.common.hero.modifier;

import com.fiskmods.heroes.common.data.SHData;
import com.fiskmods.heroes.common.hero.Hero;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class AbilityFireImmunity extends Ability
{
    public AbilityFireImmunity(int tier)
    {
        super(tier);
    }

    @Override
    public boolean canTakeDamage(LivingEntity entity, LivingEntity attacker, Hero hero, DamageSource source, float amount)
    {
        if (source.isFireDamage() && isActive(entity))
        {
            entity.extinguish();
            return false;
        }

        return true;
    }

    @Override
    public boolean isActive(LivingEntity entity)
    {
        return SHData.METAL_HEAT_COOLDOWN.get(entity) <= 0;
    }
}
