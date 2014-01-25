#ifndef ROBOT_STRAP
#define ROBOT_STRAP

/**
 * This is the base class
 */ 
class RobotStrap : public SimpleRobot
{
	private:
	
	RobotDrive myRobot; // robot drive system
	Joystick stick; // only joystick

	public:
		
		/* Constructor */
		RobotStrap();
		~RobotStrap();
		
		/* Member functions */
		void Test( void );
		void Autonomous( void );
		void OperatorControl( void );

};

#endif /* ROBOT_STRAP */