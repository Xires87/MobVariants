package net.fryc.frycmobvariants.commands;



public class TryToConvertCommand {
    /* TODO komenda

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

     */
}
