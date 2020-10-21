package com.fiskmods.heroes.common.hero.modifier;

import com.fiskmods.heroes.common.entity.IPiercingProjectile;
import com.fiskmods.heroes.common.hero.Hero;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;

public class AbilityDurability extends Ability
{
    public AbilityDurability(int tier)
    {
        super(tier);
    }

    @Override
    public boolean canTakeDamage(LivingEntity entity, LivingEntity attacker, Hero hero, DamageSource source, float amount)
    {
        Entity projectile = source.getSourceOfDamage();

        if (projectile instanceof IPiercingProjectile ? !((IPiercingProjectile) projectile).canPierceDurability(entity) : projectile instanceof EntityArrow)
        {
            return false;
        }

        return super.canTakeDamage(entity, attacker, hero, source, amount);
    }
}
