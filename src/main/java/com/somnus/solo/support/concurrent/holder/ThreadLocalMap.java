package com.somnus.solo.support.concurrent.holder;

public class ThreadLocalMap extends InheritableThreadLocal<RequestInfo> {

	@Override
	protected RequestInfo childValue(RequestInfo parentValue) {
		return RequestIdentityHolder.join(parentValue);
	}

}