package controller;

import service.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int accNo = Integer.parseInt(req.getParameter("accountNo"));
        BankService service = new BankService();
        String history = service.getTransactions(accNo);

        PrintWriter out = res.getWriter();
        out.println(history);
    }
}
