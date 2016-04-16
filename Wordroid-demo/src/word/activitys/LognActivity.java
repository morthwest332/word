package word.activitys;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import word.httputil.HttpCallbackListener;
import word.httputil.HttpUtil;
import word.model.English;
import wordroid.model.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LognActivity extends Activity {

	public static final int GET_IMAGE=1;
	public String urlStr = "http://open.iciba.com/dsapi/?date";
	private ImageView image;	
	private Handler handler=new Handler()
	{
		public void handleMessage(Message msg)
		{
				if(msg.what==GET_IMAGE)
				{
					final English english=(English) msg.obj;
					//System.out.println("为什么不显示"+image_url);
					ImageLoader.getInstance().displayImage(english.getFenxiang_img(), image);
					image.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO 自动生成的方法存根
							Intent  intent=new Intent(LognActivity.this,Main.class);
							Bundle bundle=new Bundle();
							bundle.putString("content", english.getContent());
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
			/*	ImageLoader.getInstance().displayImage(image_url, image);*/
				}
		}
	};
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		image=(ImageView)findViewById(R.id.english_image);
		HttpUtil.getData(urlStr, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO 自动生成的方法存根
				parseString(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO 自动生成的方法存根
				
			}
		});
	}
	
	
	protected void parseString(String response) {
		// TODO 自动生成的方法存根
		System.out.println("response"+response);
		Gson gson=new Gson();
		English english=gson.fromJson(response, English.class);
		System.out.println("为什么不显示"+english.getContent());
		Message msg=handler.obtainMessage();
		msg.what=GET_IMAGE;
		msg.obj=english;
		handler.sendMessage(msg);
		//response{"sid":"517","tts":"http:\/\/news.iciba.com\/admin\/tts\/2013-05-03.mp3","content":"I'm a great believer in luck, and I find the harder I work, the more I have of it.","note":"\u6211\u5f88\u76f8\u4fe1\u8fd0\u6c14\uff0c\u800c\u6211\u53d1\u73b0\u6211\u8d8a\u52aa\u529b\uff0c\u6211\u7684\u8fd0\u6c14\u8d8a\u597d\u3002(\u6258\u9a6c\u65af\u2022\u6770\u6590\u900a)","love":"313","translation":"\u611f\u8c22@8023lc\u73b2 \u63d0\u4f9b\u3002\u8bcd\u9738\u5c0f\u7f16\uff1a\u52aa\u529b\u662f\u79ef\u7d2f\u8fd0\u6c14\u7684\u91cd\u8981\u65b9\u6cd5\uff0c\u8981\u52aa\u529b\uff0c\u4e3a\u81ea\u5df1\u521b\u9020\u66f4\u591a\u673a\u4f1a\u3002","picture":"http:\/\/cdn.iciba.com\/news\/word\/2013-05-03.jpg","picture2":"http:\/\/cdn.iciba.com\/news\/word\/big_2013-05-03b.jpg","caption":"\u8bcd\u9738\u6bcf\u65e5\u4e00\u53e5","dateline":"2013-05-03","s_pv":"47394","sp_pv":"7374","tags":[{"id":"4","name":"\u52b1\u5fd7"},{"id":"13","name":"\u540d\u4eba\u540d\u8a00"}],"fenxiang_img":"http:\/\/cdn.iciba.com\/web\/news\/longweibo\/imag\/2013-05-03.jpg"}
	}
}
