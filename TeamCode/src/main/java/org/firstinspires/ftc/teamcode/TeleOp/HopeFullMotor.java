package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Motor Test", group = "main")
public class HopeFullMotor extends LinearOpMode {

    DcMotor motorToTest = null;

    public void runOpMode(){
        motorToTest = hardwareMap.dcMotor.get("motorBR");
        motorToTest.setDirection(DcMotorSimple.Direction.FORWARD);
        motorToTest.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorToTest.setPower(0.0);

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){
            motorToTest.setPower(0.5);
        }

    }
}
