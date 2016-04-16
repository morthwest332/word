package word.httputil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtil {

	public static final String URL="http://japi.juhe.cn/youdao/dictionary.from";
	public static final String API_KEY="6b3b31ba62f25da6d0913164d3dd699a";
	
	public static String setParms(String content) {
		// TODO �Զ����ɵķ������
		 try {
		 content=URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//http://japi.juhe.cn/youdao/dictionary.from?key=�������KEY&word=ƻ��&only=
		 System.out.println(URL+"?key="+API_KEY+"&word="+content+"&only=");
		return  URL+"?key="+API_KEY+"&word="+content+"&only=";
	}
	public static void getData(final String urlStr,final HttpCallbackListener listener)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				HttpURLConnection conn=null;
				try {
					URL url=new URL(urlStr);
					conn=(HttpURLConnection) url.openConnection();
					conn.setReadTimeout(8000);
					conn.setRequestMethod("GET");
					InputStream in=conn.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in),8192);
					StringBuilder builder=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null)
					{
						builder.append(line);
						
					}
					if(listener!=null)
					{
						
						listener.onFinish(builder.toString());
					}
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					if(listener!=null)
					{
						listener.onError(e);
					}
				}
				finally
				{
					if(conn!=null)
					{
					conn.disconnect();
					}
				}
			}
		}).start();
		
	}
}
