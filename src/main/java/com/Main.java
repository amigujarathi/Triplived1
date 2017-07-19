package com;

import java.util.ArrayList;
import java.util.Random;

import com.gcm.ClientMessage;
import com.gcm.ClientMessageType;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

public class Main {

	//final static String GOOGLE_API_KEY ="AIzaSyCoYdjw-WeKohWpLNM7XmwfK3XXEkqQaoE";
	//final static String GOOGLE_API_KEY ="AIzaSyACK6ZCcSdYDr8h-nJSyHmPwqQpBfhOPhE";
	final static String GOOGLE_API_KEY ="AIzaSyDyjJQ68kuPg5-AcjjlscdquN_tygNFzbM";
	
	
	public static void main(String[] args) {
		
		try {
 
            
            Sender sender = new Sender(GOOGLE_API_KEY);

            ArrayList<String> devicesList = new ArrayList<String>();
          //  devicesList.add("fI-Y18YYIT0:APA91bHANOSevOpHIeWN0lxJOAxE0YUI9rb6dZnJi0AU6ZR_k3Yklwg0vU7UpNCraInkekkGysFX_4F07N1Ie9XUgxPL041nm8dVrr_-TjwbOcHQQLu-_FNUrB0Hdlxdlez_tBwyk6v4");
            //santosh
            /*
             * 
           devicesList.add("dwIrTvonoVc:APA91bHPKskeaT9aZxfA5QyaBIM7kTzQ__Ajpi0GHs4n-s9c7tDw4oiMlMr8Srnqw-RjbgFYKZx83gylxUVD5mHHlQXcmUKUiOPxau8cLsEpZp4V2NC7LZDfOv8qh6n6AgUItQJc9cg9");
           devicesList.add("ehD0Hh2FDgY:APA91bFbLdObigWupr9JY1bj43gcQUs8_jYy1ffBQXka3EFLg6IslM-OGrMV2PvqipvxDuXlGJzYaX9s__5fgMO4QvpzZPmYrA6UTwMQR1cGYQ4qPk9VlKbVtBZa4nC7YWyyKmI1KcAs");
          //  				 
            
            devicesList.add("dxHkN_cjiBg:APA91bFjHhhcjodjyigCW2dAkkRxpj1zOejTpzmx_W9Ur7fgNITGe9COklmpRtK-VFfrx4U-Cy54S_UnpGtvTK3oBW_Y5d2nGxGgB-YUrUEy1JI3NYa4b3iBaGAZ4nwjDekgFS2i9Vw-");
            devicesList.add("eD-afFygcIw:APA91bGtgCJx_hSiS8XHRKBWDlPfRwDGuq_VvzFR7V3s9tHR5hxvpnQSHov7u1DasxMP3ZxgDc2v2bKDmuPkoA4s23vt5jZ8KpIYE2hoXzeOfJPmaFvl9rki_OQWWhfCvrM0cfyGDGeZ")*/;
            
            
            //ROHIT
          //  devicesList.add("cVIzqYiOCKE:APA91bFdN9dkBa15cyFg4OsWNJ0phkKaRaHKSyB0UWTNaBkocLsx24ByIq7AJd6iIagWNR5rQNR_hN9PfHQDV9qazleYjd8crd3uQPYIuvfGXF-c5W9CRNFVR785xcRNLlZowfg-2wuh");
            //mayank
            devicesList.add("cfPriRgL3do:APA91bE-NUeGYJZaYXhWi1hfX0k51YQ2HFyCgMZEbn7JH1x2F_NtmGYfhoIWQNOZO2g8y-cT5ncRSEo_QPmeoDxAhOsOTYe9d_Pn_Ry2o0cUhfU-tObdvOkwfFZr1v45SgLyJ8KHCUEY");
            devicesList.add("eV7K9AzBIts:APA91bEuB5D_9MEyEtj8Fs5H2V1-NZrGqye5CF0dEqvZgESe4XurA5ZeQnopznQkmTUKGRKUAIJUmud_0cdteyOXvGtD_-2jyNLC-cq9aHUdFiGsTHJD0JbLa8HCF_TFhSQIMgBNqiiK");
            
            
            ClientMessage clientMessage = new ClientMessage();
            clientMessage.setType(ClientMessageType.TIMELINE);
            clientMessage.setTitle("TripLived ");
            clientMessage.setBody("How is your Day");
           // clientMessage.setImageUrl("http://104.238.103.233/static/r/airport.jpg");
            
            // use this line to send message with payload data
            Message message = new Message.Builder()
                    .collapseKey("6")
                    .timeToLive(3000) // time in second
                    .delayWhileIdle(true)  //wait for device to become active
                    .addData("message", new Gson().toJson(clientMessage))
                    .addData("id", "ayan")
                    .build();

            // Use this for multicast messages
            
            MulticastResult result = sender.send(message, devicesList, 1);
            sender.send(message, devicesList, 1);
            

            System.out.println(result.toString());
            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {
                }
            } else {
                int error = result.getFailure();
                System.out.println(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	
}
