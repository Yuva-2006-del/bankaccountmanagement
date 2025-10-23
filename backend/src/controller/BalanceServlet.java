package controller;

import service.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int accNo = Integer.parseInt(req.getParameter("accountNo"));
        BankService service = new BankService();
        double balance = service.getBalance(accNo);

        PrintWriter out = res.getWriter();
        out.println(balance >= 0 ? balance : "Account not found!");
    }
}
