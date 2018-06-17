package com.qiufeng.txtjoiner;
import java.util.Arrays;
import android.util.Log;

public class ChineseNumConverter
{
	static int[] arr=new int[]{0,0,0,0,0};
	static final char tenThou='万',thou='千',hun='百',ten='十';
	static char[] chNums={
		'零',
		'一',
		'二',
		'三',
		'四',
		'五',
		'六',
		'七',
		'八',
		'九'
	};
	static final char tenThouf='萬',thouf='仟',hunf='佰',tenf='拾';
	static char[] chNumsf={
		'零',
		'壹',
		'贰',
		'叁',
		'肆',
		'伍',
		'陆',
		'柒',
		'捌',
		'玖'
	};
	public static int convertToInt(char[] ch){
		StringBuilder sb=new StringBuilder();
		arr=new int[]{0,0,0,0,0};
		for(int i=0;i<ch.length;i++){
			int ind=Math.abs(Arrays.binarySearch(chNums,ch[i]));
			if(ind==-1){
				ind=Math.abs(Arrays.binarySearch(chNumsf,ch[i]));
			}
			if(ind!=-1){
				if((i+1)==ch.length){
					arr[4]=ind;
					sb.append("        第"+i+"次("+ch[i]+"):{"+arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+","+arr[4]+"}\n");
					break;
				}
				switch(ch[i+1]){
					case tenThou:
						arr[0]=ind;
						break;
					case tenThouf:
						arr[0]=ind;
						break;
					case thou:
						arr[1]=ind;
						break;
					case thouf:
						arr[1]=ind;
						break;
					case hun:
						arr[2]=ind;
						break;
					case hunf:
						arr[2]=ind;
						break;
					case ten:
						arr[3]=ind;
						break;
					case tenf:
						arr[3]=ind;
						break;
					default:
						break;
				}
			}
			sb.append("        第"+i+"次("+ch[i]+"):{"+arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+","+arr[4]+"}\n");
		}
		Log.w("ChineseConvertion","chinese="+new String(ch)+", result="+sb.toString());
		return arr[0]*10000+arr[1]*1000+arr[2]*100+arr[3]*10+arr[4];
	}
}
