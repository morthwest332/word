package word.activitys;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import word.business.OperationOfBooks;
import word.database.DataAccess;
import word.database.SqlHelper;
import word.httputil.HttpCallbackListener;
import word.httputil.HttpUtil;
import word.model.BookList;
import word.model.Data;
import word.model.English;
import word.model.Result;
import word.model.WordList;
import wordroid.model.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Main extends Activity implements OnClickListener{
	
	
	//��onDestroy()����bookID(����Ϊȫ�ֱ���);
	//��Preference��onPreferenceChanged()����ѡ���ʱ��
	//Menu_Networkδ���  	4/9
	
	public  static String SETTING_BOOKID="bookID";
	public  static String BOOKNAME = "BOOKNAME";
	private Spinner pickBook;
	private TextView info;
	private ImageButton deleteBu;
	private ImageButton resetBu;
	private Button learnBu;
	private Button reviewBu;
	private Button testBu;
	private Button attentionBu;
	private TextView learn_text;
	private TextView review_text;
	public static final int MENU_SETTING = 1;
	public static final int MENU_ABOUT = MENU_SETTING+1;
	public static final int MENU_CONTACT = MENU_SETTING+2;
	public static final int MENU_NETWORK=MENU_SETTING+3;
	View myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setTitle("��׿������--Wordroid");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		LayoutInflater mInflater = LayoutInflater.from(this);
		myView = mInflater.inflate(R.layout.main, null);
		setContentView(myView);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		String content=bundle.getString("content");
		Log.i("content",content);
		OperationOfBooks OOB = new OperationOfBooks();
		
		//��wordoid.model_preferences�л�ȡ�洢ʱ��
		//getSharedPreference() ��������   ������ģʽ
		SharedPreferences setting = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
		//����Notification��֪ͨ��
		OOB.setNotify(setting.getString("time", "18:00 ����"),Main.this);
		
		 File dir = new File("data/data/wordroid.model/databases"); 
	       
	        if (!dir.exists()) 
	            dir.mkdir(); 
		 if (!(new File(SqlHelper.DB_NAME)).exists()) { 
	         
	         
	            FileOutputStream fos;
				try {
					fos = new FileOutputStream(SqlHelper.DB_NAME);
				
	            byte[] buffer = new byte[8192]; 
	            int count = 0; 
	            InputStream is = getResources().openRawResource( 
	                    R.raw.wordorid); 
	            while ((count = is.read(buffer)) > 0) { 
	                fos.write(buffer, 0, count); 
	            }

	            fos.close(); 
	            is.close(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		DataAccess.bookID=settings.getString(BOOKNAME, "");
		
		//�鿴�Ƿ�����ѧϰ�����Ѹ�ϰ��WordList����ʾ
		OOB.UpdateListInfo(Main.this);
		initWidgets();
	}
	
	private void initSpinner() {
		DataAccess data = new DataAccess(this);
		final ArrayList<BookList> bookList = data.QueryBook(null, null);
		//Log.i("size", String.valueOf(bookList.size()));
		String[] books = new String[bookList.size()+1];
		int i=0;
		for (;i<bookList.size();i++){
			books[i]=bookList.get(i).getName();
		}
		books[i]="�����´ʿ�";
		//�����鱾ѡ����
		ArrayAdapter< CharSequence > adapter = new ArrayAdapter< CharSequence >(this, android.R.layout.simple_spinner_item, books);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pickBook.setAdapter(adapter);
		pickBook.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2<bookList.size()){
					DataAccess.bookID=bookList.get(arg2).getID();
					deleteBu.setEnabled(true);
					learnBu.setEnabled(true);
					resetBu.setEnabled(true);
					reviewBu.setEnabled(true);
					testBu.setEnabled(true);
					DataAccess data2 = new DataAccess(Main.this);
					ArrayList<WordList> lists = data2.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
					int learned=0,reviewed=0;
					for (int k=0;k<lists.size();k++){
						if (lists.get(k).getLearned().equals("1")){

							learned++;
						}
						
						if (Integer.parseInt(lists.get(k).getReview_times())>=5){
							reviewed++;
						}
						review_text.setText("	"+"	"+"	"+"�Ѹ�ϰ:"+"	"+"	"+"	"+reviewed+"/"+lists.size());
						learn_text.setText("	"+"	"+"	"+"��ѧϰ:"+"	"+"	"+"	"+learned+"/"+lists.size());
					}
				}
				else if(arg2==bookList.size()){
					Intent intent = new Intent();
					intent.setClass(Main.this, ImportBook.class);
					startActivity(intent);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		if (bookList.size()==0) {
			pickBook.setSelection(1);
			info.setText("���ȴ��Ϸ�ѡ��һ���ʿ⣡");
			this.deleteBu.setEnabled(false);
			this.learnBu.setEnabled(false);
			this.resetBu.setEnabled(false);
			this.reviewBu.setEnabled(false);
			this.testBu.setEnabled(false);
			return;
		}
		int j=0;
		Log.i("BookID", DataAccess.bookID);
		for (;j<bookList.size();j++){
			if (DataAccess.bookID.equals(bookList.get(j).getID())){
				pickBook.setSelection(j);	
				break;
			}
				
		}
	}
	
	private void initWidgets() {
		// TODO Auto-generated method stub
		this.deleteBu=(ImageButton) myView.findViewById(R.id.delete);
		deleteBu.setOnClickListener(this);
		this.info=(TextView) myView.findViewById(R.id.bookinfo);
		this.learnBu=(Button) myView.findViewById(R.id.learn);
		learnBu.setOnClickListener(this);
		this.pickBook=(Spinner) myView.findViewById(R.id.pickBook);
		this.resetBu=(ImageButton) myView.findViewById(R.id.reset);
		resetBu.setOnClickListener(this);
		this.reviewBu=(Button) myView.findViewById(R.id.review);
		reviewBu.setOnClickListener(this);
		this.testBu=(Button) myView.findViewById(R.id.test);
		testBu.setOnClickListener(this);
		this.attentionBu=(Button) myView.findViewById(R.id.attention);
		attentionBu.setOnClickListener(this);
		this.review_text=(TextView) myView.findViewById(R.id.review_text);
		this.learn_text=(TextView) myView.findViewById(R.id.learn_text);
		
		
		
		DisplayMetrics dm = new DisplayMetrics(); 
		   dm = getApplicationContext().getResources().getDisplayMetrics(); 
		   int screenWidth = dm.widthPixels; 
		   int padding = (screenWidth-200);
		   this.learnBu.setPadding(padding/5, 0, padding/10, 0);
		   this.resetBu.setPadding(padding/10, 0, padding/10, 0);
		   this.testBu.setPadding(padding/10, 0, padding/10, 0);
		   this.attentionBu.setPadding(padding/10, 0, padding/5, 0);
		initSpinner();
	}
	
	

	protected void onDestroy() {
		// TODO Auto-generated method stub
		SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		settings.edit()
		.putString(BOOKNAME, DataAccess.bookID)
		.commit();
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		//��ϰ
		if (v==reviewBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, ReviewMain.class);
			this.startActivity(intent);
		}
		
		//����
		if (v==testBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, 	TestList.class);
			this.startActivity(intent);
			/*
			FragmentManager fm=getFragmentManager();
			FragmentTransaction transaction=fm.beginTransaction();
			TestListFragment fragment=new TestListFragment();
			transaction.add(R.id.main, fragment);
			transaction.commit();*/
		}
		
		//ɾ���鼮
		if (v==deleteBu){
			Dialog dialog = new AlertDialog.Builder(this)
            .setIcon(R.drawable.dialog_icon)
            .setTitle("ɾ����ǰ�ʿ�")
            .setMessage("ȷ��Ҫ������ʿ�ɾ����")
            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	DataAccess data = new DataAccess(Main.this);
                	data.DeleteBook();
                	DataAccess.bookID="";
                	Toast.makeText(Main.this, "�ôʿ���ɾ��", Toast.LENGTH_SHORT).show();
                	initSpinner();
                }
            })
            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   }
            }).create();
			dialog.show();
		}
		//����ѧϰ���
		if (v==this.resetBu){
			Dialog dialog = new AlertDialog.Builder(this)
            .setIcon(R.drawable.dialog_icon)
            .setTitle("���õ�ǰ�ʿ�")
            .setMessage("ȷ��Ҫ������ʿ�����������ʧȥ����ѧϰ��¼")
            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	DataAccess data = new DataAccess(Main.this);
                	data.ResetBook();
                	Toast.makeText(Main.this, "�ôʿ��ѱ�����", Toast.LENGTH_SHORT).show();
                	initSpinner();
                }
            })
            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   }
            }).create();
			dialog.show();
		}
		//����
		if (v==this.attentionBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, Attention.class);
			startActivity(intent);
		}
		//ѧϰ
		if (v==learnBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, Study_Test.class);
			this.startActivity(intent);
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("In Resume", DataAccess.bookID);
		this.initSpinner();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,MENU_NETWORK,0,"����");
		menu.add(0, MENU_SETTING, 1,"����");
		menu.add(0, MENU_ABOUT, 2, "˵��");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case MENU_SETTING:{
			//���Ӹ�ϰ ��������
			Intent intent = new Intent();
			intent.setClass(this, Preference.class);
			startActivity(intent);
			break;
		}
		case MENU_NETWORK:
		{
			Intent intent=new Intent();
			intent.setClass(this,Network.class);
			startActivity(intent);
			break;
		}
		case MENU_ABOUT:{
			Intent intent = new Intent();
			intent.setClass(this, Help.class);
			startActivity(intent);
			break;
		}
		case MENU_CONTACT:{
			Intent intent = new Intent();
			intent.setClass(this, about.class);
			startActivity(intent);
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
	

}

