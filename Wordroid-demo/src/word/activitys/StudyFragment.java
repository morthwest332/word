package word.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import word.database.DataAccess;
import word.model.WordList;
import wordroid.model.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StudyFragment extends Fragment {

	private ListView lv;
	private List<WordList>wordlist;
	private  List<String>shouldlist;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		inits();
		View view=inflater.inflate(R.layout.study_fragment, null);
		lv=(ListView) view.findViewById(R.id.study_lv);
		SimpleAdapter adapter=new SimpleAdapter(getActivity(), getData("studid"), R.layout.list4, new String[]{"label","image"}, new int[]{R.id.label,R.id.list4_image});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO 自动生成的方法存根
				startStudy(position);
			}
			
		});
		return view;
	}
	
	
	protected void startStudy(final int position) {
		// TODO 自动生成的方法存根
		Log.d("TEST", shouldlist.toString());
		AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
		dialog.setTitle("开始学习:");
		dialog.setIcon(R.drawable.dialog_icon);
		dialog.setMessage("list-"+shouldlist.get(position));
		dialog.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
			Intent intent=new Intent(getActivity(),studyWord.class);
			Bundle bundle=new Bundle();
			bundle.putString("list", shouldlist.get(position));
			intent.putExtras(bundle);
			startActivity(intent);
			}
		});
		dialog.show();
	}


	private void inits() {
		// TODO 自动生成的方法存根
		DataAccess data=new DataAccess(getActivity());
		wordlist=new ArrayList<WordList>();
		wordlist=data.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
		shouldlist=new ArrayList<String>(wordlist.size());
		for(int i=0;i<wordlist.size();i++)
		{
			if(wordlist.get(i).getLearned().equals("0"))
			{
				shouldlist.add(wordlist.get(i).getList());
			}
		}
	}
	private List<  Map<String, Object>> getData(String tag) {
		// TODO 自动生成的方法存根
		List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
		
		
		if(tag.equals("studid"))
		{
			
			for(int i=0;i<wordlist.size();i++)
			{
				if(wordlist.get(i).getLearned().equals("0"))
				{
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("label", "LIST-"+wordlist.get(i).getList());
					map.put("image", android.R.drawable.btn_star_big_on);
					list.add(map);
				}
			}
			
		}
		return list;
	}
	
	
	
}
