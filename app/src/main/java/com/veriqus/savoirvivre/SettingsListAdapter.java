package com.veriqus.savoirvivre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by krzysztofmarczewski on 28.11.2017.
 */


public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.settings_name);
            imageView = (ImageView) itemView.findViewById(R.id.settings_icon);
        }
    }

    // Store a member variable for the contacts
    private List<SettingPOJO> mSettings;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public SettingsListAdapter(Context context, List<SettingPOJO> settings) {
        mSettings = settings;
        mContext = context;
    }
    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public SettingsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View settingView = inflater.inflate(R.layout.single_settings_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(settingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SettingsListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        SettingPOJO singleSetting = mSettings.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(singleSetting.getName());
        ImageView imageView = viewHolder.imageView;
        imageView.setImageDrawable(getContext().getResources().getDrawable(singleSetting.getDrawable()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSettings.size();
    }
}
