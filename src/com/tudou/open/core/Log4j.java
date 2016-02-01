package com.tudou.open.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Log4j implements Initable {

	private static final Log4j it = new Log4j();

	private Log4j() {
	}

	public static Log4j getIt() {
		return it;
	}

	public void init(Object obj) throws Exception {
		URL classpathRootUrl = getClass().getClassLoader().getResource("/");
		String filename = URLDecoder.decode(classpathRootUrl.getPath(), "utf8");
		File classpathRoot = new File(filename);
		File[] log4jPropFiles = classpathRoot.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.matches("tudou.*.log4j.properties");
			}
		});
		for (File f : log4jPropFiles) {
			// System.out.println("Load log4j conf file: " + f);
			initFile(f);
		}
	}

	/**
	 * 加载配置文件
	 * 
	 * @param file
	 *            配置文件的路径
	 * @throws IOException
	 */
	public void initFile(File file) throws IOException {
		// System.out.println("Load log4j properties: " + file);
		FileInputStream istream = null;
		try {
			Properties props = new Properties();
			// 加载配置文件
			istream = new FileInputStream(file);
			// 加载文件流，加载Log4j文件的配置文件信息
			props.load(istream);
			PropertyConfigurator.configure(props);
		} finally {
			try {
				istream.close();
			} catch (IOException e) {
			}
		}
	}

}
