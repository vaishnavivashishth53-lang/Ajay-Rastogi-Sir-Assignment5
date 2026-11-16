package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String feedbackType = request.getParameter("feedbackType");
        String comments = request.getParameter("comments");

        if (name == null || name.trim().isEmpty()) {
            name = "User";
        }
        if (email == null) {
            email = "Not provided";
        }
        if (feedbackType == null) {
            feedbackType = "General";
        }
        if (comments == null) {
            comments = "No comments provided.";
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy | hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Feedback Confirmation</title>");
            out.println("    <style>");
            out.println("        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }");
            out.println("        body { background: linear-gradient(135deg, #eef2f7 0%, #dbe4ee 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; padding: 20px; }");
            out.println("        .confirmation-card { background: #ffffff; width: 100%; max-width: 540px; padding: 35px 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08); }");
            out.println("        .success-banner { text-align: center; margin-bottom: 25px; }");
            out.println("        .success-banner h1 { color: #27ae60; font-size: 24px; font-weight: 700; margin-bottom: 6px; }");
            out.println("        .success-banner p { color: #7f8c8d; font-size: 14px; }");
            out.println("        .details-box { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 20px; margin-bottom: 25px; }");
            out.println("        .detail-item { display: flex; margin-bottom: 12px; font-size: 14px; }");
            out.println("        .detail-item:last-child { margin-bottom: 0; }");
            out.println("        .detail-label { font-weight: 600; color: #475569; width: 140px; flex-shrink: 0; }");
            out.println("        .detail-value { color: #1e293b; word-break: break-word; }");
            out.println("        .badge { display: inline-block; padding: 3px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; }");
            out.println("        .badge-complaint { background: #fee2e2; color: #b91c1c; }");
            out.println("        .badge-suggestion { background: #fef3c7; color: #b45309; }");
            out.println("        .badge-compliment { background: #dcfce7; color: #15803d; }");
            out.println("        .badge-default { background: #e2e8f0; color: #334155; }");
            out.println("        .back-btn { display: block; text-align: center; text-decoration: none; width: 100%; padding: 12px; background-color: #3498db; color: #ffffff; border-radius: 8px; font-weight: 600; font-size: 15px; transition: background-color 0.2s; }");
            out.println("        .back-btn:hover { background-color: #2980b9; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <div class='confirmation-card'>");
            out.println("        <div class='success-banner'>");
            out.println("            <h1>Thank you, " + escapeHtml(name) + " for your feedback!</h1>");
            out.println("            <p>Your response has been successfully recorded.</p>");
            out.println("        </div>");
            out.println("        <div class='details-box'>");

            String badgeClass = "badge-default";
            if ("Complaint".equalsIgnoreCase(feedbackType)) {
                badgeClass = "badge-complaint";
            } else if ("Suggestion".equalsIgnoreCase(feedbackType)) {
                badgeClass = "badge-suggestion";
            } else if ("Compliment".equalsIgnoreCase(feedbackType)) {
                badgeClass = "badge-compliment";
            }

            out.println("            <div class='detail-item'>");
            out.println("                <span class='detail-label'>Name:</span>");
            out.println("                <span class='detail-value'>" + escapeHtml(name) + "</span>");
            out.println("            </div>");
            out.println("            <div class='detail-item'>");
            out.println("                <span class='detail-label'>Email:</span>");
            out.println("                <span class='detail-value'>" + escapeHtml(email) + "</span>");
            out.println("            </div>");
            out.println("            <div class='detail-item'>");
            out.println("                <span class='detail-label'>Feedback Type:</span>");
            out.println("                <span class='detail-value'><span class='badge " + badgeClass + "'>" + escapeHtml(feedbackType) + "</span></span>");
            out.println("            </div>");
            out.println("            <div class='detail-item'>");
            out.println("                <span class='detail-label'>Comments:</span>");
            out.println("                <span class='detail-value'>" + escapeHtml(comments) + "</span>");
            out.println("            </div>");
            out.println("            <div class='detail-item'>");
            out.println("                <span class='detail-label'>Submission Time:</span>");
            out.println("                <span class='detail-value'>" + escapeHtml(formattedDateTime) + "</span>");
            out.println("            </div>");
            out.println("        </div>");
            out.println("        <a href='feedback.html' class='back-btn'>Submit Another Feedback</a>");
            out.println("    </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("feedback.html");
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
