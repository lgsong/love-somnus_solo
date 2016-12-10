package com.somnus.solo.support.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.somnus.solo.support.concurrent.adapter.RunnableAdapter;
import com.somnus.solo.support.concurrent.task.Task;

public class TaskExecutor {

	private final static Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);
	
	private static final ScheduledExecutorService scheduledExcutor = Executors.newScheduledThreadPool(20); // TODO 算法升级:从配置文件中读取详细配置生成线程池

	public static void run(Task task) {
		if (task != null) {
			LOGGER.info("准备执行任务:[{}]", task.getName());
			try {
				if(task.getDelay() > 0){
					LOGGER.info("任务[{}]将在延迟[{}]毫秒后被执行", task.getName(), task.getDelay());
					scheduledExcutor.schedule(new RunnableAdapter(task), task.getDelay(), TimeUnit.MILLISECONDS);
				}else {
					scheduledExcutor.execute(new RunnableAdapter(task));
				}
			} catch (Exception e) {
				LOGGER.warn("任务:[" + task.getName() + "]执行失败", e);
			}
		}
	}
	
	public static void runFixed(Task task, long  period) {
		if (task != null) {
			LOGGER.info("准备执行任务:[{}]", task.getName());
			try {
				LOGGER.info("任务[{}]将在延迟[{}]毫秒后被执行", task.getName(), task.getDelay());
				scheduledExcutor.scheduleAtFixedRate(new RunnableAdapter(task), task.getDelay(),  period, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				LOGGER.warn("任务:[" + task.getName() + "]执行失败", e);
			}
		}
	}

}
