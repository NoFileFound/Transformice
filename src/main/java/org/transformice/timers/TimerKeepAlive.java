package org.transformice.timers;

// Imports
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class TimerKeepAlive {
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2, runnable -> {
        Thread thread = new Thread(runnable, "Transformice-KeepAlive");
        thread.setDaemon(true);
        return thread;
    });

    private final Runnable onTimeout;
    private final long timeout;
    private final TimeUnit unit;
    private ScheduledFuture<?> task;

    /**
     * Creates a keep-alive timer.
     * @param onTimeout The action to execute when the timer ends.
     * @param timeout The timeout duration.
     * @param unit The time unit of the timeout duration.
     */
    public TimerKeepAlive(Runnable onTimeout, long timeout, TimeUnit unit) {
        this.onTimeout = onTimeout;
        this.timeout = timeout;
        this.unit = unit;
    }

    /**
     * Resets the keep alive timer.
     */
    public synchronized void reset() {
        if (this.task != null) {
            this.task.cancel(false);
        }

        this.task = SCHEDULER.schedule(this.onTimeout, this.timeout, this.unit);
    }

    /**
     * Cancels the keep alive timer.
     */
    public synchronized void cancel() {
        if (this.task != null) {
            this.task.cancel(false);
            this.task = null;
        }
    }

    /**
     * Checks if the timer is running.
     * @return True if the timer is running or else False.
     */
    public synchronized boolean isRunning() {
        return this.task != null && !this.task.isDone() && !this.task.isCancelled();
    }
}