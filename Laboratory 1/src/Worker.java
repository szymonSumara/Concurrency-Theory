class Worker extends Thread{

    private final int operationNumber;
    private final Counter counter;

    Worker(int operationNumber , Counter counter){
        this.operationNumber = operationNumber;
        this.counter = counter;
    }

    public void run(){

        for(int i=0;i<operationNumber;i++)
            counter.decrement();

        for(int i=0;i<operationNumber;i++)
            counter.increment();

    }
}