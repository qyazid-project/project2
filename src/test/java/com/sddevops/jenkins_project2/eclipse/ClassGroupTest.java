package com.sddevops.jenkins_project2.eclipse;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassGroupTest {

    private ClassGroup classGroup;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        classGroup = new ClassGroup(3);

        student1 = new Student(
                1,
                "Alice",
                LocalDate.of(2002, 5, 10)
        );

        student2 = new Student(
                2,
                "Bob",
                LocalDate.of(2000, 3, 15)
        );

        student3 = new Student(
                3,
                "Charlie",
                LocalDate.of(2004, 8, 20)
        );
    }

    @Test
    void testConstructor() {
        assertEquals(3, classGroup.getCapacity());
        assertEquals(0, classGroup.getSize());
        assertNotNull(classGroup.getStudents());
        assertEquals(3, classGroup.getStudents().length);
    }

    @Test
    void testAddStudentSuccessfully() {
        boolean result = classGroup.addStudent(student1);

        assertTrue(result);
        assertEquals(1, classGroup.getSize());
        assertSame(student1, classGroup.getStudents()[0]);
    }

    @Test
    void testAddMultipleStudents() {
        assertTrue(classGroup.addStudent(student1));
        assertTrue(classGroup.addStudent(student2));
        assertTrue(classGroup.addStudent(student3));

        assertEquals(3, classGroup.getSize());
        assertSame(student1, classGroup.getStudents()[0]);
        assertSame(student2, classGroup.getStudents()[1]);
        assertSame(student3, classGroup.getStudents()[2]);
    }

    @Test
    void testAddStudentWhenClassIsFull() {
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);
        classGroup.addStudent(student3);

        Student extraStudent = new Student(
                4,
                "David",
                LocalDate.of(2001, 1, 1)
        );

        boolean result = classGroup.addStudent(extraStudent);

        assertFalse(result);
        assertEquals(3, classGroup.getSize());
    }

    @Test
    void testAddNullStudent() {
        boolean result = classGroup.addStudent(null);

        assertTrue(result);
        assertEquals(1, classGroup.getSize());
        assertNull(classGroup.getStudents()[0]);
    }

    @Test
    void testRemoveStudentSuccessfully() {
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);

        boolean result = classGroup.removeStudent(1);

        assertTrue(result);
        assertEquals(1, classGroup.getSize());
        assertSame(student2, classGroup.getStudents()[0]);
        assertNull(classGroup.getStudents()[1]);
    }

    @Test
    void testRemoveStudentFromMiddle() {
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);
        classGroup.addStudent(student3);

        boolean result = classGroup.removeStudent(2);

        assertTrue(result);
        assertEquals(2, classGroup.getSize());
        assertSame(student1, classGroup.getStudents()[0]);
        assertSame(student3, classGroup.getStudents()[1]);
        assertNull(classGroup.getStudents()[2]);
    }

    @Test
    void testRemoveLastStudent() {
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);

        boolean result = classGroup.removeStudent(2);

        assertTrue(result);
        assertEquals(1, classGroup.getSize());
        assertSame(student1, classGroup.getStudents()[0]);
        assertNull(classGroup.getStudents()[1]);
    }

    @Test
    void testRemoveStudentThatDoesNotExist() {
        classGroup.addStudent(student1);

        boolean result = classGroup.removeStudent(99);

        assertFalse(result);
        assertEquals(1, classGroup.getSize());
        assertSame(student1, classGroup.getStudents()[0]);
    }

    @Test
    void testRemoveStudentFromEmptyClass() {
        boolean result = classGroup.removeStudent(1);

        assertFalse(result);
        assertEquals(0, classGroup.getSize());
    }

    @Test
    void testGetOldestStudentFromEmptyClass() {
        Student result = classGroup.getTheOldestStudent();

        assertNull(result);
    }

    @Test
    void testGetOldestStudentWithOneStudent() {
        classGroup.addStudent(student1);

        Student result = classGroup.getTheOldestStudent();

        assertSame(student1, result);
    }

    @Test
    void testGetOldestStudentFromMultipleStudents() {
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);
        classGroup.addStudent(student3);

        Student result = classGroup.getTheOldestStudent();

        assertSame(student2, result);
        assertEquals(2, result.getId());
    }

    @Test
    void testGetOldestStudentWhenOldestIsFirst() {
        Student oldestStudent = new Student(
                4,
                "David",
                LocalDate.of(1995, 1, 1)
        );

        classGroup.addStudent(oldestStudent);
        classGroup.addStudent(student1);
        classGroup.addStudent(student2);

        Student result = classGroup.getTheOldestStudent();

        assertSame(oldestStudent, result);
    }

    @Test
    void testGetOldestStudentWhenOldestIsLast() {
        Student oldestStudent = new Student(
                4,
                "David",
                LocalDate.of(1990, 1, 1)
        );

        classGroup.addStudent(student1);
        classGroup.addStudent(student2);
        classGroup.addStudent(oldestStudent);

        Student result = classGroup.getTheOldestStudent();

        assertSame(oldestStudent, result);
    }

    @Test
    void testGetOldestStudentWithSameBirthday() {
        Student sameAgeStudent = new Student(
                4,
                "David",
                student1.getBirthday()
        );

        classGroup.addStudent(student1);
        classGroup.addStudent(sameAgeStudent);

        Student result = classGroup.getTheOldestStudent();

        assertSame(student1, result);
    }

    @Test
    void testGetCapacity() {
        assertEquals(3, classGroup.getCapacity());
    }

    @Test
    void testGetSizeAfterAddingAndRemoving() {
        assertEquals(0, classGroup.getSize());

        classGroup.addStudent(student1);
        classGroup.addStudent(student2);

        assertEquals(2, classGroup.getSize());

        classGroup.removeStudent(1);

        assertEquals(1, classGroup.getSize());
    }

    @Test
    void testGetStudentsReturnsBackingArray() {
        classGroup.addStudent(student1);

        Student[] students = classGroup.getStudents();

        assertSame(student1, students[0]);
        assertEquals(3, students.length);
    }
}