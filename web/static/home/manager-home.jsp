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
        margin-top: 10px;
      }

      .btn {
        padding: 5px 15px;
        border-radius: 4px;
        cursor: pointer;
        border: none;
      }

      .btn-view {
        background: #4caf50;
        color: white;
      }

      .btn-edit {
        background: #2196f3;
        color: white;
      }

      .btn-delete {
        background: #f44336;
        color: white;
      }

      .dashboard-stats {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-bottom: 30px;
      }

      .stat-card {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .project-list {
        margin-top: 20px;
      }

      .project-filters {
        margin-bottom: 20px;
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

      <!-- Dashboard Stats -->
      <div class="dashboard-stats">
        <div class="stat-card">
          <h3>Total Projects</h3>
          <p>${totalProjects}</p>
        </div>
        <div class="stat-card">
          <h3>Active Projects</h3>
          <p>${activeProjects}</p>
        </div>
        <div class="stat-card">
          <h3>Completed Projects</h3>
          <p>${completedProjects}</p>
        </div>
      </div>

      <!-- Project List Section -->
      <div class="project-list">
        <h2>My Projects</h2>

        <!-- Search and Filter -->
        <div class="project-filters">
          <input
            type="text"
            placeholder="Search projects..."
            id="searchProject"
          />
          <select id="statusFilter">
            <option value="">All Status</option>
            <option value="active">Active</option>
            <option value="completed">Completed</option>
          </select>
          <button
            onclick="window.location.href='${pageContext.request.contextPath}/project/create'"
            class="btn"
          >
            Create New Project
          </button>
        </div>

        <!-- Projects Grid -->
        <div class="projects-grid">
          <c:forEach var="project" items="${projects}">
            <div class="project-card">
              <h3>${project.projectName}</h3>
              <p>${project.description}</p>
              <div class="progress-bar">
                <div
                  class="progress"
                  style="width: ${project.totalTasks == 0 ? 0 : Math.round((project.completedTasks * 100.0 / project.totalTasks))}%"
                ></div>
              </div>
              <span
                >${project.totalTasks == 0 ? 0 : (project.completedTasks * 100 /
                project.totalTasks)}% Complete</span
              >
              <div class="project-meta">
                <span>Due: ${project.endDate}</span>
                <span>${project.totalTasks} Tasks</span>
              </div>
              <div class="project-actions">
                <a
                  href="project/details?id=${project.projectID}"
                  class="btn btn-view"
                  >View</a
                >
                <a
                  href="project/edit?id=${project.projectID}"
                  class="btn btn-edit"
                  >Edit</a
                >
                <button
                  onclick="confirmDelete('${project.projectID}', '${project.projectName}')"
                  class="btn btn-delete"
                >
                  Delete
                </button>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>

    <script>
      // Add search and filter functionality
      document
        .getElementById("searchProject")
        .addEventListener("input", filterProjects);
      document
        .getElementById("statusFilter")
        .addEventListener("change", filterProjects);

      function filterProjects() {
        // Add client-side filtering logic here
      }

      function confirmDelete(projectId, projectName) {
        if (
          confirm(
            `Are you sure you want to delete project "${projectName}"?\nThis will delete all associated tasks.`
          )
        ) {
          fetch("project/delete", {
            method: "POST",
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `projectId=${projectId}`,
          })
            .then((response) => {
              if (response.ok) {
                window.location.reload();
              } else {
                alert("Failed to delete project");
              }
            })
            .catch((error) => {
              console.error("Error:", error);
              alert("An error occurred while deleting the project");
            });
        }
      }
    </script>
  </body>
</html>
