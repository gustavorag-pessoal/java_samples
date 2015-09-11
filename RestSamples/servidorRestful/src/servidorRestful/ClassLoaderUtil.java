package servidorRestful;

import java.util.HashMap;
import java.util.Map;

import modelo.task.rest.ClassByte;

public class ClassLoaderUtil extends ClassLoader {
	
	private Map<String, Class> classMap;
	
	public ClassLoaderUtil(){
		this.classMap = new HashMap<String, Class>();
	}

	public void loadClassByte(ClassByte classByte){
		Class c = defineClass(classByte.getName(),classByte.getBytes(),0,classByte.getBytes().length);
		classMap.put(classByte.getName(), c);
	}
	
	public Class loadClass(ClassByte classByte){
		return classMap.get(classByte.getName());
	}

}
