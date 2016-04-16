package word.activitys;



import java.util.ArrayList;
import java.util.List;

import word.httputil.HttpCallbackListener;
import word.httputil.HttpUtil;
import word.model.network.Data;
import word.model.network.NetAdapter;
import word.model.network.Result;
import word.model.network.Search;
import word.model.network.Status;
import word.model.network.Web;
import wordroid.model.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
public class Network extends Activity{

	
	
	private Button btn;
	private EditText edit;
	private ListView text;
	StringBuffer response=new StringBuffer();
	private List<Web>web=new ArrayList<Web>();
	private static final  int SEND_FLAG=1;
	private NetAdapter adapter;
	private Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what==SEND_FLAG)
			{
				
				//�ٴβ���ʱ���������ת���쳣
				//��Ϊ����ֱ�Ӷ�̬�ı䲼�ֵĴ�С
				//��Ҫͨ��LayoutParmas
				List<Web>web=(List<Web>) msg.obj;
				/*for(Web webs:web)
				{
					web.add(webs);
					//adapter.notifyDataSetChanged();
				}*/
				adapter=new NetAdapter(getApplicationContext(), web);
				text.setAdapter(adapter);
				
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.network);
		initView();
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				//Toast.makeText(Network.this, edit.getText().toString(), Toast.LENGTH_SHORT).show();			
				String urlStr=HttpUtil.setParms(edit.getText().toString());
				edit.setText("");
						HttpUtil.getData(urlStr,new HttpCallbackListener() {
							@Override
							public void onFinish(String response) {
								// TODO �Զ����ɵķ������
								if(response==null)
								{
									//Can't create handler inside thread that has not called Looper.prepare()
									//Toast.makeText(Network.this, "δ���ҵ����", Toast.LENGTH_SHORT).show();
								/*	System.out.println(b);*/
									
									//��������
								}
								else{
								Network.this.response.append(response);
								System.out.println(response);
								parseString(response);
							}
							}
					
							@Override
							public void onError(Exception e) {
								// TODO �Զ����ɵķ������
								e.printStackTrace();
							}
						});
					}
				
			
		});
	
	}
	private  void parseString(String response) {
		// TODO �Զ����ɵķ������
		Gson gson=new Gson();
		Status status=gson.fromJson(response, Status.class);
		System.out.println(status.getResult().toString());
		/*List<Result> content=status.getResult();
		
		System.out.println(content.toString());*/
		//�ж�URL�Ƿ�Ϸ�
		Result result=status.getResult();
		Data data=result.getData();
		List<Web>web=data.getWeb();
		Message message=handler.obtainMessage();
		message.what=SEND_FLAG;
		message.obj=web;
		handler.sendMessage(message);
	
		
	
	}
	private void initView() {
		// TODO �Զ����ɵķ���
		btn=(Button) findViewById(R.id.search);
		edit=(EditText) findViewById(R.id.input);
		text=(ListView) findViewById(R.id.display);
	}
}
