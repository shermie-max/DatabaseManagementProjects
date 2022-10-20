import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    String coursePrefix;
    int courseNumber;
    String courseTitle;
    int numOfCredits;

    public void keepTrackProgram(String coursePrefix, int courseNumber, String courseTitle, int numOfCredits) {
    }

    public static void main(String[] args) throws IOException {
        //create a new scanner
        Scanner sc = new Scanner(System.in);

        //declare variables type
        String action = "";
        String mainCommand = "";


        //check input commands == q or not, if not then print the prompt
        while (!mainCommand.equals("q")) {
            System.out.println(">>>");
            mainCommand = sc.nextLine();

            //get action as the first character from the inputted string
            action = mainCommand.substring(0, 1);
            if (action.equals("q")) {
                break;
            }

            //get the second character as the object from the inputted string
            String object = mainCommand.substring(2, 3);

            switch (action.trim()) {
                case "a": //for add
                    switch (object.trim()) {
                        case "c": //for add course
                            FileWriter courseFile = new FileWriter("course.txt", true);
                            BufferedWriter courseBufferedWriter = new BufferedWriter(courseFile);
                            PrintWriter courseWriter = new PrintWriter(courseBufferedWriter);

                            courseWriter.println(mainCommand.substring(4, mainCommand.length()));
                            courseWriter.close();
                            courseBufferedWriter.close();
                            courseFile.close();
                            break;

                        case "g": //grade
                            FileWriter gradeFile = new FileWriter("grade.txt", true);
                            BufferedWriter gradeBufferedWriter = new BufferedWriter(gradeFile);
                            PrintWriter gradeWriter = new PrintWriter(gradeBufferedWriter);

                            gradeWriter.println(mainCommand.substring(4, mainCommand.length()));
                            gradeWriter.close();
                            gradeBufferedWriter.close();
                            gradeFile.close();

                            break;

                        case "m": //semester code
                            FileWriter semesterCode = new FileWriter("semesterCode.txt", true);
                            BufferedWriter semesterCodeBufferedWriter = new BufferedWriter(semesterCode);
                            PrintWriter semesterCodeWriter = new PrintWriter(semesterCodeBufferedWriter);

                            semesterCodeWriter.println(mainCommand.substring(4, mainCommand.length()));
                            semesterCodeWriter.close();
                            semesterCodeBufferedWriter.close();
                            semesterCode.close();

                            break;

                        case "s": // student info.

                            FileWriter studentInfoFile = new FileWriter("student_info.txt", true);
                            BufferedWriter studentInfoBufferedWriter = new BufferedWriter(studentInfoFile);
                            PrintWriter writer4 = new PrintWriter(studentInfoBufferedWriter);

                            writer4.println(mainCommand.substring(4, mainCommand.length()));
                            writer4.close();
                            studentInfoBufferedWriter.close();
                            studentInfoFile.close();
                            break;

                        case "t": //taken course
                            FileWriter takenCourseFile = new FileWriter("courseTaken.txt", true);
                            BufferedWriter takenCourseBufferWriter = new BufferedWriter(takenCourseFile);
                            PrintWriter takenCourseWriter = new PrintWriter(takenCourseBufferWriter);

                            takenCourseWriter.println(mainCommand.substring(4, mainCommand.length()));
                            takenCourseWriter.close();
                            takenCourseBufferWriter.close();
                            takenCourseFile.close();
                            break;
                    }
                    break;

                case "l":// for listing -- read the file
                    switch (object.trim()) {
                        case "c": //for list all course
                            FileInputStream courseInputStream = new FileInputStream("course.txt");
                            Scanner courseScanner = new Scanner(courseInputStream);
                            while (courseScanner.hasNext()) {
                                System.out.println(courseScanner.nextLine());
                            }
                            courseInputStream.close();
                            break;

                        case "g": //for list all grade
                            FileInputStream gradeInputStream = new FileInputStream("grade.txt");
                            Scanner gradeScanner = new Scanner(gradeInputStream);
                            while (gradeScanner.hasNext()) {
                                System.out.println(gradeScanner.nextLine());
                            }
                            gradeInputStream.close();

                            break;

                        case "m": //list all semester
                            FileInputStream semesterInputStream = new FileInputStream("semesterCode.txt");
                            Scanner semesterScanner = new Scanner(semesterInputStream);
                            while (semesterScanner.hasNext()) {
                                System.out.println(semesterScanner.nextLine());
                            }
                            semesterInputStream.close();

                            break;

                        case "s": //for list all student info
                            FileInputStream studentInfoInputStream = new FileInputStream("student_info.txt");
                            Scanner studentInfoScanner = new Scanner(studentInfoInputStream);
                            while (studentInfoScanner.hasNext()) {
                                System.out.println(studentInfoScanner.nextLine());
                            }
                            studentInfoInputStream.close();

                            break;

                        case "t": //for list all course
                            FileInputStream takenCourseInputStream = new FileInputStream("courseTaken.txt");
                            Scanner takenCourseScanner = new Scanner(takenCourseInputStream);
                            while (takenCourseScanner.hasNext()) {
                                System.out.println(takenCourseScanner.nextLine());
                            }
                            takenCourseInputStream.close();
                            break;
                    }
                    break;

                case "t": //read transcript
                    String fullName = mainCommand.replaceFirst(action, "").trim(); //eliminate t character
                    String inputLastName = fullName.split(" ")[0].trim();
                    String inputFirstName = fullName.split(" ")[1].trim();

                    // Create an empty hash map by declaring object
                    // of string and double type for calculating grade from grade code (A,B,C,D…)
                    Map<String, Double> gradeMap = new HashMap<>();

                    //read  grade dictionary from the grade.txt file
                    FileInputStream gradeInputStream = new FileInputStream("grade.txt");
                    Scanner gradeScanner = new Scanner(gradeInputStream);
                    while (gradeScanner.hasNext()) {
                        String gradeLine = gradeScanner.nextLine();
                        String[] gradeArray = gradeLine.trim().split(" ");
                        gradeMap.put(gradeArray[0], Double.valueOf(gradeArray[1]));
                        ;
                    }

                    //Read each file in the records of lines that have similar last and first name and build a list
                    FileInputStream takenCourseInputStream = new FileInputStream("courseTaken.txt");
                    Scanner takenCourseScanner = new Scanner(takenCourseInputStream);

                    Map<String, List<String>> finalResult = new HashMap<>();
                    Map<String, String> semesterMap = new HashMap<>();

                    int totalHour = 0;
                    double totalGpa = 0.0;

                    while (takenCourseScanner.hasNext()) {
                        String takenCourseLine = takenCourseScanner.nextLine();
                        String[] takenCourseArray = takenCourseLine.trim().split(" ");
                        String takenCourseLastNameOfEachLine = takenCourseArray[0].trim();
                        String takenCourseFistNameOfEachLine = takenCourseArray[1].trim();
                        String takenCourseCode = takenCourseArray[2].trim();
                        String takenCourseNumber = takenCourseArray[3].trim();
                        String takenCourseGrade = takenCourseArray[4].trim();
                        String takenCourseSemesterCode = takenCourseArray[5].trim();
                        String takenCourseName = "";
                        String takenCredit = "";

                        //read semesterCode first
                        FileInputStream semesterInputStream = new FileInputStream("semesterCode.txt");
                        Scanner semesterScanner = new Scanner(semesterInputStream);


                        while (semesterScanner.hasNext()) {
                            String semesterLine = semesterScanner.nextLine();
                            String[] semesterArray = semesterLine.trim().split(" ");
                            String semesterCode = semesterArray[0].trim();
                            String yearOfSemester = semesterArray[1].trim();
                            String seasonName = semesterArray[2].trim();

                            //for printing the year and season name
                            if (!semesterMap.containsKey(semesterCode.trim())) {
                                semesterMap.put(semesterCode.trim(), yearOfSemester + " " + seasonName);
                            }

                            //check to make sure it just does one time
                            if (!finalResult.containsKey(semesterCode.trim())) {
                                finalResult.put(semesterCode.trim(), new ArrayList<String>());
                            }

                        }

                        //read takenCourse name in course file
                        FileInputStream courseInputStream = new FileInputStream("course.txt");
                        Scanner courseScanner = new Scanner(courseInputStream);

                        while (courseScanner.hasNext()) {
                            String courseLine = courseScanner.nextLine();
                            String[] courseArray = courseLine.trim().split(" ");
                            String courseCode = courseArray[0].trim();
                            String courseNumber = courseArray[1].trim();

                            if (takenCourseCode.equals(courseCode) && takenCourseNumber.equals(courseNumber)) {
                                takenCourseName = courseArray[2].trim();
                                takenCredit = courseArray[3].trim();
                                break;
                            }
                        }

                        //Just care the records has the fist name, and last name the same to last, first name inputted
                        if (inputLastName.equals(takenCourseLastNameOfEachLine) && inputFirstName.equals(takenCourseFistNameOfEachLine)) {
                            totalHour = totalHour + Integer.valueOf(takenCredit);
                            totalGpa = totalGpa + (Integer.valueOf(takenCredit) * gradeMap.get(takenCourseGrade));

                            String finalString = takenCourseCode + takenCourseNumber + " " + takenCourseName + " (" + takenCredit + ") " + takenCourseGrade;
                            List<String> tempList = finalResult.get(takenCourseSemesterCode.trim());
                            tempList.add(finalString);
                            finalResult.put(takenCourseSemesterCode.trim(), tempList);
                        }
                    }

                     // Loop through all entry of final result map and print out the result
                    for (Map.Entry<String, List<String>> entry : finalResult.entrySet()) {
                        List<String> courses = entry.getValue();
                        if (courses.isEmpty()) {
                            continue;
                        }

                        System.out.println("============ Semester:" + semesterMap.get(entry.getKey()) + " ==============");


                        for (String course : courses) {
                            System.out.println(course);
                        }
                    }
                    System.out.println("STUDENT HOURS COMPLETED: " + totalHour);
                    System.out.printf("STUDENT GPA: %,.5f \n", totalGpa / totalHour);
                    takenCourseInputStream.close();
                    break;

                default:

            }
        }
    }
}
