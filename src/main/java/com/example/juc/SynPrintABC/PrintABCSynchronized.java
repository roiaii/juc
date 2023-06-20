package com.example.juc.SynPrintABC;

public class PrintABCSynchronized {
    public void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for(int i=0; i<2; i++){
                print.A();
            }
        }).start();
        new Thread(() -> {
            for(int i=0; i<2; i++){
                print.B();
            }
        }).start();
        new Thread(() -> {
            for(int i=0; i<2; i++){
                print.C();
            }
        }).start();
    }



    static class Print{
        int count = 1;

        void A(){
            synchronized (this){
                while(count != 1){
                    try{
                        this.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                count = 2;
                System.out.println(Thread.currentThread().getName() + "A");
                this.notifyAll();
            }
        }

        void B(){
            synchronized (this){
                while(count != 2){
                    try{
                        this.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                count = 3;
                System.out.println(Thread.currentThread().getName() + "B");
                this.notifyAll();
            }
        }

        void C(){
            synchronized (this){
                while(count != 3){
                    try{
                        this.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                count = 1;
                System.out.println(Thread.currentThread().getName() + "C");
                this.notifyAll();
            }
        }
    }

}
