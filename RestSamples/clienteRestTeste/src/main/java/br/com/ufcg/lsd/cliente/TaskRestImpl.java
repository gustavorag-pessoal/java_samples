package br.com.ufcg.lsd.cliente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import modelo.task.rest.TaskRest;

public class TaskRestImpl implements TaskRest, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7825673333034175806L;
	private String id;
	
	public TaskRestImpl(String id){
		this.id = id;
	}
	public TaskRestImpl(){
		this.id = "default";
	}
	public void executeTask(String arg0) {
		
		File file = new File("/home/gustavorag/Dev/SaidaDados/ARQUIVO_"+arg0+"_GERADO_ID_"+id+".txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			for(int count=1; count <=10; count ++){
				fw.write("["+arg0+"_"+id+"] Escreve linha "+count+"\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
