package word.model.network;

import java.util.List;

import wordroid.model.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NetAdapter extends BaseAdapter{

	private List<Web>list;
	private ViewHolder viewHolder;
	private LayoutInflater inflater;
	public NetAdapter(Context context,List<Web>list)
	{
		inflater=LayoutInflater.from(context);
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		Web web=list.get(position);
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.networkadapter, null);
			viewHolder=new ViewHolder();
			viewHolder.get_1=(TextView) convertView.findViewById(R.id.adapter_get_1);
			viewHolder.get_2=(TextView) convertView.findViewById(R.id.adapter_get_2);
			convertView.setTag(convertView);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.get_1.setText(web.getKey());
		viewHolder.get_2.setText(web.getValue().toString());
		return convertView;
	}

	class ViewHolder
	{
		TextView get_1;
		TextView get_2;
	}
}
