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
      }

      .container {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        background: white;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      }

      .form-group {
        margin-bottom: 15px;
      }

      input[type="email"] {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
      }

      button {
        background: #4caf50;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Forgot Password</h2>
      <p>Enter your email address to reset your password.</p>

      <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
      </c:if>

      <form
        action="${pageContext.request.contextPath}/auth/forgot-password"
        method="POST"
      >
        <input type="hidden" name="step" value="verify" />
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />
        <br />
        <button type="submit">Continue</button>
      </form>

      <p>
        <a href="${pageContext.request.contextPath}/auth/login"
          >Back to Login</a
        >
      </p>
    </div>
  </body>
</html>
