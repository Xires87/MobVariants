package net.fryc.frycmobvariants.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fryc.frycmobvariants.util.CanConvert;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.Iterator;

public class TryToConvertCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("tryToConvert").requires((source) -> {
            return source.hasPermissionLevel(2);
        })).executes((context) -> {
            return execute((ServerCommandSource)context.getSource(), ImmutableList.of(((ServerCommandSource)context.getSource()).getEntityOrThrow()));
        })).then(CommandManager.argument("targets", EntityArgumentType.entities()).executes((context) -> {
            return execute((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"));
        })));
    }

    private static int execute(ServerCommandSource source, Collection<? extends Entity> targets) {
        Iterator var2 = targets.iterator();
        boolean success = false;
        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if(entity instanceof CanConvert mob){
                mob.setCanConvertToTrue();
                success = true;
            }
        }

        boolean s = success;
        source.sendFeedback(() -> {
            if(s){
                return Text.literal("Command executed successfully");
            }
            return Text.literal("Wrong target").formatted(Formatting.RED);
        }, false);

        return targets.size();
    }
}
