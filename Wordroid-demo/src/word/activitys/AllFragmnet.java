package word.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import word.database.DataAccess;
import word.model.WordList;
import wordroid.model.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AllFragmnet extends Fragment{

	private ListView lv;
	private List<WordList>list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view=inflater.inflate(R.layout.study_fragment, null);
		inits(view);
		return view;
	}
	private void inits(View view) {
		// TODO 自动生成的方法存根
		list=new ArrayList<WordList>();
		DataAccess data=new DataAccess(getActivity());
		list=data.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
		lv=(ListView) view.findViewById(R.id.study_lv);
		SimpleAdapter adapter=new SimpleAdapter(getActivity(), getData(),  R.layout.list5, new String[]{"label","status","image"}, new int[]{R.id.label,R.id.status,R.id.list5_image} );
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				startStudy(position);
			}
			
		});
	}
	protected void startStudy(final int position) {
		// TODO 自动生成的方法存根
		Dialog dialog = new AlertDialog.Builder(getActivity())
        .setIcon(R.drawable.dialog_icon)
        .setTitle("开始学习：")
        .setMessage("LIST-"+(position+1))
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	Intent intent = new Intent();
				Bundle bundle = new Bundle();
                bundle.putString("list", String.valueOf(position+1));
				intent.setClass(getActivity(), studyWord.class);
				intent.putExtras(bundle);	
				startActivity(intent);
            	}
        })
        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	}
        }).create();
		dialog.show();
	}
	private List< Map<String, Object>> getData() {
		// TODO 自动生成的方法存根
		
		List<Map<String ,Object>>all=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getLearned().equals("0"))
			{
				Map<String,Object>map=new HashMap<String,Object>();
					map.put("label"," LIST-"+list.get(i).getList());
					map.put("status", "未学习");
					map.put("image", android.R.drawable.btn_star_big_on);
					all.add(map);
			}
			else if(list.get(i).getLearned().equals("1"))
			{
				Map<String,Object>map=new HashMap<String,Object>();
				map.put("label"," LIST-"+list.get(i).getList());
				map.put("status", "已学习");
				map.put("image", android.R.drawable.btn_star_big_on);
				all.add(map);
			}
		}
		return all;
	}
	
}
