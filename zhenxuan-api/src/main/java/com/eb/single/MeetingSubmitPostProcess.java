package com.eb.single;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

/**
 * @author suyh
 * @since 2026-08-31
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MeetingSubmitPostProcess {
    private final MeetingTaskThread meetingTaskThread = new MeetingTaskThread();

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        meetingTaskThread.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void onShutdown() {
        meetingTaskThread.shutdown();
    }

    // 监听待办任务写进数据库的事件，然后唤醒工作线程
    // @EventListener(MeetingSubmitCompletedEvent.class)
    public void onMeetingSubmitCompleted() {
        wakeupAfterCommit();
    }

    private void wakeupAfterCommit() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            meetingTaskThread.wakeup();
            return;
        }

        // 事务控制，事务提交之后再唤醒工作线程
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        meetingTaskThread.wakeup();
                    }
                }
        );
    }

    /**
     * 单线程控制
     */
    private class MeetingTaskThread extends AbstractSingleTaskThread {

        public MeetingTaskThread() {
            super("MeetingTask");
        }

        @Override
        protected void work() {
            // 查询出待办任务列表对应的主键id
            List<String> taskIds = null; // meetingFinishedTaskService.noCompletedList();

            if (taskIds == null || taskIds.isEmpty()) {
                return;
            }

            for (String taskId : taskIds) {
                if (status.get() != TaskThreadStatusEnums.RUNNING) {
                    log.info("status is no RUNNING, current status: {}", status);
                    return;
                }

                processTask(taskId);
            }
        }

        /**
         * 处理单个任务（核心流程）
         */
        private void processTask(String taskId) {
            // ...
        }

    }

}
