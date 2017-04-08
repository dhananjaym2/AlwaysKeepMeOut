package keepmeout.travel.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import keepmeout.travel.R;
import keepmeout.travel.pojo.SuggestionModel;
import keepmeout.travel.pojo.SuggestionNameAndId;

/**
 * Created by vikesh on 08-04-2017.
 */

public class SuggestionsAdapter extends BaseAdapter {
    private final ArrayList<SuggestionNameAndId> al_suggestions;
    private final Context mContext;
    String LOG_TAG = SuggestionsAdapter.class.getSimpleName();

    public SuggestionsAdapter(Context mContext, ArrayList<SuggestionNameAndId> al_suggestions) {
        Log.v(LOG_TAG, "in Station_ListViewAdapter() constructor");
        this.mContext = mContext;
        this.al_suggestions = al_suggestions;
    }

    @Override
    public int getCount() {
        if (al_suggestions != null) {
            return al_suggestions.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return al_suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        private TextView suggestion_name_tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_suggestions, viewGroup, false);

            mViewHolder = new ViewHolder();

            mViewHolder.suggestion_name_tv = (TextView) convertView.findViewById(R.id.suggestion_name_tv);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.suggestion_name_tv.setText(al_suggestions.get(position).getNameSuggested());
        return convertView;
    }
}