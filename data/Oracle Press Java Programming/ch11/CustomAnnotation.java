
public class CustomAnnotation {

    @WorkInProgress
    @Task(description = "Implement tax computations",
    targetDate = "Jan 1, 2012",
    estimatedHours = 50,
    additionalNote = "This implementation is critical for the final launch")
    public static float ComputeTax(float amount, float rate) {
        return 0;
    }
}

@interface WorkInProgress {
}

@interface Task {

    String description();

    String targetDate();

    int estimatedHours();

    String additionalNote();
}