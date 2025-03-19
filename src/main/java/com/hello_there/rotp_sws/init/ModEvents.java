package com.hello_there.rotp_sws.init;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.hello_there.rotp_sws.config.StandSpawnConfig;
import com.hello_there.rotp_ss.util.PlayerDataHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Optional;

public class ModEvents {

    @SubscribeEvent
    public void onPlayerFirstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide) return;

        if (!PlayerDataHelper.hasRolledStand(player)) {
            double roll = player.getRandom().nextDouble() * 100.0D;
            boolean grantStand = roll <= StandSpawnConfig.STAND_CHANCE.get();

            if (grantStand && StandSpawnConfig.STAND_CHANCE.get() > 0) {
                giveAssignedStand(player);
            }

            PlayerDataHelper.setHasRolledStand(player, true);
        }
    }

    private void giveAssignedStand(PlayerEntity player) {
        IStandPower.getStandPowerOptional(player).ifPresent(standCap -> {
            if (!standCap.hasPower()) {
                if (!StandSpawnConfig.USE_STAND_ORDER.get()) {
                    giveRandomStand(player);
                    return;
                }

                List<? extends String> standAssignments = StandSpawnConfig.STAND_GIVING_ORDER.get();
                int joinOrder = PlayerDataHelper.getJoinOrder(player);

                if (joinOrder < standAssignments.size()) {
                    String standName = standAssignments.get(joinOrder);
                    Optional<StandType<?>> standOptional = getStandByName(standName);

                    if (standOptional.isPresent()) {
                        StandType<?> stand = standOptional.get();
                        if (standCap.givePower(stand)) {
                            player.sendMessage(
                                    new TranslationTextComponent("rotp_ss.stand_granted_pog"),
                                    player.getUUID()
                            );
                        }
                    }
                } else {
                    giveRandomStand(player);
                }
            }
        });
    }

    private Optional<StandType<?>> getStandByName(String standName) {
        return StandUtil.filterStands(stand ->
                stand.getRegistryName() != null &&
                        stand.getRegistryName().toString().equals(standName)
        ).findFirst();
    }

    private void giveRandomStand(PlayerEntity player) {
        IStandPower.getStandPowerOptional(player).ifPresent(standCap -> {
            if (!standCap.hasPower()) {
                StandType<?> stand = StandUtil.randomStand(player, player.getRandom());

                if (stand != null && standCap.givePower(stand)) {
                    player.sendMessage(
                            new TranslationTextComponent("rotp_ss.stand_granted_pog"),
                            player.getUUID()
                    );
                }
            }
        });
    }
}