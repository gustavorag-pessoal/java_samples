package servidorRestful;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class FileUploadApplication extends Application {

    public static void main(String[] args) throws Exception {
        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 8182);
        c.getDefaultHost().attach(new FileUploadApplication());

        c.start();
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/upload", FileUploadServerResource.class);
        return router;
    }

}
