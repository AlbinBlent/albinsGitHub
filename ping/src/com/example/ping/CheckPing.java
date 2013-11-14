package com.example.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckPing {

	public long checkPing(String host, int timeout) {
		// TODO Auto-generated method stub

		InetAddress address = null;
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long pingTime = 0;
		try {

			final long startTime = System.currentTimeMillis();
			address.isReachable(timeout);
			final long endTime = System.currentTimeMillis();
			pingTime = endTime - startTime;

		} catch (IOException e) {
			System.out.println("is not reachable");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pingTime;

	}

}
