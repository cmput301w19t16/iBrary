package ca.rededaniskal.EntityClasses;

public class Display_Thread {
    private Thread thread;
    private String username;

    public Display_Thread(Thread thread, String username) {
        this.thread = thread;
        this.username = username;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
