package net.adventurez.entity.model;

import com.google.common.collect.ImmutableList;

import net.adventurez.entity.RedFungusEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class RedFungusModel<T extends RedFungusEntity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public RedFungusModel() {
        this.head = (new ModelPart(this)).setTextureSize(64, 64);
        this.head.setPivot(0.0F, 10.0F, 0.0F);
        this.head.setTextureOffset(0, 0).addCuboid(-8.0F, -5.0304F, -7.6528F, 16.0F, 7.0F, 15.0F, 0.0F, false);
        this.head.setTextureOffset(0, 22).addCuboid(-6.0F, -7.0304F, -5.6528F, 12.0F, 2.0F, 11.0F, 0.0F, false);

        this.body = (new ModelPart(this)).setTextureSize(64, 64);
        this.body.setPivot(0.0F, 15.0F, 0.0F);
        this.body.setTextureOffset(0, 35).addCuboid(-4.0F, -4.0F, -3.0F, 8.0F, 7.0F, 5.0F, 0.0F, false);

        this.leftFoot = (new ModelPart(this)).setTextureSize(64, 64);
        this.leftFoot.setPivot(-2.0F, 18.0F, -0.5F);
        this.leftFoot.setTextureOffset(26, 35).addCuboid(-1.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

        this.rightFoot = (new ModelPart(this)).setTextureSize(64, 64);
        this.rightFoot.setPivot(2.0F, 18.0F, -0.5F);
        this.rightFoot.setTextureOffset(0, 0).addCuboid(-2.0F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.body, this.leftFoot, this.rightFoot);
    }

    @Override
    public void setAngles(T fungus, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
        this.head.pitch = -0.1745F;
        this.rightFoot.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftFoot.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
        // System.out.print(this.handSwingProgress);
        if (k > 0) {
            this.head.pitch = -k * 1.2F;
        }

    }

}
