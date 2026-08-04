package com.sddevops.jenkins_project2.eclipse;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private Student student;
    private LocalDate birthday;

    @BeforeEach
    void setUp() {
        birthday = LocalDate.of(2000, 5, 15);
        student = new Student(1, "Alice", birthday);
    }

    @Test
    void testConstructorWithoutBestFriend() {
        assertEquals(1, student.getId());
        assertEquals("Alice", student.getName());
        assertEquals(birthday, student.getBirthday());
        assertNull(student.getFriend());
    }

    @Test
    void testConstructorWithBestFriend() {
        Student friend = new Student(
                2,
                "Bob",
                LocalDate.of(2001, 6, 20)
        );

        Student studentWithFriend = new Student(
                1,
                "Alice",
                birthday,
                friend
        );

        assertEquals(1, studentWithFriend.getId());
        assertEquals("Alice", studentWithFriend.getName());
        assertEquals(birthday, studentWithFriend.getBirthday());
        assertSame(friend, studentWithFriend.getFriend());
    }

    @Test
    void testSetId() {
        student.setId(10);

        assertEquals(10, student.getId());
    }

    @Test
    void testSetName() {
        student.setName("Charlie");

        assertEquals("Charlie", student.getName());
    }

    @Test
    void testSetBirthday() {
        LocalDate newBirthday = LocalDate.of(1999, 12, 25);

        student.setBirthday(newBirthday);

        assertEquals(newBirthday, student.getBirthday());
    }

    @Test
    void testSetFriend() {
        Student friend = new Student(
                2,
                "Bob",
                LocalDate.of(2001, 6, 20)
        );

        student.setFriend(friend);

        assertSame(friend, student.getFriend());
    }

    @Test
    void testSetFriendToNull() {
        Student friend = new Student(
                2,
                "Bob",
                LocalDate.of(2001, 6, 20)
        );

        student.setFriend(friend);
        student.setFriend(null);

        assertNull(student.getFriend());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(student, student);
    }

    @Test
    void testEqualsIdenticalStudent() {
        Student identicalStudent = new Student(
                1,
                "Alice",
                LocalDate.of(2000, 5, 15)
        );

        assertEquals(student, identicalStudent);
    }

    @Test
    void testEqualsIgnoresFriend() {
        Student friendOne = new Student(
                2,
                "Bob",
                LocalDate.of(2001, 1, 1)
        );

        Student friendTwo = new Student(
                3,
                "Charlie",
                LocalDate.of(2002, 1, 1)
        );

        Student firstStudent = new Student(
                1,
                "Alice",
                birthday,
                friendOne
        );

        Student secondStudent = new Student(
                1,
                "Alice",
                birthday,
                friendTwo
        );

        assertEquals(firstStudent, secondStudent);
    }

    @Test
    void testNotEqualsDifferentId() {
        Student differentStudent = new Student(
                2,
                "Alice",
                birthday
        );

        assertNotEquals(student, differentStudent);
    }

    @Test
    void testNotEqualsDifferentName() {
        Student differentStudent = new Student(
                1,
                "Bob",
                birthday
        );

        assertNotEquals(student, differentStudent);
    }

    @Test
    void testNotEqualsDifferentBirthday() {
        Student differentStudent = new Student(
                1,
                "Alice",
                LocalDate.of(2001, 5, 15)
        );

        assertNotEquals(student, differentStudent);
    }

    @Test
    void testNotEqualsNull() {
        assertNotEquals(null, student);
    }

    @Test
    void testNotEqualsDifferentObjectType() {
        assertNotEquals("Alice", student);
    }

    @Test
    void testHashCodeForEqualStudents() {
        Student identicalStudent = new Student(
                1,
                "Alice",
                birthday
        );

        assertEquals(student.hashCode(), identicalStudent.hashCode());
    }

    @Test
    void testCompareByNameBefore() {
        Student alice = new Student(1, "Alice", birthday);
        Student bob = new Student(2, "Bob", birthday);

        int result = Student.compareByName.compare(alice, bob);

        assertTrue(result < 0);
    }

    @Test
    void testCompareByNameAfter() {
        Student alice = new Student(1, "Alice", birthday);
        Student bob = new Student(2, "Bob", birthday);

        int result = Student.compareByName.compare(bob, alice);

        assertTrue(result > 0);
    }

    @Test
    void testCompareByNameEqualIgnoringCase() {
        Student first = new Student(1, "Alice", birthday);
        Student second = new Student(2, "ALICE", birthday);

        int result = Student.compareByName.compare(first, second);

        assertEquals(0, result);
    }

    @Test
    void testCompareByBirthdayBefore() {
        Student olderStudent = new Student(
                1,
                "Alice",
                LocalDate.of(1999, 1, 1)
        );

        Student youngerStudent = new Student(
                2,
                "Bob",
                LocalDate.of(2000, 1, 1)
        );

        int result = Student.compareByBirthday.compare(
                olderStudent,
                youngerStudent
        );

        assertTrue(result < 0);
    }

    @Test
    void testCompareByBirthdayAfter() {
        Student olderStudent = new Student(
                1,
                "Alice",
                LocalDate.of(1999, 1, 1)
        );

        Student youngerStudent = new Student(
                2,
                "Bob",
                LocalDate.of(2000, 1, 1)
        );

        int result = Student.compareByBirthday.compare(
                youngerStudent,
                olderStudent
        );

        assertTrue(result > 0);
    }

    @Test
    void testCompareByBirthdayEqual() {
        Student first = new Student(1, "Alice", birthday);
        Student second = new Student(2, "Bob", birthday);

        int result = Student.compareByBirthday.compare(first, second);

        assertEquals(0, result);
    }

    @Test
    void testToStringWithoutFriend() {
        String expected = "Student{id = 1, name = 'Alice', "
                + "birthday = 2000-05-15, friend = no best friend}";

        assertEquals(expected, student.toString());
    }

    @Test
    void testToStringWithFriend() {
        Student friend = new Student(
                2,
                "Bob",
                LocalDate.of(2001, 6, 20)
        );

        student.setFriend(friend);

        String expected = "Student{id = 1, name = 'Alice', "
                + "birthday = 2000-05-15, friend = Bob}";

        assertEquals(expected, student.toString());
    }

    @Test
    void testAssignRandomUsernameMinimumLength() {
        Random random = new FixedRandom(0, 0);

        student.assignRandomUsername(random);

        assertEquals(5, student.getName().length());
        assertEquals("AAAAA", student.getName());
    }

    @Test
    void testAssignRandomUsernameMaximumLength() {
        Random random = new FixedRandom(5, 62);

        student.assignRandomUsername(random);

        assertEquals(10, student.getName().length());
        assertEquals("__________", student.getName());
    }

    @Test
    void testAssignRandomUsernameContainsOnlyAllowedCharacters() {
        student.assignRandomUsername(new Random(12345));

        assertTrue(student.getName().matches("[A-Za-z0-9_]{5,10}"));
    }

    private static class FixedRandom extends Random {

        private static final long serialVersionUID = 1L;

        private final int lengthValue;
        private final int characterValue;

        FixedRandom(int lengthValue, int characterValue) {
            this.lengthValue = lengthValue;
            this.characterValue = characterValue;
        }

        @Override
        public int nextInt(int bound) {
            if (bound == 6) {
                return lengthValue;
            }

            return characterValue;
        }
    }
}