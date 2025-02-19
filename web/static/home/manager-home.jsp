<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Manager Dashboard</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f0f2f5;
      }

      .dashboard-container {
        max-width: 1200px;
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

      .btn {
        background-color: #4267b2;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-decoration: none;
      }

      .btn-danger {
        background-color: #dc3545;
      }
    </style>
  </head>
  <body>
    <div class="dashboard-container">
      <div class="header">
        <h1>Welcome, ${user.firstName} ${user.lastName}</h1>
        <div>
          <a href="create-project" class="btn">Create New Project</a>
          <a href="logout" class="btn btn-danger">Logout</a>
        </div>
      </div>

      <h2>Your Projects</h2>
      <!-- Add project list here -->

      <h2>Recent Tasks</h2>
      <!-- Add task list here -->
    </div>
  </body>
</html>
