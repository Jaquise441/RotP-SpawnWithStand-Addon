package com.hello_there.rotp_ss.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public class PlayerDataHelper {
    private static final String HAS_ROLLED_KEY = "rotp_ss_hasRolledStand";
    private static final String JOIN_ORDER_KEY = "rotp_ss_joinOrder";

    public static boolean hasRolledStand(PlayerEntity player) {
        CompoundNBT data = player.getPersistentData();
        return data.getBoolean(HAS_ROLLED_KEY);
    }

    public static void setHasRolledStand(PlayerEntity player, boolean hasRolled) {
        CompoundNBT data = player.getPersistentData();
        data.putBoolean(HAS_ROLLED_KEY, hasRolled);
        player.save(data);
    }

    public static int getJoinOrder(PlayerEntity player) {
        CompoundNBT data = player.getPersistentData();
        if (!data.contains(JOIN_ORDER_KEY)) {
            List<PlayerEntity> players = (List<PlayerEntity>) player.level.players();
            int joinOrder = players.indexOf(player);
            data.putInt(JOIN_ORDER_KEY, joinOrder);
            player.save(data);
        }
        return data.getInt(JOIN_ORDER_KEY);
    }
}