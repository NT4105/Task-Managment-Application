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
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
      }

      .container {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 400px;
      }

      .form-group {
        margin-bottom: 15px;
      }

      .form-group label {
        display: block;
        margin-bottom: 5px;
      }

      .form-group input {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
      }

      .btn {
        background-color: #1877f2;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
      }

      .error {
        color: red;
        margin-bottom: 10px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Reset Password</h2>

      <c:if test="${not empty error}">
        <div class="error">${error}</div>
      </c:if>

      <form
        method="post"
        action="forgot-password"
        onsubmit="return validateForm()"
      >
        <input type="hidden" name="step" value="reset" />
        <input type="hidden" name="email" value="${email}" />

        <div class="form-group">
          <label for="newPassword">New Password:</label>
          <input type="password" id="newPassword" name="newPassword" required />
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

      <p style="text-align: center; margin-top: 15px">
        <a href="auth/login">Back to Login</a>
      </p>
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
