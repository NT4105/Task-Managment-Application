<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Profile</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f0f2f5;
      }

      .profile-container {
        max-width: 800px;
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
        display: inline-block;
      }

      .btn-danger {
        background-color: #dc3545;
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

      .readonly-field {
        background-color: #e9ecef;
        cursor: not-allowed;
      }
    </style>
  </head>
  <body>
    <div class="profile-container">
      <div class="header">
        <h1>Profile</h1>
        <a href="home" class="btn">Back to Home</a>
      </div>

      <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
      </c:if>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form method="post" action="profile">
        <div class="form-group">
          <label for="userName">Username</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value="${user.userName}"
            class="readonly-field"
            readonly
          />
        </div>

        <div class="form-group">
          <label for="dob">Date of Birth</label>
          <input type="date" id="dob" name="dob" value="${user.dob}" required />
        </div>

        <div class="form-group">
          <label for="firstName">First Name</label>
          <input
            type="text"
            id="firstName"
            name="firstName"
            value="${user.firstName}"
            required
          />
        </div>

        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input
            type="text"
            id="lastName"
            name="lastName"
            value="${user.lastName}"
            required
          />
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value="${user.email}"
            required
          />
        </div>

        <div class="form-group">
          <label for="phone">Phone</label>
          <input
            type="tel"
            id="phone"
            name="phone"
            value="${user.phone}"
            required
          />
        </div>

        <div class="form-group">
          <label for="role">Role</label>
          <input
            type="text"
            id="role"
            value="${user.role.displayValue}"
            class="readonly-field"
            readonly
          />
        </div>

        <button type="submit" class="btn">Update Profile</button>
      </form>

      <div style="margin-top: 20px">
        <a href="change-password" class="btn">Change Password</a>
      </div>
    </div>

    <script>
      // Add client-side validation if needed
      document.querySelector("form").addEventListener("submit", function (e) {
        const phone = document.getElementById("phone").value;
        if (!/^\d{10}$/.test(phone)) {
          e.preventDefault();
          alert("Phone number must be 10 digits");
        }
      });
    </script>
  </body>
</html>
