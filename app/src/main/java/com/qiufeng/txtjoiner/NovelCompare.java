package com.qiufeng.txtjoiner;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigInteger;

public class NovelCompare implements Comparator<File>
{
	StringBuilder sb;
	public NovelCompare(){
	}
	public int compare(File o1, File o2) {
		sb.append("SORT mode="+MainActivity.sortMode+" ");
		String n1=getInfo(o1).replaceAll("\\.[a-zA-Z0-9_$]*$","").replaceAll("\\s+","");
		String n2=getInfo(o2).replaceAll("\\.[a-zA-Z0-9_$]*$","").replaceAll("\\s+","");
		ui.alert("replace1","n1:"+n1+"\nn2:"+n2,true);
		/*Pattern p1=Pattern.compile("第[^章节]+章");
		Pattern p2=p1;
		ui.alert("compile","RegEx compilation end",true);
		Matcher m1=p1.matcher(n1);
		Matcher m2=p2.matcher(n2);
		m1.
		ui.alert("matcher","matcher get",true);
		String g=m1.group();
		ui.alert("Group","group get,g="+g,true);
		String gg=g.replaceAll("第|章|节","");
		ui.alert("replace","replace test pass",true);*/
		String s1=n1/*m1*/.replaceAll("第|章|节|(Chapter)|(CH)","");
		//ui.alert("s1",s1,true);
		String s2=n2/*m2*/.replaceAll("第|章|节|(Chapter)|(CH)","");
		//ui.alert("s2",s2,true);
		int r1=toInt(s1);
		int r2=toInt(s2);
		//ui.alert("int","r1="+r1+",r2="+r2,true);
		sb.append(r1+","+r2+"\n");
		sb.append("\toriginal:1="+n1+",2="+n2+"\n");
		sb.append("\taftReplace:1="+s1+",2="+s2+"\n");
		if(r1!=-3&&r2!=-3)
			return compareInt(r1,r2);
		else
			return compareHex(s1,s2);
		//return 1;
	}
	public int compareHex(String s1,String s2){
		/*String[] arra={"A","B","C","D","E","F"};
		s1=s1.toUpperCase();
		s2=s2.toUpperCase();
		boolean b1=false,b2=false;
		for(String arrb : arra){
			if(s1.contains(arrb))
				b1=true;
			if(s2.contains(arrb))
				b2=true;
			if(b1&&b2)break;
		}
		if(!b1)
			s1=Integer.toHexString(Integer.valueOf(s1));
		if(!b2)
			s2=Integer.toHexString(Integer.valueOf(s2));
		if(s1.length()>s2.length()){
			int dif=(s1.length()-s2.length());
			for(int bb=0;bb<dif;bb++){
				s2=" "+s2;
			}
		}else if(s2.length()>s1.length()){
			int dif=(s2.length()-s1.length());
			for(int bb=0;bb<dif;bb++){
				s1=" "+s1;
			}
		}
		String[] hexes={
			"0","1","2","3","4","5","6","7","8","9",
			"A","B","C","D","E","F"
		};
		char[] c1=s1.toCharArray(),c2=s2.toCharArray();
		for(int ii=0;ii<c1.length;ii++){
			Character bb1=(Character)(c1[ii]);
			Character bb2=(Character)(c2[ii]);
			String bs1=Character.toString(bb1);
			String bs2=Character.toString(bb2);
			if(Arrays.binarySearch(hexes,bs1)>Arrays.binarySearch(hexes,bs2)){
				return -1;
			}else if(Arrays.binarySearch(hexes,bs1)<Arrays.binarySearch(hexes,bs2)){
				return 1;
			}
		}
		return 0;*/
		return new BigInteger(s1,16).compareTo(new BigInteger(s2,16));
	}
	public File[] sortFiles(File file){
		//ui.alert("开始","Novel",true);
		sb=new StringBuilder();
		File[] paths=file.listFiles();
		if(paths==null)paths=new File[]{};
		//ui.alert("sort","开始",true);
		Arrays.sort(paths,this);  
		//ui.alert("sort","结束",true);
		//ui.alert(file.getPath(),sb.toString(),true);
		/*StringBuilder sbb=new StringBuilder();
		for(int kkk=0;kkk<paths.length;kkk++){
			sbb.append(paths[kkk].getName()+"\n");
		}
		ui.alert("sort",sbb.toString(),true);*/
		return paths;
	}
	public int compareInt(int r1,int r2){
		if(r1>r2){
			sb.append("\tfirst:"+r2+"\n");
			return 1;
		}else if(r2>r1){
			sb.append("\tfirst:"+r1+"\n");
			return -1;
		}else{
			sb.append("\tno change.\n");
			return 0;
		}
	}
	public int toInt(String s){
		if(Hex.checkHexString(s)){
			ui.alert("res","hex",true);
			/*//Long r=Long.parseLong(s,16);
			Long r=toDec(s);
			ui.alert("hex",String.valueOf(r),true);
			return r.intValue();*/
			return -3;
		}
		try{
			int res=Integer.valueOf(s);
			ui.alert("res","int"+res,true);
			return res;
		}catch(Exception e){}
		ui.alert("res","ch",true);
		return ChineseNumConverter.convertToInt(s.toCharArray());
	}
	public String getInfo(File f){
		return f.getName();
	}
	public Long toDec(String hex){
		char[] chex=hex.toCharArray();
		int[] h=new int[chex.length];
		for(int buf=0;buf<chex.length;buf++){
			Character c=(Character)(chex[buf]);
			switch(c.toUpperCase(c)){
				case 'A':
					h[buf]=10;
					break;
				case 'B':
					h[buf]=11;
					break;
				case 'C':
					h[buf]=12;
					break;
				case 'D':
					h[buf]=13;
					break;
				case 'E':
					h[buf]=14;
					break;
				case 'F':
					h[buf]=15;
					break;
				default:
					h[buf]=c.getNumericValue(c);
					break;
			}
		}
		ui.alert("convert","ok",true);
		long res=0;
		StringBuilder sbs=new StringBuilder();
		for(int ran=h.length-1;ran>=0;ran--){
			res+=h[h.length-1-ran]+Math.pow(16,ran);
			//ui.alert((h.length-1-ran)+"",res+"",true);
			sbs.append((h.length-1-ran)+":"+res);
		}
		ui.alert(""+res,sbs.toString(),true);
		return res;
	}
}
