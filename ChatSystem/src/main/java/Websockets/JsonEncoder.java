/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encodes {@link Message}s to JSON
 * @author jgeenen
 */
public class JsonEncoder implements Encoder.Text<Message> {
    
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(Message arg0) throws EncodeException {
        try {
            return mapper.writeValueAsString(arg0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
