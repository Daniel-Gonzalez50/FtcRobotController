package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class UseString extends OpMode {
    @Override
    public void init(){
        String myName = "Daniel Gonzalez";
        int grade = 9

        telemetry.addData("Hello", myName);
        telemetry.addData("Grade Level", grade);
    }

    @Override
    public void loop(){

    }
}
