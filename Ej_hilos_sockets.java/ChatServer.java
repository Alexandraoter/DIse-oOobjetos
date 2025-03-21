import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static final int PORT = 12345;
    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("El servidor de chat está en ejecución...");
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(listener.accept(), writers).start();
            }
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private Set<PrintWriter> writers;

    public ClientHandler(Socket socket, Set<PrintWriter> writers) {
        this.socket = socket;
        this.writers = writers;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            writers.add(out);

            String input;
            while ((input = in.readLine()) != null) {
                for (PrintWriter writer : writers) {
                    writer.println(input);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar el cliente: " + e);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}