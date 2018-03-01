package com.winway.android.ewidgets.dialog.table;

import com.winway.android.ewidgets.R;

import android.content.Context;
import android.graphics.Color;  
import android.view.Gravity;  
import android.widget.TableRow;  
import android.widget.TextView;  
  
public class AddHeadRow extends AddRow {  
  
  
    private String[] header;  
    private int textColor = Color.BLACK;  
    private int textLenghtMax = 10;          //定于每一行最多显示几个字  
    private int cellBackgroundColor = Color.WHITE;  
      
    public AddHeadRow(Context context,String[] header) {  
        super(context);  
          
        this.context = context;  
        this.header = header;  
    }  
    /* 
     * 设置字体的颜色 
     */  
    public void setTextClor(int color){  
        this.textColor = color;  
    }  
    /* 
     * 设置每一行的的字符数 
     */  
    public void setTextMaxEms(int lenght){  
        this.textLenghtMax = lenght;  
    }  
    /* 
     * 设置表格的背景颜色 默认为白色 
     */  
    public  void setBackgroundColor(int color){  
        this.cellBackgroundColor = color;  
    }  
    @Override  
    public TableRow addTableRow(){  
        TableRow tableRow = new TableRow(context);  
//        tableRow.addView(addDivision());           //添加分隔符号  
        tableRow.setLayoutParams(params);  
        for(int i = 0;i<header.length;i++){  
            TextView textView = new TextView(context);        
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,  
                    TableRow.LayoutParams.MATCH_PARENT);  
//            params.span=2;
//            params.column=2;
            textView.setLayoutParams(params);  
            textView.setGravity(Gravity.CENTER|Gravity.CENTER);  //文本居中显示         
            textView.setTextColor(textColor); //设置文本颜色  
            textView.setSingleLine(false);  
            textView.setMaxEms(textLenghtMax);  
            textView.setBackgroundColor(cellBackgroundColor); //设施背景颜色   
            textView.setText(header[i]); 
            textView.setPadding(15, 15, 15, 15);
            textView.setBackgroundResource(R.drawable.border);
//            textView.setTextAppearance(context, R.style.textview_allgrade_content);
            tableRow.addView(textView);  
//            tableRow.addView(addDivision());  
        }  
        return tableRow;  
    }  
}  
