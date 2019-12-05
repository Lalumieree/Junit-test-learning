package simulate;

/**
 * This class represents the cruise control. It contains a speed controller
 * which it controls it to monitor and adjust car speed when cruising is 
 * enabled.
 */

class Controller {
	//	cruise controller states
  	final static int INACTIVE = 0; 		//The car engine is inactive (off)
  	final static int ACTIVE   = 1; 		//The car engine is active (on) but cruising is not enabled
  	final static int CRUISING = 2; 		//The car engine is active, and cruising is enabled
  	final static int STANDBY  = 3; 		//The car engine is active, but cruising has been disabled. Cruising can be resumed.
  	private int controlState  = INACTIVE; //represents the current state of the controller
  	private SpeedControl sc;			// The speed controller

	// -----------
	// Constructor
	// -----------
  	Controller(CarSimulator cs)	{
  		sc=new SpeedControl(cs); 
  	}

	/**
	 * Braking would disable cruising.
	 * If the controller state is Cruising then it will be set to StandBy
	 */
   	synchronized void brake(){
    	if (controlState==CRUISING) {
    		sc.disableControl(); 
    		controlState=STANDBY; 
    	}
 	}

	/**
	 * Accelerating would disable cruising.
	 * If the controller state is Cruising then it will be set to StandBy
	 */
	synchronized void accelerator() {
    	if (controlState==CRUISING) {
    		sc.disableControl(); 
    		controlState=STANDBY; 
    	}
  	}

	/**
	 * Turning the car engine off would disable cruising and set 
	 * the controller state to Inactive
	 */
   	synchronized void engineOff(){
    	if (controlState!=INACTIVE) {
      		sc.disableControl();
      		controlState=INACTIVE;
    	}
 	}

	/**
	 * When turning the car engine on, the controller resets the target
	 * cruise control speed. The controller state would be active
	 */
   	synchronized void engineOn(){
    	if (controlState==INACTIVE) {
    		sc.clearSpeed(); 
    		controlState=ACTIVE;
    	}
  	}

	/**
	 * When turning the cruise control on, cruising is enabled. The speed 
	 * controller would set the target cruise control speed and continue
	 * to monitor and adjust car speed 
	 */
   	synchronized void on() {
    	if( controlState!=INACTIVE) {
      		sc.recordSpeed(); 
      		sc.enableControl();
      		controlState=CRUISING;
    	}
  	}

	/**
	 * When turning the cruise control off, cruising is disabled. The
	 * controller becomes in Standby mode, allowing a possible resuming
	 * of the cruise control 
	 */
   	synchronized void off(){
    	if (controlState==CRUISING) {
    		sc.disableControl(); 
    		controlState=STANDBY;
    	}
  	}

	/**
	 * Resuming from a standby mode would enable cruising with the 
	 * same target speed used before disabling cruising 
	 */
   	synchronized void resume() {
    	if(controlState==STANDBY) {
    		sc.enableControl(); 
    		controlState=CRUISING;
    	}
  	}

   	/**
   	 * @return controller state
   	 */
  	public synchronized int getControlState() {
    	return controlState;
  	}

  	/**
  	 * @return speed control state
  	 */
  	public synchronized int getState() {
    	return sc.getState();
  	}
  	
  	/**
  	 * @return speed control object
  	 */
  	public SpeedControl getSpeedControl() {
  		return sc;
  	}
}