package servidorRestful;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.jar.JarInputStream;

import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import modelo.task.rest.ClassByte;
import modelo.task.rest.TaskRest;

public class FileUploadServerResource extends ServerResource {

	
	
	@Post
	public String receive(Representation file) throws Exception {

		File fichier = new File("/home/gustavorag/Dev/SaidaDados/Text.txt");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		file.write(os);
		String className = file.getDisposition().getFilename();

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

		//ClassByte classByte;

		ObjectInputStream ois = new ObjectInputStream(is);
		ClassByte classByte =  (ClassByte) ois.readObject();
		System.out.println(classByte.getName());
		//System.out.println(className);
		//ClassByte classByte = new ClassByte(className, os.toByteArray());
//		is.reset();
//		is.read(classByte.getBytes());
		
		ClassLoaderUtil clu = new ClassLoaderUtil();
		clu.loadClassByte(classByte);
		Class c = clu.loadClass(classByte);
		
		System.out.println("Classe carregada com sucesso: "+c.getName());
		
		TaskRest task = (TaskRest) c.newInstance();
		task.executeTask("SERVIDOR RECEBENDO TASK IMPLEMENTADA");
		
		//clu.loadClassByte(classByte);
		//Class c = clu.loadClass(classByte.getName());
		//task.executeTask("ServidorRest");
		return "ok";

	} 
	
	
//    @Post
//    public Representation upload(Representation entity) throws Exception {
//        Representation result = null;
//        if (entity != null
//                && MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),
//                        true)) {
//            // 1/ Create a factory for disk-based file items
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            factory.setSizeThreshold(1000240);
//
//            // 2/ Create a new file upload handler based on the Restlet
//            // FileUpload extension that will parse Restlet requests and
//            // generates FileItems.
//            RestletFileUpload upload = new RestletFileUpload(factory);
//
//            // 3/ Request is parsed by the handler which generates a
//            // list of FileItems
//            FileItemIterator fileIterator = upload.getItemIterator(entity);
//
//            // Process only the uploaded item called "fileToUpload"
//            // and return back
//            boolean found = false;
//            while (fileIterator.hasNext() && !found) {
//                FileItemStream fi = fileIterator.next();
//
//                if (fi.getFieldName().equals("fileToUpload")) {
//                    // For the matter of sample code, it filters the multo
//                    // part form according to the field name.
//                    found = true;
//                    // consume the stream immediately, otherwise the stream
//                    // will be closed.
//                    StringBuilder sb = new StringBuilder("media type: ");
//                    sb.append(fi.getContentType()).append("\n");
//                    sb.append("file name : ");
//                    sb.append(fi.getName()).append("\n");
//                    BufferedReader br = new BufferedReader(new InputStreamReader(fi.openStream()));
//                    
//                    String filePath = "/home/gustavorag/Dev/SaidaDados/Text.txt";
//                    
//                    saveFile(fi.openStream(), filePath);
//                    
//                    String line = null;
//                    while ((line = br.readLine()) != null) {
//                        sb.append(line);
//                    }
//                    sb.append("\n");
//                    result = new StringRepresentation(sb.toString(),
//                            MediaType.TEXT_PLAIN);
//                }
//            }
//        } else {
//            // POST request with no entity.
//            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
//        }
//
//        return result;
//    }
    
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

}
