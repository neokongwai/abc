package watsons.wine;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class FoodDishAdapter extends BaseAdapter  {
private LayoutInflater linflater;
private TextView txt_1, txt_2;
private List<String> str1;
private List<String> str2;
private Context ctx;
private Typeface tf1;
private Typeface tf2;

public FoodDishAdapter(Context context, List<String> s1, List<String> s2) {
    str1 = s1;
    str2 = s2;
    linflater = LayoutInflater.from(context);
    ctx = context;
    
    tf1 = Typeface.createFromAsset(context.getAssets(),
			"fonts/Arial.ttf");
    tf2 = Typeface.createFromAsset(ctx.getAssets(),
			"fonts/msjhbd.ttf");
}

@Override
public int getCount() {
    return str1.size();
}

@Override
public Object getItem(int arg0) {
    return str1.get(arg0);
}

@Override
public long getItemId(int arg0) {
    return arg0;
}

@Override
public View getView(final int position, View convertView, ViewGroup parent) {

    if (convertView == null) {

        convertView = linflater.inflate(R.layout.food_dish_item, null);

    }

    txt_1 = (TextView) convertView.findViewById(R.id.list_fooditem_dish_text_en);
    txt_2 = (TextView) convertView.findViewById(R.id.list_fooditem_dish_text_chi);
    txt_1.setText(str1.get(position));
    txt_2.setText(str2.get(position));

    txt_1.setTypeface(tf1);
    txt_2.setTypeface(tf2);

    return convertView;
}
}