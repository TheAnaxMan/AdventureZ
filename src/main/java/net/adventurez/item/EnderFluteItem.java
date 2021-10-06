package net.adventurez.item;

import java.util.List;

import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EnderFluteItem extends Item {

    public EnderFluteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (world.isClient) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundInit.FLUTE_CALL_EVENT, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.2F + 0.9F);
        } else {
            itemStack.damage(1, user, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
            List<EnderWhaleEntity> list = world.getEntitiesByClass(EnderWhaleEntity.class, new Box(user.getBlockPos()).expand(100D), EntityPredicates.EXCEPT_SPECTATOR);
            if (!list.isEmpty()) {
                list.get(0).getMoveControl().moveTo(user.getX(), user.getY(), user.getZ(), 1.0D);
            }
        }

        user.getItemCooldownManager().set(itemStack.getItem(), 40);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient && entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            float cooldownProgress = playerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), 0.0F);
            if (cooldownProgress > 0.0F) {
                world.addParticle(ParticleTypes.NOTE, (double) playerEntity.getX() + playerEntity.getRotationVecClient().getX() + world.random.nextDouble() * 0.3D,
                        (double) playerEntity.getY() + playerEntity.getHeight() * 0.8D, (double) playerEntity.getZ() + playerEntity.getRotationVecClient().getZ() + world.random.nextDouble() * 0.3D,
                        playerEntity.getRotationVecClient().getX() * world.random.nextDouble(), 0.0D, 0.0D);
            }
        }
    }
}
