package word.activitys;

import java.util.ArrayList;
import java.util.List;

import wordroid.model.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class Study_Test extends FragmentActivity {

	private ViewPager pager;
	private List<Fragment>list;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自动生成的方法存根
		super.onCreate(arg0);
		setContentView(R.layout.study_test);
		list=new ArrayList<Fragment>();
		list.add(new StudyFragment());
		list.add(new AllFragmnet());
		pager=(ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(adapter);
		
	}
	
	 FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

		@Override
		public Fragment getItem(int arg0) {
			// TODO 自动生成的方法存根
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return list.size();
		}
		
	
	};
}
