package friday.nicknamer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListOfItems.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListOfItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfItems extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<String> listOfPages;
    ArrayList<String> otherDetailPages;
    ArrayList<String> selectedValues = new ArrayList<>();
    RecyclerView recyclerView = getView().findViewById(R.id.recycler_fragment);
    int current = 0;

    public ListOfItems() {
        // Required empty public constructor
    }

    public static ListOfItems newInstance() {
        ListOfItems fragment = new ListOfItems();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        listOfPages = getPageArrayFromXML();
        mListener.setPageTitle(listOfPages.get(current));
        otherDetailPages = getOtherDetailsFromXML();

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private ArrayList<String> getOtherDetailsFromXML() {
        return new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.OtherDetails)));
    }

        private ArrayList<String> getPageArrayFromXML() {
            return new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Pages)));
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

    public void onFabClick() {
        if (current < listOfPages.size() - 1) {
            current++;
            mListener.setPageTitle(listOfPages.get(current));
        }
    }

    public void backButtonPressed() {
        Log.i("FridayTag", "Here2");
        if ((current) > 0) {
            current--;
            mListener.setPageTitle(listOfPages.get(current));
        } else {
            Log.i("FridayTag", "Here");
            Snackbar.make(Objects.requireNonNull(getView()), "Are you sure you want to exit?", Snackbar.LENGTH_INDEFINITE).setAction("YES", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.exitApp();
                }
            }).show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(NicknamerAction action);

        void setPageTitle(String title);

        void exitApp();
    }
}
