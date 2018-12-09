package com.example.keccontacts;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.example.listselect.WorldPopulation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.UserDictionary.Words;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.View.OnClickListener;

public class MSelectAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<StaffClass> worldpopulationlist = null;
	private ArrayList<StaffClass> arraylist;
	private SparseBooleanArray mSelectedItemsIds;

	public MSelectAdapter(Context context, List<StaffClass> worldpopulationlist) {
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
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.mitem, parent, false);
		}

		final StaffClass p = getProduct(position);
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
		

		((TextView) view.findViewById(R.id.tvDescr)).setText(worldpopulationlist.get(position).getName());
		((TextView) view.findViewById(R.id.tvPrice)).setText(worldpopulationlist.get(position).getMobile());
		((TextView) view.findViewById(R.id.tvmobile)).setText(worldpopulationlist.get(position).getMail());
		((TextView) view.findViewById(R.id.tvdept)).setText(worldpopulationlist.get(position).getDepart());
		//((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
		
	 boolean b1=worldpopulationlist.get(position).getBox();
		//Toast.makeText(mContext, b1+"", Toast.LENGTH_LONG).show();
		final CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
		cbBuy.setOnCheckedChangeListener(myCheckChangList);
		cbBuy.setTag(position);
		//cbBuy.setChecked(p.box);
		cbBuy.setChecked(b1);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
			//p.setBox();
			if(cbBuy.isChecked())
			cbBuy.setChecked(false);
			else
				cbBuy.setChecked(true);
			}
		});

		return view;
	}
	
	
	OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			getProduct((Integer) buttonView.getTag()).box = isChecked;
		}
	};
	StaffClass getProduct(int position) {
		return ((StaffClass) getItem(position));
	}

	ArrayList<StaffClass> getBox() {
		ArrayList<StaffClass> box = new ArrayList<StaffClass>();
		for (StaffClass p : arraylist) {
			if (p.box)
				box.add(p);
		}
		return box;
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
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)||wp.getId().toLowerCase(Locale.getDefault()).contains(charText)||wp.getDepart().toLowerCase(Locale.getDefault()).contains(charText)||wp.getMobile().toLowerCase(Locale.getDefault()).contains(charText)||wp.getMail().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	

}
