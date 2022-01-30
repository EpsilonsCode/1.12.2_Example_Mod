package com.l.mod.entities;

import com.l.mod.util.Reference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class GhoulRenderer extends RenderLiving<GhoulEntity> {

    public static final Factory FACTORY = new Factory();

    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/ghoul.png");

    public GhoulRenderer(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new GhoulModel(), 0.2F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(GhoulEntity entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<GhoulEntity> {

        @Override
        public Render<? super GhoulEntity> createRenderFor(RenderManager manager) {
            return new GhoulRenderer(manager);
        }

    }

}
