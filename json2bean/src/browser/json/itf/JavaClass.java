 package browser.json.itf;
 /**
  * 封装类信息
  *
  */
 public class JavaClass {
 	private String packageName;
 	private String className;
 	private String[] imports;
 
 	public JavaClass() {
 	}
 
 	public JavaClass(String className, String[] imports,String packageName) {
 		super();
 		this.className = className;
 		this.imports = imports;
 		this.packageName = packageName;
 	}
 
 	public String getclassName() {
 		return className;
 	}
 
 	public void setclassName(String className) {
 		this.className = className;
 	}
 
 	public String[] getImports() {
 		return imports;
 	}
 
 	public void setImports(String[] imports) {
 		this.imports = imports;
 	}
 
 	public String getpackageName() {
 		return packageName;
 	}
 
 	public void setpackageName(String packageName) {
 		this.packageName = packageName;
 	}
 }