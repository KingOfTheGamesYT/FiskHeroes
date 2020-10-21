package com.fiskmods.heroes.common.hero.modifier;

import com.fiskmods.heroes.SHConstants;
import com.fiskmods.heroes.common.config.Rule;
import com.fiskmods.heroes.common.data.SHData;
import com.fiskmods.heroes.common.data.effect.StatEffect;
import com.fiskmods.heroes.common.data.effect.StatusEffect;
import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.util.FiskServerUtils;
import com.fiskmods.heroes.util.SHHelper;

import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class AbilityIntangibility extends Ability
{
    public static final String KEY_INTANGIBILITY = "INTANGIBILITY";

    private final boolean isAbsolute;

    public AbilityIntangibility(int tier, boolean absolute)
    {
        super(tier);
        isAbsolute = absolute;
    }

    @Override
    public void onUpdate(LivingEntity entity, Hero hero, Phase phase, boolean enabled)
    {
        if (phase == Phase.END && enabled)
        {
            boolean flag = SHData.INTANGIBLE.get(entity);

            if (flag)
            {
                flag = isActive(entity);
                entity.noClip = flag;
            }

            SHHelper.incr(SHData.INTANGIBILITY_TIMER, entity, SHConstants.FADE_INVISIBILITY, flag);
        }
    }

    @Override
    public boolean canTakeDamage(LivingEntity entity, LivingEntity attacker, Hero hero, DamageSource source, float amount)
    {
        if (SHData.INTANGIBLE.get(entity) && isActive(entity))
        {
            if (isAbsolute)
            {
                return !FiskServerUtils.isMeleeDamage(source) && !FiskServerUtils.isProjectileDamage(source) && !source.canHarmInCreative() && source != DamageSource.inWall;
            }

            return source != DamageSource.inWall;
        }

        return true;
    }

    @Override
    public float damageDealt(LivingEntity entity, LivingEntity target, Hero hero, DamageSource source, float amount, float originalAmount)
    {
        return isAbsolute && SHData.INTANGIBLE.get(entity) && isActive(entity) && FiskServerUtils.isMeleeDamage(source) ? 0 : amount;
    }

    @Override
    public boolean isActive(LivingEntity entity)
    {
        return !StatusEffect.has(entity, StatEffect.PHASE_SUPPRESSANT) && Rule.ALLOW_INTANGIBILITY.get(entity);
    }

    @Override
    public boolean renderIcon(PlayerEntity player)
    {
        return SHData.INTANGIBLE.get(player);
    }

    @Override
    public int getX()
    {
        return 54;
    }

    @Override
    public int getY()
    {
        return 0;
    }
}
