package controller;

import service.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int accNo = Integer.parseInt(req.getParameter("accountNo"));
        double amount = Double.parseDouble(req.getParameter("amount"));

        BankService service = new BankService();
        boolean ok = service.withdraw(accNo, amount);

        PrintWriter out = res.getWriter();
        out.println(ok ? "Withdrawal successful!" : "Insufficient balance!");
    }
}
