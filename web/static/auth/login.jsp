<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login Page</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
      }

      .login-container {
        text-align: center;
        background-color: white;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      input {
        width: 100%;
        padding: 10px;
        margin: 8px 0;
        box-sizing: border-box;
      }

      button {
        background-color: #4267b2;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }

      .forgot-password {
        text-align: right;
        margin-bottom: 15px;
      }
      .forgot-password a {
        color: #1877f2;
        text-decoration: none;
      }
      .forgot-password a:hover {
        text-decoration: underline;
      }
      .success-message {
        color: green;
        margin-bottom: 10px;
      }
      .error-message {
        color: red;
        margin-bottom: 10px;
      }
    </style>
  </head>
  <body>
    <div class="login-container">
      <h2>Login</h2>

      <!-- Display success message if exists -->
      <% if (session.getAttribute("success") != null) { %>
      <div class="success-message">
        <%= session.getAttribute("success") %> <%
        session.removeAttribute("success"); %>
      </div>
      <% } %>

      <!-- Display error message if exists -->
      <% if (request.getAttribute("error") != null) { %>
      <div class="error-message"><%= request.getAttribute("error") %></div>
      <% } %>

      <form
        action="${pageContext.request.contextPath}/auth/login"
        method="post"
      >
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" name="username" required />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required />
        </div>
        <div class="form-links">
          <a href="${pageContext.request.contextPath}/auth/forgot-password"
            >Forgot Password?</a
          >
        </div>
        <button type="submit">Login</button>
      </form>
      <p>
        Don't have an account?
        <a href="${pageContext.request.contextPath}/auth/sign-up">Sign up</a>
      </p>
    </div>
  </body>
</html>
