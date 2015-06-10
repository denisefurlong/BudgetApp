package denisefurlong.com.budgetapp.helpers;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import denisefurlong.com.budgetapp.R;

public class DoubleTextViewArrayAdapter extends ArrayAdapter<Pair<String,String>> {
    private HashMap< Pair<String,String> , Integer> mIdMap = new HashMap<Pair<String,String>, Integer>();
    private Context mContext;

    public DoubleTextViewArrayAdapter(Context context, int textViewResourceId, ArrayList<Pair<String,String>> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i),i);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_double_text, null);

        TextView money_description_view = (TextView)convertView.findViewById(R.id.money_description_view);
        money_description_view.setText(getItem(position).first);

        TextView money_view = (TextView)convertView.findViewById(R.id.money_view);
        money_view.setText(getItem(position).second);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Pair<String,String> item;
        try{
            item = getItem(position);
        }
        catch(Exception e){
            item = getItem(position);
        }

        return mIdMap.get(item);
    }

    @Override
    public void add(Pair<String,String> temp)
    {
        mIdMap.put(temp,mIdMap.size()+1);
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }
}
