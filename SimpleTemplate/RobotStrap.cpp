#include "WPILib.h"
#include "RobotStrap.h"
#include "constants.h"

/* Constructor */
RobotStrap::RobotStrap() :
	// Initialize all the member objects in the same order
	// declared within the body.
	myRobot(LEFT_MOTOR_PORT, RIGHT_MOTOR_PORT),
	stick(JOYSTICK_PORT)
{
	myRobot.SetExpiration(0.1);
}

/**
 * Runs during test mode
 */
void RobotStrap::Test( void )
{

}

/**
 * Drive left & right motors for 2 seconds then stop
 */
void RobotStrap::Autonomous( void )
{
	myRobot.SetSafetyEnabled(false);
	myRobot.Drive(-0.5, 0.0); 	// drive forwards half speed
	Wait(2.0); 				//    for 2 seconds
	myRobot.Drive(0.0, 0.0); 	// stop robot
}

/**
 * Runs the motors with arcade steering. 
 */
void RobotStrap::OperatorControl( void )
{
	// Get an instance of the driver station to use its API
	DriverStation* ds = DriverStation::GetInstance();
	
	// Set the safety
	myRobot.SetSafetyEnabled(true);

	// Establish arm
	Jaguar* arm = new Jaguar(ARM_MOTOR_PORT);
	
	// Infinite loop
	while (IsOperatorControl())
	{
		/* Portion of code that gets status updates */
		float voltage = ds -> GetBatteryVoltage();
		
		/* Portion of code that controls arm */
		bool launchValue;
		bool rewindValue;
		launchValue = stick.GetRawButton(1);
		rewindValue = stick.GetRawButton(2);
		if(launchValue)
		{
			// make arm launch forwards
			arm -> Set(1);
			
		}
		if(rewindValue)
		{
			// make the arm launch backwards
			arm -> Set(-1);
		}
		
		/* Portion of code that sets up driving */
		myRobot.ArcadeDrive(stick); // drive with arcade style (use right stick)
		Wait(0.005);				// wait for a motor update time
	}
}

START_ROBOT_CLASS(RobotStrap);

