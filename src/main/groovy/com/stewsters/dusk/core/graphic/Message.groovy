package com.stewsters.dusk.core.graphic

import com.stewsters.dusk.core.entity.Entity
import squidpony.squidcolor.SColor

public class Message {

    public String message;
    public SColor color;
    public List<Entity> concerning;


    public Message(Map params) {
        message = params.message;
        color = params.color;
        concerning = params.concerning
    }


}