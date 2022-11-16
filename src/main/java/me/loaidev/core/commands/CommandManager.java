package me.loaidev.core.commands;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import me.loaidev.core.CorePlugin;
import me.loaidev.core.User;
import me.loaidev.core.UserProvider;
import me.loaidev.core.commands.dispatchers.CommandDispatcher;
import me.loaidev.core.commands.dispatchers.ConsoleCommandDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CommandManager<U extends User> {

    protected final @NotNull CorePlugin plugin;

    protected final @NotNull UserProvider<U> userProvider;

    protected final @MonotonicNonNull PaperCommandManager<CommandDispatcher> commandManager;

    protected final @MonotonicNonNull AnnotationParser<CommandDispatcher> annotationParser;

    public CommandManager(@NotNull CorePlugin plugin, @NotNull UserProvider<U> userProvider, @NotNull Function<@NotNull CommandTree<CommandDispatcher>, @NotNull CommandExecutionCoordinator<CommandDispatcher>> coordinator) {
        this.plugin = plugin;
        this.userProvider = userProvider;
        this.commandManager = this.createPaperCommandManager(coordinator);
        this.annotationParser = this.createAnnotationParser();
    }

    public static <U extends User> CommandManager<U> syncCommandManager(@NotNull CorePlugin plugin, @NotNull UserProvider<U> userProvider) {
        return new CommandManager<>(plugin, userProvider, CommandExecutionCoordinator.simpleCoordinator());
    }

    public static <U extends User> CommandManager<U> asyncCommandManager(@NotNull CorePlugin plugin, @NotNull UserProvider<U> userProvider) {
        return new CommandManager<>(plugin, userProvider, AsynchronousCommandExecutionCoordinator.<CommandDispatcher>newBuilder().build());
    }

    public void parseCommands(@NotNull Object object) {
        this.annotationParser.parse(object);
    }
    public PaperCommandManager<CommandDispatcher> createPaperCommandManager(@NotNull Function<@NotNull CommandTree<CommandDispatcher>, @NotNull CommandExecutionCoordinator<CommandDispatcher>> coordinator) {
        try {
            return new PaperCommandManager<>(
                    plugin,
                    coordinator,
                    this::getCommandDispatcher,
                    CommandDispatcher::getBukkitSender);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AnnotationParser<CommandDispatcher> createAnnotationParser() {
        return new AnnotationParser<>(this.commandManager, CommandDispatcher.class, parameters -> SimpleCommandMeta.empty());
    }

    protected @NotNull CommandDispatcher getCommandDispatcher(@NotNull CommandSender sender) {
        if (sender instanceof Player player) {
            return this.userProvider.getUser(player);
        } else {
            return new ConsoleCommandDispatcher((ConsoleCommandSender) sender);
        }
    }
}
