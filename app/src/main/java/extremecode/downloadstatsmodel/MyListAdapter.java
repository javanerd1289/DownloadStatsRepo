package extremecode.downloadstatsmodel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Junaid Hassan on 10-May-17.
 */

public class MyListAdapter extends ArrayAdapter<MyListModel> {

    public MyListAdapter(Context context, ArrayList<MyListModel> model) {
        super(context, R.layout.items, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.idText);
            viewHolder.appName = (TextView) convertView.findViewById(R.id.appNameText);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateText);
            viewHolder.deviceName = (TextView) convertView.findViewById(R.id.deviceNameText);
            viewHolder.myTime = (TextView) convertView.findViewById(R.id.myTime);
            viewHolder.row = (LinearLayout) convertView.findViewById(R.id.row1);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyListModel model = getItem(position);
        viewHolder.deviceName.setText(model.deviceName);
        viewHolder.date.setText(model.date);
        viewHolder.id.setText(model.id);
        viewHolder.appName.setText(model.appName);
        viewHolder.myTime.setText(model.nowTime);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        if (position == 0) {
            viewHolder.row.setBackgroundColor(Color.CYAN);
        }

        if (position % 2 == 1) {
            viewHolder.row.setBackgroundColor(Color.YELLOW);
        }

        if (position % 2 == 0) {
            viewHolder.row.setBackgroundColor(Color.CYAN);
        }

        if (formattedDate.equals(model.date)) {
            viewHolder.row.setBackgroundColor(Color.CYAN);
        }
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        public TextView id;
        public TextView appName;
        public TextView date;
        public TextView deviceName;
        public TextView myTime;
        public LinearLayout row;
    }
}
