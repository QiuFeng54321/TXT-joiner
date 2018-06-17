package com.qiufeng.txtjoiner;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NovInCompare extends NovelCompare
{

	@Override
	public String getInfo(File f)
	{
		// TODO: Implement this method
		try{
			QTextInputStream qtis=new QTextInputStream(f);
			String txt=qtis.readFullText(MainActivity.encodingMode);
			Pattern p=Pattern.compile("(第[^章节]*章)|((Chapter)|(CH)[0-9])",Pattern.CASE_INSENSITIVE);
			Matcher m=p.matcher(txt);
			String g=m.group();
			return g;
		}catch(Exception e){
			return "第1章";
		}
	}
	
}
