package com.example.keccontacts;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//import com.example.listselect.WorldPopulation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ListViewAdapterOffice extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<StaffClass> worldpopulationlist = null;
	private ArrayList<StaffClass> arraylist;
	private SparseBooleanArray mSelectedItemsIds;
	public String str="";
	public ListViewAdapterOffice(Context context, List<StaffClass> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<StaffClass>();
		this.arraylist.addAll(worldpopulationlist);
		mSelectedItemsIds = new SparseBooleanArray();
	}

	public class ViewHolder {
		TextView stoffice;
		TextView stphone;
		
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
			view = inflater.inflate(R.layout.listview_office, null);
			// Locate the TextViews in listview_item.xml
			holder.stoffice = (TextView) view.findViewById(R.id.names);
			holder.stphone = (TextView) view.findViewById(R.id.mails);
			//holder.mobile1=(TextView)view.findViewById(R.id.departs);
			
			//holder.mail1=(TextView)view.findViewById(R.id.maill1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		
		
		//view.setBackgroundColor("#EEEEEE");
		//view.setBackgroundColor(Color.rgb(230, 230, 230));
			//holder.mobile1.setBackgroundColor(Color.BLUE);
		
		
		holder.stoffice.setText(worldpopulationlist.get(position).getOff());
		holder.stphone.setText(worldpopulationlist.get(position).getPhone());
		//holder.mobile1.setText("Call");
		//holder.mail1.setText(worldpopulationlist.get(position).getMail());
		
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, SingleOffice.class);
				// Pass all data rank
				intent.putExtra("offic",(worldpopulationlist.get(position).getOff()));
				intent.putExtra("phon",(worldpopulationlist.get(position).getPhone()));
				
				mContext.startActivity(intent);
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
				if (wp.getOff().toLowerCase(Locale.getDefault()).contains(charText)||wp.getPhone().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
