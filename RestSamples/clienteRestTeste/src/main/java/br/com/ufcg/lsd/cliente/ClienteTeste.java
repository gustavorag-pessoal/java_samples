package br.com.ufcg.lsd.cliente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import modelo.task.rest.TaskRest;

public class ClienteTeste {

	static String baseUrl = "http://localhost:8787/servidor";
	static String executTaskUrl = "/resource";
	static String executTask_TaskUrl = "/runTask";
	
//	public static void main(String[] args) throws IOException {
//		System.out.println("Starting client");
//		//String url = "http://localhost:8080/JAXRS-FileUpload/rest/files/upload";
//		String url = baseUrl+executTaskUrl+executTask_TaskUrl;
//		doPostTask(url);
//
//	}
//	
	private static void doPostTask(String url){
		
		try {
			
			TaskRest taskRestA = new TaskRestImpl("001");
			TaskRest taskRestB = new TaskRestImpl("002");

			InputStream isA = serializarTask(taskRestA);

			ClientConfig clientConfig = new ClientConfig();
			clientConfig.connectorProvider(new GrizzlyConnectorProvider());
			System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
			
			Client client = ClientBuilder.newClient(clientConfig).register(MultiPartFeature.class);
//			WebTarget webTarget = client.target(url); /home/gustavorag/Dev/SaidaDados/ARQUIVO_Servidor 01_GERADO_ID_001.txt
			MultiPart multiPart = new MultiPart();
			multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

			FileDataBodyPart streamDataBodyPart = new FileDataBodyPart("file",new File("/home/gustavorag/Dev/SaidaDados/ARQUIVO_Servidor 01_GERADO_ID_001.txt"));
			multiPart.bodyPart(streamDataBodyPart);

			WebTarget webTarget = client.target(url);
			
			Response response = webTarget.request()
					.post(Entity.entity(multiPart, multiPart.getMediaType()));

			System.out.println(response.getStatus() + " "
					+ response.getStatusInfo() + " " + response);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static void doPost(String url) throws IOException{

		Client client = ClientBuilder.newClient();

		System.out.println("Invoking: "+url);
		
		WebTarget webTarget = client.target(url);
		//WebTarget webTargetWithQueryParam = webTarget.queryParam("task", isA);
		Builder invocationBuilder = webTarget.request();
		final InputStream responseStream = invocationBuilder.get(InputStream.class);
		OutputStream outpuStream = new FileOutputStream(new File("temp.txt"));

		
		int length;
        byte[] buffer = new byte[1024];
        while((length = responseStream.read(buffer)) != -1) {
        	outpuStream.write(buffer, 0, length);
        }
        outpuStream.flush();
        responseStream.close();
		
		System.out.println(new String(buffer, "UTF-8"));

    }
	
	private static InputStream serializarTask(TaskRest task) throws IOException{
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(task);
		InputStream is = new ByteArrayInputStream(os.toByteArray());

		os.flush();
		os.close();
		oos.flush();
		oos.close();
		
		return is;
		

	}


}
