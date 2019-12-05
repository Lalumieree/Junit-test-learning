package simulate;

/**
 * A car simulation thread. Simulating starting or stopping car engine
 * accelerating and braking as well as running on cruise control 
 */

public class CarSimulator implements Runnable {

    private boolean ignition = false;     //engine off
    private double throttle = 0.0;        //throttle setting 0 .. 10, controlling the amount of gaz getting to the engine
    private int speed = 0;                //speed 0 .. 120 mph!, actual simulated car speed
    private int distance = 0;             //the crossed distance since starting the engine 
    private int brakepedal = 0;           //brake setting 0..10, allowing different levels of braking
    Thread engine = null;				  //car engine, null when the car is stopped 
	
    final static int maxSpeed = 120;
    final static double maxThrottle = 10.0;
    final static int maxBrake = 10;
    final static double airResistance = 12.5;  //inverse air resistance factor
    final static int ticksPerSecond = 5;

	// -----------
	// Constructor
	// -----------    
    public CarSimulator() {
        super();
    }

    /**
     * Starting the car engine. It creates a thread for the engine.
     */
    public synchronized void  engineOn(){
        ignition = true;
        if (engine==null) {
            engine = new Thread(this);
            engine.start();
        }
    }

    /**
     * Stopping the car engine. Resets all parameters.
     */
    public synchronized void  engineOff() {
        ignition = false;
        speed=0;
        distance=0;
        throttle=0;
        brakepedal=0;
        engine=null;
    }

    /**
     * When the engine is started, the throttle setting is incremented 
     * by 5 if not already at its maximum otherwise it is set to the maximum 
     * throttle value.
     */
    public synchronized void accelerate() {
        if (engine != null) {
        	if (brakepedal>0) brakepedal=0;
           	if (throttle<(maxThrottle-5))
               	throttle +=5.0;
           	else
            	throttle=maxThrottle;
        }
	}

    /**
     * When the engine is started, the brake setting is incremented 
     * by 1 if not already at its maximum otherwise it is set to the maximum 
     * braking value.
     */    
    public synchronized void brake() {
        if (engine != null) {
       		if (throttle>0) throttle=0.0;
       		if (brakepedal<(maxBrake-1))
                brakepedal +=1;
       		else
                brakepedal=maxBrake;
        }
    }

    /**
     * When the engine is started, the engine thread runs until it
     * is turned off. While running, speed and distance are
     * updated every 200 milliseconds
     *
     */
    public void run(){
      try {
        double fdist=0.0;
        double fspeed=0.0;
        while (engine!=null) {
            synchronized(this) {
            	//updating the speed based on the actual speed, throttle and brakepedal values
                fspeed = fspeed+((throttle - fspeed/airResistance - 2*brakepedal))/ticksPerSecond;
                if (fspeed>maxSpeed) fspeed=maxSpeed;
                if (fspeed<0) fspeed=0;
                //updating the distance based on the calculated speed
                fdist = fdist + (fspeed/36.0)/ticksPerSecond;
                speed = (int)fspeed;
                distance=(int)fdist;
                //adjusting throttle value to account for decay;
                if (throttle>0.0) throttle-=0.5/ticksPerSecond; if (throttle<0.0) throttle=0;
            }
            Thread.sleep(1000/ticksPerSecond);
        }
      } catch (InterruptedException e) {
      		System.out.println("Interrupted Exception caught.");
      }
    }

    /**
     * The controller set the throttle value while the cruise control is on
     * to adjust the car speed
     */
    public synchronized void setThrottle(double val) {
        throttle=val;
        if (throttle<0.0) throttle=0.0;
        if (throttle>10.0) throttle=10.0;
        brakepedal=0;
    }

    /**
     * @return current car speed
     */
    public synchronized int getSpeed() {
        return speed;
    }

    /**
     * @return current ignition state - true if the car is started, false otherwise
     */
    public synchronized boolean getIgnition() {
      return ignition;
    }
 
    /**
     * @return throttle level
     */
    public synchronized double getThrottle() {
      return throttle;
    }

    /**
     * @return current brakepedal level
     */
    public synchronized int getBrakepedal() {
      return brakepedal;
    }
    
    /**
     * @return current distance
     */   
    public synchronized int getDistance() {
    	return distance;
    }

}
