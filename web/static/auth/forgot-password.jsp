<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Forgot Password</title>
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

      input[type="email"] {
        width: 100%;
        padding: 12px;
        border: 2px solid #e1e1e1;
        border-radius: 6px;
        box-sizing: border-box;
        transition: border-color 0.3s ease;
      }

      input[type="email"]:focus {
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

      .back-link {
        margin-top: 30px;
      }

      .back-link a {
        color: #1877f2;
        text-decoration: none;
        font-weight: 500;
      }

      .back-link a:focus {
        outline: none;
      }

      .back-link a:hover {
        text-decoration: underline;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Forgot Password</h2>

      <% String step = (String) request.getAttribute("step"); if (step == null
      || step.equals("verify")) { %>
      <!-- Step 1: Email Verification Form -->
      <p>Enter your email address to reset your password.</p>
      <form action="forgot-password" method="POST">
        <input type="hidden" name="step" value="verify" />
        <div class="form-group">
          <input type="email" name="email" required />
        </div>
        <button type="submit" class="btn btn-primary">Continue</button>
      </form>
      <% } else if (step.equals("reset")) { %>
      <!-- Step 2: Password Reset Form -->
      <p>Enter your new password.</p>
      <form action="forgot-password" method="POST">
        <input type="hidden" name="step" value="reset" />
        <div class="form-group">
          <label>New Password</label>
          <input type="password" name="newPassword" required />
        </div>
        <div class="form-group">
          <label>Confirm Password</label>
          <input type="password" name="confirmPassword" required />
        </div>
        <button type="submit" class="btn btn-primary">Reset Password</button>
      </form>
      <% } %> <% if (request.getAttribute("error") != null) { %>
      <div class="error-message"><%= request.getAttribute("error") %></div>
      <% } %>

      <div class="back-link">
        <a href="login">Back to Login</a>
      </div>
    </div>
  </body>
</html>
