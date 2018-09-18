package com.ramzcalender.sample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CallLogHelper extends Activity {
	static String[] Months = new String[] {

			"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
	};

	static String fromDate,toDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static Cursor getAllCallLogs(ContentResolver cr) {

		// reading all data in descending order according to DATE
		//String strOrder = android.provider.CallLog.Calls.DURATION + " DESC";
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		Uri callUri = Uri.parse("content://call_log/calls");



		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("MMMM");
		String fromDate1 = format.format(date).toUpperCase();
		Log.d("TAG1",fromDate1);

		if(fromDate1.equals("JANUARY")){
			calendar.set(2018, Calendar.JANUARY, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.JANUARY, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("FEBRUARY")){
			calendar.set(2018, Calendar.FEBRUARY, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.FEBRUARY, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("MARCH")){
			calendar.set(2018, Calendar.MARCH, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.MARCH, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("APRIL")){
			calendar.set(2018, Calendar.APRIL, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.APRIL, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("MAY")){
			calendar.set(2018, Calendar.MAY, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.MAY, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("JUNE")){
			calendar.set(2018, Calendar.JUNE, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.JUNE, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("JULY")){
			calendar.set(2018, Calendar.JULY, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.JULY, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("AUGUST")){
			calendar.set(2018, Calendar.AUGUST, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.AUGUST, 31);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("SEPTEMBER")){
			calendar.set(2018, Calendar.SEPTEMBER, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.SEPTEMBER, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("OCTOBER")){
			calendar.set(2018, Calendar.OCTOBER, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.OCTOBER, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("NOVEMBER")){
			calendar.set(2018, Calendar.NOVEMBER, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.NOVEMBER, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}else if(fromDate1.equals("DECEMBER")){
			calendar.set(2018, Calendar.DECEMBER, 1);
			fromDate = String.valueOf(calendar.getTimeInMillis());
			calendar.set(2018, Calendar.DECEMBER, 30);
			toDate = String.valueOf(calendar.getTimeInMillis());
		}
		Log.d("TAG1",toDate);
		/*calendar.set(2018, Calendar.AUGUST, 1);
		fromDate = String.valueOf(calendar.getTimeInMillis());
		calendar.set(2018, Calendar.MONTH, 30);
		toDate = String.valueOf(calendar.getTimeInMillis());*/
		/*Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		Date date = calendar1.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		//SimpleDateFormat format = new SimpleDateFormat("yyyy MMMM dd");
		String fromDate = format.format(date);

		Date myDate = new Date();
		String toDate = format.format(myDate);
		Log.d("TAG1",fromDate);
		Log.d("TAG1",toDate);
*/
		String[] whereValue = {fromDate,toDate};


		Cursor curCallLogs = cr.query(callUri, null, android.provider.CallLog.Calls.DATE+" BETWEEN ? AND ?", whereValue, strOrder);

	//	Cursor curCallLogs = cr.query(callUri, null, null, null, strOrder);

		return curCallLogs;
	}

	public static void insertPlaceholderCall(ContentResolver contentResolver,
											 String name, String number) {
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, number);
		values.put(CallLog.Calls.DATE, System.currentTimeMillis());
		values.put(CallLog.Calls.DURATION, 0);
		values.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
		values.put(CallLog.Calls.NEW, 1);
		values.put(CallLog.Calls.CACHED_NAME, name);
		values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
		values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");
		Log.d("Call Log", "Inserting call log placeholder for " + number);
		contentResolver.insert(CallLog.Calls.CONTENT_URI, values);
	}

}
