package org.likabframework.rmi.invocation;

import java.lang.reflect.Method;

import org.likabframework.core.remote.Invocation;

/**
 * ClassName:RmiInvocation
 * @author sxp
 * @date:2017年5月7日 上午10:15:34
 */
public class RmiInvocation implements Invocation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7143414789062013629L;

	private Object stub;
	
	private Object[] args;
	
	private String methodName;

	private Class<?>[] parameterTypes;
	
	/**
	 * @param args
	 * @param methodName
	 * @param parameterTypes
	 */
	public RmiInvocation(Object[] args, String methodName,
			Class<?>[] parameterTypes) {
		super();
		this.args = args;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	public Object getStub() {
		return stub;
	}

	public void setStub(Object stub) {
		this.stub = stub;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	@Override
	public Object invoke(Object target) throws Exception {
		Method method = target.getClass().getMethod(methodName, parameterTypes);
		return method.invoke(target, args);
	}
}
