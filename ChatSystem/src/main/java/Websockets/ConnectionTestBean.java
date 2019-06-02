/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Websockets;

import Models.Kweet;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.*;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;


@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ConnectionTestBean {

    ObjectMapper mapper = new ObjectMapper();

    @EJB
    private ConnectionTestBean delegate;

    @Asynchronous
    public void send(Session session, Kweet message, int repeats, long delay, double delayMultiplier ){
        try {
            synchronized(session){
                session.getBasicRemote().sendObject(mapper.writeValueAsString(message));
            }
            Thread.sleep(delay);
        } catch (InterruptedException | IOException | EncodeException ex) {
            throw new IllegalStateException(ex);
        }
        if(1<repeats){
            delegate.send(
                session,
                message,
                repeats-1,
                Math.round(delay*delayMultiplier),
                delayMultiplier
            );
        }

    }
}
