package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

public class Break extends Command {
    private GameWorld gw;

    public Break(GameWorld gw){
        super("Break");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e){
        gw.decreaseSpeed();
    }
}
