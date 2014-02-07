// Pack this nonsense
package edu.wpi.first.wpilibj.templates;

// Import the nonsense
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;


public class RobotStrap extends SimpleRobot
{
    /* Set up all the objects that we will be using */
    private RobotDrive drivetrain;
    private Joystick leftStick;
    private Joystick rightStick;
    private Jaguar arm;
    private Compressor c;
    private DigitalInput fwdlim;
    private DigitalInput fwdlim;
    
    /* Set up all the constant values */
    // Drive system motor ports
    private final int LEFT_MOTOR_PORT = 1;
    private final int RIGHT_MOTOR_PORT = 2;
    
    // Driver joystick ports
    private final int LEFT_JOYSTICK_PORT = 1;
    private final int RIGHT_JOYSTICK_PORT = 2;
    
    // Arm motor ports
    private final int ARM_MOTOR_PORT = 3;
    
    // Pneumatic system ports
    private final int SPIKE_RELAY_PORT = 1;
    private final int PRESSURE_SWITCH_PORT = 8;
    
    // Limit switch ports
    private final int FWD_LIM_PORT = 10;
    private final int BWD_LIM_PORT = 9;
    
    /* Constructor */
    public RobotStrap()
    {
        // Throw caution to the wind
        getWatchdog().setEnabled(false);
        
        drivetrain = new RobotDrive(LEFT_MOTOR_PORT, RIGHT_MOTOR_PORT);
        leftStick = new Joystick(LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RIGHT_JOYSTICK_PORT);
        arm = new Jaguar(ARM_MOTOR_PORT);
    	c = new Compressor(PRESSURE_SWITCH_PORT, SPIKE_RELAY_PORT);
    	fwdlim = new DigitalInput(FWD_LIM_PORT);
    	bwdlim = new DigitalInput(BWD_LIM_PORT);
    }
    
    /* Autonomous */
    public void autonomous() {
        drivetrain.drive(0.5, 0.0); // drive 50% forward with 0% turn
        Timer.delay(2.0); // wait 2 seconds
        drivetrain.drive(0.0, 0.0); // drive 0% forward, 0% turn (stop)
    }
    
    /* Telo-op */
    public void operatorControl()
    {
    	// Start the auto compressor manager
    	c.start();
        
        while (true && isOperatorControl() && isEnabled())
        {
            
            // Print status if the button is pressed
        	if(rightStick.getRawButton(11))
    			printStatus();
    
    		// Move the arm based on buttons
    		boolean launchValue = rightStick.getRawButton(1);
    		boolean rewindValue = rightStick.getRawButton(2);
    
    		// Determine the speed to throw the arm at
    		double speed = (rightStick.getThrottle() - (-1)) / 2;
    
    		if(launchValue /* && fwdlim.get() */)
    			// make arm launch forwards
    			arm.set(1 * speed);
    		else if(rewindValue /* && bwdlim.get() */)
    			// make the arm launch backwards
    			arm.set(-1 * speed);
    		else
    			arm.set(0);
            
            // tank drive
            drivetrain.tankDrive(leftStick, rightStick);
            
            // delay
            Timer.delay(0.005);
        }
    }
    
    /* Print robot status via driver station API */
    public void printStatus()
    {
        // Get an instance of the driver station to use its API
    	DriverStation ds = DriverStation.getInstance();
         
        // Get driver station LCD
        DriverStationLCD dslcd = DriverStationLCD.getInstance();
        
    	// Get important variable data
    	double voltage = ds.getBatteryVoltage();
    
        // Print the voltage
        dslcd.println(DriverStationLCD.Line.kMain6, 1, "Battery Voltage: " + String.valueOf(voltage));
    }
    
    /* Test function */
    public void test()
    {
        
    }
}
