package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Vector;

import rg.pac_space.R;

public class StatisticsListViewScoreAdapterHeader extends BaseAdapter {

    private Vector<String> myVector;
    private static LayoutInflater inflater = null;
    private Context myContext;

    @Override
    public int getCount() {
        return myVector.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return myVector.get(position);
    }

    /**
     * Constructs a newly allocated {@code StatisticsListViewResultAdapter} object.
     *
     * @param arg0 - Represents a {@code Context} object.
     */
    public StatisticsListViewScoreAdapterHeader(Context arg0) {
        this.myContext = arg0;
        this.myVector = new Vector<>();
        this.myVector.add("");
        inflater = (LayoutInflater) arg0.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * This method is used to get a {@code View} that displays the data at the specified position in the data set
     *
     * @param position    Represents an {@code int}.
     * @param convertView Represents a {@code View} object.
     * @param parent      Represents a {@code ViewGroup} object.
     * @return A {@code View} object.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) vi = inflater.inflate(R.layout.custom_list_row_score_header, null);

        ((TextView) vi.findViewById(R.id.custom_list_row_score_TxtVwNumber)).setText(myContext.getString(R.string.str_scoreHeaderPosition));
        ((TextView) vi.findViewById(R.id.custom_list_row_score_TxtVwPlayerName)).setText(myContext.getString(R.string.str_scoreHeaderPlayerName));
        ((TextView) vi.findViewById(R.id.custom_list_row_score_TxtVwScore)).setText(myContext.getString(R.string.str_scoreHeaderScore));

        return vi;
    }

}
