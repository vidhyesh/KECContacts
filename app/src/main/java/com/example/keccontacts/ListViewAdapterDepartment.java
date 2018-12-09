package com.example.keccontacts;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//import com.example.listselect.WorldPopulation;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ListViewAdapterDepartment extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<StaffClass> worldpopulationlist = null;
	private ArrayList<StaffClass> arraylist;
	private SparseBooleanArray mSelectedItemsIds;

	public ListViewAdapterDepartment(Context context, List<StaffClass> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<StaffClass>();
		this.arraylist.addAll(worldpopulationlist);
		mSelectedItemsIds = new SparseBooleanArray();
	}

	public class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		TextView mobile1;
		TextView mail1;
		TextView dept;
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
			view = inflater.inflate(R.layout.listview_department, null);
			// Locate the TextViews in listview_item.xml
			holder.dept = (TextView) view.findViewById(R.id.dept);
			//holder.mail1=(TextView)view.findViewById(R.id.maill1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.dept.setText(worldpopulationlist.get(position).getId());
		
	

		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, Deptlist.class);
				// Pass all data rank
				intent.putExtra("dept",(worldpopulationlist.get(position).getId()));
				// Pass all data country
				
				// Pass all data flag
				// Start SingleItemView Class
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
		charText = charText.toLowerCase(Locale.getDefault());
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} 
		else 
		{
			for (StaffClass wp : arraylist) 
			{
				if (wp.getId().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
