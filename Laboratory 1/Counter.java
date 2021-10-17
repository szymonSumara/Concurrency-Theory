class Counter {
    private int value;

    Counter(int value){
        this.value = value;
    }

    synchronized int decrement(){
        return --value;
    }

    synchronized int increment(){
        return ++value;
    }

    synchronized int getValue(){
        return value;
    }
}