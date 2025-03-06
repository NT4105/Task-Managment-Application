<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="security"
uri="http://your.domain.com/security" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Update Task</title>
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

      h2 {
        color: #1877f2;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #e4e6eb;
      }

      .form-group {
        margin-bottom: 20px;
      }

      .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #050505;
      }

      .form-group input[type="text"],
      .form-group textarea,
      .form-group input[type="date"] {
        width: 100%;
        padding: 12px;
        border: 1px solid #dddfe2;
        border-radius: 6px;
        font-size: 16px;
        color: #1c1e21;
        box-sizing: border-box;
      }

      .form-group textarea {
        height: 120px;
        resize: vertical;
      }

      .message {
        padding: 15px;
        margin-bottom: 20px;
        border-radius: 6px;
      }

      .success-message {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
      }

      .error-message {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
      }

      .btn {
        display: inline-block;
        padding: 12px 24px;
        border-radius: 6px;
        font-size: 16px;
        font-weight: 600;
        text-decoration: none;
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
        margin-right: 10px;
      }

      .btn-secondary:hover {
        background: #d8dadf;
      }

      .button-group {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
      }

      .left-buttons {
        display: flex;
        gap: 10px;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>Update Task</h2>

      <c:if test="${not empty successMessage}">
        <div class="message success-message">${successMessage}</div>
      </c:if>

      <c:if test="${not empty error}">
        <div class="message error-message">${error}</div>
      </c:if>

      <form
        action="${pageContext.request.contextPath}/task/update"
        method="POST"
        id="updateForm"
      >
        <input type="hidden" name="id" value="${task.encodedTaskId}" />

        <div class="form-group">
          <label for="taskName">Task Name</label>
          <input
            type="text"
            id="taskName"
            name="taskName"
            value="${task.taskName}"
            required
          />
        </div>

        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" name="description" required>
${task.description}</textarea
          >
        </div>

        <div class="form-group">
          <label for="dueDate">Due Date</label>
          <input
            type="date"
            id="dueDate"
            name="dueDate"
            value="${task.dueDate}"
            required
          />
        </div>

        <div class="button-group">
          <div class="left-buttons">
            <button
              type="submit"
              class="btn btn-primary"
              onclick="return confirmUpdate()"
            >
              Update Task
            </button>
          </div>
          <a
            href="${pageContext.request.contextPath}/project/view?id=${project.encodedProjectId}"
            class="btn btn-secondary"
            >Back to Project</a
          >
        </div>
      </form>
    </div>

    <script>
      const today = new Date().toISOString().split("T")[0];
      document.getElementById("dueDate").setAttribute("min", today);

      function confirmUpdate() {
        return confirm("Are you sure you want to update this task?");
      }
    </script>
  </body>
</html>
