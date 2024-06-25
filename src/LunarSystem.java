import java.io.*;
import java.util.*;

public class LunarSystem {
    public static void main(String[] args) {
        LunarSystem lunarSystem = new LunarSystem();
        lunarSystem.run();
    }
    private final HashMap<String, Student> database;

    public LunarSystem() {
        this.database = loadDatabase();
    }

    private HashMap<String, Student> loadDatabase() {
        try {
            FileInputStream fileIn = new FileInputStream("Lunar.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            HashMap<String, Student> loadedDatabase = (HashMap<String, Student>) in.readObject();
            in.close();
            fileIn.close();
            return loadedDatabase;
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void saveDatabase() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Lunar.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(database);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter webid: ");
        String webID = scanner.nextLine().toUpperCase();
        if (webID.equals("REGISTRAR")) {
            registrarOptions(scanner);
        } else {
            if (database.containsKey(webID)){
                studentOptions(webID, scanner);
            } else {
                System.out.println("Invalid login: Could not find user");
            }
        }
    }

    private void registrarOptions(Scanner scanner) {
        System.out.println("Welcome Registrar.");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Options:");
            System.out.println("R) Register a student");
            System.out.println("D) De-register a student");
            System.out.println("E) View course enrollment");
            System.out.println("L) Logout");
            System.out.print("Please select an option: ");
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "R":
                    registerStudent(scanner);
                    break;
                case "D":
                    deregisterStudent(scanner);
                    break;
                case "E":
                    viewCourseEnrollment(scanner);
                    break;
                case "L":
                    System.out.println("Registrar logged out.");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void registerStudent(Scanner scanner) {
        System.out.print("Please enter a webid for the new student: ");
        String webID = scanner.nextLine().toUpperCase();
        if (database.containsKey(webID)) {
            System.out.println(webID + " is already registered.");
        } else {
            database.put(webID, new Student(webID, null));
            System.out.println(webID + " registered.");
        }
    }

    private void deregisterStudent(Scanner scanner) {
        System.out.print("Please enter webid of the student to de-register: ");
        String webID = scanner.nextLine().toUpperCase();
        if (database.containsKey(webID)) {
            database.remove(webID);
            System.out.println(webID + " de-registered.");
        } else {
            System.out.println("Error: Could not find student in database");
        }
    }

    private void viewCourseEnrollment(Scanner scanner) {
        System.out.print("Please enter course: ");
        String course = scanner.nextLine().toUpperCase();

        String[] parts = course.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid input. Please enter course in format 'Department Number'.");
            return;
        }

        String department = parts[0];
        int number;
        try {
            number = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid course number. Please enter a valid integer.");
            return;
        }
        boolean found = false;
        for (Student student : database.values()) {
            List<Course> courses = student.getCourses();
            if (courses != null) {
                for (Course c : courses) {
                    if (c.getDepartment().equals(department) && c.getNumber() == number) {
                        if (!found) {
                            System.out.println("Students registered in " + department + " " + number + ":");
                            System.out.println("Student Semester");
                            System.out.println("-----------");
                        }
                        System.out.println(student.getWebID()+ " "+c.getSemester());
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No students enrolled in " + department + " " + number + ".");
        }
    }

    private void studentOptions(String webID, Scanner scanner) {
        System.out.println("Welcome " + webID + ".");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Options:");
            System.out.println("A) Add a class");
            System.out.println("D) Drop a class");
            System.out.println("C) View your classes sorted by course name/department");
            System.out.println("S) View your courses sorted by semester");
            System.out.println("L) Logout");
            System.out.print("Please select an option: ");
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "A":
                    addClass(webID, scanner);
                    break;
                case "D":
                    dropClass(webID, scanner);
                    break;
                case "C":
                    viewClassesByDepartment(webID);
                    break;
                case "S":
                    viewCoursesBySemester(webID);
                    break;
                case "L":
                    System.out.println(webID+" logged out.");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Lunar System, a second place course registration system for second rate courses at a second class school.");
        if (database.isEmpty()) {
            System.out.println("No previous data found.");
        }
        boolean running = true;
        while (running) {
            System.out.println("Menu:");
            System.out.println("L) Login");
            System.out.println("X) Save state and quit");
            System.out.println("Q) Quit without saving state.");
            System.out.print("Please select an option: ");
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "L":
                    login();
                    break;
                case "X":
                    saveDatabase();
                    System.out.println("State saved.");
                    running = false;
                    break;
                case "Q":
                    System.out.println("Exiting without saving state.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void addClass(String webID, Scanner scanner) {
        System.out.print("Please enter course Name: ");
        String courseName = scanner.nextLine();


        System.out.print("Please select a semester: ");
        String semester = scanner.nextLine();

        String[] parts = courseName.split(" ");
        String department = parts[0];
        int number = Integer.parseInt(parts[1]);



        Course course = new Course(department, number, semester);



        Student student = database.get(webID);
        if (student != null) {
            List<Course> courses = student.getCourses();
            if (courses == null){
                courses = new ArrayList<>();
                student.setCourses(courses);
            }
            courses.add(course);
            student.setCourses(courses);
            database.put(webID, student);
            System.out.println(department+" "+ number +" added in " + semester + ".");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void dropClass(String webID, Scanner scanner) {
        System.out.print("Please enter department: ");
        String department = scanner.nextLine();

        System.out.print("Please enter number: ");
        int number = Integer.parseInt(scanner.nextLine());

        System.out.print("Please select a semester: ");
        String semester = scanner.nextLine();

        Student student = database.get(webID);
        if (student != null) {
            List<Course> courses = student.getCourses();
            Course courseToRemove = new Course(department, number, semester);
            if (courses.remove(courseToRemove)) {
                student.setCourses(courses);
                database.put(webID, student);
                System.out.println("course number "+number + " dropped from " + semester + ".");
            } else {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private void viewClassesByDepartment(String webID) {
        Student student = database.get(webID);
        if (student != null) {
            List<Course> courses = student.getCourses();
            courses.sort(new CourseNameComparator());
            System.out.println("Dept. Course Semester");
            System.out.println("-------------------------------");
            for (Course course : courses) {
                System.out.println(course.getDepartment() + " " + course.getNumber() + " " + course.getSemester());
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private void viewCoursesBySemester(String webID) {
        Student student = database.get(webID);
        if (student != null) {
            List<Course> courses = student.getCourses();
            courses.sort(new SemesterComparator());
            System.out.println("Dept. Course Semester");
            System.out.println("-------------------------------");
            for (Course course : courses) {
                System.out.println(course.getDepartment() + " " + course.getNumber() + " " + course.getSemester());
            }
        } else {
            System.out.println("Student not found.");
        }
    }


}
