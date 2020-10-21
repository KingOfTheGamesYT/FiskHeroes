package com.fiskmods.heroes.common.interaction.key;

import com.fiskmods.heroes.client.gui.GuiSpellMenu;
import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.common.hero.modifier.Ability;
import com.fiskmods.heroes.common.hero.modifier.AbilitySpellcasting;
import com.fiskmods.heroes.common.interaction.InteractionType;
import com.fiskmods.heroes.util.SHHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;

public class KeyPressSpellMenu extends KeyPressBase
{
    public KeyPressSpellMenu()
    {
        requireModifier(Ability.SPELLCASTING);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public KeyBinding getKey(PlayerEntity player, Hero hero)
    {
        return hero.getKey(player, AbilitySpellcasting.KEY_MENU);
    }

    @Override
    public void receive(PlayerEntity sender, PlayerEntity clientPlayer, InteractionType type, Side side, int x, int y, int z)
    {
        if (side.isClient())
        {
            openMenu(sender);
        }
    }

    @SideOnly(Side.CLIENT)
    private void openMenu(PlayerEntity sender)
    {
        Minecraft.getInstance().displayGuiScreen(new GuiSpellMenu(SHHelper.getSpells(sender, SHHelper.getHero(sender))));
    }
}
