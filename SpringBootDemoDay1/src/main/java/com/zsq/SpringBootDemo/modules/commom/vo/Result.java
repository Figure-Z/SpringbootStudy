package com.zsq.SpringBootDemo.modules.commom.vo;

public class Result<T> {

	
	//用于判断是否成功的判断吗
	private int status;
	private String message;
	private T object;
	
	
	public Result() {
		super();
	}
	
	public Result(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Result(int status, String message, T object) {
		super();
		this.status = status;
		this.message = message;
		this.object = object;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	
	//常见内部枚举类
	public enum ResultStatus{
		SUCCESS(200),FAILD(500);
		
		public int status;

		private ResultStatus(int status) {
			this.status = status;
		}
	}
	
	
}
