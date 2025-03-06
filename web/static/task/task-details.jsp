<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Task Details</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background: #f0f2f5;
        color: #1c1e21;
      }

      .container {
        max-width: 800px;
        margin: 40px auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #e4e6eb;
      }

      .task-header {
        display: flex;
        align-items: center;
        gap: 15px;
      }

      .task-title {
        font-size: 24px;
        color: #1877f2;
        margin: 0;
      }

      .task-info {
        background: #f7f8fa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 20px;
      }

      .info-row {
        display: flex;
        margin-bottom: 15px;
      }

      .info-label {
        width: 120px;
        font-weight: bold;
        color: #65676b;
      }

      .info-value {
        flex: 1;
      }

      .description {
        background: #f7f8fa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 20px;
        white-space: pre-line;
      }

      .btn {
        display: inline-block;
        padding: 10px 20px;
        border-radius: 6px;
        text-decoration: none;
        font-weight: 600;
        cursor: pointer;
        border: none;
        transition: background-color 0.3s;
      }

      .btn-primary {
        background: #1877f2;
        color: white;
      }

      .btn-primary:hover {
        background: #166fe5;
      }

      .btn-secondary {
        background: #e4e6eb;
        color: #050505;
      }

      .btn-secondary:hover {
        background: #d8dadf;
      }

      .actions {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 30px;
      }

      .left-actions {
        display: flex;
        gap: 10px;
      }

      .status-badge {
        display: inline-block;
        padding: 6px 12px;
        border-radius: 20px;
        font-weight: 600;
        font-size: 14px;
      }

      .status-PENDING {
        background: #fff3cd;
        color: #856404;
      }

      .status-COMPLETED {
        background: #d4edda;
        color: #155724;
      }

      .status-IN_PROGRESS {
        background: #cce5ff;
        color: #004085;
      }

      h3 {
        margin-top: 30px;
        color: #1c1e21;
      }

      .btn-danger {
        background: #dc3545;
        color: white;
        margin-left: 10px;
      }

      .btn-danger:hover {
        background: #c82333;
      }

      .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
      }

      .modal-content {
        background-color: white;
        margin: 15% auto;
        padding: 20px;
        border-radius: 8px;
        width: 400px;
        text-align: center;
      }

      .modal-buttons {
        display: flex;
        justify-content: center;
        gap: 10px;
        margin-top: 20px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <div class="task-header">
          <span class="status-badge status-${task.status}">${task.status}</span>
          <h1 class="task-title">${task.taskName}</h1>
        </div>
        <a
          href="${pageContext.request.contextPath}/project/view?id=${project.encodedProjectId}"
          class="btn btn-secondary"
          >Back to Project</a
        >
      </div>

      <div class="task-info">
        <div class="info-row">
          <div class="info-label">Project:</div>
          <div class="info-value">${task.projectName}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Due Date:</div>
          <div class="info-value">${task.dueDate}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Created:</div>
          <div class="info-value">${task.createdAt}</div>
        </div>
        <div class="info-row">
          <div class="info-label">Last Updated:</div>
          <div class="info-value">${task.updatedAt}</div>
        </div>
      </div>

      <h3>Description</h3>
      <div class="description">${task.description}</div>

      <c:if test="${sessionScope.userRole == 'MANAGER'}">
        <div class="left-actions">
          <a
            href="${pageContext.request.contextPath}/task/update?id=${task.encodedTaskId}"
            class="btn btn-primary"
            >Edit Task</a
          >
          <button onclick="showDeleteModal()" class="btn btn-danger">
            Delete Task
          </button>
        </div>
      </c:if>
    </div>

    <!-- Delete Confirmation Modal -->
    <div id="deleteModal" class="modal" style="display: none">
      <div class="modal-content">
        <h3>Delete Task</h3>
        <p>Are you sure you want to delete this task?</p>
        <div class="modal-buttons">
          <button class="btn btn-secondary" onclick="closeDeleteModal()">
            Cancel
          </button>
          <button class="btn btn-danger" onclick="confirmDelete()">
            Delete
          </button>
        </div>
      </div>
    </div>

    <script>
      function showDeleteModal() {
        document.getElementById("deleteModal").style.display = "block";
      }

      function closeDeleteModal() {
        document.getElementById("deleteModal").style.display = "none";
      }

      function confirmDelete() {
        // Tạo form để submit
        const form = document.createElement("form");
        form.method = "POST";
        form.action = "${pageContext.request.contextPath}/task/delete";

        // Thêm taskId
        const taskIdInput = document.createElement("input");
        taskIdInput.type = "hidden";
        taskIdInput.name = "taskId";
        taskIdInput.value = "${task.encodedTaskId}";
        form.appendChild(taskIdInput);

        // Add encoded project ID for redirect
        const projectIdInput = document.createElement("input");
        projectIdInput.type = "hidden";
        projectIdInput.name = "projectId";
        projectIdInput.value = "${project.encodedProjectId}";
        form.appendChild(projectIdInput);

        document.body.appendChild(form);
        form.submit();
      }
    </script>
  </body>
</html>
