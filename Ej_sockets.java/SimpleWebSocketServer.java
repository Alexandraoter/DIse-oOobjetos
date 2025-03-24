import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;

public class SimpleWebSocketServer {

    public static void main(String[] args) throws IOException {
        int port = 8080; // El puerto en el que escuchará el servidor
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor WebSocket iniciado en el puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket);

            new Thread(() -> {
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    while (true) {
                        out.println("Hola desde Java: " + System.currentTimeMillis());
                        Thread.sleep(1000); // Envía un mensaje cada segundo
                    }
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error en la conexión con el cliente: " + e);
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                    }
                }
            }).start();
        }
    }
}

/*Servidor Java:
Crea un ServerSocket que escucha en el puerto 8080.
Cuando un cliente se conecta, crea un nuevo hilo para manejar la comunicación.
En el hilo, envía un mensaje al cliente cada segundo. */