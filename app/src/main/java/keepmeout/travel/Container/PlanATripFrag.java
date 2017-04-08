package keepmeout.travel.Container;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import keepmeout.travel.R;
import keepmeout.travel.adapters.SuggestionsActvAdapter;
import keepmeout.travel.pojo.SuggestionModel;
import keepmeout.travel.util.AlertDialog_OnClickInterface;
import keepmeout.travel.util.AppLog;
import keepmeout.travel.util.AppUtil;


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
    private EditText source_edittext;
    private OnFragmentInteractionListener mListener;
    private SuggestionModel[] suggestionModels;
    private List<SuggestionModel> suggestionModelList;
    private AutoCompleteTextView source_autocompletetv;

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

    private Calendar calender;
    private DatePickerDialog dpd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_plan_a_trip, container, false);
        source_edittext = (EditText) parentView.findViewById(R.id.source_edittext);
        source_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (after >= 3) {
                    requestAsync_fetchSuggestions(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startDate_textView = (TextView) parentView.findViewById(R.id.start_date);
        calender = Calendar.getInstance();
        dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Log.d("date set", "i : " + i + " i1 : " + i1 + " i2 : " + i2);
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        startDate_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dpd.show();
            }
        });

        source_autocompletetv = (AutoCompleteTextView) parentView.findViewById(R.id.source_autocompletetv);
        return parentView;
    }

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
                    AppLog.v(LOG_TAG, "onAlertDialogButtonClicked() buttonText" + buttonText + " strTag:" + strTag);

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

                parseSuggestionsResponse(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AppUtil.showAlertDialogWith1Button(getActivity(), getString(R.string.WhoopsThereWasAnErrorPleaseTryAgainLater), null, null, LOG_TAG, false);

            }
        });
    }

    private void parseSuggestionsResponse(String response) {
        Gson gson = new GsonBuilder().create();
        //AppLog.v("size:", " items:" +  new Gson().fromJson(response, SuggestionModel.class));
        suggestionModels = new Gson().fromJson(response, SuggestionModel[].class);
        for (SuggestionModel section : suggestionModels) {
            AppLog.v("Debug", section.getText());
        }

        suggestionModelList = Arrays.asList(suggestionModels);

        SuggestionsActvAdapter suggestionsActvAdapter = new SuggestionsActvAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, suggestionModelList);
        source_autocompletetv.setAdapter(suggestionsActvAdapter);

        //AppLog.v("size:", " items:" + suggestionResponse.getSuggestionModels().size());
    }
}
