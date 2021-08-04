package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Vector;

import rg.pac_space.R;

public class StatisticsListViewResultAdapter extends BaseAdapter {

    private Vector<StatisticsListViewResultAdapterObject> myVector;
    private static LayoutInflater inflater = null;

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
     * @param arg1 - Represents a {@code Vector<>} object.
     */
    public StatisticsListViewResultAdapter(Context arg0, Vector<StatisticsListViewResultAdapterObject> arg1) {
        this.myVector = arg1;
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
        if (vi == null) vi = inflater.inflate(R.layout.custom_list_row_result, null);

        // Get object in the vector
        StatisticsListViewResultAdapterObject var = myVector.get(position);

        ((ImageView) vi.findViewById(R.id.custom_list_row_result_ImgVwImage)).setImageDrawable(var.getIcon());
        ((TextView) vi.findViewById(R.id.custom_list_row_result_TxtVwRowName)).setText(var.getName());
        ((TextView) vi.findViewById(R.id.custom_list_row_result_TxtVwQuantity)).setText(var.getQuantity());
        ((TextView) vi.findViewById(R.id.custom_list_row_result_TxtVwScore)).setText(var.getPoints());

        return vi;
    }
}
