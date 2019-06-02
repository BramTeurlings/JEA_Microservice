/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Websockets;

import java.io.Serializable;

/**
 * Represents a simple message for websockets
 * @author jgeenen
 */
public class Message implements Serializable{
    
    private String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
