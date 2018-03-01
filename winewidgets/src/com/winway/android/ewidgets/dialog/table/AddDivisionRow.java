package com.winway.android.ewidgets.dialog.table;

import android.content.Context;  
import android.graphics.Color;  
import android.view.View;  
import android.widget.TableRow;  
  
public class AddDivisionRow extends AddRow {  
  
    public AddDivisionRow(Context context) {  
        super(context);  
        // TODO Auto-generated constructor stub  
    }  
  
    @Override  
    public TableRow addTableRow() {  
        // TODO Auto-generated method stub  
        TableRow tableRow = new TableRow(context);  
        tableRow.setLayoutParams(params);  
        tableRow.setBackgroundColor(color);  
        View view  = new View(context);       
        TableRow.LayoutParams params1= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, width);  
        view.setLayoutParams(params1);  
        view.setBackgroundColor(Color.RED);  
        tableRow.addView(view);  
        return tableRow;  
    }  
  
      
  
}  