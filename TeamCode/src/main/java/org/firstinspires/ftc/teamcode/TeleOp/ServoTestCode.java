package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Test", group = "main")

public class ServoTestCode extends LinearOpMode {

    Servo servoToTest = null;

    public void runOpMode(){
        servoToTest = hardwareMap.servo.get("servo");
        servoToTest.setPosition(0.0);
        servoToTest.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.a){
                servoToTest.setPosition(360);
            }

        }
    }
}
