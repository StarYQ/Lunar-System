import java.util.Comparator;


public class SemesterComparator implements Comparator<Course> {


    @Override
    public int compare(Course left, Course right) {
        return left.getSemester().compareTo(right.getSemester());
    }
}
