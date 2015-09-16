package com.littlecloud;

import com.littlecloud.gui.SystemTrayMenu;

public class Main {

    public static void main(String[] args) {
        SystemTrayMenu traymenu = new SystemTrayMenu();
        traymenu.createTrayMenu();
    }
}
