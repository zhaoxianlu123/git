package com.example.demo1.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import jdk.internal.dynalink.beans.StaticClass;
import org.bouncycastle.util.test.Test;
import org.bouncycastle.util.test.TestResult;

public class test {
    public static int staticVar = 0;

    public static  void main (String args[]){
//        String booleans="true";
//        boolean b = Boolean.valueOf(booleans).booleanValue();
//        boolean aBoolean = Boolean.getBoolean(booleans);
//        Boolean s=true;
//        String s1 = String.valueOf(s);
//        System.out.println(s1);
        System.out.println(new Test() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public TestResult perform() {
                return null;
            }
        });
    }
        static int test()

        {

            int x = 1;

            try

            {

                return x;

            }

            finally

            {

                ++x;

            }

        }



}
