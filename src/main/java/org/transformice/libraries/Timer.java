package org.transformice.libraries;

// Imports
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Timer {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private long delay;
    private final boolean enable;
    private ScheduledFuture<?> scheduledTask;

    /**
     * Creates a new timer.
     */
    public Timer() {
        this.delay = -1;
        this.enable = true;
    }

    /**
     * Creates a new timer by given time.
     * @param enable Enable the timer.
     * @param delay The timer delay.
     */
    public Timer(boolean enable, long delay) {
        this.enable = enable;
        this.delay = delay;
    }

    /**
     * Schedules a timer.
     * @param task Function to run after the delay.
     * @param timeUnit Unit.
     */
    public void schedule(Runnable task, TimeUnit timeUnit) {
        if(!this.enable) return;

        if (this.scheduledTask != null && !this.scheduledTask.isDone()) {
            this.scheduledTask.cancel(false);
        }
        this.scheduledTask = this.scheduler.schedule(task, this.delay, timeUnit);
    }

    /**
     * Schedules a timer.
     * @param task Function to run after the delay.
     * @param delay The delay.
     * @param timeUnit Unit.
     */
    public void schedule(Runnable task, long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.schedule(task, timeUnit);
    }

    /**
     * Schedules a timer everytime.
     * @param command The function to repeat.
     * @param initialDelay The beginning time.
     * @param delay The delay.
     * @param unit Unit.
     */
    public void scheduleAtFixedRate(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        this.scheduler.scheduleAtFixedRate(command, initialDelay, delay, unit);
    }

    /**
     * Cancels the current timer.
     */
    public void cancel() {
        if(!this.enable) return;

        if (this.scheduledTask != null) {
            this.scheduledTask.cancel(false);
        }
    }

    /**
     * Gets the remaining delay in milliseconds until the timer is over.
     * @return The remaining delay in milliseconds, or -1 if no task is scheduled.
     */
    public long getRemainingTime() {
        if (this.scheduledTask == null || this.scheduledTask.isDone()) {
            return -1;
        }
        return this.scheduledTask.getDelay(TimeUnit.MILLISECONDS);
    }
}