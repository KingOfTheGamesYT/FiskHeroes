package com.fiskmods.heroes.common.data.arrow;

import com.fiskmods.heroes.common.arrowtype.ArrowType;
import com.fiskmods.heroes.common.entity.arrow.EntityTrickArrow;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class DefaultArrowData implements IArrowData
{
    protected final ArrowType arrowType;
    protected EntityTrickArrow arrowEntity;

    public DefaultArrowData(ArrowType type, EntityTrickArrow arrow)
    {
        arrowType = type;

        if (arrow != null)
        {
            arrowEntity = type.newInstance(arrow.world, arrow.posX, arrow.posY, arrow.posZ);

            if (arrowEntity != null)
            {
                arrowEntity.setEntityId(arrow.getEntityId());
                arrowEntity.setArrowItem(arrow.getArrowItem());
                arrowEntity.setArrowId(arrow.getArrowId());
                arrowEntity.setHero(arrow.getHero());
                arrowEntity.shootingEntity = arrow.shootingEntity;
                arrowEntity.canBePickedUp = arrow.canBePickedUp;

                if (arrow.getNoEntity())
                {
                    arrowEntity.setNoEntity();
                }
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    @Override
    public void readFromNBT(CompoundNBT nbt)
    {
    }

    @Override
    public void writeToNBT(CompoundNBT nbt)
    {
    }

    @Override
    public EntityTrickArrow getEntity(World world)
    {
        if (arrowEntity == null)
        {
            arrowEntity = arrowType.newInstance(world);
        }

        return arrowEntity;
    }

    @Override
    public ArrowType getType()
    {
        return arrowType;
    }
}
