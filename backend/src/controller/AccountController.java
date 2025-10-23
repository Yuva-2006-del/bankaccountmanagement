package controller;

import service.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/createAccount")
public class AccountController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String name = req.getParameter("name");
        double balance = Double.parseDouble(req.getParameter("balance"));

        BankService service = new BankService();
        boolean ok = service.createAccount(name, balance);

        PrintWriter out = res.getWriter();
        if (ok) out.println("Account created successfully!");
        else out.println("Failed to create account.");
    }
}
