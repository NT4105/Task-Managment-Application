<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Logout</title>
  </head>
  <body>
    <h1>Logging out...</h1>
    <script>
      setTimeout(function () {
        window.location.href = "auth/login";
      }, 3000);
    </script>
  </body>
</html>
