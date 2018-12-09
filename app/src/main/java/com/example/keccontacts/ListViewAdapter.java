package com.example.keccontacts;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//import com.example.listselect.WorldPopulation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<StaffClass> worldpopulationlist = null;
	private ArrayList<StaffClass> arraylist;
	private SparseBooleanArray mSelectedItemsIds;
	public String str="";
	public ListViewAdapter(Context context, List<StaffClass> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<StaffClass>();
		this.arraylist.addAll(worldpopulationlist);
		mSelectedItemsIds = new SparseBooleanArray();
	}

	public class ViewHolder {
		TextView stname;
		TextView stmail;
		TextView stmobile;
		TextView mobile1;
		TextView mail1;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	
	@Override
	public StaffClass getItem(int position) {
		return worldpopulationlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.stname = (TextView) view.findViewById(R.id.names);
			holder.stmail = (TextView) view.findViewById(R.id.mails);
			holder.stmobile = (TextView) view.findViewById(R.id.mobiles);
			holder.mobile1=(TextView)view.findViewById(R.id.departs);
			
			//holder.mail1=(TextView)view.findViewById(R.id.maill1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		String type=worldpopulationlist.get(position).getType();
		if(type.equals("Non Teaching"))
		{
		
		//view.setBackgroundColor("#EEEEEE");
		view.setBackgroundColor(Color.rgb(230, 230, 230));
			//holder.mobile1.setBackgroundColor(Color.BLUE);
		}
		else
		{
			//view.setBackgroundColor(Color.MAGENTA);
			view.setBackgroundColor(Color.rgb(255, 255, 255));
			//holder.mobile1.setBackgroundColor(Color.GRAY);
		}
		
		holder.stname.setText(worldpopulationlist.get(position).getName());
		holder.stmail.setText(worldpopulationlist.get(position).getMobile());
		holder.stmobile.setText(worldpopulationlist.get(position).getMail());
		holder.mobile1.setText(worldpopulationlist.get(position).getDepart());
		//holder.mail1.setText(worldpopulationlist.get(position).getMail());
		
	

		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("id",(worldpopulationlist.get(position).getId()));
				intent.putExtra("name",(worldpopulationlist.get(position).getName()));
				// Pass all data country
				intent.putExtra("mobile1",(worldpopulationlist.get(position).getMobile()));
				// Pass all data population
				intent.putExtra("population",(worldpopulationlist.get(position).getMail()));
				intent.putExtra("country",(worldpopulationlist.get(position).getDepart()));
				//aaaa
				intent.putExtra("mail1",(worldpopulationlist.get(position).getMail()));
				intent.putExtra("flag","1");
				//intent.putExtra("mobile1","aaa");
				//intent.putExtra("mail1","bbb");
				// Pass all data flag
				// Start SingleItemView Class
				mContext.startActivity(intent);
			}
		});
		view.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(mContext,"Onlongclick", Toast.LENGTH_LONG).show();
				worldpopulationlist.get(position).setBox();
				Intent i = new Intent(mContext, MultiSelect.class);
				i.putExtra("rank",(worldpopulationlist.get(position).getId()));
				i.putExtra("str",str);
				//Intent i = new Intent(getApplicationContext(), MainActivity.class);
				mContext.startActivity(i);
				return false;
			}
		});
		

		return view;
	}
	//@Override
	public void remove(StaffClass object) {
		worldpopulationlist.remove(object);
		notifyDataSetChanged();
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	// Filter Class
	public void filter(String charText) {
		str=charText;
		charText = charText.toLowerCase(Locale.getDefault());
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} 
		else 
		{
			for (StaffClass wp : arraylist) 
			{
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)||wp.getId().toLowerCase(Locale.getDefault()).contains(charText)||wp.getDepart().toLowerCase(Locale.getDefault()).contains(charText)||wp.getMobile().toLowerCase(Locale.getDefault()).contains(charText)||wp.getMail().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
