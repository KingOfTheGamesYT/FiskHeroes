package com.fiskmods.heroes.common.item;

import java.util.List;
import java.util.function.Predicate;

import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.common.hero.HeroIteration;
import com.fiskmods.heroes.common.interaction.key.KeyPressMiniaturizeSuit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemMiniAtomSuit extends ItemFlashRing
{
    private static final Predicate<Hero> PREDICATE = t -> !t.isHidden() && t.getKeyBinding(KeyPressMiniaturizeSuit.KEY_MINIATURIZE_SUIT) > 0;

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, PlayerEntity player)
    {
//        if (!world.isRemote)
        {
            if (!itemstack.hasTag())
            {
                Hero hero = Hero.REGISTRY.getValues().stream().filter(PREDICATE).findFirst().orElse(null);
                itemstack.setTag(new CompoundNBT());

                if (hero != null)
                {
                    setContainedArmor(itemstack, hero.getDefault().createArmorStacks());
                }
            }
            else if (itemstack.getTag().hasKey("Suit", NBT.TAG_STRING))
            {
                HeroIteration iter = HeroIteration.lookup(itemstack.getTag().getString("Suit"));

                if (iter != null)
                {
                    setContainedArmor(itemstack, iter.createArmorStacks());
                }

                itemstack.getTag().removeTag("Suit");
            }

            ItemStack[] armorFromNBT = getArmorFromNBT(itemstack);

            if (armorFromNBT != null)
            {
                if (player.capabilities.isCreativeMode || --itemstack.stackSize <= 0)
                {
                    player.setCurrentItemOrArmor(0, null);
                }

                for (int i = 0; i < 4; ++i)
                {
                    swapArmor(player, armorFromNBT[i], 3 - i);
                }
            }
        }

        return itemstack;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        Hero.REGISTRY.getValues().stream().filter(PREDICATE).sorted().forEach(t ->
        {
            if (tab == null)
            {
                t.getIterations().stream().sorted().forEach(t1 -> list.add(create(item, t1)));
            }
            else
            {
                list.add(create(item, t.getDefault()));
            }
        });
    }

    @Override
    public void getListItems(Item item, CreativeTabs tab, List list)
    {
        Hero.REGISTRY.getValues().stream().filter(PREDICATE).findFirst().ifPresent(t -> list.add(create(item, t.getDefault())));
    }
}
