import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;

import com.sun.net.httpserver.*;

public class BasicHttpServerExample {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(BasicHttpServerExample::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {

        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();

        System.out.println(query);
        if (query != null) {
            String[] splitQuery = query.split("&");
            System.out.println(Arrays.toString(splitQuery));
            if (splitQuery.length == 2) {
                    int rows; 
                    int cols; 
                try {
                    rows = Integer.parseInt(splitQuery[0].split("=")[1]);
                    cols = Integer.parseInt(splitQuery[1].split("=")[1]);

                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "Please enter valid integers in the query string";
                    exchange.sendResponseHeaders(200, response.getBytes().length);// response code and length
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }

                int[][] maze = MazeGenerator.generate(rows, cols);
                StringBuffer buffer = new StringBuffer();
                buffer.append("{\n");
                for (int i = 0; i < maze.length - 1; i++) {
                    buffer.append("{" + maze[i][0]);
                    for (int j = 1; j < maze[i].length; j++) {
                        buffer.append("," + maze[i][j]);
                    }
                    buffer.append("},\n");
                }
                buffer.append("{" + maze[maze.length - 1][0]);
                for (int j = 1; j < maze[maze.length - 1].length; j++) {
                    buffer.append("," + maze[maze.length - 1][j]);
                }
                buffer.append("}\n");
                buffer.append("}\n");

                String response = buffer.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);// response code and length
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
        }

        String response = "Please supply valid rows and columns in form \"?rows=XXX&cols=XXX\"";
        exchange.sendResponseHeaders(200, response.getBytes().length);// response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}