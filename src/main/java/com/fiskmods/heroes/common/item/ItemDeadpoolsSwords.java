package com.fiskmods.heroes.common.item;

import com.fiskmods.heroes.SHConstants;
import com.fiskmods.heroes.client.SHRenderHooks;
import com.fiskmods.heroes.common.hero.Heroes;
import com.fiskmods.heroes.common.tileentity.TileEntityDisplayStand;
import com.fiskmods.heroes.util.SHHelper;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import mods.battlegear2.api.IAllowItem;
import mods.battlegear2.api.IOffhandWield;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@InterfaceList(value = {@Interface(modid = "battlegear2", iface = "mods.battlegear2.api.IOffhandWield"), @Interface(modid = "battlegear2", iface = "mods.battlegear2.api.IAllowItem")})
public class ItemDeadpoolsSwords extends ItemSword implements IDualItem, IOffhandWield, IAllowItem, IEquipmentItem
{
    public ItemDeadpoolsSwords()
    {
        super(ToolMaterial.IRON);
        setMaxDamage(SHConstants.MAX_DMG_DEADPOOL_SWORDS);
    }

    @Override
    public float getSwingProgress(PlayerEntity player, float partialTicks)
    {
        return SHRenderHooks.getSwingProgress(player, partialTicks);
    }

    @Override
    public boolean onEntitySwingOffHand(PlayerEntity player, ItemStack itemstack)
    {
        return false;
    }

    @Override
    public boolean onSwingEnd(PlayerEntity player, ItemStack itemstack, boolean right)
    {
        return false;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, TileEntityDisplayStand tile)
    {
        return SHHelper.getHeroFromArmor(tile.fakePlayer, 2) == Heroes.deadpool_xmen;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack itemstack)
    {
        Multimap multimap = super.getAttributeModifiers(itemstack);
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", SHConstants.DMG_DEADPOOL_SWORDS, 0));
        return multimap;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
    }

    @Override
    public boolean isOffhandWieldable(ItemStack itemstack, PlayerEntity player)
    {
        return false;
    }

    @Override
    public boolean allowOffhand(ItemStack main, ItemStack off)
    {
        return false;
    }
}
