package com.lynn.demo;

import lombok.Data;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskDemo {


    @Data
    public static class Task extends RecursiveTask<Integer> {
        int max = 2;
        Integer start;
        Integer end;

        public Task(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end - start < max) {
                int total = 0;
                for (Integer i = start; i <= end; i++) {
                    total += i;
                }
                System.out.println(Thread.currentThread().getName() + "开始计算的部分：start = " + start + "; end = " + end + ";total = " + total);
                return total;
            }
            Task task1 = new Task(start, (start + end ) / 2);
            Task task2 = new Task( (start + end ) / 2 + 1, end);
            task1.fork();
            task2.fork();
            return task1.join() + task2.join();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task task = new Task(1, 100);
        ForkJoinPool pool = new ForkJoinPool(2);
        ForkJoinTask<Integer> future = pool.submit(task);
        System.out.println(future.get());
    }

}
