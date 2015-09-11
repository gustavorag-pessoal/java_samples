package br.com.ufcg.lsd.cliente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.engine.io.IoUtils;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.resource.ClientResource;

import modelo.task.rest.ClassByte;

public class ClienteRestlet {

	public static void main(String[] args) throws IOException {
		
		
		ClientResource cr = new ClientResource("http://localhost:8182/upload");
		File file  = new File("/home/gustavorag/Dev/SaidaDados/ARQUIVO_Servidor 01_GERADO_ID_001.txt");

		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		///ByteArrayOutputStream baOs = serializarTask(TaskRestImpl.class);
		InputStream is = cl.getResourceAsStream(TaskRestImpl.class.getName().replace('.', '/') + ".class");
		ByteArrayOutputStream baOs = new ByteArrayOutputStream();
		IoUtils.copy(is, baOs);
		
		ClassByte cb = new ClassByte(TaskRestImpl.class.getName(),  baOs.toByteArray());
		
		ByteArrayOutputStream baOsCb = serializarTask(cb);
		
		FileRepresentation fileEntity = new FileRepresentation(file, MediaType.TEXT_PLAIN);
		ByteArrayRepresentation baEntity = new ByteArrayRepresentation(baOsCb.toByteArray());
		
		Form fileForm = new Form();
		fileForm.add("CLASS_NAME", TaskRestImpl.class.getName());
		fileForm.add(Disposition.NAME_FILENAME,TaskRestImpl.class.getName());
		Disposition disposition = new Disposition(Disposition.TYPE_INLINE, fileForm);
		baEntity.setDisposition(disposition);
		try{
			cr.post(baEntity);
			// resource.receive(fileEntity);

		}
		catch(Exception e){
			System.out.print("dd");
			e.printStackTrace();
		} 
		

//		FileInputStream fileStream = new FileInputStream(new File(
//                "F:/workspacesEclipse/easystorage/EssaiUpload/essai.tar"));
//        InputStreamBody streamBody = new InputStreamBody(fileStream,
//                "application/octet-stream", "file.tar");
//
//        StringBody comment = new StringBody("A binary file of some kind",
//                Charset.forName("UTF-8"));
//        MultipartEntity reqEntity = new MultipartEntity();
//        reqEntity.addPart("comment", comment);
//        reqEntity.addPart("bin", streamBody);
//
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost("http://localhost:8182/testFileUpload");
//        httppost.setEntity(reqEntity);
//
//        HttpResponse response = httpclient.execute(httppost);
//        HttpEntity resEntity = response.getEntity();
//        if (resEntity != null) {
//            resEntity.consumeContent();
//        }
//        httpclient.getConnectionManager().shutdown();
	
//		InputStream is= new FileInputStream(new File("/home/gustavorag/Dev/SaidaDados/ARQUIVO_Servidor 01_GERADO_ID_001.txt"));
//		  InputStreamBody fileContent=new InputStreamBody(is,"mp-test.zip");
//		  HttpPost postStart=new HttpPost(BASE_URL + "/ingest/addZippedMediaPackage");
//		  MultipartEntity mpEntity=new MultipartEntity();
//		  mpEntity.addPart("workflowDefinitionId",new StringBody("full"));
//		  mpEntity.addPart("userfile",fileContent);
//		  postStart.setEntity(mpEntity);
//		  HttpResponse response=client.execute(postStart);


		  

		//		FileRepresentation rep = new FileRepresentation("/home/gustavorag/Dev/SaidaDados/ARQUIVO_Servidor 01_GERADO_ID_001.txt", MediaType.MULTIPART_FORM_DATA, 0);
		//		//Representation encodedRep = new EncodeRepresentation(Encoding.GZIP,rep);
		//		Client client = new Client(Protocol.HTTP);
		//		ClientResource cr = new ClientResource("http://localhost:8182/upload");
		//		cr.setNext(client);
		//		try{
		//			Representation response = cr.post(rep);
		//			System.out.println("******" + response.getText());
		//		}catch(Exception e){
		//			e.printStackTrace();
		//		}


	}
	
	private static ByteArrayOutputStream serializarTask(Object o) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(o);

		os.flush();
		os.close();
		oos.flush();
		oos.close();
		
		return os;
		

	}
	
}
