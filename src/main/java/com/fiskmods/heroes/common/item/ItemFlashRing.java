package com.fiskmods.heroes.common.item;

import java.util.List;
import java.util.function.Predicate;

import com.fiskmods.heroes.common.book.widget.IItemListEntry;
import com.fiskmods.heroes.common.hero.Hero;
import com.fiskmods.heroes.common.hero.HeroIteration;
import com.fiskmods.heroes.util.FiskServerUtils;
import com.fiskmods.heroes.util.SHFormatHelper;
import com.fiskmods.heroes.util.SHHelper;
import com.google.common.collect.Iterables;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemFlashRing extends ItemUntextured implements IItemListEntry
{
    private static final Predicate<Hero> PREDICATE = t -> Iterables.any(t.getEquipmentStacks(), t1 -> t1.getItem() == ModItems.flashRing);

    public ItemFlashRing()
    {
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack itemstack, PlayerEntity player, List list, boolean advanced)
    {
        HeroIteration iter = getContainedHero(itemstack);

        if (iter != null)
        {
            list.add(SHFormatHelper.formatHero(iter));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, PlayerEntity player)
    {
//        if (!world.isRemote)
        {
            if (itemstack.hasTag())
            {
                if (itemstack.getTag().hasKey("Suit", NBT.TAG_STRING))
                {
                    HeroIteration iter = HeroIteration.lookup(itemstack.getTag().getString("Suit"));

                    if (iter != null)
                    {
                        setContainedArmor(itemstack, iter.createArmorStacks());
                    }

                    itemstack.getTag().removeTag("Suit");
                }

                if (itemstack.getTag().getBoolean("Dispensed"))
                {
                    itemstack.getTag().removeTag("Items");
                }

                if (itemstack.getTag().hasKey("Dispensed"))
                {
                    itemstack.getTag().removeTag("Dispensed");
                }
            }

            ItemStack[] equipment = SHHelper.getEquipment(player);
            HeroIteration iter = SHHelper.getHeroIter(equipment);

            if (itemstack.hasTag() && itemstack.getTag().hasKey("Items", NBT.TAG_LIST))
            {
                ItemStack[] armorFromNBT = getArmorFromNBT(itemstack);

                if (iter != null && PREDICATE.test(iter.hero))
                {
                    setContainedArmor(itemstack, equipment);

                    if (armorFromNBT != null)
                    {
                        for (int i = 0; i < 4; ++i)
                        {
                            player.setCurrentItemOrArmor(4 - i, armorFromNBT[i]);
                        }
                    }
                }
                else
                {
                    if (armorFromNBT != null)
                    {
                        for (int i = 0; i < 4; ++i)
                        {
                            swapArmor(player, armorFromNBT[i], 3 - i);
                        }
                    }

                    itemstack.getTag().removeTag("Items");
                }
            }
            else if (iter != null && PREDICATE.test(iter.hero))
            {
                setContainedArmor(itemstack, equipment);

                for (int i = 1; i <= 4; ++i)
                {
                    player.setCurrentItemOrArmor(i, null);
                }
            }

            if (itemstack.hasTag() && itemstack.getTag().hasNoTags())
            {
                itemstack.setTag(null);
            }
        }

        return itemstack;
    }

    public void swapArmor(PlayerEntity player, ItemStack itemstack, int slot)
    {
        if (itemstack != null)
        {
            ItemStack armor = player.getCurrentArmor(slot);

            if (armor != null && !player.inventory.addItemStackToInventory(armor))
            {
                player.entityDropItem(armor, 0);
            }

            player.setCurrentItemOrArmor(slot + 1, itemstack);
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item));
        Hero.REGISTRY.getValues().stream().filter(PREDICATE.and(t -> !t.isHidden())).sorted().forEach(t ->
        {
            if (tab == null)
            {
                t.getIterations().stream().sorted().forEach(t1 -> list.add(create(t1)));
            }
            else
            {
                list.add(create(t.getDefault()));
            }
        });
    }

    @Override
    public void getListItems(Item item, CreativeTabs tab, List list)
    {
        super.getSubItems(item, tab, list);
    }

    @Override
    public String getPageLink(ItemStack itemstack)
    {
        return itemstack.getUnlocalizedName();
    }

    public static void setContainedArmor(ItemStack itemstack, ItemStack... armor)
    {
        NBTTagList itemsList = new NBTTagList();

        for (int i = 0; i < armor.length; ++i)
        {
            if (armor[i] != null)
            {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.setByte("Slot", (byte) i);
                itemsList.appendTag(armor[i].writeToNBT(itemTag));
            }
        }

        if (!itemstack.hasTag())
        {
            itemstack.setTag(new CompoundNBT());
        }

        itemstack.getTag().setTag("Items", itemsList);
    }

    public static ItemStack[] getArmorFromNBT(ItemStack itemstack)
    {
        if (itemstack.hasTag() && itemstack.getTag().hasKey("Items"))
        {
            NBTTagList nbtItems = itemstack.getTag().getTagList("Items", NBT.TAG_COMPOUND);
            ItemStack[] items = new ItemStack[4];

            for (int i = 0; i < nbtItems.tagCount(); ++i)
            {
                CompoundNBT item = nbtItems.getCompoundTagAt(i);
                byte slot = item.getByte("Slot");

                if (slot >= 0 && slot < items.length)
                {
                    items[slot] = ItemStack.loadItemStackFromNBT(item);
                }
            }

            return items;
        }

        return null;
    }

    public static HeroIteration getContainedHero(ItemStack itemstack)
    {
        if (itemstack.hasTag())
        {
            if (itemstack.getTag().hasKey("Items", NBT.TAG_LIST) && !itemstack.getTag().getBoolean("Dispensed"))
            {
                return SHHelper.getHeroIterFromArmor(FiskServerUtils.nonNull(getArmorFromNBT(itemstack)));
            }
            else if (itemstack.getTag().hasKey("Suit", NBT.TAG_STRING))
            {
                return HeroIteration.lookup(itemstack.getTag().getString("Suit"));
            }
        }

        return null;
    }

    public static ItemStack create(Item item, HeroIteration iter)
    {
        ItemStack itemstack = new ItemStack(item);
        itemstack.setTag(new CompoundNBT());
        itemstack.getTag().setString("Suit", iter.toString());

        return itemstack;
    }

    public static ItemStack create(HeroIteration iter)
    {
        return create(ModItems.flashRing, iter);
    }
}
