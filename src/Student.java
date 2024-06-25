import java.io.Serializable;
import java.util.List;


public class Student implements Serializable {
    private String webID;
    private List<Course> courses;


    public Student(String webID, List<Course> courses) {
        this.webID = webID;
        this.courses = courses;
    }


    public String getWebID() {
        return webID;
    }


    public void setWebID(String webID) {
        this.webID = webID;
    }


    public List<Course> getCourses() {
        return courses;
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

