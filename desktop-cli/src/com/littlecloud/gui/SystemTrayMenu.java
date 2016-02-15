/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package com.littlecloud.gui;
/*
 * TrayIconDemo.java
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.util.logging.Logger;

public class SystemTrayMenu {
    /*
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    */
    public static void createTrayMenu() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            LOGGER.warning("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/com/littlecloud/gui/images/bulb.gif", "Little cloud"));
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem syncItem = new MenuItem("Synchronize");
        CheckboxMenuItem encoding = new CheckboxMenuItem("Client-side encoding");
        MenuItem userItem = new MenuItem("Change user");
        MenuItem pathItem = new MenuItem("Change path");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(syncItem);
        popup.addSeparator();
        popup.add(encoding);
        popup.addSeparator();
        popup.add(userItem);
        popup.add(pathItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            LOGGER.severe("TrayIcon could not be added.");
            return;
        }

        trayIcon.setImageAutoSize(true);

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });

        pathItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
    		JFileChooser chooser = new JFileChooser();
    		chooser.setCurrentDirectory(new java.io.File("."));
    		chooser.setDialogTitle("Choose directory");
    		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    		chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				LOGGER.finer("getCurrentDirectory(): " + chooser.getCurrentDirectory());
				LOGGER.finer("getSelectedFile() : " + chooser.getSelectedFile());
			} else {
				LOGGER.fine("No Selection");
            }
        });

        encoding.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    //
                } else {
                   // trayIcon.setImageAutoSize(false);
                }
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path, String description) {

        System.out.println(new File(".").getAbsolutePath());

        URL imageURL = TrayIcon.class.getResource(path);
        
        if (imageURL == null) {
            LOGGER.severe("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

	private static Logger LOGGER = Logger.getLogger(SystemTrayMenu.class.getName());
}
