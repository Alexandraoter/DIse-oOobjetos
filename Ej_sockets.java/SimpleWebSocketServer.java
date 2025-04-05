import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

public class SimpleWebSocketServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor WebSocket iniciado en el puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket);

            new Thread(() -> {
                try {
                    Scanner in = new Scanner(clientSocket.getInputStream());
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // 1. Leer la solicitud HTTP del cliente
                    String request = "";
                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        request += line + "\n";
                        if (line.isEmpty()) {
                            break;
                        }
                    }

                    // 2. Analizar los encabezados de la solicitud
                    String secWebSocketKey = null;
                    Scanner requestScanner = new Scanner(request);
                    while (requestScanner.hasNextLine()) {
                        String line = requestScanner.nextLine();
                        if (line.startsWith("Sec-WebSocket-Key: ")) {
                            secWebSocketKey = line.substring("Sec-WebSocket-Key: ".length()).trim();
                            break;
                        }
                    }

                    // 3. Generar la respuesta de handshake
                    String secWebSocketAccept = generateSecWebSocketAccept(secWebSocketKey);
                    String response = "HTTP/1.1 101 Switching Protocols\r\n" +
                            "Upgrade: websocket\r\n" +
                            "Connection: Upgrade\r\n" +
                            "Sec-WebSocket-Accept: " + secWebSocketAccept + "\r\n\r\n";

                    // 4. Enviar la respuesta de handshake al cliente
                    out.print(response);
                    out.flush();

                    // 5. Enviar datos en frames WebSocket (ejemplo)
                    String message = "Hola desde Java WebSocket!";
                    byte[] frame = createWebSocketFrame(message.getBytes(StandardCharsets.UTF_8));
                    clientSocket.getOutputStream().write(frame);
                    clientSocket.getOutputStream().flush();

                    // 6. Mantener la conexión abierta y enviar mensajes cada segundo.
                    while (true) {
                        Thread.sleep(1000);
                        String timeMessage = "Hora: " + System.currentTimeMillis();
                        byte[] timeFrame = createWebSocketFrame(timeMessage.getBytes(StandardCharsets.UTF_8));
                        clientSocket.getOutputStream().write(timeFrame);
                        clientSocket.getOutputStream().flush();
                    }

                } catch (Exception e) {
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

    private static String generateSecWebSocketAccept(String secWebSocketKey) throws Exception {
        String data = secWebSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    private static byte[] createWebSocketFrame(byte[] payload) {
        int payloadLength = payload.length;
        int frameLength = 2 + payloadLength; // 2 bytes de encabezado

        byte[] frame = new byte[frameLength];
        frame[0] = (byte) 0x81; // Text frame, FIN = 1
        frame[1] = (byte) payloadLength;

        System.arraycopy(payload, 0, frame, 2, payloadLength);

        return frame;
    }
}

/*import java.io.IOException;
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
 */