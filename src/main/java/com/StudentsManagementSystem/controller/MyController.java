package com.StudentsManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.StudentsManagementSystem.entity.Student;
import com.StudentsManagementSystem.service.StudentService;

@Controller  // Marks this class as a Spring MVC controller
public class MyController {

    @Autowired  // Automatically injects the StudentService bean
    private StudentService service;
    
    @GetMapping("/home")
    public String home() {
        return "home"; // Returns the "home" HTML file when accessing the "/home" URL
    }
    
    @GetMapping("/students")
    public String getAllStudents(Model model) {
        model.addAttribute("students", service.getAllStudents()); // Adds the list of all students to the model
        return "students"; // Returns the "students" HTML file
    }
    
    @GetMapping("students/new")
    public String createStudentForm(Model model) {
        Student student = new Student(); // Creates a new Student object to hold the form data
        model.addAttribute("student", student); // Adds the new Student object to the model
        return "create-student"; // Returns the "create-student" HTML file
    }
    
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.saveStudent(student); // Saves the student to the database
        return "redirect:/students"; // Redirects to the "/students" URL
    }
    
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable int id, Model model) {
        model.addAttribute("student", service.getById(id)); // Adds the student with the given ID to the model
        return "edit_student"; // Returns the "edit_student" HTML file
    }
    
    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute("student") Student student) {
        Student existingStudent = service.getById(id); // Retrieves the existing student with the given ID
        existingStudent.setFirstName(student.getFirstName()); // Updates the student's first name
        existingStudent.setLastName(student.getLastName()); // Updates the student's last name
        existingStudent.setEmail(student.getEmail()); // Updates the student's email
        service.saveStudent(existingStudent); // Saves the updated student to the database
        return "redirect:/students"; // Redirects to the "/students" URL
    }
    
    @GetMapping("/students/{id}")
    public String deleteById(@PathVariable int id) {
        service.deleteById(id); // Deletes the student with the given ID from the database
        return "redirect:/students"; // Redirects to the "/students" URL
    }
}
