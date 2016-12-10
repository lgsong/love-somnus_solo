package com.somnus.solo.support.concurrent.adapter;

import com.somnus.solo.support.concurrent.task.Task;

/**
 * Runnable接口适配器
 */
public class RunnableAdapter extends AbstractAdapter implements Runnable {

	private Task task;
	
	public RunnableAdapter(Task task){
		super(task);
		this.task = task;
	}
	
	@Override
	public void run() {
		super.supportRequestIdentity();
		if(task != null){
			task.run();
		}
	}

}

