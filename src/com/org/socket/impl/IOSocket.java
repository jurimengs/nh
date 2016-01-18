package com.org.socket.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.exception.SSocketException;
import com.org.socket.ISocket;
/**
 * 系统阻塞的客户端 
 * 
 * @since 2011-09-06 13:43:43
 *
 */
public class IOSocket implements ISocket{

	private static Log log = LogFactory.getLog(IOSocket.class);

	protected Socket socket;
	
	protected InputStream ins = null;
	
	protected OutputStream out = null;
	
	private int bufferSize = 512;
	
	public IOSocket(){
	}
	
	public IOSocket(Socket s){
		this.socket = s;
	}
	

	public void close() {
		try {
			if (socket != null && !isClose()) {
				
				if(out != null){
					try {
							out.close();
						} catch (IOException ie) {
							ie.printStackTrace();
						}
			    }
				
				if(ins != null){
					try {
							ins.close();
						} catch (IOException ie) {
							ie.printStackTrace();
						}
				}
				socket.close();
				socket = null;
			}
		} catch (IOException e) {
			e = new SSocketException(e.getMessage());
			e.printStackTrace();
			log.info("Socket中关闭通讯通道失败");
		}
	}

	public void connect(String hostName,int port,int timeoutSeconds) {
		try{
			if(socket == null)
				socket = new Socket();
			socket.connect(new InetSocketAddress(hostName, port));
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(timeoutSeconds*1000);
			log.info("连接["+hostName+":"+port+"]成功");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean isClose() {
		return socket.isClosed();
	}

	public byte[] read() {
		if(socket != null && !isClose()){			
			try {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ins = socket.getInputStream();
				byte[] b = new byte[bufferSize];
				int fb = ins.read();
				bout.write(fb);		
				while((ins.available()) > 0 ){
					int n = ins.read(b);
					bout.write(b, 0, n);
				}
				bout.flush();
				return bout.toByteArray();
			} catch (IOException e) {

				log.info("socket 读取数据出现异常");
				if(ins != null){
					try {
							ins.close();
						} catch (IOException ie) {
							ie.printStackTrace();
						}
				}
				e.printStackTrace();
			}
		}
		return null;
	}

	public void write(byte[] data, boolean flush) {
		if(socket != null && !socket.isClosed()){
			try {
				out = socket.getOutputStream();
				out.write(data, 0, data.length);
				if(flush)
					out.flush();
			} catch (IOException e) {
				log.info("socket 读取数据出现异常");
				if(out != null){
					try {
							out.close();
						} catch (IOException ie) {
							ie.printStackTrace();
						}
			    }
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String []args){

		log.info("Socket中关闭通讯通道失败");
		   
		ISocket so = null;
		try{
			so = new IOLenthSocket();
			
			so.connect("172.28.250.111", 3737, 20);
			
			byte[] data = "{\"version\":\"ver1.0.0\",\"charset\":\"UTF-8\",\"accessChannelNo\":\"00000003\",\"accessType\":\"0001\",\"commType\":\"0001\",\"deviceType\":\"\",\"smpType\":\"M0025\",\"dType\":\"01\",\"deviceInfo\":{},\"tranAttribute\":\"\",\"monitorFiled\":{},\"routeFiled\":\"\",\"traceNo\":\"\",\"securityInfo\":{},\"clientInfo\":{},\"memId\":\"2396\"}".getBytes("utf-8");
			so.write(data, true);
			System.out.println("data send ok");
			byte[] bb = so.read();
			System.out.println(new String(bb,"utf-8"));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(so != null)
			 try{
					so.close();
			 } catch (SSocketException e) {
					e.printStackTrace();
			}
	    }
	}
	
}
