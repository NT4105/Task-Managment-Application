<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Reset Password</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f5f5f5;
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
      }

      .container {
        max-width: 400px;
        margin: 0 auto;
        padding: 40px;
        background: white;
        border-radius: 12px;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        text-align: center;
      }

      h2 {
        color: #4267b2;
        margin-bottom: 30px;
        text-transform: uppercase;
        letter-spacing: 1px;
      }

      .form-group {
        margin-bottom: 20px;
        text-align: left;
      }

      input[type="password"] {
        width: 100%;
        padding: 12px;
        border: 2px solid #e1e1e1;
        border-radius: 6px;
        box-sizing: border-box;
        transition: border-color 0.3s ease;
      }

      input[type="password"]:focus {
        border-color: #4267b2;
        outline: none;
      }

      button {
        background: #4267b2;
        color: white;
        padding: 12px 24px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        width: 100%;
        font-size: 16px;
        font-weight: 600;
        transition: background-color 0.3s ease;
      }

      button:hover {
        background-color: #365899;
      }

      .success-message {
        color: #28a745;
        background-color: #d4edda;
        border: 1px solid #c3e6cb;
        padding: 12px;
        border-radius: 6px;
        margin-bottom: 20px;
      }

      .error-message {
        color: #dc3545;
        background-color: #f8d7da;
        border: 1px solid #f5c6cb;
        padding: 12px;
        border-radius: 6px;
        margin-bottom: 20px;
      }

      .back-to-login {
        margin-top: 30px;
        text-align: center;
      }

      .back-to-login a {
        color: #1877f2;
        text-decoration: none;
        font-weight: 600;
      }

      .back-to-login a:hover {
        text-decoration: underline;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Reset Password</h2>

      <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
      </c:if>

      <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
      </c:if>

      <c:if test="${empty success}">
        <form
          method="post"
          action="${pageContext.request.contextPath}/auth/forgot-password"
          onsubmit="return validateForm()"
        >
          <input type="hidden" name="step" value="reset" />

          <div class="form-group">
            <label for="newPassword">New Password:</label>
            <input
              type="password"
              id="newPassword"
              name="newPassword"
              required
            />
          </div>

          <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              required
            />
          </div>

          <button type="submit" class="btn">Reset Password</button>
        </form>
      </c:if>

      <div class="back-to-login">
        <a href="${pageContext.request.contextPath}/auth/login"
          >Return to Login</a
        >
      </div>
    </div>

    <script>
      function validateForm() {
        var newPassword = document.getElementById("newPassword").value;
        var confirmPassword = document.getElementById("confirmPassword").value;

        if (newPassword.length < 8) {
          alert("Password must be at least 8 characters long!");
          return false;
        }

        if (newPassword !== confirmPassword) {
          alert("Passwords do not match!");
          return false;
        }

        return true;
      }
    </script>
  </body>
</html>
