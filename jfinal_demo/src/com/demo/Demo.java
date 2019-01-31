package com.demo;

import java.util.HashSet;
import java.util.Random;

public class Demo {

	public static void main(String[] args) {
//		System.out.println(System.currentTimeMillis()/1000L);
		HashSet<Integer> set = new HashSet<Integer>();
		Random ran = new Random();
		while(true){
			if (set.add(ran.nextInt(10)+1)) {
				System.out.println(set);
				if(set.size()==11){
					System.out.println(set);
					break;
				}
			}
		}
	}
	
}
