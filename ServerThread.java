import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {
    String inLine = null;
    BufferedReader inputStream = null;
    PrintWriter outputStream = null;
    Socket socket = null;
    ServerDaemon daemon;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.daemon = new ServerDaemon();
    }

    public void run() {
        try{
            inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch(IOException e) {
            System.out.println("Error in server thread!");
        }

        try {
            inLine = inputStream.readLine();
            while(!inLine.equalsIgnoreCase("EXIT")) {
                String outLine = daemon.processLine(inLine);
                outputStream.println(outLine);
                outputStream.flush();
                System.out.println("Response sent: " + outLine);
                inLine = inputStream.readLine();
            }
        } catch(IOException e) {
            String threadName = this.getName();
            System.out.println("Error! Client " + threadName
                    + " terminated abruptly.");
        } catch(NullPointerException e) {
            String threadName = this.getName();
            System.out.println("Client " + threadName + " exited.");
        } finally {
            try {
                System.out.println("Connection closing...");
                if(inputStream != null) {
                    inputStream.close();
                    System.out.println("Socket input stream closed.");
                }

                if(outputStream != null) {
                    outputStream.close();
                    System.out.println("Socket output stream closed.");
                }
                if(socket != null) {
                    socket.close();
                    System.out.println("Socket closed.");
                }
            } catch(IOException ie) {
                ie.printStackTrace();
                System.out.println("Socket failed to close!");
            }
        }
    }
}
