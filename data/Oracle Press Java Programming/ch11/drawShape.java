
import java.lang.annotation.*;

@WorkInProgress
class Shape {

    public void drawShape() {
    }
}

public class drawShape extends Shape {

    @Override
    public void drawShape() {
    }

    public static void main(String[] args) {
        Shape shape = new Shape();
        Class cls = shape.getClass();
        if (cls.isAnnotationPresent(WorkInProgress.class)) {
            System.out.println("Shape class does require some work");
            WorkInProgress progress = (WorkInProgress) cls.getAnnotation(
                    WorkInProgress.class);
            System.out.println(progress.doSomething());
        } else {
            System.out.println("Shape is fully implemented");
        }
        System.out.println();
        drawShape line = new drawShape();
        cls = line.getClass();
        if (cls.isAnnotationPresent(WorkInProgress.class)) {
            System.out.println("Line class does require some work");
            WorkInProgress progress = (WorkInProgress) cls.getAnnotation(
                    WorkInProgress.class);
            System.out.println(progress.doSomething());
        } else {
            System.out.println("Line is fully implemented");
        }
        System.out.println();
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@interface WorkInProgress {

    String doSomething() default "\tDo what?";
}