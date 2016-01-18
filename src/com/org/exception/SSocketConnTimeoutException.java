package com.org.exception;

import java.net.SocketTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSocketConnTimeoutException extends SocketTimeoutException {

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(SSocketConnTimeoutException.class);
	
	public SSocketConnTimeoutException(){
		super();
	}
	
	public SSocketConnTimeoutException(String ems){
		super(ems);
	}

	public String getMessage() {
		String errorMsg = super.getMessage();
		return "SSocketConnTimeoutException[连接超时异常 ]:" + errorMsg;
	}

	public void printStackTrace(){
		super.printStackTrace();
		log.error("SSocketConnTimeoutException[连接超时异常]");
	}

}
