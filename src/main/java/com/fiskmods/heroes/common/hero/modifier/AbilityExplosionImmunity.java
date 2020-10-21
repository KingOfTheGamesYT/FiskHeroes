package com.fiskmods.heroes.common.hero.modifier;

import com.fiskmods.heroes.common.hero.Hero;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class AbilityExplosionImmunity extends Ability
{
    public AbilityExplosionImmunity(int tier)
    {
        super(tier);
    }

    @Override
    public boolean canTakeDamage(LivingEntity entity, LivingEntity attacker, Hero hero, DamageSource source, float amount)
    {
        return !source.isExplosion();
    }
}
