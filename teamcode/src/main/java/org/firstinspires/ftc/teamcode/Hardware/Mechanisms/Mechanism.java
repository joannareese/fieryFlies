package org.firstinspires.ftc.teamcode.Hardware.Mechanisms;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.openftc.revextensions2.RevBulkData;

public abstract class Mechanism {
    public Robot bot;
    public RevBulkData bulk;
    public Mechanism(Robot r){
        bot = r;
        bulk = bot.bulkData;
    }

}
