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
        max-width: 600px;
        margin: 0 auto;
        background-color: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
      }

      .form-group {
        margin-bottom: 20px;
      }

      .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #333;
      }

      .form-group input {
        width: 100%;
        padding: 10px;
        border: 1x solid #ddd;
        border-radius: 6px;
        box-sizing: border-box;
        font-size: 14px;
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

      .btn:hover {
        background-color: #365899;
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

      .button-group {
        margin-top: 20px;
        text-align: right;
      }

      .back-btn {
        display: inline-block;
        padding: 8px 16px;
        background-color: #f0f0f0;
        color: #333;
        text-decoration: none;
        border-radius: 4px;
      }

      .back-btn:hover {
        background-color: #e0e0e0;
      }
    </style>
  </head>
  <body>
    <div class="profile-container">
      <div class="header">
        <h1>Profile</h1>
        <a href="home" class="btn">Back to Dashboard</a>
      </div>

      <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
      </c:if>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form method="post" action="profile" id="profileForm">
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
            pattern="[0-9]{10}"
            title="Phone number must be 10 digits"
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
        <a
          href="${pageContext.request.contextPath}/user/change-password"
          class="btn"
          >Change Password</a
        >
      </div>
    </div>

    <script>
      // Track form changes
      document
        .getElementById("profileForm")
        .addEventListener("change", function () {
          const currentFormData = new FormData(this);
          for (let pair of currentFormData.entries()) {
            const input = document.getElementById(pair[0]);
            if (input && input.defaultValue !== pair[1]) {
              formChanged = true;
              return;
            }
          }
          formChanged = false;
        });

      // Reset formChanged when form submits successfully
      document
        .getElementById("profileForm")
        .addEventListener("submit", function (e) {
          const phone = document.getElementById("phone").value;
          if (!/^[0-9]{10}$/.test(phone)) {
            e.preventDefault();
            alert("Phone number must be exactly 10 digits");
            return;
          }
          formChanged = false;
          const inputs = this.getElementsByTagName("input");
          for (let input of inputs) {
            input.defaultValue = input.value;
          }
        });

      // Only handle beforeunload event for browser's "Leave site" warning
      window.addEventListener("beforeunload", function (e) {
        if (formChanged) {
          e.preventDefault();
          e.returnValue = ""; // Modern browsers will show their own message
          return "";
        }
      });
    </script>
  </body>
</html>
