package com.example.alexandrebornerand.pretaporter;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentController;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatDelegate;

import com.example.alexandrebornerand.pretaporter.Model.Product;
import com.squareup.timessquare.CalendarPickerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.when;

public class dateSelectorActivityTest {
    @Mock
    CalendarPickerView datePicker;
    @Mock
    Product product;
    @Mock
    SimpleDateFormat simpleDateFormat;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    Handler mHandler;
    @Mock
    FragmentController mFragments;
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    SimpleArrayMap<Class<? extends SupportActivity.ExtraData>, SupportActivity.ExtraData> mExtraDataMap;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    @InjectMocks
    com.example.alexandrebornerand.pretaporter.dateSelectorActivity dateSelectorActivity;

    @Mock
    ArrayList<String> mockArrayList;

    private Calendar cal;
    private Calendar cal1;
    private Date today;
    private Date tomorrow;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        cal = Calendar.getInstance();
        cal1 = Calendar.getInstance();
        today = new Date();
        cal1.setTime(today);
        cal1.add(Calendar.DATE, 1);
        tomorrow = cal1.getTime();
    }

    @Test
    public void testGetNextAvailableDay() throws Exception {

        when(product.getDatesUnavailable()).thenReturn(new ArrayList<String>(Arrays.asList(new String[0])));

        Date result = dateSelectorActivity.getNextAvailableDay();
        Assert.assertEquals(simpleDateFormat.format(new Date()), simpleDateFormat.format(result));
    }

    @Test
    public void testSameDay() throws Exception {
        boolean result = dateSelectorActivity.sameDay(new GregorianCalendar(2019, Calendar.APRIL, 23, 8, 44).getTime(), new GregorianCalendar(2019, Calendar.APRIL, 23, 7, 44).getTime());
        Assert.assertEquals(true, result);
    }
    @Test
    public void testNotSameDay() throws Exception {
        boolean result = dateSelectorActivity.sameDay(new GregorianCalendar(2019, Calendar.APRIL, 26, 8, 44).getTime(), new GregorianCalendar(2019, Calendar.APRIL, 23, 7, 44).getTime());
        Assert.assertEquals(false, result);
    }



    @Test
    public void testValidateDateRange() throws Exception {
        when(product.getDatesUnavailable()).thenReturn(new ArrayList<String>(Arrays.asList("String")));

        boolean result = dateSelectorActivity.validateDateRange(new ArrayList<Date>(Arrays.asList(new GregorianCalendar(2019, Calendar.APRIL, 23, 7, 44).getTime())));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testGetHighlightedDays() throws Exception {
        cal.setTime(today);

        when(mockArrayList.get(0)).thenReturn(simpleDateFormat.format(today));
        when(mockArrayList.get(1)).thenReturn(simpleDateFormat.format(tomorrow));
        product.setDatesUnavailable(mockArrayList);
        ArrayList<Date> expected = new ArrayList<>();
        for (int i=0; i<mockArrayList.size(); i++) {
            expected.add(simpleDateFormat.parse(mockArrayList.get(i)));
        }
        ArrayList<Date> result = dateSelectorActivity.getHighlightedDays();
        Assert.assertEquals(expected, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme