import java.util.Comparator;


public class CourseNameComparator implements Comparator<Course> {


    @Override
    public int compare(Course left, Course right) {

        int departmentComparison = left.getDepartment().compareTo(right.getDepartment());
        if (departmentComparison != 0) {
            return departmentComparison;
        }


        return Integer.compare(left.getNumber(), right.getNumber());
    }
}

