import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;

/**
 * This is the main implementation of the client for GameAide, which
 * participants in the campaign will take command of.
 *
 * @author Robert Ringstad
 */
public class Client {
    private static final int PORT = 8888;

    public static void main(String args[]) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        Socket socket = null;
        String line;
        String response;
        BufferedReader bufferedReader = null;
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            socket = new Socket(address, PORT);
            bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in));
            inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception!");
        }

        System.out.println("Client address: " + address);
        System.out.println("Please input a string (EXIT to end): ");

        try {
            line = bufferedReader.readLine();
            while (!line.equalsIgnoreCase("EXIT")) {
                outputStream.println(line);
                outputStream.flush();
                response = inputStream.readLine();
                System.out.println("Server Response:");
                System.out.println(response);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket error!");
        } finally {
            inputStream.close();
            outputStream.close();
            bufferedReader.close();
            socket.close();
            System.out.println("Connection closed.");
        }
    }
}