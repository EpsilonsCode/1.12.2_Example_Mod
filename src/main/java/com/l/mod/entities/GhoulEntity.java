package com.l.mod.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GhoulEntity extends EntityMob {

    private static final DataParameter<Boolean> IS_ASLEEP = EntityDataManager.<Boolean>createKey(GhoulEntity.class, DataSerializers.BOOLEAN);

    private int ticks = 0;

    public GhoulEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISleep(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(this.isServerWorld())
        {
            //Minecraft.getMinecraft().player.sendChatMessage(ticks + " : " + isAsleep() + " : " );
            /*
            for(EntityAITasks.EntityAITaskEntry entry : this.tasks.taskEntries)
            {
                if(entry.using)
                    Minecraft.getMinecraft().player.sendChatMessage(entry.action.toString());
            }

             */
            EntityPlayer player = this.world.getNearestAttackablePlayer(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), 16, 16);
            if(player!=null)
            {
                ticks = 0;
                setIsAsleep(false);
            }
            else
            {
                if(ticks<400)
                    ticks++;
                else
                    setIsAsleep(true);
            }
        }
    }


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_ASLEEP, Boolean.valueOf(false));
    }

    public void setIsAsleep(boolean isAsleep)
    {
        this.getDataManager().set(IS_ASLEEP, Boolean.valueOf(isAsleep));
    }

    public boolean isAsleep()
    {
        return this.getDataManager().get(IS_ASLEEP).booleanValue();
    }

    public static class EntityAISleep extends EntityAIBase
    {
        private final GhoulEntity entity;

        public EntityAISleep(GhoulEntity entity) {
            this.entity = entity;
            setMutexBits(7);
        }

        @Override
        public boolean shouldExecute() {
            return entity.isAsleep();
        }

        @Override
        public boolean isInterruptible()
        {
            return false;
        }

    }
}
