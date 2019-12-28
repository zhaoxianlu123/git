package com.example.demo1.controller;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PoolExecutorTest {
    public static void mains (String [] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i =0;i<7;i++){
            final int index=i;
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第"+index+"个线程"+Thread.currentThread().getName());
                }
            });
        }
    }
  public static void main (String [] args){
      ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
      scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
          @Override
          public void run() {
            System.out.println("");
          }
      },1,3, TimeUnit.SECONDS);

  }

}
