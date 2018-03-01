package com.winway.android.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * 谷歌json工具类
 * @author zgq
 *
 */
public class GsonUtils {

	public static <T> T toList(InputStream is, Type type) {
		T list = null;
		Reader reader = new BufferedReader(new InputStreamReader(is));
		JsonReader jr = new JsonReader(reader);
		Gson gson = new Gson();
		list = gson.fromJson(jr, type);
		return list;
	}

	public static <T> T toList(String json, Type type) {
		T list = null;
		Gson gson = new Gson();
		list = gson.fromJson(json, type);
		return list;
	}
	
	/**
	 * 初始化gson设置
	 * @return
	 */
	public static Gson build() {
		return new GsonBuilder()
		.serializeNulls()// 允许序列化null成员
		.setDateFormat("yyyy-MM-dd HH:mm:ss")//设置日期格式
		.create();
	}
	
	 /** 
     * JsonWriter 
     * 把Json数据写入文件里面 
     * @param gson 
     * @param mapList 
     * @return 
     * @throws Exception 
     */  
	public static File writerJsonToFile(Gson gson, Object obj,Type type,String fileName)throws Exception {  
        File file = new File(fileName);
        OutputStream out = new FileOutputStream(file);  
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));//设计编码  
        gson.toJson(obj, type, writer);  
        writer.flush();  
        writer.close();  
        return file;  
    }  
    
    /** 
     * JsonReader 
     * 读取从本地文件的Json数据 
     * @param gson 
     * @param file 
     * @return 
     * @throws Exception 
     */  
    public static Object readJsonFromFile(Gson gson, File file,Type type)throws Exception {  
        InputStream input = new FileInputStream(file);  
        JsonReader reader = new JsonReader(new InputStreamReader(input));  
        Object result= gson.fromJson(reader,type);  
        reader.close();  
        return result;
    }  
    
    /**
	 * 读取从本地文件的Json数据 
	 * @param gson
	 * @param preader
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Object readJsonFromFile(Gson gson,Reader preader, Type type) throws Exception {
		JsonReader reader = new JsonReader(preader);
		Object object= gson.fromJson(reader, type);
		return object;
	}
	
	/**
	 * json字符串转对象
	 * @param gson
	 * @param jsonStr
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Object readJsonFromJsonStr(Gson gson,String jsonStr, Type type) throws Exception {
		Object object= gson.fromJson(jsonStr, type);
		return object;
	}
	

}
