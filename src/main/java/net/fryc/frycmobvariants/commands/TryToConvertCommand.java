package net.fryc.frycmobvariants.commands;


import com.mojang.brigadier.CommandDispatcher;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;

import java.util.Collection;
import java.util.Iterator;

public class TryToConvertCommand {


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands
                .literal("tryToConvert")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("targets", EntityArgument.entities()).executes((context) -> {
                    return execute(context.getSource(), EntityArgument.getEntities(context, "targets"));
                })));
    }

    private static int execute(CommandSourceStack source, Collection<? extends Entity> targets) {
        Iterator<? extends Entity> var2 = targets.iterator();
        int affectedTargets = 0;
        while(var2.hasNext()) {
            Entity entity = var2.next();
            if(entity instanceof CanConvert mob){
                mob.setCanConvertToTrue();
                affectedTargets++;
            }
        }

        if(affectedTargets > 0) {
            source.sendSystemMessage(Component.literal("Affected " + affectedTargets + " target" + (affectedTargets > 1 ? "s" : "")));
        }
        else {
            source.sendSystemMessage(Component.literal("Wrong target").withColor(TextColor.RED));
        }


        return targets.size();
    }

}
