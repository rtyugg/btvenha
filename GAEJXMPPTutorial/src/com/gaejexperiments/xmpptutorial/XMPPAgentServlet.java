package com.gaejexperiments.xmpptutorial;

import com.google.appengine.api.xmpp.*;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class XMPPAgentServlet  extends HttpServlet {
	public static final Logger _log =
			Logger.getLogger(XMPPAgentServlet.class.getName());
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
			{
				
		try {
			String strStatus = "";
			XMPPService xmpp = XMPPServiceFactory.getXMPPService();
			//STEP 2
			Message msg = xmpp.parseMessage(req);
			JID fromJid = msg.getFromJid();
			String body = msg.getBody();
			_log.info("Received a message from " + fromJid + " and body = " +
			body);
			//STEP 3
			String msgBody = "You sent me : " + body;
			Message replyMessage = new MessageBuilder()
			.withRecipientJids(fromJid)
			.withBody(msgBody)
			.build();
			//STEP 4
			boolean messageSent = false;
			if (xmpp.getPresence(fromJid).isAvailable()) {
			SendResponse status = xmpp.sendMessage(replyMessage);
			messageSent = (status.getStatusMap().get(fromJid) ==
			SendResponse.Status.SUCCESS);
			}
			//STEP 5
			if (messageSent) {
			strStatus = "Message has been sent successfully";
			}
			else {
			strStatus = "Message could not be sent";
			}
			_log.info(strStatus);
			} catch (Exception e) {
			_log.log(Level.SEVERE,e.getMessage());
			}
			}
			}