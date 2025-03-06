<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="security"
uri="http://your.domain.com/security" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${project.projectName} - Project Details</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f0f2f5;
      }

      .container {
        max-width: 1200px;
        margin: 0 auto;
        background-color: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #eee;
      }

      .project-header {
        flex-grow: 1;
      }

      .project-title {
        font-size: 24px;
        margin: 0;
        color: #1a1a1a;
      }

      .project-info {
        background-color: #f8f9fa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 30px;
      }

      .info-row {
        display: flex;
        margin-bottom: 15px;
        align-items: flex-start;
      }

      .info-label {
        width: 120px;
        font-weight: bold;
        color: #666;
        padding-top: 3px;
      }

      .info-value {
        flex-grow: 1;
        color: #333;
      }

      .description-value {
        flex-grow: 1;
        color: #333;
        white-space: pre-line;
        word-wrap: break-word;
        max-width: calc(100% - 120px);
        line-height: 1.3;
        padding-left: 20px;
      }

      .description-value p {
        margin: 0 0 4px 0;
      }

      .description-value .list-item {
        display: block;
        padding-left: 20px;
        position: relative;
        margin-bottom: 2px;
      }

      .description-value .list-item::before {
        content: "";
        position: absolute;
        left: 0;
        top: 10px;
        width: 4px;
        height: 4px;
        background-color: #666;
        border-radius: 50%;
      }

      .progress-section {
        margin-bottom: 30px;
      }

      .progress-bar {
        background: #e9ecef;
        height: 20px;
        border-radius: 10px;
        margin: 10px 0;
        overflow: hidden;
      }

      .progress {
        background: #4caf50;
        height: 100%;
        border-radius: 10px;
        transition: width 0.3s ease;
      }

      .tasks-section {
        margin-top: 30px;
      }

      .task-list {
        margin-top: 20px;
      }

      .task-item {
        padding: 15px;
        background: #f8f9fa;
        border: 1px solid #e9ecef;
        margin-bottom: 10px;
        border-radius: 8px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
      }

      .task-item:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      }

      .task-info {
        flex-grow: 1;
      }

      .task-name {
        font-weight: bold;
        color: #2c3e50;
        margin-bottom: 5px;
      }

      .task-meta {
        font-size: 0.9em;
        color: #666;
      }

      .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-decoration: none;
        font-weight: 500;
        transition: background-color 0.2s ease;
      }

      .btn-primary {
        background-color: #4267b2;
        color: white;
      }

      .btn-secondary {
        background-color: #6c757d;
        color: white;
      }

      .btn-danger {
        background-color: #dc3545;
        color: white;
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
        animation: slideIn 0.3s ease;
      }

      @keyframes slideIn {
        from {
          transform: translateX(100%);
        }
        to {
          transform: translateX(0);
        }
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

      .status-badge {
        padding: 4px 8px;
        border-radius: 12px;
        font-size: 0.85em;
        font-weight: 500;
      }

      .status-PENDING {
        background-color: #fef3c7;
        color: #92400e;
      }

      .status-IN_PROGRESS {
        background-color: #dbeafe;
        color: #1e40af;
      }

      .status-COMPLETED {
        background-color: #d1fae5;
        color: #065f46;
      }

      .team-members-section {
        margin-top: 30px;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .team-members-list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 15px;
        margin-top: 15px;
      }

      .member-card {
        background: #f8f9fa;
        border-radius: 6px;
        padding: 15px;
        border: 1px solid #e9ecef;
      }

      .member-info {
        display: flex;
        flex-direction: column;
        gap: 5px;
      }

      .member-name {
        font-weight: 500;
        color: #2c3e50;
      }

      .member-joined {
        font-size: 0.85em;
        color: #6c757d;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <div class="project-header">
          <h1 class="project-title">${project.projectName}</h1>
        </div>
        <div>
          <a
            href="${pageContext.request.contextPath}/home"
            class="btn btn-secondary"
            >Back to Dashboard</a
          >
          <c:if test="${sessionScope.userRole == 'MANAGER'}">
            <a
              href="${pageContext.request.contextPath}/project/update?id=${project.encodedProjectId}"
              class="btn btn-primary"
              >Edit Project</a
            >
            <a
              href="${pageContext.request.contextPath}/task/create?projectId=${project.encodedProjectId}"
              class="btn btn-primary"
              >Add Task</a
            >
          </c:if>
        </div>
      </div>

      <div class="project-info">
        <div class="info-row">
          <div class="info-label">Description:</div>
          <div class="description-value">${project.description}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Start Date:</div>
          <div class="info-value">${project.startDate}</div>
        </div>
        <div class="info-row">
          <div class="info-label">End Date:</div>
          <div class="info-value">${project.endDate}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Progress:</div>
          <div class="info-value">
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
          </div>
        </div>
      </div>

      <div class="team-members-section">
        <h3>Team Members</h3>
        <div class="team-members-list">
          <c:forEach items="${project.projectMembers}" var="member">
            <div class="member-card">
              <div class="member-info">
                <span class="member-name"
                  >${member.firstName} ${member.lastName}</span
                >
                <span class="member-joined">Joined: ${member.joinedAt}</span>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>

      <div class="tasks-section">
        <h2>Tasks</h2>
        <div class="task-list">
          <c:forEach var="task" items="${tasks}">
            <div class="task-item">
              <div class="task-info">
                <div class="task-name">${task.taskName}</div>
                <div class="task-meta">
                  <span class="status-badge status-${task.status}"
                    >${task.status}</span
                  >
                  <span>Due: ${task.dueDate}</span>
                </div>
              </div>
              <div>
                <a
                  href="${pageContext.request.contextPath}/task/details?id=${task.encodedTaskId}"
                  class="btn btn-primary"
                  >View</a
                >
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>

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

    <script>
      // Auto-hide alerts after 5 seconds
      setTimeout(function () {
        const alerts = document.getElementsByClassName("alert");
        for (let alert of alerts) {
          alert.style.display = "none";
        }
      }, 5000);
    </script>
  </body>
</html>
