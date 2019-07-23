package browser.json.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import browser.json.itf.Attribute;
import browser.json.itf.JavaClass;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BeanByCreateMain {
 	private final static String TEMPLATE_NAME = "template.ftl";
 	private final static String FILE_NAME = "User.java";
 	//private String projectSrc = "F:\\JAVAworkspace\\eclipse\\json2bean\\";
 	private static String projectSrc;
 	private String templateSrc = projectSrc + "bean_template";
 	private String propertiesSrc = projectSrc + "src\\bean.properties";
 	private String outputSrc = projectSrc + "src\\bean\\";
 	private Configuration cfg;
 	private Properties properties;
 	
 	static {
 		File file = new File("");
 		String url = null;
		try {
			url = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
 		url = url.replaceAll("\\\\","\\\\\\\\");
 		projectSrc = url + "\\\\";
 	}
 	
 	/*
 	 * 执行入口
 	 */
 	public static void main(String[] args) throws Exception {
 		BeanByCreateMain hfm = new BeanByCreateMain();
 		hfm.init();
 		hfm.process();
 	}
 	
 	/**
 	 * 初始化工作
 	 */
 	public void init() throws Exception {
 		
 		// 负责管理的实例创建+设置模板文件所在的目录
 		cfg = new Configuration();
 		// load资源的实例创建
 		properties = new Properties();
 		System.out.println(templateSrc);
 		cfg.setDirectoryForTemplateLoading(new File(templateSrc));
 		System.out.println(propertiesSrc);
 		properties.load(new FileInputStream(propertiesSrc));
 	}
 
 	
 	public void process() throws IOException, TemplateException {
 		
 		//1.load信息
 		JavaClass clazz = loadClass();
 		List<Attribute> attributes = loadAttr();
 		//2.向root中放入模版中所需信息
 		Map<String, Object> root = new HashMap<String, Object>();
 		root.put("clazz",clazz);
 		root.put("attributes",attributes);
 		//3.将模版进行指定文件的输出
 		write(root);
 		
 	}
 	
 	
 	/*
 	 * 加载类信息：包名，类名，import
 	 */
 	public JavaClass loadClass() {
 		String packagename = properties.getProperty("packagename");
 		String classname = properties.getProperty("classname");
 		String values = properties.getProperty("imports");
 		String[] imports = values.split(",");
 		JavaClass clazz = new JavaClass(classname, imports, packagename);
 		return clazz;
 	}
 	
 	/*
 	 * 加载属性：属性名，属性类型
 	 */
 	public List<Attribute> loadAttr() {
 		List<Attribute> attributes = new ArrayList<Attribute>();
 		String values = properties.getProperty("attribute");
 		String[] strs = values.split(",");
 		for (String s : strs) {
 			String[] sp = s.split(":");
 			attributes.add(new Attribute(sp[0],sp[1]));
 		}
 		return attributes;
 	}
 	/*
 	 * 将模版进行指定文件的输出
 	 */
 	public void write(Map<String, Object> root) throws IOException, TemplateException{
 		Template t = cfg.getTemplate(TEMPLATE_NAME);
 		OutputStream out = new FileOutputStream(new File(outputSrc + FILE_NAME));
 		t.process(root, new OutputStreamWriter(out));
 	}
 	
 }