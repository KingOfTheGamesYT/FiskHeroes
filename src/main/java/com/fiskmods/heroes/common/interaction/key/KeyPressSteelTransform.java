package com.fiskmods.heroes.common.interaction.key;

import com.fiskmods.heroes.common.data.SHData;
import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.common.hero.modifier.Ability;
import com.fiskmods.heroes.common.hero.modifier.AbilitySteelTransformation;
import com.fiskmods.heroes.common.interaction.InteractionType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;

public class KeyPressSteelTransform extends KeyPressBase
{
    public KeyPressSteelTransform()
    {
        requireModifier(Ability.STEEL_TRANSFORMATION);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public KeyBinding getKey(PlayerEntity player, Hero hero)
    {
        return hero.getKey(player, AbilitySteelTransformation.KEY_STEEL_TRANSFORM);
    }

    @Override
    public void receive(PlayerEntity sender, PlayerEntity clientPlayer, InteractionType type, Side side, int x, int y, int z)
    {
        if (side.isClient())
        {
            SHData.STEELED.set(sender, !SHData.STEELED.get(sender));
        }
    }
}
