package com.example.loopstr;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.facebook.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedActivity extends Activity {
    private final static String BUSINESS = "com.example.loopstr.BUSINESS";
    private ActionBar actionBar;
    public  ArrayList<BusinessDetails> businessDetails;
    public ListView list;
    String[] catList;
    Spinner spinCat;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        session = Session.getActiveSession();
        
        catList = new String[] { "Entertainment", "Food", "Retail", "Nightlife"};
        spinCat = (Spinner) findViewById(R.id.spinner1);
        
        final ArrayList<String> list1 = new ArrayList<String>();
        for (int i = 0; i < catList.length; ++i) {
          list1.add(catList[i]);
        }
        
        final ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(),R.layout.list_item, list1);
        spinCat.setAdapter(adapter1);
        
        spinCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
            	requeryFb();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        
        /* navigation bar top*/
        actionBar = getActionBar();
        list = (ListView) this.findViewById(R.id.listView1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusinessDetails busDetailItem = businessDetails.get(position);
                Intent intent = new Intent(getBaseContext(), BusinessDetailDialog.class);
                intent.putExtra(BUSINESS, busDetailItem);
                startActivity(intent);
            }
        });

        String grapPath = "search";
        Bundle params = new Bundle();
        params.putString("q", catList[0]);
        params.putString("type", "place");
        params.putString("distance", "1000");
        params.putString("center", "51.89,-8.472");
        
        getBusinessDetails(session, grapPath, params);
    }
    
    public void requeryFb(){
    	Log.e("Here", "hee");
    	
    	Object a = spinCat.getSelectedItemPosition();
    	int catIndex = Integer.parseInt(a.toString());
    	String category = catList[catIndex];
    	
    	String grapPath = "search";
        Bundle params = new Bundle();
        params.putString("q", category);
        params.putString("type", "place");
        params.putString("distance", "1000");
        params.putString("center", "51.89,-8.472");
        
        getBusinessDetails(session, grapPath, params);
    }

    private void getBusinessDetails(Session session, String grapPath, Bundle params) {
        Request request = new Request(session, grapPath, params, HttpMethod.GET, new Request.Callback()
        {
            @Override
            public void onCompleted(Response response)
            {
                if (response != null)
                {
                    JSONObject jsonObject;
                    JSONArray jArray;

                    try {
                        jsonObject = new JSONObject(
                                response.getGraphObject()
                                        .getInnerJSONObject()
                                        .toString());
                        jArray = jsonObject
                                .getJSONArray("data");

                        JSONObject childJSONObject;
                        businessDetails = new ArrayList<BusinessDetails>();
                        try {
                            for (int n = 0; n < jArray.length(); n++) {
                                childJSONObject = jArray.getJSONObject(n);
                                String busNameString = childJSONObject.getString("name");
                                String categoryString = childJSONObject.getString("category");
                                String idString = childJSONObject.getString("id");
                                businessDetails.add(new BusinessDetails(busNameString, categoryString, idString));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("array", businessDetails.toString());
                        MyAdapter adapter = new MyAdapter(getApplicationContext(), businessDetails);
                        list.setAdapter(adapter);

                    } catch (JSONException e) {
                        Log.d("Loopstr", "Error with JSON");
                    }
                }
            }
        });

        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }

    /*
     * 
     * 
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        menu.getItem(0).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.log_out:
                fbLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void fbLogout(){
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(this);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}

