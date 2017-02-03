
public class MultiDimArrayApp {

    public static void main(String[] args) {
        final int MAX_STUDENTS = 50, MAX_SUBJECTS = 3;
        int[][] marks = new int[MAX_STUDENTS][MAX_SUBJECTS];
        // Adding data to the array
        for (int id = 0; id < MAX_STUDENTS; id++) {
            for (int subject = 0; subject < MAX_SUBJECTS; subject++) {
                marks[id][subject] = (int) (Math.random() * 100);
            }
        }
        // Printing Array
        System.out.print("Student\t");
        for (int subject = 0; subject < MAX_SUBJECTS; subject++) {
            System.out.print("\t" + "Subject " + subject + "\t");
        }
        System.out.println();
        for (int id = 0; id < MAX_STUDENTS; id++) {
            System.out.print("Student " + (id + 1) + '\t');
            for (int subject = 0; subject < MAX_SUBJECTS; subject++) {
                System.out.print("\t" + marks[id][subject] + "\t");
            }
            System.out.println();
        }
    }
}