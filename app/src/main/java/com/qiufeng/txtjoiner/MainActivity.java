package com.qiufeng.txtjoiner;

import android.widget.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;
import java.io.IOException;
import android.view.MenuInflater;
import android.view.Gravity;

public class MainActivity extends Activity 
{
	LayoutInflater li;
	/*配置*/
	NovelCompare nc=new NovelCompare();
	DefaultCompare dc=new DefaultCompare();
	NovInCompare nic=new NovInCompare();
	static Activity cx;
	static String ENCODING_MODE_UTF8="UTF-8";
	static String ENCODING_MODE_GBK="GBK";
	public static String encodingMode=ENCODING_MODE_UTF8;
	static String SORT_MODE_DEFAULT="DEFAULT";
	static String SORT_MODE_NOVEL="NOVEL";
	static String SORT_MODE_NOVELIN="NOVEL_IN";
	public static String sortMode=SORT_MODE_DEFAULT;
	//select按钮的文本
	StringBuilder selectSb=new StringBuilder();
	//合并后的文本
	StringBuilder joinSb=new StringBuilder();
	//开始layout
	LinearLayout ll;
	//join txt的layout
	ScrollView jc;
	LinearLayout jtl;
	Button jtbtn;
	//顶部主区
	TextView title;
	LinearLayout selL;
	FileChooserDialog f;
	EditText et;
	EditText afterspliterator;
	EditText beforespliterator;
	ViewGroup.LayoutParams etSvp;
	ImageButton select;
	Button confirm,ret;
	//选项区
	LinearLayout cgg;//这是底部选择合并属性的layout
	RadioGroup encg;//这是选择编码的Radio组
	RadioButton rbUtf;//utf-8
	RadioButton rbAGbk;//GBK
	//排序区
	RadioGroup sortg;
	RadioButton sortDef,sortNov,sortNovIn;//Default和Novel
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		cx=this;
		//ui.alert("一万一千二百三十四",String.valueOf(ChineseNumConverter.convertToInt(new char[]{'一','万','一','千','二','百','三','十','四'})),false);
		li=LayoutInflater.from(this);
		ll=(LinearLayout)li.inflate(R.layout.main,null);
		jc=new ScrollView(this);
		jtl=newLL();
		jc.addView(jtl);
		initJtl();
        setContentView(jc);
    }
	public LinearLayout newLL(){
		return new LinearLayout(this);
	}
	public void initJtl(){
		jtl.setOrientation(jtl.VERTICAL);
		//标题
		title=new TextView(this);
		title.setTextSize(30);
		title.setTypeface(Typeface.create("宋体",Typeface.BOLD));
		title.setText("合并TXT");
		title.setTextColor(Color.BLACK);
		jtl.addView(title);
		//文件选择Layout
		selL=new LinearLayout(this);
		et=new EditText(this);
		et.addTextChangedListener(new TextWatcher() {             
				@Override    
				public void onTextChanged(CharSequence s, int start, int before, int count) {    
					
				}
				@Override    
				public void beforeTextChanged(CharSequence s, int start, int count,int after) {     
					 
				}
				@Override    
				public void afterTextChanged(Editable s) {     
					selectSb=new StringBuilder(s.toString());
				}    
		});  
		etSvp=new ViewGroup.LayoutParams(500,-2);
		et.setHint("请输入要合并的文件,以换行分隔，或者选择文件");
		select=new ImageButton(this);
		select.setImageResource(R.drawable.ic_action_pick_sdcard);
		//设置选择文件的框
		select.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				f=new FileChooserDialog(cx,"/sdcard",false,true);
				f.setOnConfirmListener(new FileChooserDialog.OnConfirmListener(){
					public void onConfirm(String path,String text){
						if(!selectSb.toString().equals(""))selectSb.append('\n');
						selectSb.append(path);
						et.setText(selectSb.toString());
					}
				});
				f.setOnFileSelectedListener(new FileChooserDialog.OnFileSelectedListener(){
					public void onFileSelected(File file){
						if(!selectSb.toString().equals(""))selectSb.append('\n');
						selectSb.append(file.getPath());
						et.setText(selectSb.toString());
					}
				});
				f.show();
			}
		});
		selL.addView(et,etSvp);
		selL.addView(select,new ViewGroup.LayoutParams(200,150));
		jtl.addView(selL);
		//设置分隔符
		beforespliterator=new EditText(cx);
		beforespliterator.setHint("请输入每个txt前插入的字");
		jtl.addView(beforespliterator);
		afterspliterator=new EditText(cx);
		afterspliterator.setHint("请输入每个txt后插入的字");
		jtl.addView(afterspliterator);
		//选项区
		cgg=new LinearLayout(this);
		cgg.setGravity(Gravity.TOP);
		//编码选择区
		encg=new RadioGroup(this);
		rbUtf=new RadioButton(this);
		rbAGbk=new RadioButton(this);
		encg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup rg,int index){
					switch(index){
						case 1:
							encodingMode=ENCODING_MODE_UTF8;
							break;
						case 2:
							encodingMode=ENCODING_MODE_GBK;
							break;
						default:
							ui.toast(cx,"无法识别字符编码index("+index+")!");
							break;
					}
				}
			});
		rbUtf.setText("UTF-8");
		rbAGbk.setText("GBK");
		encg.setOrientation(encg.VERTICAL);
		encg.addView(rbUtf);
		encg.addView(rbAGbk);
		cgg.addView(encg);
		//排序区
		sortg=new RadioGroup(this);
		sortDef=new RadioButton(this);
		sortNov=new RadioButton(this);
		sortNovIn=new RadioButton(this);
		sortg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				public void onCheckedChanged(RadioGroup rg,int index){
					switch(index){
						case 3:
							sortMode=SORT_MODE_DEFAULT;
							break;
						case 4:
							sortMode=SORT_MODE_NOVEL;
							break;
						case 5:
							sortMode=SORT_MODE_NOVELIN;
							break;
						default:
							ui.toast(cx,"无法识别排序方式index("+index+")!");
							break;
					}
				}
			});
		sortDef.setText("默认排序");
		sortNov.setText("小说章节智能排序");
		sortNovIn.setText("小说章节智能排序(文本内)");
		sortg.setOrientation(sortg.VERTICAL);
		sortg.addView(sortDef);
		sortg.addView(sortNov);
		sortg.addView(sortNovIn);
		cgg.addView(sortg);
		jtl.addView(cgg);
		//确定和返回
		confirm=new Button(this);
		confirm.setText("确定");
		confirm.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				//TODO
				try
				{
					doConfirm();
				}
				catch (Exception e)
				{}
			}
		});
		jtl.addView(confirm);
		
	}
	public void doConfirm()throws Exception{
		String[] paths=selectSb.toString().split("\n");
		File[] files=new File[paths.length];
		StringBuilder res=new StringBuilder();
		for(int k=0;k<paths.length;k++){
			files[k]=new File(paths[k]);
			res=joinTxt(files[k],res);
		}
		joinSb=res;
		requestSave();
		//new AlertDialog.Builder(this).setMessage(res.toString()).show();
	}
	public StringBuilder joinTxt(File file,StringBuilder sb){
		StringBuilder res=sb;
		if(file.isDirectory()){
			File[] ff=dc.sortFiles(file);
			if(sortMode==SORT_MODE_NOVEL){
				ff=nc.sortFiles(file);
			}else if(sortMode==SORT_MODE_NOVELIN){
				ff=nic.sortFiles(file);
			}
			for(File fb : ff){
				res.append(format(beforespliterator.getText().toString(),fb));
				res=joinTxt(fb,res);
			}
		}else if(file.isFile()){
			try
			{
				QTextInputStream qtis=new QTextInputStream(file);
				res.append(qtis.readFullText(encodingMode));
				res.append(format(afterspliterator.getText().toString(),file));
			}
			catch (IOException e)
			{}
		}
		return res;
	}
	public void requestSave(){
		FileChooserDialog save=new FileChooserDialog(cx,"/sdcard",true,true);
		save.setOnConfirmListener(new FileChooserDialog.OnConfirmListener(){
			public void onConfirm(String path,String text){
				String act=path+"/"+text;
				try{
					new File(act).createNewFile();
					QTextOutputStream qtos=new QTextOutputStream(new File(act));
					qtos.simpleWrite(joinSb.toString());
					qtos.close();
					ui.toast(cx,"保存成功!");
				}catch(Exception e){
					ui.toast(cx,"保存失败!"+e.getMessage());
				}
			}
		});
		save.show();
	}
	public String format(String str,File file){
		return str
			.replace("{filename}",file.getName())
			.replace("{filepath}",file.getPath());
	}
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {  
		MenuInflater inflater = getMenuInflater();  
		inflater.inflate(R.menu.main, menu);  
		menu.findItem(R.id.info).setIcon(R.drawable.ic_information);
		menu.findItem(R.id.helpI).setIcon(R.drawable.ic_help_circle);
		return super.onCreateOptionsMenu(menu);  
	}  

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		// TODO: Implement this method
		if(item.getTitle().toString().equals("关于")){
			startActivity(new Intent(MainActivity.this,AboutActivity.class));
		}else if(item.getTitle().toString().equals("帮助")){
			startActivity(new Intent(MainActivity.this,HelpActivity.class));
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
