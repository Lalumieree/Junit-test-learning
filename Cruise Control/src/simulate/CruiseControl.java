package simulate;

/**
 * Container of other classes: CarSimulator and Controller.
 * It receives commands and handle them by sending 
 * events to the car simulator and to the controller that controls
 * speed when cruising is on.
 */

public class CruiseControl {

    CarSimulator car;	// The simulated car
    Controller control;	// Controls the car's speed when cruising is on

    // ---------
	// Constructor
	// ---------
    public CruiseControl() {
        car = new CarSimulator();
        control = new Controller(car);
    }

    /**
     * Accepts command inputs and handle them by sending 
     * events to the car simulator and to the controller
     * appropriately.
     * 
     * @param command - the received command as a string
     * @return true if the command is recognized else otherwise
   	 */
   	public boolean handleCommand(String command)
   	{
        if(command.equals("engineOn")) //start the car engine
        {
            car.engineOn();
            control.engineOn();
            return true;
        }
        else if (command.equals("engineOff")) //stop the car engine
        {
            car.engineOff();
            control.engineOff();
            return true;
        }
        else if (command.equals("accelerator")) //accelerate
        {
            car.accelerate();
            control.accelerator();
	        return true;
        }
        else if (command.equals("brake"))	//brake
        {
           	car.brake();
           	control.brake();
	        return true;
        }
        else if (command.equals("on"))	//turn cruise control on
        {
            control.on();
            return true;
        }
        else if (command.equals("off")) //turn cruise control off
        {
            control.off();
            return true;
        }
        else if (command.equals("resume")) //resume cruising
        {
            control.resume();
            return true;
        }
        else
           return false;
    }

	/**
	 * @return the car simulator associated with the cruise control system
	 */
   	public CarSimulator getCar() {
		return car;
	}

   	/**
   	 * @return the controller associated with the cruise control system
   	 */
	public Controller getControl() {
		return control;
	}

}
