/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Websockets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Decodes {@link Message}s from JSON
 * @author jgeenen
 */
public class JsonDecoder implements Decoder.Text<Message>{

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void init(EndpointConfig config) {

    }
    
    @Override
    public Message decode(String arg0) throws DecodeException {
        try {
            return mapper.readValue(arg0, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }


    @Override
    public void destroy() {

    }
    
}
