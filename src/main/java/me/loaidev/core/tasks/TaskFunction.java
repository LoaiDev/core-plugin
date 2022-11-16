package me.loaidev.core.tasks;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TaskFunction<I, O> {

    protected @NotNull BiConsumer<I, CompletableFuture<O>> consumer;

    public TaskFunction(@NotNull Function<I, O> function) {
        this.consumer = (previous, future) -> future.complete(function.apply(previous));
    }

    public TaskFunction(@NotNull Consumer<I> consumer) {
        this.consumer = (i, future) -> {
            consumer.accept(i);
            future.complete(null);
        };
    }

    public TaskFunction(@NotNull Supplier<O> supplier) {
        this.consumer = (i, future) -> future.complete(supplier.get());
    }

    public TaskFunction(@NotNull Runnable runnable) {
        this.consumer = (i, future) -> {
            runnable.run();
            future.complete(null);
        };
    }

    public void execute(I previous, CompletableFuture<O> future) {
        this.consumer.accept(previous, future);
    }
}
