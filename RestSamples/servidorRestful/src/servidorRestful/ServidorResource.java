package servidorRestful;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;


@Path("/resource")
public class ServidorResource {

	@POST
	@Path("runTask")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream) {

		String filePath = "/home/gustavorag/Dev/SaidaDados/Text.txt";

		// save the file to the server
		saveFile(fileInputStream, filePath);

		String output = "File saved to server location : " + filePath;

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(
					serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
//	@POST
//	@Path("runTask")
//	public Response uploadFile(@FormDataParam("file") InputStream inputStream) {
//
//		OutputStream out;
//		try {
//			out = new FileOutputStream(new File("/home/gustavorag/Dev/SaidaDados/Text.txt"));
//			int read = 0;
//			byte[] bytes = new byte[1024];
//
//			while ((read = inputStream.read(bytes)) != -1) {
//				out.write(bytes, 0, read);
//			}
//			out.flush();
//			out.close();
//			
//			return Response.status(200).build();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return Response.status(500).build();
//
//		//		FormDataBodyPart filePart = form.getField("task");
//		//		ContentDisposition headerOfFilePart =  filePart.getContentDisposition();
//		//		InputStream inputStream = filePart.getValueAs(InputStream.class);
////		ObjectInputStream objLeitura;
////		TaskRest taskToRun;
////		
////		try {
////			
////			objLeitura = new ObjectInputStream(inputStream);
////			taskToRun = (TaskRest) objLeitura.readObject();
////			
////			taskToRun.executeTask("Servidor 01");
////			return Response.status(200).build();
////			
////		} catch (IOException e) {
////			System.out.println(e.getMessage());
////			e.printStackTrace();
////		} catch (ClassNotFoundException e) {
////			System.out.println(e.getMessage());
////			e.printStackTrace();
////		}
////		return Response.status(500).build();
//
//	}

}
