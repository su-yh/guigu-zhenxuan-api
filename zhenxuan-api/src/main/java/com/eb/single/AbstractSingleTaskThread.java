package com.eb.single;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 单线程任务处理基类
 * 提供统一的线程生命周期管理、状态控制、唤醒/关闭机制
 * 子类只需实现业务相关的 work() 方法
 *
 * @author suyh
 * @since 2026-08-31
 */
@Slf4j
public abstract class AbstractSingleTaskThread extends Thread {

    protected final Object lock = new Object();
    protected final AtomicReference<TaskThreadStatusEnums> status = new AtomicReference<>(TaskThreadStatusEnums.NONE);

    // 空闲等待时间，默认1小时
    private final Duration idleWaitDuration;
    // 启动延迟时间，默认3秒
    private final Duration startupDelay;

    protected AbstractSingleTaskThread(String threadName) {
        this(threadName, Duration.ofHours(1), Duration.ofSeconds(3));
    }

    protected AbstractSingleTaskThread(String threadName, Duration idleWaitDuration, Duration startupDelay) {
        super(threadName);
        this.idleWaitDuration = idleWaitDuration;
        this.startupDelay = startupDelay;
    }

    @Override
    public final void run() {
        log.info("Thread [{}] will start in {} ms.", getName(), startupDelay.toMillis());
        delay(startupDelay);

        if (status.compareAndSet(TaskThreadStatusEnums.NONE, TaskThreadStatusEnums.RUNNING)) {
            log.info("Thread [{}] is running.", getName());
        }

        do {
            if (status.get() != TaskThreadStatusEnums.RUNNING) {
                break;
            }

            try {
                log.debug("Thread [{}] work begin", getName());
                work();
                log.debug("Thread [{}] work finished", getName());
            } catch (Exception e) {
                log.error("Thread [{}] exception.", getName(), e);
            }

            if (status.get() != TaskThreadStatusEnums.RUNNING) {
                break;
            }

            // 空闲等待
            synchronized (lock) {
                try {
                    lock.wait(idleWaitDuration.toMillis());
                } catch (InterruptedException e) {
                    log.warn("Thread [{}] work interrupted.", getName(), e);
                }
            }
        } while (status.get() == TaskThreadStatusEnums.RUNNING);

        status.set(TaskThreadStatusEnums.STOPPED);
        log.info("Thread [{}] is stopped.", getName());

        synchronized (status) {
            status.notifyAll();
        }
    }

    /**
     * 子类实现具体业务逻辑
     */
    protected abstract void work();

    /**
     * 唤醒线程立即执行
     */
    public void wakeup() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * 优雅关闭线程
     */
    public void shutdown() {
        status.set(TaskThreadStatusEnums.STOPPING);

        // 唤醒启动延迟中的线程
        synchronized (this) {
            this.notifyAll();
            try {
                this.wait(5);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }

        wakeup();

        boolean terminated = false;
        final int count = 3;
        for (int i = 0; i < count; i++) {
            synchronized (status) {
                try {
                    status.wait(1000);
                } catch (InterruptedException e) {
                    log.warn("Thread [{}] status wait interrupted", getName(), e);
                    Thread.currentThread().interrupt();
                }
            }

            if (status.get() == TaskThreadStatusEnums.STOPPED) {
                terminated = true;
                break;
            }
        }

        if (terminated) {
            log.info("Thread [{}] terminated successfully.", getName());
        } else {
            log.warn("Timeout occurred while terminating thread [{}].", getName());
        }
    }

    private void delay(Duration duration) {
        synchronized (this) {
            try {
                this.wait(duration.toMillis());
            } catch (InterruptedException e) {
                log.warn("Thread [{}] delay interrupted.", getName(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();

        return sw.toString();
    }

    public enum TaskThreadStatusEnums {
        NONE,
        RUNNING,
        STOPPING,
        STOPPED
    }
}

