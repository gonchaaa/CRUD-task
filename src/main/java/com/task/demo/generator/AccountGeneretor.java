package com.task.demo.generator;

import java.util.Random;

public class AccountGeneretor {
    public static String generatorAccountNumber(){
        String prefix="AZE";
        StringBuilder sb=new StringBuilder();
        Random random=new Random();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return prefix + sb;
    }
}
