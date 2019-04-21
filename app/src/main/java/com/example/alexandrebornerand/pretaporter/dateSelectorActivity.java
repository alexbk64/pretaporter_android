package com.example.alexandrebornerand.pretaporter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;

/**
 * ref
 * https://github.com/square/android-times-square
 */
public class dateSelectorActivity extends AppCompatActivity {
    final private String TAG = "dateSelectorActivity";

    private CalendarPickerView datePicker;
    private Product product;
    private SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selector);

        //Toolbar imp
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        //get today's date
        Date today = new Date();
        //get today's date next year
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        //get product passed from parent activity
        product = (Product) getIntent().getExtras().get("product");
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //initialise the CalendarPickerView (calendar)
        datePicker = (CalendarPickerView) findViewById(R.id.calendar_view);
        //set minimum and max dates, initially selected date, and set to range selector mode
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(getNextAvailableDay())
                .inMode(RANGE);
        //highlightDates(ArrayList<Date>) *takes an arraylist of date objects. create method to pass it these dates
        datePicker.highlightDates(getHighlightedDays());
        //disable dates on which item is unavailable, or impossible dates (i.e. past)
        datePicker.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                return isSelectable(date);
            }
        });
//        datePicker.setOnInvalidDateSelectedListener(null);
//        datePicker.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
//            @Override
//            public void onInvalidDateSelected(Date date) {
//
//            }
//        });


//        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(Date date) {
//                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
//                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();
//
//                Calendar calSelected = Calendar.getInstance();
//                calSelected.setTime(date);
//
//                //return date to productPageActivity
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result",selectedDate);
//                setResult(Activity.RESULT_OK,returnIntent);
//                finish();
//            }
//
//            @Override
//            public void onDateUnselected(Date date) {
//
//            }
//        });
    }//END onCreate

    private Date getNextAvailableDay() {
        //sets dateToReturn to today
        Date dateToReturn = new Date();

        Calendar cal = Calendar.getInstance();
        //if today is not available, see next day and repeat
        for (Date date : getHighlightedDays()) {
            if (sameDay(dateToReturn, date)) {
                cal.setTime(dateToReturn);
                cal.add(Calendar.DATE, 1);
                dateToReturn = cal.getTime();
            }
        }


        return dateToReturn;
    }

    /*
    Ref https://stackoverflow.com/questions/2517709/comparing-two-java-util-dates-to-see-if-they-are-in-the-same-day
     */

    private boolean sameDay(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        return (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_date_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
//            case R.id.action_settings:
//                return true;
            case R.id.action_next:
                ///will come back to bite you in the... lets see what the error is. probably nullpointerex
                final int minimumRange = product.getMinimumNumberOfDays();
                ArrayList<Date> selectedDates = (ArrayList<Date>) datePicker.getSelectedDates();
                //date range is invalid
                if (!validateDateRange(selectedDates)) {
                    //if date range includes unavailable date, make toast and prompt user to choose another date range
                    Toast.makeText(dateSelectorActivity.this, "Please select a valid date range",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
                //if date range below user's predefined minimum
                if (selectedDates.size() < minimumRange) {
                    Toast.makeText(dateSelectorActivity.this, product.getLister().getFirst_name() + " asks for a minimum of " + minimumRange + " days.",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
                //if two checks above are passed, do below
                Toast.makeText(dateSelectorActivity.this, selectedDates.toString(),
                        Toast.LENGTH_LONG).show();
                returnDateRange(selectedDates);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateDateRange(ArrayList<Date> selectedDates) {
        if (product.getDatesUnavailable()!=null) {
            for (String stringDate : product.getDatesUnavailable()) {
                try {
                    Date temp = simpleDateFormat.parse(stringDate);
                    if (selectedDates.contains(temp))
                        return false;
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
            return true;
        }
        return true;
    }

    private ArrayList<Date> getHighlightedDays() {
        ArrayList<Date> highlightedDays = new ArrayList<>();
        Date today = new Date();
        if (product.getDatesUnavailable() != null) {
            for (String prevDate : product.getDatesUnavailable()) {
//            String dateInString = "21-04-2019";
                Date date = null;
                try {
                    date = simpleDateFormat.parse(prevDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //if date is in the past, dont add it to highlightedDays
                if(!date.before(today) || sameDay(date, today))
                    highlightedDays.add(date);
            }
        }
        return highlightedDays;
    }

    private void returnDateRange(ArrayList<Date> selectedDateRange) {
        //convert ArrayList<Date> to ArrayList<String> and return this to previous activity
        //in onActivityResult in previous activity will need to reconvert to ArrayList<Date>
        ArrayList<String> dates = new ArrayList<>();
        if (product.getDatesUnavailable() == null)
            product.setDatesUnavailable(new ArrayList<String>());
        if (product.getDatesUnavailable() != null) {
            for (Date date : selectedDateRange) {
                try {
                    dates.add(simpleDateFormat.format(date));
                } catch (Exception e) {
                    Log.d(TAG, "returnDateRange: ");
                    e.printStackTrace();
                }
            }
        }
//        return date to productPageActivity
        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", selectedDateRange);
        returnIntent.putExtra("result", dates);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private boolean isSelectable(Date date) {
        //if date has previously been selected, return false. Else return true;
        //first parse date into string
        String dateString = simpleDateFormat.format(date);
        if (product.getDatesUnavailable() != null) {
            for (String previousDate : product.getDatesUnavailable()) {
                if (previousDate.equals(dateString))
                    return false;
            }
        }
        return true;
    }

}
