package zad1;

public class StringTask implements Runnable {
    private final String input;
    private final int multiplyCount;
    private volatile String result;
    private volatile TaskState state;
    private Thread taskThread;

    public StringTask(String input, int multiplyCount) {
        this.input = input;
        this.multiplyCount = multiplyCount;
        this.state = TaskState.CREATED;
        this.result = "";
    }

    @Override
    public void run() {
        this.state = TaskState.RUNNING;
        String currentString = "";

        try{
            for (int i = 0;i < multiplyCount;i++) {
                if(Thread.currentThread().isInterrupted()) {
                    state = TaskState.ABORTED;
                    return;
                }
                currentString = currentString + input;
            }
            this.result = currentString;
            this.state = TaskState.READY;
        } catch (Exception e){
            this.state = TaskState.ABORTED;
        }
    }
    public void start(){
        if(taskThread == null){
            taskThread = new Thread(this);
            taskThread.start();
        }
    }
    public void abort(){
        if( taskThread != null){
            taskThread.interrupt();
        }
    }
    public boolean isDone(){
        return state == TaskState.READY || state == TaskState.ABORTED;
    }

    public TaskState getState(){
        return state;
    }
    public String getResult(){
        return result;
    }
}
