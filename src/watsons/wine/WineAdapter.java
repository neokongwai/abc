package watsons.wine;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WineAdapter extends ArrayAdapter<Wine>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Wine> data = null;
    ImageSpan is;
    LinearLayout ll;
    
    public WineAdapter(Context context, int layoutResourceId, ArrayList<Wine> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WineHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new WineHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_product_item_image);
            holder.txtName = (TextView)row.findViewById(R.id.list_product_item_name);
            holder.txtSize = (TextView)row.findViewById(R.id.list_product_item_size_value);
            holder.txtOriPrice = (TextView)row.findViewById(R.id.list_product_item_ori_price_value);
            holder.txtProPrice = (TextView)row.findViewById(R.id.list_product_item_pro_price_value);
            holder.txtRatingRP = (TextView)row.findViewById(R.id.list_product_item_rating_rp_value);
            holder.txtRatingWS = (TextView)row.findViewById(R.id.list_product_item_rating_ws_value);
            holder.txtRatingJH = (TextView)row.findViewById(R.id.list_product_item_rating_jh_value);
            
            row.setTag(holder);
        }
        else
        {
            holder = (WineHolder)row.getTag();
        }
        
        Wine wine = data.get(position);

        holder.imgIcon.setImageBitmap(wine.photo);
        holder.txtName.setText(wine.name);
        if (wine.size != null && wine.size != "")
        	holder.txtSize.setText(wine.size);
        else 
        {
        	ll = (LinearLayout) row.findViewById(R.id.list_product_item_size_layout);
        	ll.setVisibility(View.GONE);
        }
        
        if ((wine.original_price != null) && (wine.original_price !="null"))
        	holder.txtOriPrice.setText("HK$" + wine.original_price);
        else
        {
        	ll = (LinearLayout) row.findViewById(R.id.list_product_item_ori_price_layout);
        	ll.setVisibility(View.GONE);
        }
        if ((wine.promote_price != null) && !(wine.promote_price.equals("null"))) {
        	holder.txtProPrice.setText("HK$" + wine.promote_price);
        }
        else
        {
        	ll = (LinearLayout) row.findViewById(R.id.list_product_item_pro_price_layout);
        	ll.setVisibility(View.GONE);
        	holder.txtProPrice.setVisibility(View.GONE);
        }
        if ((wine.rating_rp == null && wine.rating_ws == null && wine.rating_jh == null) ||
        		(wine.rating_rp == "null" && wine.rating_ws == "null" && wine.rating_jh == "null"))
        {
        	ll = (LinearLayout) row.findViewById(R.id.list_product_item_rating_layout);
        	ll.setVisibility(View.GONE);
        }
        else
        {
        	Log.d("vk", wine.name + "; jh="+ wine.rating_jh + "; rp=" +wine.rating_rp+ "; js="+ wine.rating_ws);
	        if (wine.rating_rp != null &&  wine.rating_rp != "null")
	        {
	        	SpannableString tmp = new SpannableString("tmp " + wine.rating_rp);
	        	is = new ImageSpan(context, R.drawable.rating_rp);
	        	tmp.setSpan(is, 0, 3, 0);
	            holder.txtRatingRP.setText(tmp);
	        }
	        else
	        {
	        	holder.txtRatingRP.setVisibility(View.GONE);
	        }
	        if (wine.rating_ws != null && wine.rating_ws != "null")
	        {
	        	SpannableString tmp = new SpannableString("tmp " + wine.rating_ws);
	        	is = new ImageSpan(context, R.drawable.rating_ws);
	        	tmp.setSpan(is, 0, 3, 0);
	            holder.txtRatingWS.setText(tmp);
	        }
	        else
	        {
	        	holder.txtRatingWS.setVisibility(View.GONE);
	        }
	        if ((wine.rating_jh != null) && (wine.rating_jh != "null"))
	        {
	        	 SpannableString tmp = new SpannableString("tmp " + wine.rating_ws);
	        	 is = new ImageSpan(context, R.drawable.rating_jh);
	        	 tmp.setSpan(is, 0, 3, 0);
	             holder.txtRatingJH.setText(tmp);
	        }
	        else
	        {
	        	Log.d("vk", "elsecase--->" +wine.name + "; "+ wine.rating_jh);
	        	holder.txtRatingJH.setVisibility(View.GONE);
	        }
        }
        return row;
    }
    
    static class WineHolder
    {
        ImageView imgIcon;
        TextView txtName;
        TextView txtSize;
        TextView txtOriPrice;
        TextView txtProPrice;
        TextView txtRatingRP;
        TextView txtRatingWS;
        TextView txtRatingJH;
    }
}