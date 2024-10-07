package controller;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import repository.EmployeeRepository;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

//    displaying employee form
    @GetMapping("/insertform")
    public String showInsertForm() {
        return "add-employee";
    }
//    inserting data
    @PostMapping("/insert")
    public ModelAndView insertStudent(@ModelAttribute  Employee employee) {
        ModelAndView modelAndView = new ModelAndView();
        employeeRepository.save(employee);
        modelAndView.setViewName("redirect:/success");
        return modelAndView;
    }

//show success message
    @GetMapping("/success")
    public ModelAndView showSuccessPage() {
        ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("message", "Employee Details Added Successfully!!");
        return modelAndView;
    }
    //display all employee details
    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Employee> employees =employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "list";
    }
    // show updating form
    @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable int id, Model model) {
        Employee employee =employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return "redirect:/list";
        }
        model.addAttribute("employee", employee);
        return "update";
    }

//update employee
     @PostMapping("/update")
     public String updateStudent(@ModelAttribute Employee employee, Model model) {
     employeeRepository.save(employee); // Save the updated student
     model.addAttribute("message", "Updated Successfully!!");
     model.addAttribute("employee", employee);
     return "update";
     }

     // delete employee
@GetMapping("/delete/{id}")
public String deleteEmployee(@PathVariable int id, Model model) {
    employeeRepository.deleteById(id);
    model.addAttribute("delete", "Employee deleted successfully!");
    return "redirect:/list"; // Redirect to the list after deletion
}
}
