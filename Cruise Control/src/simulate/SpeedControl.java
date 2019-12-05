package simulate;

/**
 * This class is used by the Controller class to control
 * car speed in CarSimulator when cruising is on. It has a thread 
 * that runs while cruising is enabled, it monitors the car speed
 * and adjust its throttle accordingly to maintain the speed 
 * selected for cruising 
 */

 class SpeedControl implements Runnable {
  	final static int DISABLED = 0; //speed control states
  	final static int ENABLED  = 1;
  	private int state = DISABLED;  //initial state
  	private int setSpeed = 0;      //target cruise control speed
  	private Thread speedController;
  	private CarSimulator cs;           //interface to control speed of engine

	// -----------
	// Constructor
	// ----------- 
	/**
	 * constructs a speed controller for the car simulator specified
	 * in parameters
	 * 
	 * @param cs - interface of the car simulator to control its speed
	 */
  	SpeedControl(CarSimulator cs){
    	this.cs=cs; 
  	}

	/**
	 * Sets the target cruise control speed to the car current speed
	 */
  	synchronized void recordSpeed(){
    	setSpeed=cs.getSpeed(); 
  	}

	/**
	 * It clears the target cruise control speed.
	 * It is used by Controller to reset target cruise control speed
	 * when the car engine is first started (before enabling cruising)
	 */
  	synchronized void clearSpeed(){
    	if (state==DISABLED) {
    		setSpeed=0;
    	}
  	}

	/**
	 * Enables cruise control
	 */
  	synchronized void enableControl(){
    	if (state==DISABLED) {
      		speedController= new Thread(this);
      		speedController.start();
      		state=ENABLED;
    	}
  	}

	/**
	 * Disables cruise control
	 */
  	synchronized void disableControl(){
    	if (state==ENABLED)  {
    		state=DISABLED;
    	}
  	}

	/**
	 * The speed controller thread is started when cruising enabled.
	 * It runs until the cruise control is disabled.
	 * While running, the speed controller verifies every 0.5 second
	 * the current car speed and adjusts the car throttle based on 
	 * the current car speed and the target cruise control speed
	 */
  	public void run() {     // the speed controller thread
    	try {
      		while (state==ENABLED) {
        		if (state==ENABLED) synchronized(this) {
          			double error = (float)(setSpeed-cs.getSpeed())/6.0;
          			double steady = (double)setSpeed/12.0;
          			cs.setThrottle(steady+error); //simplified feed back control
        		}
        		Thread.sleep(500);
      		}
    	} catch (InterruptedException e) {}
    	speedController=null;
  	}

  	/**
  	 * @return state - cruise control enabled (1) or disabled (0)
  	 */
  	public int getState(){
	  	return state;
  	}
  	
  	/**
  	 * @return the target cruise speed
  	 */
  	public int getCruiseSpeed() {
  		return setSpeed;
  	}
}





