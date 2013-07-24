package com.gotja;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class EmailAddressAdapter extends ArrayAdapter<EmailAddressWrapper>{

    Context context; 
    int layoutResourceId;    
    ArrayList<EmailAddressWrapper> data = null;
    
    //store check button state 
    /*
     Create an ArrayList of Boolean Object to store the state of the each CheckBox
Initializes the ArrayList items to default value false, means no CheckBox is checked yet.
When you click on CheckBox. Set a check against Checked/Unchecked state and store that value in ArrayList.
Now set that position to CheckBox using setChecked() method.
     */
    //check:http://stackoverflow.com/questions/4803756/android-cursoradapter-listview-and-checkbox/4804366#4804366
    
    public ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
    
    public EmailAddressAdapter(Context context, int layoutResourceId, ArrayList<EmailAddressWrapper> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        
        for (int i = 0; i < this.getCount(); i++) {
            itemChecked.add(i, false); // initializes all items value with false
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EmailHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new EmailHolder();
            holder.txtName = (TextView)row.findViewById(R.id.nameText);
            holder.txtMail = (TextView)row.findViewById(R.id.emailText);
            holder.ckbox = (CheckBox)row.findViewById(R.id.mailCheck);
            row.setTag(holder);
        }
        else
        {
            holder = (EmailHolder)row.getTag();
        }
        
        EmailAddressWrapper emailAddress = data.get(position);
        holder.txtName.setText(emailAddress.name);
        holder.txtMail.setText(emailAddress.email);

        holder.ckbox.setOnClickListener(new OnClickListener() {

            @Override
			public void onClick(View v) {

                CheckBox cb = (CheckBox) v.findViewById(R.id.mailCheck);

                if (cb.isChecked()) {
                    itemChecked.set(position, true);
                    // do some operations here
                } else if (!cb.isChecked()) {
                    itemChecked.set(position, false);
                    // do some operations here
                }
            }
        });
        holder.ckbox.setChecked(itemChecked.get(position)); 
        // this will Check or Uncheck the
        // CheckBox in ListView
        // according to their original
        // position and CheckBox never
        // loss his State when you
        // Scroll the List Items.
        return row;
    }
    
    static class EmailHolder
    {
        TextView txtName;
        TextView txtMail;
        CheckBox ckbox;
    }
}
