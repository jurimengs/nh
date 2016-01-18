package com.org.exception;

import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSocketException extends SocketException {

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(SSocketException.class);
	
	public SSocketException(){
		super();
	}
	
	public SSocketException(String ems){
		super(ems);
	}
	
	
	public String getMessage() {
		String errorMsg = super.getMessage();
		return "SSocketException socket[通讯异常]: " + errorMsg;
	}

	public void printStackTrace(){
		log.error("SSocketException[通讯异常]");
		super.printStackTrace();
	}
	

}
