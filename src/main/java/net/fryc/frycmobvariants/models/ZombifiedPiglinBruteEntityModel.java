package net.fryc.frycmobvariants.models;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class ZombifiedPiglinBruteEntityModel extends PiglinEntityModel<MobEntity> {

    private final ModelPart leftEar;
    private final ModelTransform bodyRotation;
    private final ModelTransform headRotation;
    private final ModelTransform leftArmRotation;
    private final ModelTransform rightArmRotation;

    public ZombifiedPiglinBruteEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.leftEar = this.head.getChild("left_ear");
        this.bodyRotation = this.body.getTransform();
        this.headRotation = this.head.getTransform();
        this.leftArmRotation = this.leftArm.getTransform();
        this.rightArmRotation = this.rightArm.getTransform();
    }

    public void setAngles(MobEntity mobEntity, float f, float g, float h, float i, float j) {
        this.body.setTransform(this.bodyRotation);
        this.head.setTransform(this.headRotation);
        this.leftArm.setTransform(this.leftArmRotation);
        this.rightArm.setTransform(this.rightArmRotation);
        super.setAngles(mobEntity, f, g, h, i, j);
        float k = 0.5235988F;
        float l = h * 0.1F + f * 0.5F;
        float m = 0.08F + g * 0.4F;
        this.leftEar.roll = -0.5235988F - MathHelper.cos(l * 1.2F) * m;
        this.rightEar.roll = 0.5235988F + MathHelper.cos(l) * m;

        CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, mobEntity.isAttacking(), this.handSwingProgress, h);


        this.leftPants.copyTransform(this.leftLeg);
        this.rightPants.copyTransform(this.rightLeg);
        this.leftSleeve.copyTransform(this.leftArm);
        this.rightSleeve.copyTransform(this.rightArm);
        this.jacket.copyTransform(this.body);
        this.hat.copyTransform(this.head);
    }
}
