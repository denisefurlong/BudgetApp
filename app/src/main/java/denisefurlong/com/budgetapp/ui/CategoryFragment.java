package denisefurlong.com.budgetapp.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Random;
import denisefurlong.com.budgetapp.helpers.DoubleTextViewArrayAdapter;
import denisefurlong.com.budgetapp.R;

public class CategoryFragment extends Fragment {

    private ArrayList<Pair<String, String>> mExpenseListItems;
    private DoubleTextViewArrayAdapter mDayExpenseAdapter;
    private ListView mDayExpenseListView;
    private static String CATEGORY_NAME = "category_name";
    private String mCategory;
    private OnFragmentInteractionListener mListener;

    public static CategoryFragment newInstance(String categoryName) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(CATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fmRootView = inflater.inflate(R.layout.fragment_category, container, false);
        mDayExpenseListView = (ListView) fmRootView.findViewById(R.id.list_items_for_month);
        return fmRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpenseListItems = new ArrayList<Pair<String, String>>();
        String[] values = new String[]{"Rent", "Coffee", "Food"};
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < values.length; ++i) {
            int x = rnd.nextInt(20);
            int y = rnd.nextInt(99);
            mExpenseListItems.add(new Pair<String, String>(values[i], "£" + x + "." + y));
        }
        mDayExpenseAdapter = new DoubleTextViewArrayAdapter(getActivity(), R.layout.list_double_text, mExpenseListItems);
        mDayExpenseListView.setAdapter(mDayExpenseAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
