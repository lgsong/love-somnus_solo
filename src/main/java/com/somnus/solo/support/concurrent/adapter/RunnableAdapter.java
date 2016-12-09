package com.somnus.solo.support.concurrent.adapter;

/**
 * Runnable接口适配器
 */
public class RunnableAdapter extends AbstractAdapter implements Runnable {

	private Runnable task;
	
	public RunnableAdapter(Runnable task){
		super();
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

