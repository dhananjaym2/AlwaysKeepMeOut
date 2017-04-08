package keepmeout.travel.Container;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import keepmeout.travel.R;
import keepmeout.travel.adapters.SuggestionsAdapter;
import keepmeout.travel.constants.AppConstants;
import keepmeout.travel.pojo.A2BModesResponse;
import keepmeout.travel.pojo.SuggestionModel;
import keepmeout.travel.pojo.SuggestionResponse;
import keepmeout.travel.util.AlertDialog_OnClickInterface;
import keepmeout.travel.util.AppLog;
import keepmeout.travel.util.AppUtil;
import keepmeout.travel.util.Preferences;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanATripFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanATripFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanATripFrag extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = "PlanTrip.java";

    private String mParam1;
    private String mParam2;
    private TextView startDate_textView;
    private EditText source_edittext, destination_edittext;
    private OnFragmentInteractionListener mListener;
    private SuggestionModel[] suggestionModels;
    private ArrayList<SuggestionResponse> suggestionModelList, destinationModelList = new ArrayList<>();
    private ListView sourceSuggestions_listView, destinationSuggestions_listView;
    SuggestionsAdapter suggestionsAdapter;
    private RelativeLayout destination_parent;
    private Calendar calender;
    private DatePickerDialog dpd;
    private boolean clickedFrom = true;
    private Button Search_button;
    private String id_source = "id_source";
    private String xid_source = "xid_source";
    private String id_destination = "id_destination";
    private String xid_destination = "xid_destination";

    public PlanATripFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanATripFrag.
     */
    public static PlanATripFrag newInstance(String param1, String param2) {
        PlanATripFrag fragment = new PlanATripFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_plan_a_trip, container, false);
        sourceSuggestions_listView = (ListView) parentView.findViewById(R.id.sourceSuggestions_listView);
        destinationSuggestions_listView = (ListView) parentView.findViewById(R.id.destinationSuggestions_listView);
        destination_parent = (RelativeLayout) parentView.findViewById(R.id.destination_parent);
        source_edittext = (EditText) parentView.findViewById(R.id.source_edittext);
        destination_edittext = (EditText) parentView.findViewById(R.id.destination_edittext);
        source_edittext.addTextChangedListener(source_text_watcher);
        destination_edittext.addTextChangedListener(destination_text_watcher);
        startDate_textView = (TextView) parentView.findViewById(R.id.start_date);
        Search_button = (Button) parentView.findViewById(R.id.Search_button);
        calender = Calendar.getInstance();
        dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month + 1, day);

                String format = "EEE, d MMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format);

                startDate_textView.setText(sdf.format(cal.getTimeInMillis()));
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        startDate_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });

        sourceSuggestions_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //source_edittext.addTextChangedListener(null);
                source_edittext.removeTextChangedListener(source_text_watcher);
                source_edittext.setText(suggestionModelList.get(i).getNameSuggested());
                Preferences.setStringPreference(getActivity(), id_source, suggestionModelList.get(i).getId());
                Preferences.setLongPreference(getActivity(), xid_source, suggestionModelList.get(i).getXid());
                sourceSuggestions_listView.setVisibility(View.GONE);
                destination_parent.setVisibility(View.VISIBLE);
                //source_edittext.addTextChangedListener(source_text_watcher);
            }
        });

        destinationSuggestions_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //source_edittext.addTextChangedListener(null);
                destination_edittext.removeTextChangedListener(source_text_watcher);
                destination_edittext.setText(destinationModelList.get(i).getNameSuggested());
                Preferences.setStringPreference(getActivity(), id_destination, destinationModelList.get(i).getId());
                Preferences.setLongPreference(getActivity(), xid_destination, destinationModelList.get(i).getXid());
                destinationSuggestions_listView.setVisibility(View.GONE);
                //source_edittext.addTextChangedListener(source_text_watcher);
            }
        });

        Search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAsync_fetchModes();
            }
        });

        return parentView;
    }

    TextWatcher source_text_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
            if (after >= 2) {
                destination_parent.setVisibility(View.GONE);
                sourceSuggestions_listView.setVisibility(View.VISIBLE);
                clickedFrom = true;
                requestAsync_fetchSuggestions(charSequence.toString());
            } else if (sourceSuggestions_listView != null && suggestionModelList != null && suggestionsAdapter != null) {
                sourceSuggestions_listView.setVisibility(View.GONE);
                destination_parent.setVisibility(View.VISIBLE);
                suggestionModelList.clear();
                suggestionsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher destination_text_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
            if (after >= 2) {
                destinationSuggestions_listView.setVisibility(View.VISIBLE);
                requestAsync_fetchSuggestions(charSequence.toString());
                clickedFrom = false;
            } else if (destinationSuggestions_listView != null && destinationModelList != null && suggestionsAdapter != null) {
                destinationSuggestions_listView.setVisibility(View.GONE);
                destinationModelList.clear();
                suggestionsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //  Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void requestAsync_fetchSuggestions(String userInputQuery) {
        if (!AppUtil.isInternetAvailable(getActivity())) {
            AppUtil.showAlertDialogWith_TwoButtons(getActivity(), getString(R.string.
                    PleaseConnectToAWorkingInternetConnection), new AlertDialog_OnClickInterface() {
                @Override
                public void onAlertDialogButtonClicked(String buttonText, String strTag) {
                    if (strTag != null && strTag.equals(LOG_TAG)) {
                        if (buttonText != null && buttonText.equals(getString(R.string.Retry))) {
                            //requestAsync_fetchSuggestions();
                        }
                    }
                }
            }, getString(R.string.Retry), getString(android.R.string.cancel), LOG_TAG, false);
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String strUrl = getString(R.string.city_suggestion_URL);
        AppLog.v(LOG_TAG, "in requestAsync_fetchSuggestions strUrl:" + strUrl);
        RequestParams params = new RequestParams();
        params.put("query", userInputQuery);
        AppLog.v(LOG_TAG, "params.toString():" + params.toString());

        client.get(getActivity(), strUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                AppLog.v(LOG_TAG, "in onSuccess()" + response);
                if (clickedFrom)
                    parseSuggestionsResponse(response);
                else
                    parseSuggestionsResponseDestination(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AppUtil.showAlertDialogWith1Button(getActivity(), getString(R.string.WhoopsThereWasAnErrorPleaseTryAgainLater), null, null, LOG_TAG, false);

            }
        });
    }

    private void parseSuggestionsResponse(String response) {
        SuggestionResponse sidnname;
        suggestionModelList = new ArrayList<>();
        suggestionModels = new Gson().fromJson(response, SuggestionModel[].class);
        for (SuggestionModel model : suggestionModels) {
            sidnname = new SuggestionResponse(model.getText(), model.getId(), model.getXid());

            suggestionModelList.add(sidnname);
        }

        suggestionsAdapter = new SuggestionsAdapter(getActivity(), suggestionModelList);

        sourceSuggestions_listView.setAdapter(suggestionsAdapter);
    }

    private void parseSuggestionsResponseDestination(String response) {
        SuggestionResponse sidnname;
        suggestionModelList = new ArrayList<>();
        suggestionModels = new Gson().fromJson(response, SuggestionModel[].class);
        for (SuggestionModel model : suggestionModels) {
            sidnname = new SuggestionResponse(model.getText(), model.getId(), model.getXid());

            destinationModelList.add(sidnname);
        }

        suggestionsAdapter = new SuggestionsAdapter(getActivity(), destinationModelList);

        destinationSuggestions_listView.setAdapter(suggestionsAdapter);
    }

    private void requestAsync_fetchModes() {
        long source_xid = Preferences.getLongPreference(getActivity(), xid_source);
        long destination_xid = Preferences.getLongPreference(getActivity(), xid_destination);
        if (!AppUtil.isInternetAvailable(getActivity())) {

            AppUtil.showAlertDialogWith_TwoButtons(getActivity(), getString(R.string.
                    PleaseConnectToAWorkingInternetConnection), new AlertDialog_OnClickInterface() {
                @Override
                public void onAlertDialogButtonClicked(String buttonText, String strTag) {
                    AppLog.v(LOG_TAG, "onAlertDialogButtonClicked() buttonText" + buttonText + " strTag:" + strTag);

                    if (strTag != null && strTag.equals(LOG_TAG)) {
                        if (buttonText != null && buttonText.equals(getString(R.string.Retry))) {
                            //requestAsync_fetchPickAndDropPointsForDriver();
                        }
                    }
                }
            }, getString(R.string.Retry), getString(android.R.string.cancel), LOG_TAG, false);

        }

        AsyncHttpClient client = new AsyncHttpClient();

        String strUrl = "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$&originCityId=" + source_xid +
/*1065223*/ "&destinationCityId=" + destination_xid /*2511944*/;
        AppLog.v(LOG_TAG, "in requestAsync_fetchSuggestions strUrl:" + strUrl);

        client.get(getActivity(), strUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                AppLog.v(LOG_TAG, "in onSuccess()" + response);

                parseResponse_A2BModesAPI(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AppUtil.showAlertDialogWith1Button(getActivity(), getString(R.string.WhoopsThereWasAnErrorPleaseTryAgainLater), null, null, LOG_TAG, false);
            }
        });
    }

    private void parseResponse_A2BModesAPI(String response) {
        Gson gson = new GsonBuilder().create();
        A2BModesResponse a2BModesResponse = gson.fromJson(response, A2BModesResponse.class);
        AppUtil.showToast(getActivity(), "Distance:" + a2BModesResponse.getData().getDistance(), false);

        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra(AppConstants.DestinationLat, a2BModesResponse.getData().getDestination().getLat());
        intent.putExtra(AppConstants.DestinationLong, a2BModesResponse.getData().getDestination().getLng());
        intent.putExtra(AppConstants.SourceLat, a2BModesResponse.getData().getOrigin().getLat());
        intent.putExtra(AppConstants.SourceLong, a2BModesResponse.getData().getOrigin().getLng());
        startActivity(intent);
    }

}
