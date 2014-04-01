package com.example.loopstr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessDetailDialog extends Activity {
    private final static String BUSINESS = "com.example.loopstr.BUSINESS";
    private TextView busName;
    private TextView busDescription;
    private TextView busHours;
    private TextView busContact;
    private ListView busMessages;
    private ArrayList<BusinessMessage> businessMessagesArray;
    private BusinessDetails businessDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessdetail_dialog);
        Session session = Session.getActiveSession();

        Intent intent = getIntent();
        businessDetails = (BusinessDetails) intent.getSerializableExtra(BUSINESS);

        //busName = (TextView) findViewById(R.id.business_name);
        busDescription = (TextView) findViewById(R.id.business_description);
        busHours = (TextView) findViewById(R.id.business_hours);
        busContact = (TextView) findViewById(R.id.business_contact);
        busMessages = (ListView) this.findViewById(R.id.business_messages);

        assert businessDetails != null;
        String grapPath = businessDetails.getId();

        Bundle params = new Bundle();
        /*params.putString("q", "food");
        params.putString("type", "place");
        params.putString("distance", "1000");
        params.putString("center", "51.89,-8.472");*/

        getBusinessDetailsByID(session, grapPath, params);

        String grapPath2 = businessDetails.getId() + "/feed";
        Bundle params2 = new Bundle();

        getBusinessMessages(session, grapPath2, params2);

//        busName.setText("");
        setTitle(businessDetails.getName());
    }

    private void getBusinessMessages(Session session, String grapPath, Bundle params) {
        Request request = new Request(session, grapPath, params, HttpMethod.GET, new Request.Callback()
        {
            @Override
            public void onCompleted(Response response)
            {
                if (response != null)
                {
                    JSONObject jsonObject;
                    JSONArray jArray;
                    JSONObject jsonHours;

                    try {
                        jsonObject = new JSONObject(
                                response.getGraphObject()
                                        .getInnerJSONObject()
                                        .toString());

                        jArray = jsonObject
                                .getJSONArray("data");

                        JSONObject childJSONObject;
                        businessMessagesArray = new ArrayList<BusinessMessage>();
                        try {
                            for (int n = 0; n < jArray.length(); n++) {
                                childJSONObject = jArray.getJSONObject(n);
                                String busMessage = childJSONObject.getString("message");
                                businessMessagesArray.add(new BusinessMessage(businessDetails.getId(), busMessage));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MessageAdapter adapter = new MessageAdapter(getApplicationContext(), businessMessagesArray);
                        busMessages.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }

    private void getBusinessDetailsByID(Session session, String grapPath, Bundle params){
        Request request = new Request(session, grapPath, params, HttpMethod.GET, new Request.Callback()
        {
            @Override
            public void onCompleted(Response response)
            {
                if (response != null)
                {
                    JSONObject jsonObject;
                    JSONArray jArray;
                    JSONObject jsonHours;

                    try {
                        jsonObject = new JSONObject(
                                response.getGraphObject()
                                        .getInnerJSONObject()
                                        .toString());

                        String busAboutString = jsonObject.getString("about");
                        String busContactString = jsonObject.getString("phone");

                        if (busAboutString.equals("")){
                            busDescription.setText(R.string.insert_description);
                        } else {
                            busDescription.setText(busAboutString);
                        }
                        if (busContactString.equals("")) {
                            busContact.setText(R.string.insert_contact);
                        } else {
                            busContact.setText(busContactString);

                            jsonHours = jsonObject.getJSONObject("hours");

                            String busHoursString = "\nMonday: " + jsonHours.getString("mon_1_open") + " - " + jsonHours.getString("mon_1_close")
                                    + "\n" + "Tuesday: " + jsonHours.getString("tue_1_open") + " - " + jsonHours.getString("tue_1_close")
                                    + "\n" + "Wednesday: " + jsonHours.getString("wed_1_open") + " - " + jsonHours.getString("wed_1_close")
                                    + "\n" + "Thursday: " + jsonHours.getString("thu_1_open") + " - " + jsonHours.getString("thu_1_close")
                                    + "\n" + "Friday: " + jsonHours.getString("fri_1_open") + " - " + jsonHours.getString("fri_1_close")
                                    + "\n" + "Saturday: " + jsonHours.getString("sat_1_open") + " - " + jsonHours.getString("sat_1_close")
                                    + "\n" + "Sunday: " + jsonHours.getString("sun_1_open") + " - " + jsonHours.getString("sun_1_close");
                            busHours.setText(busHoursString);

                            if (busAboutString == null){
                                busDescription.setText(R.string.insert_description);
                            }else{
                                busDescription.setText(busAboutString);
                            }
                            if (busContactString == null) {
                                busContact.setText(R.string.insert_contact);
                            } else {
                                busContact.setText("\nContact: " + busContactString + "\n" + "\nMessages: ");

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }


}
    
