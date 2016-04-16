package word.activitys;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import word.database.DataAccess;
import word.model.BookList;
import word.model.WordList;
import wordroid.model.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;


@SuppressWarnings("deprecation")
public class study extends TabActivity implements TabHost.TabContentFactory{
    /** Called when the activity is first created. */
	
	String info = "hey";
	public ArrayList<WordList> wordlist;
	private ArrayList<String> listShould ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost th = getTabHost();
        //���ݶ�ȡ
        DataAccess data = new DataAccess(this);
        wordlist=data.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
        listShould = new ArrayList<String>(wordlist.size());
		for(int i=0;i<wordlist.size();i++){
			if (wordlist.get(i).getLearned().equals("0")){
				listShould.add(wordlist.get(i).getList());
			}
		}
		BookList book =data.QueryBook("ID ='"+DataAccess.bookID+"'", null).get(0);
		this.setTitle("ѧϰ-"+book.getName());
        //
        LayoutInflater.from(this).inflate(R.layout.studyword_main, th.getTabContentView(), true);
        
        
        th.addTab(th.newTabSpec("studid").setIndicator("δѧ����LIST",study.this.getResources().getDrawable(R.drawable.not_learn)).setContent(this));
        th.addTab(th.newTabSpec("all").setIndicator("���е�LIST",study.this.getResources().getDrawable(R.drawable.all)).setContent(this));
    }
    
    //ʵ��TabContentFactory�ӿ� �ص�һ�����صĲ���
     public View createTabContent(String tag) {
    	 ListView lv = new ListView(this);
    	 LinearLayout ll = new LinearLayout(this);
    	 ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    	 ll.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.main_back));
         ll.setOrientation(LinearLayout.VERTICAL);
         TextView tv = new TextView(this);
         tv.setTextColor(Color.BLACK);
         tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.main_budget_lv_header));
         ll.addView(tv);
    	 ll.addView(lv);
    	 
    	 //���ñ�����ɫ
    	 lv.setCacheColorHint(0);
    	 
    	 //adapter  getData(tag)
    	 SimpleAdapter adapterAll = new SimpleAdapter(this, getData(tag), R.layout.list5, new String[]{"label","status","image"}, new int[]{R.id.label,R.id.status,R.id.list5_image});
    	 SimpleAdapter adapterStudid = new SimpleAdapter(this, getData(tag), R.layout.list4, new String[]{"label","image"}, new int[]{R.id.label,R.id.list4_image});
         
           if(tag.equals("all")){
        	   tv.setText("    ���е�LIST");
        	   lv.setAdapter(adapterAll);
               lv.setOnItemClickListener(new OnItemClickListener(){

			     public void onItemClick(AdapterView<?> arg0, View v, final int arg2,
					  long id) {
				      // TODO Auto-generated method stub
		    	 
		    	       //Intent intent = null;
			    	 //arg0Ϊ��ȡlistview�е����ж���
			    	 
			    	 //arg2Ϊ���λ���Ƿ�����ѧϰ����
		    	       if(wordlist.get(arg2).getLearned().equals("1")){
		    	    	   //��ѧϰ�����¼�����
		    	    	startStudy(arg2,2,1);
		    	       }
		    	       else
		    	    	   //δѧϰ���ĵ������
		    	    	startStudy(arg2,2,0);
			      }
        	 
             });
               

               
           }
           else if(tag.equals("studid")){
        	   tv.setText("    δѧϰ����LIST");
            	 lv.setAdapter(adapterStudid);
                 lv.setOnItemClickListener(new OnItemClickListener(){

  			     public void onItemClick(AdapterView<?> arg0, View v, int position,
  					  long id) {
  				      // TODO Auto-generated method stub
  			    	 
  			    	 
  		    	     startStudy(position,1,0);
  			      }
          	 
               });
    lv.setOnItemLongClickListener(new OnItemLongClickListener(){

     public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
     			final int arg2, long arg3) {
     			// TODO Auto-generated method stub
     	Dialog dialog = new AlertDialog.Builder(study.this)
     		     .setIcon(R.drawable.dialog_icon).setTitle("����")
     		     .setItems(new String[]{"���Ϊ��ѧϰ"}, new DialogInterface.OnClickListener(){
     		    	 	@SuppressLint("SimpleDateFormat")
					public void onClick(DialogInterface dialog, int which) {
     							// TODO Auto-generated method stub
     							if (which==0){
     								DataAccess data = new DataAccess(study.this);
     								WordList labelList = wordlist.get(Integer.parseInt(listShould.get(arg2))-1);
     								labelList.setLearned("1");
     								Calendar cal = Calendar.getInstance();
     							    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
     							    String date=f.format(cal.getTime());
     							    labelList.setLearnedTime(date);
     							    labelList.setReview_times("0");
     							    labelList.setReviewTime("");
     								data.UpdateList(labelList);
     								Intent intent = new Intent();
     								intent.setClass(study.this, study.class);
     								finish();
     								startActivity(intent);
     							}
     						}
     		            	
     		            })
     		            .setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
     		            .create();
     					dialog.show();
     					return false;
     				}
     				
     			});
                 
             }
           
           return ll;
       }
     
    private List<Map<String, Object>> getData(String tag) {
 		// TODO Auto-generated method stub
 		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 		
 		if (tag.equals("studid")){
 			
 			for (int i=0;i<wordlist.size();i++){
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("image", android.R.drawable.btn_star_big_on);
 					list.add(map);
 				}
 			}
 		}
 		else if(tag.equals("all")){
 			for (int i=0;i<wordlist.size();i++){
 				
 				//viewHolderΪTextView(�б���) TextView(ѧϰ״̬)  image
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("status", "δѧϰ");
 					map.put("image", android.R.drawable.btn_star_big_on);
 					list.add(map);
 				}
 				else if(wordlist.get(i).getLearned().equals("1")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("status", "��ѧϰ");
 					map.put("image", android.R.drawable.btn_star_big_off);
 					list.add(map);
 				}
 			}
 		}

 		return list;
 	}

 	private void startStudy(final int arg2,final int tag,final int check) {
 		if(tag==2){
 			if(check==1){
 		 		Dialog dialog = new AlertDialog.Builder(study.this)
 		        .setIcon(R.drawable.dialog_icon)
 		        .setTitle("��ʾ")
 		        .setMessage("�˵�Ԫ��ѧ��������ѧϰ�����ѧϰ���ȣ��Ƿ������\nLIST-"+(arg2+1))
 		        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	Intent intent = new Intent();
 						Bundle bundle = new Bundle();
 		                bundle.putString("list", String.valueOf(arg2+1));
 						intent.setClass(study.this, studyWord.class);
 						intent.putExtras(bundle);	
 						finish();
 						startActivity(intent);
 		            	}
 		        })
 		        .setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	}
 		        }).create();
 				dialog.show();
 			}
 			else{
 		Dialog dialog = new AlertDialog.Builder(study.this)
        .setIcon(R.drawable.dialog_icon)
        .setTitle("��ʼѧϰ��")
        .setMessage("LIST-"+(arg2+1))
        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	Intent intent = new Intent();
				Bundle bundle = new Bundle();
                bundle.putString("list", String.valueOf(arg2+1));
				intent.setClass(study.this, studyWord.class);
				intent.putExtras(bundle);	
				finish();
				startActivity(intent);
            	}
        })
        .setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	}
        }).create();
		dialog.show();
 			}

 		}
 		else if(tag==1)
 		{
 			Dialog dialog = new AlertDialog.Builder(study.this)
 	        .setIcon(R.drawable.dialog_icon)//Ц��  ���Ը���
 	        .setTitle("��ʼѧϰ��")
 	        .setMessage("LIST-"+listShould.get(arg2))
 	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	finish();
 	            	Intent intent = new Intent();
 					Bundle bundle = new Bundle();
 	            	bundle.putString("list", listShould.get(arg2));
 					intent.setClass(study.this, studyWord.class);
 					intent.putExtras(bundle);	
 					
 					startActivity(intent);
 	            	}
 	        })
 	        .setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	}
 	        }).create();
 			dialog.show();
 		}
		
	}

     
}