
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@WorkInProgress
public class RuntimeAnnotation {

    @WorkInProgress
    @Task(description = "Implement tax computations",
    estimatedHours = 50,
    additionalNote = "This implementation is critical for the final launch")
    public static float ComputeTax(float amount, float rate) {
        return 0;
    }

    public static void main(String args[]) {
        try {
            RuntimeAnnotation obj = new RuntimeAnnotation();
            Class cls = obj.getClass();
            WorkInProgress annotation =
                    (WorkInProgress) cls.getAnnotation(WorkInProgress.class);
            System.out.println("Class " + cls.getName());
            if (cls.isAnnotationPresent(WorkInProgress.class)) {
                System.out.println("\tThis class is not fully implemented");
            }
            System.out.println("\nList of methods:");
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
                if (method.isAnnotationPresent(WorkInProgress.class)) {
                    System.out.println(
                            "\tThis method is not fully implemented");
                }
                if (method.isAnnotationPresent(Task.class)) {
                    Task annotationTask =
                            (Task) method.getAnnotation(Task.class);
                    System.out.printf("\tWhat TODO: "
                            + annotationTask.description()
                            + "%n\tTarget date: "
                            + annotationTask.targetDate()
                            + "%n\tEstimated hours: "
                            + annotationTask.estimatedHours()
                            + "%n\tNote: " + annotationTask.additionalNote()
                            + "%n");
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface WorkInProgress {
}

@Retention(RetentionPolicy.RUNTIME)
@interface Task {

    String description();

    String targetDate() default "Jan 1, 2012";

    int estimatedHours();

    String additionalNote();
}