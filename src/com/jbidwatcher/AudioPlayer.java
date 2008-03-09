package com.jbidwatcher;
/*
 * Copyright (c) 2000-2007, CyberFOX Software, Inc. All Rights Reserved.
 *
 * Developed by mrs (Morgan Schweers)
 */

import javazoom.jlme.util.*;
import com.jbidwatcher.util.queue.MessageQueue;
import com.jbidwatcher.util.queue.MQFactory;
import com.jbidwatcher.util.config.JConfig;
import com.jbidwatcher.util.config.ErrorManagement;

public class AudioPlayer implements MessageQueue.Listener {
  private AudioPlayer() { }

  public void messageAction(Object deQ) {
    String s=(String)deQ;
    String playme = JConfig.getResource(s).toString();
    ErrorManagement.logMessage("playme = " + playme);
    try {
      Player.playURL(playme);
    } catch(Exception mp3Exception) {
      ErrorManagement.handleException("Failed to play.", mp3Exception);
    }
  }

  public static void start() {
    MQFactory.getConcrete("sfx").registerListener(new AudioPlayer());
  }
}