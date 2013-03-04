package watsons.wine.mycellars;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyCellarUpdateItmesAdapter extends BaseExpandableListAdapter {
  
    private Context mContext = null;
    private String[] groups = { "a\nb", "b", "c" };
    private String[] familis = { "aa", "bb", "cc" };
    private String[] friends = { "aaa", "bbb", "ccc" };
    private String[] colleagues = { "aaaa", "bbbb", "cccc" };
  
    private List<String> groupList = null;
    private List<List<String>> itemList = null;
  
    public MyCellarUpdateItmesAdapter(Context context) {
        this.mContext = context;
        groupList = new ArrayList<String>();
        itemList = new ArrayList<List<String>>();
        initData();
    }
  
    private void initData() {
        for (int i = 0; i < groups.length; i++) {
            groupList.add(groups[i]);
        }
        List<String> item1 = new ArrayList<String>();
        for (int i = 0; i < familis.length; i++) {
            item1.add(familis[i]);
        }
  
        List<String> item2 = new ArrayList<String>();
        for (int i = 0; i < friends.length; i++) {
            item2.add(friends[i]);
        }
  
        List<String> item3 = new ArrayList<String>();
        for (int i = 0; i < colleagues.length; i++) {
            item3.add(colleagues[i]);
        }
  
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
    }
  
    public boolean areAllItemsEnabled() {
        return false;
    }
  
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).get(childPosition);
    }
  
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
  
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        TextView text = null;
        if (convertView == null) {
            text = new TextView(mContext);
        } else {
            text = (TextView) convertView;
        }
        
        String name = (String) itemList.get(groupPosition).get(childPosition);
        
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 40);
        text.setLayoutParams(lp);
        text.setTextSize(18);
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        text.setPadding(45, 0, 0, 0);
        text.setText(name);
        return text;
    }
  
    
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).size();
    }
  
    
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }
  
   
    public int getGroupCount() {
        return groupList.size();
    }
  
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
  
  
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        TextView text = null;
        if (convertView == null) {
            text = new TextView(mContext);
        } else {
            text = (TextView) convertView;
        }
        String name = (String) groupList.get(groupPosition);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 40);
        text.setLayoutParams(lp);
        text.setTextSize(18);
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        text.setPadding(36, 0, 0, 0);
        text.setText(name);
        return text;
    }
  
    
    public boolean isEmpty() {
        return false;
    }
  
    public void onGroupCollapsed(int groupPosition) {
  
    }
  
    public void onGroupExpanded(int groupPosition) {
  
    }
  
    /*
     * Indicates whether the child and group IDs are stable across changes to
     * the underlying data.
     */
    public boolean hasStableIds() {
        return false;
    }
  
    /*
     * Whether the child at the specified position is selectable.
     */
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}