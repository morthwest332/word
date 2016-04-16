package word.widget;

import java.util.HashMap;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;


//Preference   时间配置
public class timePreference extends DialogPreference {
	public LinearLayout ll; 
	public Context context;
	TimePicker timePicker;
		
	public timePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	

	//对话框弹出时初始化
	@Override
	//Prepares the dialog builder to be shown when the preference is clicked. Use this to set custom properties on the dialog
	protected void onPrepareDialogBuilder(Builder builder) {
		// TODO Auto-generated method stub
		super.onPrepareDialogBuilder(builder);
		
		//加入时间选择器
		//TimePicker
		ll = new LinearLayout(context);
		ll.setLayoutParams(new 
				LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT));
		ll.setGravity(Gravity.CENTER);
		timePicker = new TimePicker(context);
		timePicker.setIs24HourView(true);
		ll.addView(timePicker);
		
		builder.setView(ll);
	}


	@Override
	protected void onBindDialogView(View view) {
		// TODO Auto-generated method stub
		
		super.onBindDialogView(view);
	}

	
	//关闭对话框时，保存设置信息
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		if (positiveResult){
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("hour", timePicker.getCurrentHour());
			map.put("minute", timePicker.getCurrentMinute());
			String ifAm =" 上午";
			if(timePicker.getCurrentHour()>12)ifAm=" 下午";
			int minute=map.get("minute");
			String mi=String.valueOf(minute);
			if (minute<10)mi="0"+minute;
			this.persistString(""+timePicker.getCurrentHour()+":"+mi+ifAm);
			this.getOnPreferenceChangeListener().onPreferenceChange(this, map);
			
		}
		super.onDialogClosed(positiveResult);
	}
	

}
