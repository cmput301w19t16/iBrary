package ca.rededaniskal.EntityClasses;

import java.util.Objects;

public class Display_Thread {
    private Thread thread;
    private String username;

    public Display_Thread(Thread thread, String username) {
        this.thread = thread;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Display_Thread that = (Display_Thread) o;
        return Objects.equals(thread, that.thread) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thread);
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
