<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ page import="utils.SecurityUtil"
%> <%@ page import="model.Project" %> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://your.domain.com/security" %>

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
        width: 100%;
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

      .profile-actions {
        display: flex;
        gap: 10px;
        margin-left: auto;
      }

      .search-container {
        display: flex;
        justify-content: flex-end;
        margin: 20px 0;
      }

      .search-box {
        display: flex;
        gap: 15px;
        align-items: center;
      }

      .search-input {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        width: 300px;
        font-size: 14px;
      }

      .search-select {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        min-width: 150px;
        font-size: 14px;
        height: 40px;
      }

      .search-btn {
        padding: 8px 20px;
        height: 35px;
      }

      .btn-secondary {
        background-color: #6c757d;
        color: white;
      }

      .btn-secondary:hover {
        background-color: #5a6268;
      }

      .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.4);
      }

      .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 400px;
        border-radius: 5px;
      }

      .modal-buttons {
        margin-top: 20px;
        text-align: right;
      }

      .modal-buttons button {
        margin-left: 10px;
        padding: 8px 16px;
        border-radius: 4px;
        cursor: pointer;
      }

      .btn-confirm {
        background-color: #dc3545;
        color: white;
        border: none;
      }

      .btn-cancel {
        background-color: #6c757d;
        color: white;
        border: none;
      }

      .project-meta {
        margin-top: 10px;
        display: flex;
        flex-direction: column;
        gap: 5px;
      }

      .project-meta div {
        color: #666;
        font-size: 14px;
      }

      .project-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
      }

      .project-header h2 {
        margin: 0;
      }

      .btn-primary {
        background-color: #4267b2;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        text-decoration: none;
        display: inline-block;
      }

      .btn-primary:hover {
        background-color: #365899;
      }

      .alert {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 1000;
        min-width: 300px;
      }
      
      .alert-success {
        color: #155724;
        background-color: #d4edda;
        border-color: #c3e6cb;
      }

      .alert-error {
        color: #721c24;
        background-color: #f8d7da;
        border-color: #f5c6cb;
      }

      .project-stats {
        margin-top: 20px;
        padding: 15px;
        background-color: #f8f9fa;
        border-radius: 8px;
      }
    </style>
  </head>
  <body>
    <c:if test="${not empty sessionScope.successMessage}">
        <div id="successAlert" class="alert alert-success">
            ${sessionScope.successMessage}
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div id="errorAlert" class="alert alert-error">
            ${sessionScope.errorMessage}
        </div>
    </c:if>

    <div class="dashboard-container">
      <c:if test="${not empty sessionScope.successMessage}">
        <div id="successAlert" class="alert alert-success">
            ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
      </c:if>

      <div class="header">
        <h1>Welcome, ${sessionScope.firstName} ${sessionScope.lastName}</h1>
        <div class="profile-actions">

          <a href="${pageContext.request.contextPath}/profile" class="btn"
            >Your Profile</a
          >
          <a
            href="${pageContext.request.contextPath}/auth/logout"
            class="btn btn-danger"
            >Logout</a
          >
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

      <!-- Add search and filter form at the top -->
      <div class="search-container">
        <form id="searchForm" action="${pageContext.request.contextPath}/home" method="GET">
            <div class="search-box">
                <input type="text" 
                       name="searchQuery" 
                       value="${param.searchQuery}" 
                       placeholder="Search projects..."
                       class="search-input">
                <select name="status" class="search-select" onchange="this.form.submit()">
                    <option value="">All Status</option>
                    <option value="active" ${param.status == 'active' ? 'selected' : ''}>In Progress</option>
                    <option value="completed" ${param.status == 'completed' ? 'selected' : ''}>Completed</option>
                </select>
            </div>
        </form>
      </div>

      <!-- Project List Section -->
      <div class="project-list">
        <div class="project-header">
          <h2>My Projects</h2>
          <a href="${pageContext.request.contextPath}/project/create" class="btn btn-primary">Create New Project</a>
        </div>

        <!-- Projects Grid -->
        <div class="projects-grid">
          <c:forEach var="project" items="${projects}">
            <div class="project-card">
              <h3>${project.projectName}</h3>

              <div class="progress-bar">
                <div
                  class="progress"
                  style="width: ${project.totalTasks == 0 ? 0 : Math.round((project.completedTasks * 100.0 / project.totalTasks))}%"
                ></div>
              </div>
              <span
                >${project.totalTasks == 0 ? 0 :
                Math.round((project.completedTasks * 100.0 /
                project.totalTasks))}% Complete</span
              >
              <div class="project-meta">
                <div>Due: ${project.endDate}</div>
                <div>Tasks: ${project.totalTasks}</div>
              </div>
              <div class="project-actions">
                <a href="${pageContext.request.contextPath}/project/view?id=${project.encodedProjectId}" 
                   class="btn btn-primary">View</a>
                
                <c:if test="${sessionScope.userRole == 'MANAGER'}">
                    <button class="btn btn-danger delete-project-btn" 
                            onclick="deleteProject('${project.encodedProjectId}', '${fn:escapeXml(project.projectName)}')" 
                            data-project-name="${project.projectName}" 
                            data-tasks-count="${project.totalTasks}">Delete</button>
                </c:if>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>

    <!-- Thêm modal HTML -->
    <div id="deleteModal" class="modal">
        <div class="modal-content">
            <h3>Delete Project</h3>
            <p id="deleteMessage"></p>
            <div class="modal-buttons">
                <button class="btn-cancel" onclick="closeDeleteModal()">Cancel</button>
                <button class="btn-confirm" onclick="confirmDelete()">Delete</button>
            </div>
        </div>
    </div>

    <script>
      document.addEventListener('DOMContentLoaded', function() {
          // Submit form when search input changes (with debounce)
          let timeout = null;
          document.querySelector('.search-input').addEventListener('input', function() {
              clearTimeout(timeout);
              timeout = setTimeout(() => {
                  document.getElementById('searchForm').submit();
              }, 500);
          });

          // Auto-hide success message
          let successAlert = document.getElementById('successAlert');
          let errorAlert = document.getElementById('errorAlert');
          
          if (successAlert) {
              setTimeout(function() {
                  successAlert.style.display = 'none';
                  // Clear the session message
                  <% session.removeAttribute("successMessage"); %>
              }, 2000);
          }
          
          if (errorAlert) {
              setTimeout(function() {
                  errorAlert.style.display = 'none';
                  // Clear the session message
                  <% session.removeAttribute("errorMessage"); %>
              }, 2000);
          }
      });

      let projectToDelete = null;

      function deleteProject(projectId) {
        projectToDelete = projectId;
        document.getElementById('deleteMessage').textContent = 'Are you sure you want to delete this project?';
        document.getElementById('deleteModal').style.display = 'block';
      }

      function confirmDelete() {
        fetch('project/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'projectId=' + projectToDelete
        })
        .then(response => {
            if (response.ok) {
                // Reload trang để hiển thị message
                window.location.reload();
            } else {
                // Reload trang để hiển thị error message
                window.location.reload();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            window.location.reload();
        });
        
        closeDeleteModal();
      }

      function closeDeleteModal() {
        document.getElementById('deleteModal').style.display = 'none';
      }

      // Đóng modal khi click bên ngoài
      window.onclick = function(event) {
        let modal = document.getElementById('deleteModal');
        if (event.target == modal) {
          closeDeleteModal();
        }
      }
    </script>
  </body>
</html>
