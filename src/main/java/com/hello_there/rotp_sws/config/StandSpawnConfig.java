package com.hello_there.rotp_sws.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class StandSpawnConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.DoubleValue STAND_CHANCE;
    public static final ForgeConfigSpec.BooleanValue USE_STAND_ORDER;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> STAND_GIVING_ORDER;

    static {
        BUILDER.push("Stand Spawn Settings");

        STAND_CHANCE = BUILDER
                .comment("Chance (0-100%) to get a stand upon first joining a world (Default: 13.0)")
                .defineInRange("standChance", 13.0D, 0.0D, 100.0D);

        USE_STAND_ORDER = BUILDER
                .comment("If true, stands are given in the order specified in standOrder list. If false, stands are randomly given (if you get the chances).")
                .define("useStandOrder", false);

        STAND_GIVING_ORDER = BUILDER
                .comment("List of the order in which players (if they get the chances) are given stands (CORRECT WAY OF CONFIG: [\"jojo:the_world\", \"rotp_kingcrimson:kingcrimson\"]. This way the first person (who gets the chances) will spawn with The World, and the second one with King Crimson. This is expandable, provided you write the correct ID for the stand. After the list is finished, stands are given randomly.")
                .defineList("standOrder", new ArrayList<>(), entry -> entry instanceof String);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}