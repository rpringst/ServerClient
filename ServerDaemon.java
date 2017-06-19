public class ServerDaemon {
    private static final int WAIT = 0;
    private static final int SENTDATA = 1;
    private static final int EXIT = 2;

    private int state;

    public ServerDaemon() {
        this.state = WAIT;
    }

    public String processLine(String line) {
        return line;
    }
}
