package keepmeout.travel.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import keepmeout.travel.R;
import keepmeout.travel.pojo.SuggestionModel;

/**
 * Created by vikesh on 08-04-2017.
 */

public class SuggestionsActvAdapter extends ArrayAdapter<SuggestionModel> {
    private final Context mContext;
    private final int mLayoutResourceId;
    private final ArrayList<SuggestionModel> mSuggestions;
    private final ArrayList<SuggestionModel> mSuggestions_All;

    public SuggestionsActvAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SuggestionModel> suggestions) {
        super(context, resource, suggestions);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mSuggestions = new ArrayList<>(suggestions);
        this.mSuggestions_All = new ArrayList<>(suggestions);
        //this.mDepartments_Suggestion = new ArrayList<>();
    }

    /*public int getCount() {
        return mSuggestions.size();
    }

    public Department getItem(int position) {
        return mDepartments.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            Department department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textView);
            name.setText(department.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((Department) resultValue).name;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mDepartments_Suggestion.clear();
                    for (Department department : mDepartments_All) {
                        if (department.name.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mDepartments_Suggestion.add(department);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mDepartments_Suggestion;
                    filterResults.count = mDepartments_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDepartments.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof Department) {
                            mDepartments.add((Department) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mDepartments.addAll(mDepartments_All);
                }
                notifyDataSetChanged();
            }
        };
    }*/
}
