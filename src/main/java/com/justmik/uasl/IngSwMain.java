package com.justmik.uasl;

import com.justmik.uasl.controller.Controller;

import java.awt.EventQueue;

public class IngSwMain {


    public static void main(String[] args) {
        EventQueue.invokeLater(Controller::new);
    }

}
