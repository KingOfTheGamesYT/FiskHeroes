package com.fiskmods.heroes.common.tileentity;

import com.fiskmods.heroes.util.SHTileHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntitySH extends TileEntity
{
    @Override
    public final void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    @Override
    public final void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    public void markBlockForUpdate()
    {
        world.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeCustomNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readCustomNBT(pkt.func_148857_g());
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        TileEntity tile = SHTileHelper.getTileBase(this);

        if (tile == this)
        {
            int y = yCoord;

            while (SHTileHelper.getTileBase(world.getTileEntity(xCoord, ++y, zCoord)) == this)
            {
                world.markBlockForUpdate(xCoord, y, zCoord);
            }
        }
        else if (tile != null)
        {
            world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        }

        markBlockForUpdate();
    }

    protected abstract void writeCustomNBT(NBTTagCompound nbt);

    protected abstract void readCustomNBT(NBTTagCompound nbt);
}
