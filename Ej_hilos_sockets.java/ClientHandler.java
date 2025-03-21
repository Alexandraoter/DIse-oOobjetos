import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler extends Thread {

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
    

