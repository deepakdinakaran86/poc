package com.pcs.device.gateway.G2021.writeback;

import com.hazelcast.util.Base64;

public class Test {

	public static void main(String[] args) {
	    System.out.println(Base64.encode("neo4jAdmin:neo4jAdmin".getBytes()));
    }

}
