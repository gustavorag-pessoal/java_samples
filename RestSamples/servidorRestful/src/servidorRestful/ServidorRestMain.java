package servidorRestful;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class ServidorRestMain {

	private static final URI BASE_URI = URI.create("http://localhost:8787/servidor/");

//    public static void main(String[] args) {
//        try {
//            System.out.println("\"Hello World\" Jersey Example App");
//
//            final ResourceConfig resourceConfig = new ResourceConfig(ServidorResource.class);
//            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
//            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    server.shutdownNow();
//                }
//            }));
//            server.start();
//
//            Thread.currentThread().join();
//        } catch (IOException | InterruptedException ex) {
//            Logger.getLogger(ServidorRestMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}
