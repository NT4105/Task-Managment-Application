<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Forgot Password</title>
    <style>
      .login-container {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
      }
    </style>
  </head>
  <body>
    <div class="login-container">
      <h2>Forgot Password</h2>
      <p>Enter your email address to reset your password.</p>

      <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
      </c:if>

      <form method="post" action="auth/forgot-password">
        <input type="hidden" name="step" value="verify" />
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />
        <br />
        <button type="submit">Continue</button>
      </form>

      <p><a href="auth/login">Back to Login</a></p>
    </div>
  </body>
</html>
