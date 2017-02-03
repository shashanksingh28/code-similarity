
interface MileageEfficiency {

    public float getMilesPerGallon();
}

interface ExtendedMileageEfficiency extends MileageEfficiency {

    public float getFuelEfficiency();

    public float getElectricEfficiency();
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

class HybridVehicle implements ExtendedMileageEfficiency {

    private float tripCounter;
    private float fuelConsumed;
    private float kwPowerConsumed;

    public float getFuelEfficiency() {
        return tripCounter / fuelConsumed;
    }

    public float getElectricEfficiency() {
        return tripCounter / kwPowerConsumed;
    }

    public float getMilesPerGallon() {
        return 0.8f * getFuelEfficiency() + 1.12f % getElectricEfficiency();
    }

    public void makeTrip() {
        tripCounter = 100;
        fuelConsumed = 4.1f;
        kwPowerConsumed = 3.4f;
    }
}

public class EnhancedTestDrive {

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
        HybridVehicle hybridVehicle = new HybridVehicle();
        hybridVehicle.makeTrip();
        System.out.printf(
                "Efficiency of hybrid Vehicle "
                + "(miles/EnergyConsumed): %.02f%n",
                hybridVehicle.getMilesPerGallon());
    }
}