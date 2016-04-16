package word.business;

import java.util.ArrayList;

import word.activitys.Main;
import word.activitys.Preference;
import word.activitys.ReviewMain;
import word.database.DataAccess;
import word.model.WordList;
import wordroid.model.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class makeNotify extends BroadcastReceiver{

	//����̹㲥������
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//�����㲥
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			OperationOfBooks OOB = new OperationOfBooks();
			SharedPreferences settings = context.getSharedPreferences("wordroid.model_preferences", 0);
			OOB.setNotify(settings.getString("time", "18:00 ����"),context);
		}
		else if(intent.getAction().equals("shownotify")){
			SharedPreferences settings = context.getSharedPreferences("wordroid.model_preferences", 0);
			
			//��ʱ�����Ƿ���
			//���������е�����Ҫ��ϰ������֪ͨ
			if(settings.getBoolean("notify", false)){
				DataAccess data = new DataAccess(context);
				ArrayList<WordList> list=data.QueryList(null, null);
				boolean notify=false;
				//�ж��Ƿ��е�����Ҫ��ϰ
				for(int i=0;i<list.size();i++){
					if (list.get(i).getShouldReview().equals("1")){
						notify=true;
						break;
					}
				}
				if(notify){
					NotificationManager notiManager = (NotificationManager) context.getSystemService("notification");
					Notification notification = new Notification(R.drawable.icon, "�е�����Ҫ��ϰ~", System.currentTimeMillis());
					notification.flags = Notification.FLAG_AUTO_CANCEL;
					Intent intent1 = new Intent(context, Main.class);
					PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
							intent1, PendingIntent.FLAG_UPDATE_CURRENT);
					notification.setLatestEventInfo(context, "��ϰ����", "�е�����Ҫ��ϰ~",
							contentIntent);
					notiManager.notify(0, notification);
					Log.i("receive", "receive");
				}
				
			}
		}
		
		
	}
}
