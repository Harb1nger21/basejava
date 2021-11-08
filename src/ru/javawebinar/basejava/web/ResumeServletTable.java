package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResumeServletTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlHelper helper = new SqlHelper(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes", "postgres", "Rjr2.ntqkm"));
        List<Resume> resumes = new ArrayList<>();
        helper.execute("SELECT uuid, fullname FROM resume", ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return null;
                }
        );
        request.setAttribute("resumes", resumes);
        request.getRequestDispatcher("/tableOfResumes.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
