package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServletTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlStorage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes","postgres","Rjr2.ntqkm");
        List<Resume> resumes = storage.getAllSorted();
        request.setAttribute("resumes", resumes);
        request.getRequestDispatcher("/tableOfResumes.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
