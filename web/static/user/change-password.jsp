<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Change Password</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f0f2f5;
      }

      .container {
        max-width: 500px;
        margin: 0 auto;
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
      }

      .form-group {
        margin-bottom: 15px;
      }

      .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
      }

      .form-group input {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
      }

      .btn {
        background-color: #4267b2;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-decoration: none;
      }

      .alert {
        padding: 10px;
        margin-bottom: 15px;
        border-radius: 4px;
      }

      .alert-success {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
      }

      .alert-danger {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h1>Change Password</h1>
        <a href="profile" class="btn">Back to Profile</a>
      </div>

      <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
      </c:if>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form method="post" action="change-password" id="passwordForm">
        <div class="form-group">
          <label for="currentPassword">Current Password</label>
          <input
            type="password"
            id="currentPassword"
            name="currentPassword"
            required
          />
        </div>

        <div class="form-group">
          <label for="newPassword">New Password</label>
          <input type="password" id="newPassword" name="newPassword" required />
        </div>

        <div class="form-group">
          <label for="confirmPassword">Confirm New Password</label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            required
          />
        </div>

        <button type="submit" class="btn">Change Password</button>
      </form>
    </div>

    <script>
      document
        .getElementById("passwordForm")
        .addEventListener("submit", function (e) {
          const newPassword = document.getElementById("newPassword").value;
          const confirmPassword =
            document.getElementById("confirmPassword").value;

          if (newPassword !== confirmPassword) {
            e.preventDefault();
            alert("New passwords do not match!");
          }

          if (newPassword.length < 6) {
            e.preventDefault();
            alert("Password must be at least 6 characters long!");
          }
        });
    </script>
  </body>
</html>
