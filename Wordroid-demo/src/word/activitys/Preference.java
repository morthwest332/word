package word.activitys;


import java.util.HashMap;

import word.business.OperationOfBooks;
import word.widget.timePreference;
import wordroid.model.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

public class Preference extends PreferenceActivity implements OnPreferenceChangeListener {
    ListPreference listpre;
	timePreference timepre;
	//应用设置
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference);  
		timepre = (timePreference) this.findPreference("time");
		timepre.setOnPreferenceChangeListener(this);
		SharedPreferences settings = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
		timepre.setSummary(settings.getString("time", "18:00 下午"));
		CharSequence[] list={"英语","美语"}; 	
		listpre=(ListPreference)findPreference("category");
		listpre.setEntries(list);
		CharSequence[] list2={"1","2"}; 
		listpre.setEntryValues(list2);
		listpre.setSummary(listpre.getEntry());
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener(){

			@Override
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// TODO Auto-generated method stub
				listpre.setSummary(listpre.getEntry());
				
			}
			
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onPreferenceChange(android.preference.Preference preference,
			Object newValue) {
		// TODO Auto-generated method stub
		if(preference.getKey().equals("time")){
			HashMap<String,Integer> map =(HashMap<String, Integer>) newValue;
			String ifAm="上午";
			if(map.get("hour")>12)ifAm=" 下午";
			int minute=map.get("minute");
			String mi=String.valueOf(minute);
			if (minute<10)mi="0"+minute;
			
			//时间选择保存
			timepre.setSummary(""+map.get("hour")+":"+mi+" "+ifAm);
			OperationOfBooks OOB = new OperationOfBooks();
			SharedPreferences settings = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
			OOB.setNotify(settings.getString("time", "18:00 下午"),this);
		}
		
		
		return false;
	}
	

}
