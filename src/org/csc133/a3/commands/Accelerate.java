package org.csc133.a3.commands;

import com.codename1.ui.Command;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

public class Accelerate extends Command {
    private GameWorld gw;

    public Accelerate(GameWorld gw){
        super("Accel");
        this.gw =gw;
    }

    public void actionPerformed(ActionEvent e){
        gw.increaseSpeed();
    }

}
