<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login - Task Management Application</title>
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
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
      }

      .login-container {
        text-align: center;
        background-color: white;
        padding: 40px;
        border-radius: 12px;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 400px;
      }

      .app-title {
        color: #4267b2;
        font-size: 24px;
        margin-bottom: 30px;
        text-transform: uppercase;
        letter-spacing: 1px;
      }

      .form-group {
        margin-bottom: 20px;
        text-align: left;
      }

      label {
        display: block;
        margin-bottom: 8px;
        color: #666;
        font-weight: 500;
      }

      input {
        width: 100%;
        padding: 12px;
        margin: 8px 0;
        box-sizing: border-box;
        border: 2px solid #e1e1e1;
        border-radius: 6px;
        transition: border-color 0.3s ease;
      }

      input:focus {
        border-color: #4267b2;
        outline: none;
      }

      button {
        background-color: #4267b2;
        color: white;
        padding: 12px 24px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 16px;
        font-weight: 600;
        width: 100%;
        transition: background-color 0.3s ease;
      }

      button:hover {
        background-color: #365899;
      }

      .forgot-password {
        text-align: right;
        margin: 15px 0;
      }

      .forgot-password a {
        color: #1877f2;
        text-decoration: none;
        font-size: 14px;
      }

      .forgot-password a:hover {
        text-decoration: underline;
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

      .signup-link {
        margin-top: 20px;
        color: #666;
      }

      .signup-link a {
        color: #1877f2;
        text-decoration: none;
        font-weight: 600;
      }

      .signup-link a:hover {
        text-decoration: underline;
      }
    </style>
  </head>
  <body>
    <div class="login-container">
      <h1 class="app-title">Task Management Application</h1>
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
        method="post"
        action="${pageContext.request.contextPath}/auth/login"
      >
        <div class="form-group">
          <label for="userName">Username</label>
          <input
            type="text"
            id="userName"
            name="userName"
            required
            placeholder="Enter your username"
          />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            required
            placeholder="Enter your password"
          />
        </div>
        <div class="forgot-password">
          <a href="${pageContext.request.contextPath}/auth/forgot-password"
            >Forgot Password?</a
          >
        </div>
        <button type="submit">Login</button>
      </form>
      <div class="signup-link">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/auth/sign-up">Sign up</a>
      </div>
    </div>
  </body>
</html>
