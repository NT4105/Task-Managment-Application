<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

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

      .project-card {
        border: 1px solid #ddd;
        padding: 15px;
        margin-bottom: 15px;
        border-radius: 5px;
      }

      .progress-bar {
        background: #eee;
        height: 20px;
        border-radius: 10px;
        margin: 10px 0;
      }

      .progress {
        background: #4caf50;
        height: 100%;
        border-radius: 10px;
        color: white;
        text-align: center;
        transition: width 0.3s ease;
      }

      .project-stats {
        display: flex;
        justify-content: space-between;
        margin: 10px 0;
        color: #666;
      }

      .project-actions {
        display: flex;
        gap: 10px;
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
      <div class="project-list">
        <c:forEach var="project" items="${projects}">
          <div class="project-card">
            <h3>${project.projectName}</h3>
            <p>${project.description}</p>

            <div class="progress-bar">
              <div
                class="progress"
                style="width: '${project.completionPercentage}%'"
              >
                <span
                  >${String.format("%.1f", project.completionPercentage)}%</span
                >
              </div>
            </div>

            <div class="project-stats">
              <span
                >Tasks: ${project.completedTasks}/${project.totalTasks}</span
              >
              <span>Due: ${project.endDate}</span>
            </div>

            <div class="project-actions">
              <a href="project-details?id=${project.projectID}" class="btn"
                >View Details</a
              >
              <a href="edit-project?id=${project.projectID}" class="btn"
                >Edit</a
              >
            </div>
          </div>
        </c:forEach>
      </div>

      <h2>Recent Tasks</h2>
      <!-- Add task list here -->
    </div>
  </body>
</html>
