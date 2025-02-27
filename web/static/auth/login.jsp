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
    </style>
  </head>
  <body>
    <div class="login-container">
      <h1>Login</h1>
      <c:if test="${requestScope.error != null}">
        <p style="color: red">${requestScope.error}</p>
      </c:if>
      <form method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />
        <br />
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required />
        <div class="forgot-password">
          <a href="forgot-password">Forgot Password?</a>
        </div>
        <button type="submit">Login</button>
      </form>
      <p>Don't have an account? <a href="sign-up">Sign up</a></p>
    </div>
  </body>
</html>
