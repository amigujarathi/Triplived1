package com.gcm;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.owlike.genson.Genson;

 

@Controller
@RequestMapping("/sendMessage")
public class GCMServer {
	
	//Change Sender into your GOOGLE API KEY
	//final String GOOGLE_API_KEY = "AIzaSyCPuFogDW0TTxLzO_-6x4u_FSGU418Q8G4";
	//final String GOOGLE_API_KEY ="AIzaSyCoYdjw-WeKohWpLNM7XmwfK3XXEkqQaoE";
	final String GOOGLE_API_KEY ="AIzaSyBQMPYUz42edb0z3G1GISebi0hs0FlBjrg";
	
	@GET
	@RequestMapping(method= RequestMethod.GET , value="/{text}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(@PathParam("text") String text) throws Exception, IOException
	{
		//Genson genson = new Genson();
		//UserDetail user = genson.deserialize(text, UserDetail.class);
		UserDetail user = new UserDetail();
		user.setRegid(text);
		final String regid = user.getRegid();
		new Thread(){
			public void run(){
				try {
					Sender sender = new Sender(GOOGLE_API_KEY);
					Message message = new Message.Builder()
					.collapseKey("message")
					.timeToLive(3)
					.delayWhileIdle(true)
					.addData("message", "Welcome to your Push Notifications")
					.build();
					
					Result result = sender.send(message, regid, 1);
					System.out.println(result.toString());
					
					//Send message to multiple devices
					/*
					ArrayList<String> devicesList = new ArrayList<String>();
					devicesList.add("regID1");
					devicesList.add("regID2");
					MulticastResult multicastResult = sender.send(message, devicesList, 0);
					*/
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		return Response.created(null).build();
	}


}
