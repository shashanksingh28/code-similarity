
interface MileageEfficiency {

    public float getMilesPerGallon();
}

class GasVehicle implements MileageEfficiency {

    private float fuelConsumed;
    private float tripCounter;

    public float getMilesPerGallon() {
        return tripCounter / fuelConsumed;
    }

    public void makeTrip() {
        tripCounter = 100;
        fuelConsumed = 8.5f;
    }
}

class ElectricVehicle implements MileageEfficiency {

    private float kwPowerConsumed;
    private float tripCounter;

    public float getMilesPerGallon() {
        return tripCounter / kwPowerConsumed;
    }

    public void makeTrip() {
        tripCounter = 100;
        kwPowerConsumed = 5.6f;
    }
}

public class TestDrive {

    public static void main(String[] args) {
        GasVehicle gasolineVehicle = new GasVehicle();
        gasolineVehicle.makeTrip();
        System.out.printf(
                "Efficiency of Gas Vehicle (miles/gallon): %.02f%n",
                gasolineVehicle.getMilesPerGallon());
        ElectricVehicle electricVehicle = new ElectricVehicle();
        electricVehicle.makeTrip();
        System.out.printf(
                "Efficiency of Electric Vehicle (miles/kw): %.02f%n",
                electricVehicle.getMilesPerGallon());
    }
}