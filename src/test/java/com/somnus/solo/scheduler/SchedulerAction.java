package com.somnus.solo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年11月3日 下午9:57:28 
 * @version V1.0 
 */
@Component
public class SchedulerAction {
	
	public SchedulerAction(){
		System.out.println("#####################");
	}
    
    private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TaskExecutor executor;
	
    /*@Scheduled(fixedDelay=1000) #延时多少毫秒，多少毫秒执行一次*/
    @Scheduled(cron="0 */1 * * * ?")
	public void execute(){
        executor.execute(new Runnable(){

            @Override
            public void run() {
                log.debug("[Thread " + Thread.currentThread().getId()+ " start]");
                System.out.println("每分钟执行一次");
                log.debug("[Thread " + Thread.currentThread().getId()+ " end]");  
            }
            
        });
	}
	
}
