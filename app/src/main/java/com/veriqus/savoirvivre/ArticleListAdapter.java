package com.veriqus.savoirvivre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by krzysztofmarczewski on 06.09.2017.
 */

public class ArticleListAdapter extends ArrayAdapter<ItemList> {

        public ArticleListAdapter(Context context, ArrayList<ItemList> itemLists) {
            super(context, 0, itemLists);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ItemList itemList = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_item, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.title_text_article_list);
            TextView content = (TextView) convertView.findViewById(R.id.content_text_article_list);

            //View strip = convertView.findViewById(R.id.strip);

            //String articleType = ((MainActivity) this.getContext()).getArticleType(itemList.title);

//            if (articleType.equals("good")) {
//                strip.setBackgroundColor(Color.parseColor("#009688"));
//            } else if (articleType.equals("bad")) {
//                strip.setBackgroundColor(Color.parseColor("#A54E4E"));
//            }


            // Populate the data into the template view using the data object
            title.setText(itemList.title);
            content.setText(itemList.content);
            // Return the completed view to render on screen
            return convertView;
        }
}
