package controller;

import dao.IStudentDAO;
import dao.StudentDAO;
import student.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet (name = "StudentServlet", urlPatterns = "/student")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IStudentDAO studentDAO;

    public void init () {
        studentDAO = new StudentDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acction = request.getParameter("action");
        if (acction == null) {
            acction = "";
        }
        try {
            switch (acction) {
                case "create":
                    insertStudent(request, response);
                    break;
                case  "edit":
                    editStudent(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acction = request.getParameter("action");
        if (acction == null) {
            acction = "";
        }
        try {
            switch (acction){
                case "create":
                    insertForm(request, response);
                    break;
                case "edit":
                    editForm(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                default:
                    listStudent(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void listStudent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Student> list = new StudentDAO().showAllStudent();
        request.setAttribute("listStudent", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(request, response);
    }

    public void insertStudent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        Student newStudent = new Student(name, age, address, email);

        studentDAO.addNewStudent(newStudent);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("add.jsp");
        requestDispatcher.forward(request, response);
    }

    public void editStudent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        Student editedStudent = new Student(id, name, age, address, email);
        studentDAO.updateStudent(editedStudent);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit.jsp");
        requestDispatcher.forward(request, response);
    }

    public void deleteStudent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);

        List<Student> studentList = studentDAO.showAllStudent();
        request.setAttribute("studentList", studentList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(request, response);
    }

    public void insertForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create.jsp");
        requestDispatcher.forward(request, response);
    }

    public void editForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.selectStudent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        request.setAttribute("student", student);
        dispatcher.forward(request, response);
    }
}
