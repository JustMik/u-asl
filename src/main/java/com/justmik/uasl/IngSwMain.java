package it.justmik.dev;

import it.justmik.dev.controller.Controller;

import java.awt.EventQueue;

public class IngSwMain {


    public static void main(String[] args) {
        EventQueue.invokeLater(Controller::new);
    }

}
