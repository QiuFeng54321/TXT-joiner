package com.qiufeng.txtjoiner;
import java.util.Comparator;
import java.io.File;
import java.util.Arrays;

public class DefaultCompare implements Comparator<File>
{
	StringBuilder sb;
	public DefaultCompare(){}
	@Override
	public int compare(File p1, File p2)
	{
		// TODO: Implement this method
		return p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase());  
	}
	public File[] sortFiles(File file){
		sb=new StringBuilder();
		File[] paths=file.listFiles();
		if(paths==null)paths=new File[]{};
		Arrays.sort(paths,this);  
		//ui.alert(file.getPath(),sb.toString(),true);
		return paths;
	}
	
}
