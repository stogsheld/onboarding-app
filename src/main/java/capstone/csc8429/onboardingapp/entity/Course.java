package capstone.csc8429.onboardingapp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "course_info")
public class Course {

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "course_content")
    private String courseContent;


    // Constructors
    public Course() {
    }

    public Course(int courseId, String courseName, String courseDescription, String courseContent) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseContent = courseContent;
    }

    public Course(String courseName, String courseDescription, String courseContent) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseContent = courseContent;
    }

    // Getters/Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    // toString()

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseContent='" + courseContent + '\'' +
                '}';
    }
}
