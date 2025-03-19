package com.hello_there.rotp_sws;


import com.hello_there.rotp_sws.config.StandSpawnConfig;
import com.hello_there.rotp_sws.init.ModEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

@Mod(com.hello_there.rotp_sws.RotpSpawnWithStandAddon.MOD_ID)
public class RotpSpawnWithStandAddon {
    public static final String MOD_ID = "rotp_sws";
    public static final Logger LOGGER = LogManager.getLogger();

    public RotpSpawnWithStandAddon() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StandSpawnConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }
}
