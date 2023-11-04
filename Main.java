
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final int PORT = 8001;
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(50);

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Listening on port " + PORT + "...");

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    logger.info("Connected: " + socket.getRemoteSocketAddress());
                    ServerThread serverThread = new ServerThread(socket);
                    threadPool.execute(serverThread);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Connection Error", e);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server Error", e);
        } finally {
            threadPool.shutdown();
        }
    }
}
