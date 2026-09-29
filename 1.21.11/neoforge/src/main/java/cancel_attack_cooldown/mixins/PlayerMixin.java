package cancel_attack_cooldown.mixins;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    /**
     * 1.21.2+ 将原 sweepAttack() 拆成了 isSweepAttack（判定，含音效/粒子分支）+ doSweepAttack（AOE）。
     * 在判定处返回 false 可同时去掉横扫伤害与横扫音效/粒子，等价于 1.20.1 中取消 sweepAttack()。
     */
    @Inject(method = "isSweepAttack", at = @At("HEAD"), cancellable = true)
    private void sweepAttack(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "getAttackStrengthScale", at = @At("HEAD"), cancellable = true)
    private void getAttackStrengthScale(float partialTicks, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0F);
    }

    @Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
    private void hideAttackIndicator(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.0F);
    }
}
