import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App {
    public static String message() {
        return "End-to-End DevOps Pipeline Successful!";
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", exchange -> {
            byte[] response = message().getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        server.start();
        System.out.println("Server running on port 8080");
    }
}