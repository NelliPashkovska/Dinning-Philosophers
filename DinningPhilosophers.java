class Semaphore {
    private int value;
    
    public Semaphore() {
        value = 0;
    }

    public Semaphore(int v) {
        value = v;
    }

public synchronized void P() {
    while(value <= 0) {
        try {
            wait();
        }
            catch (InterruptedException e) { }
    }
    value --;
}

public synchronized void V() {
    ++value;
    notify();
}

}

public class DinningPhilosophers {
    Semaphore[] forks = new Semaphore[5];

    public void wykonaj() {
        int i;
        for (i=0; i<5; i++) {
            forks[i] = new Semaphore(1);
        }
        for (i=0; i<5; i++) {
            Thread philosopher = new Philosopher(i, forks);
            philosopher.start();
        }
    }

    public static void main(String[] args) {
        System.out.println("Dinning philosophers problem ");
        DinningPhilosophers table = new DinningPhilosophers();
        table.wykonaj();
    }
}

class Philosopher extends Thread {
    int pn;
    Semaphore [] forks;
    public Philosopher (int n, Semaphore [] forks) {
        pn = n;
        this.forks = forks;
    }

    public void run(){
        int pnl = (pn+1) % 5;
        while(true) {
            System.out.println("Philosopher " + pn + " is thinking...");
            try {
                Thread.sleep((int) (Math.random()*300));
            } catch(InterruptedException e){ }

            forks[pn].P();
            System.out.println("Philosopher " + pn + " is picking a fork " + pn + " up");
            try {
                Thread.sleep((int) (Math.random()*100));
            } catch(InterruptedException e){ }
            forks[pnl].P();
            System.out.println("Philosopher " + pn + " is picking up a fork " + pnl + " up");

            System.out.println("Philosopher " + pn + " is eating...");

            try {
                Thread.sleep((int) (Math.random()*100));
            } catch(InterruptedException e){ }

            System.out.println("Philosopher " + pn + " is putting a fork " + pn+1 + " down");
            forks[pnl].V();
            System.out.println("Philosopher " + pn + " is putting a fork " + pn + " down");
            forks[pn].V();
        }
    }
}