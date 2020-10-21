package com.fiskmods.heroes.common.item;

import java.util.List;

import com.fiskmods.heroes.FiskHeroes;
import com.fiskmods.heroes.gameboii.GameboiiCartridge;
import com.fiskmods.heroes.gameboii.GameboiiColor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemGameboii extends ItemUntextured
{
    private static final String TAG_CARTRIDGE = "Cartridge";

    public ItemGameboii()
    {
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack itemstack, PlayerEntity player, List list, boolean advanced)
    {
//        list.add(StatCollector.translateToLocalFormatted("item.gameboii.desc1"));
//        list.add(StatCollector.translateToLocalFormatted("item.gameboii.desc2"));

        GameboiiCartridge cartridge = get(itemstack);

        if (cartridge != null)
        {
            list.add(StatCollector.translateToLocalFormatted("gameboii.cartridge." + cartridge.id));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, PlayerEntity player)
    {
        GameboiiCartridge cartridge = get(itemstack);

        if (cartridge != null)
        {
            player.openGui(FiskHeroes.MODID, 5, world, cartridge.ordinal(), 0, 0);
        }
        else if (!world.isRemote)
        {
            player.addChatMessage(new ChatComponentTranslation("message.gameboii.noCartridge").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }

        return itemstack;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (GameboiiColor color : GameboiiColor.values())
        {
            list.add(new ItemStack(item, 1, color.ordinal()));
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        GameboiiCartridge cartridge = get(stack);

        if (cartridge != null)
        {
            return cartridge.create();
        }

        return null;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return get(stack) != null;
    }

    public static GameboiiCartridge get(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().hasKey(TAG_CARTRIDGE, NBT.TAG_ANY_NUMERIC) ? GameboiiCartridge.get(stack.getTag().getByte(TAG_CARTRIDGE)) : null;
    }

    public static ItemStack set(ItemStack stack, GameboiiCartridge cartridge)
    {
        if (!stack.hasTag())
        {
            if (cartridge == null)
            {
                return stack;
            }

            stack.setTag(new CompoundNBT());
        }

        if (cartridge != null)
        {
            stack.getTag().setByte(TAG_CARTRIDGE, (byte) cartridge.ordinal());
        }
        else
        {
            stack.getTag().removeTag(TAG_CARTRIDGE);
        }

        return stack;
    }
}
