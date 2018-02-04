package com.huawei.www;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * com.huawei.www.DateTransform
 * DateTransform
 */
public class DateTransform extends UDF {

    public Text evaluate(Text datestr){
        if(datestr==null){
            return null;
        }
        String data=datestr.toString().trim();
        if(data.length()==0){
            return null;
        }
        String s =clean(data);
        return new Text(s);
    }

    private String clean(String data) {
        String s = data.replaceAll("\"", "");
        SimpleDateFormat out=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat in=new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",Locale.ENGLISH);
        Date parse = null;
        try {
            parse = in.parse(s);//先提取日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out.format(parse);//再解析成字符串

    }

    /**
     * 31/Aug/2015:00:04:37
     * @param args
     */
    public static void main(String[] args) throws ParseException {

        Text s=new DateTransform().evaluate(new Text("\"31/Aug/2015:00:04:37\""));
        System.out.println(s);
    }

}
