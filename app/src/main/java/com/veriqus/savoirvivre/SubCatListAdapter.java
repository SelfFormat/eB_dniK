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


public class SubCatListAdapter extends RecyclerView.Adapter<SubCatListAdapter.ViewHolder> {

    OnLearningSubSelected mCallback;

    // Container Activity must implement this interface
    public interface OnLearningSubSelected {
        public void onLearningSubSelected(String subCatName, int category);
    }
    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.nameTextView = (TextView) itemView.findViewById(R.id.subclass_name);
            this.imageView = (ImageView) itemView.findViewById(R.id.subclass_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    // Store a member variable for the contacts
    private List<SubClass> mSubClasses;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public SubCatListAdapter(Context context, List<SubClass> subClasses) {
        mSubClasses = subClasses;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public SubCatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View settingView = inflater.inflate(R.layout.single_subcat, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(settingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SubCatListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        SubClass singleSub = mSubClasses.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(singleSub.getName());
        ImageView imageView = viewHolder.imageView;
        if(singleSub.isDone()) {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSubClasses.size();
    }

}
