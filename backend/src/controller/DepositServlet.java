package controller;

import service.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int accNo = Integer.parseInt(req.getParameter("accountNo"));
        double amount = Double.parseDouble(req.getParameter("amount"));

        BankService service = new BankService();
        boolean ok = service.deposit(accNo, amount);

        PrintWriter out = res.getWriter();
        out.println(ok ? "Deposit successful!" : "Deposit failed!");
    }
}
