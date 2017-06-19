import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the main implementation of the server for GameAide, which the
 * Dungeon Master or Game Leader will take command of.
 *
 * @author Robert Ringstad
 */
public class ThreadedServer {
    private static final int PORT = 8888;

    public static void main(String args[]) throws IOException {
        Socket socket = null;
        ServerSocket serverSocket = null;

        System.out.println("Server listening on port: " + PORT + ".");

        try {
            // Attempt to create socket for server
            serverSocket = new ServerSocket(PORT);
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Server error!");
        }

        while(true) {
            try {
                // Attempt to connect with client
                socket = serverSocket.accept();
                System.out.println("Connection established.");
                // Create new thread for this client
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Connection error!");
            }
        }
    }
}

